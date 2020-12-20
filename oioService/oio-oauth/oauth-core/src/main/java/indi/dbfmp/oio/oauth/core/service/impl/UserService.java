package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.dto.webDto.ResetPwdDto;
import indi.dbfmp.oio.oauth.core.dto.webDto.UserInfoDto;
import indi.dbfmp.oio.oauth.core.entity.UserPermission;
import indi.dbfmp.oio.oauth.core.entity.UserRole;
import indi.dbfmp.oio.oauth.core.entity.Users;
import indi.dbfmp.oio.oauth.core.innerService.IUserPermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserRoleInnerService;
import indi.dbfmp.oio.oauth.core.interceptor.UserInfoContext;
import inid.dbfmp.oauth.api.dto.PayloadDto;
import inid.dbfmp.oauth.api.exception.CommonException;
import indi.dbfmp.oio.oauth.core.innerService.IUsersInnerService;
import inid.dbfmp.oauth.api.enums.StatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  用户服务
 * </p>
 *
 * @author dbfmp
 * @name: UserService
 * @since 2020/11/28 下午5:31
 */
@Slf4j
@Service
public class UserService {

    @Value("${oio.defaultPassword}")
    private String defaultPassword;
    @Autowired
    private IUsersInnerService usersInnerService;
    @Autowired
    private IUserRoleInnerService userRoleInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;

    /**
     * 使用默认密码设置新密码
     *
     * todo 密码加密
     * @param resetPwdDto dto
     */
    public void setNewPwdByDefaultPwd(ResetPwdDto resetPwdDto) {
        if (!defaultPassword.equals(resetPwdDto.getOldPwd())) {
            throw new CommonException("默认密码不正确！");
        }
        //检查用户合法性
        //查询用户
        Users queryUser = usersInnerService.getOne(new LambdaQueryWrapper<Users>()
                        .select(Users::getLoginFlag, Users::getUserId,Users::getDefaultPwd,Users::getId)
                        .eq(Users::getPhone, resetPwdDto.getUserName())
                );
        //todo 使用加密的密码
        if (null == queryUser) {
            throw new CommonException("用户不存在！");
        }
        queryUser.setPassword(resetPwdDto.getNewPwd());
        queryUser.setDefaultPwd(StatusEnums.UN_VALID.getCode());
        if (!usersInnerService.updateById(queryUser)) {
            throw new CommonException("更新密码失败！");
        }
    }

    public Users registerUser(Users users, boolean isDefaultPwd) {
        if (isDefaultPwd) {
            users.setPassword(defaultPassword);
            users.setDefaultPwd(1);
        } else {
            users.setDefaultPwd(0);
        }
        //密码加密
        users.setPassword(users.getPassword());
        users.setUserId(IdUtil.objectId());
        usersInnerService.save(users);
        return users;
    }

    public UserInfoDto getUserInfo() {
        PayloadDto userContext = UserInfoContext.getUserInfo();
        if (null == userContext) {
            return new UserInfoDto();
        }
        Users queryUser = usersInnerService.getOne(new LambdaQueryWrapper<Users>().eq(Users::getUserId,userContext.getUserId()));
        if (null == queryUser) {
            return new UserInfoDto();
        }
        //查询角色
        List<UserRole> userRoleList = userRoleInnerService.list(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getOrgId,userContext.getOrgId())
                .eq(UserRole::getUserId,userContext.getUserId()));
        List<UserPermission> userPermissionList = userPermissionInnerService.list(new LambdaQueryWrapper<UserPermission>()
                .eq(UserPermission::getOrgId,userContext.getOrgId())
                .eq(UserPermission::getUserId,userContext.getUserId()));
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(userContext.getUserId())
                .name(queryUser.getNickName())
                .build();
        if (CollectionUtil.isNotEmpty(userRoleList)) {
            List<UserInfoDto.UserRoleDto> userRoleDtoList = new ArrayList<>();
            userRoleList.forEach(userRole -> {
                UserInfoDto.UserRoleDto userRoleDto = new UserInfoDto.UserRoleDto();
                BeanUtil.copyProperties(userRole,userRoleDto);
                userRoleDtoList.add(userRoleDto);
            });
            userInfoDto.setUserRoleList(userRoleDtoList);
        }
        if (CollectionUtil.isNotEmpty(userPermissionList)) {
            List<UserInfoDto.UserPermissionDto> userPermissionDtoList = new ArrayList<>();
            userPermissionList.forEach(userPermission -> {
                UserInfoDto.UserPermissionDto userPermissionDto = new UserInfoDto.UserPermissionDto();
                BeanUtil.copyProperties(userPermission,userPermissionDto);
                userPermissionDtoList.add(userPermissionDto);
            });
            userInfoDto.setUserPermissionList(userPermissionDtoList);
        }
        return userInfoDto;
    }

}
