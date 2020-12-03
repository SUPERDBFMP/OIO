package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.dto.webDto.ResetPwdDto;
import indi.dbfmp.oio.oauth.core.entity.Users;
import inid.dbfmp.oauth.api.exception.CommonException;
import indi.dbfmp.oio.oauth.core.innerService.IUsersInnerService;
import inid.dbfmp.oauth.api.enums.StatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

}
