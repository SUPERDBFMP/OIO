package indi.dbfmp.oio.oauth.core.service.dubboServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.Client;
import indi.dbfmp.oio.oauth.core.entity.Groups;
import indi.dbfmp.oio.oauth.core.entity.Org;
import indi.dbfmp.oio.oauth.core.entity.Users;
import indi.dbfmp.oio.oauth.core.innerService.IClientInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IGroupsInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IOrgInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUsersInnerService;
import indi.dbfmp.oio.oauth.core.service.impl.GroupUserService;
import indi.dbfmp.oio.oauth.core.service.impl.UserService;
import indi.dbfmp.validator.core.annotation.ValidateBefore;
import inid.dbfmp.oauth.api.dto.RegisterUserReq;
import inid.dbfmp.oauth.api.dto.RegisterUserResp;
import inid.dbfmp.oauth.api.enums.StatusEnums;
import inid.dbfmp.oauth.api.exception.CommonException;
import inid.dbfmp.oauth.api.service.RegisterUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  注册用户服务
 * </p>
 *
 * @author dbfmp
 * @name: RegisterUserServiceImpl
 * @since 2020/12/3 下午9:24
 */
@Slf4j
@Component
@Service(group = "${spring.profiles.active}")
public class RegisterUserServiceImpl implements RegisterUserService {

    @Autowired
    private IClientInnerService clientInnerService;
    @Autowired
    private IGroupsInnerService groupsInnerService;
    @Autowired
    private IOrgInnerService orgInnerService;
    @Autowired
    private IUsersInnerService usersInnerService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupUserService groupUserService;

    /**
     * 注册新用户
     *
     * @param req 注册请求
     * @return 注册响应
     */
    @Override
    @ValidateBefore
    public RegisterUserResp registerUser(RegisterUserReq req) {
        log.info("registerUser:{}",req);
        //检查appId
        Client queryClient = clientInnerService.getOne(new LambdaQueryWrapper<Client>().eq(Client::getClientId, req.getAppId()));
        if (StatusEnums.UN_VALID.getCode() == queryClient.getAccessFlag()) {
            throw new CommonException("应用被封禁，无法注册！");
        }
        //检查org
        Org queryOrg = orgInnerService.getOne(new LambdaQueryWrapper<Org>().eq(Org::getId, queryClient.getOrgId()));
        if (null == queryOrg) {
            log.warn("无效的组织机构");
            throw new CommonException("无效的组织机构！");
        }
        //检查group
        Groups queryGroup = groupsInnerService.getOne(new LambdaQueryWrapper<Groups>().eq(Groups::getOrgId, queryOrg.getId()).eq(Groups::getId, req.getGroupId()));
        if (null == queryGroup) {
            throw new CommonException("无效的分组！");
        }
        //检查是否存在用户
        Users queryUser = usersInnerService.getOne(new LambdaQueryWrapper<Users>().eq(Users::getPhone, req.getPhone()));
        if (null != queryUser) {
            throw new CommonException("用户" + req.getPhone() + "已注册！");
        }
        //注册用户
        Users rUsers = userService.registerUser(Users.builder()
                .phone(req.getPhone())
                .email(req.getEmail())
                .defaultPwd(req.getDefaultPwd())
                .password(req.getPassword())
                .build(),1 == req.getDefaultPwd());
        //授予权限
        if (groupUserService.saveUserToNewGroup(rUsers.getUserId(), queryGroup.getId())) {
            return RegisterUserResp.builder()
                    .phone(rUsers.getPhone())
                    .userId(rUsers.getUserId())
                    .build();
        }
        throw new CommonException("注册用户失败！");
    }
}
