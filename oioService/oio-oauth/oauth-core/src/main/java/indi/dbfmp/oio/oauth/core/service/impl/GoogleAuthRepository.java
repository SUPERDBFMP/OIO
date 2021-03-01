package indi.dbfmp.oio.oauth.core.service.impl;

import java.util.List;
import java.util.Optional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.warrenstrange.googleauth.ICredentialRepository;

import org.springframework.stereotype.Service;

import indi.dbfmp.oio.oauth.core.entity.Users;
import indi.dbfmp.oio.oauth.core.innerService.IUsersInnerService;
import indi.dbfmp.oio.oauth.core.innerService.impl.UsersInnerServiceImpl;
import inid.dbfmp.oauth.api.enums.StatusEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 谷歌验证器存储实现
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleAuthRepository implements ICredentialRepository {

    private final IUsersInnerService usersInnerService;

    /**
     * 获取验证秘钥
     */
    @Override
    public String getSecretKey(String userName) {
        log.info("获取用户秘钥：{}",userName);
        //用户名形式：userName-手机号
        String[] sp = userName.split("-");
        String phone = sp[1];
        return Optional.ofNullable(usersInnerService.getOne(new LambdaQueryWrapper<Users>().eq(Users::getPhone, phone))).map(Users::getAuthKey).orElse(null);
    }

    /**
     * 保存两步认证凭证
     */
    @Override
    public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {
        log.info("保存用户两步认证信息,userName:{};secretKey:{}",userName,secretKey);
        //用户名形式：userName-手机号
        String[] sp = userName.split("-");
        String phone = sp[1];
        Users updateUsers = Users.builder()
            .phone(phone)
            .openTowStepAuth(StatusEnums.VALID.getCode())
            .authKey(secretKey)
        .build();
        usersInnerService.update(updateUsers, new LambdaQueryWrapper<Users>().eq(Users::getPhone, phone));
    }
    
}
