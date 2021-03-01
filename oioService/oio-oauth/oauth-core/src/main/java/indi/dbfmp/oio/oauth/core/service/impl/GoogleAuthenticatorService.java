package indi.dbfmp.oio.oauth.core.service.impl;

import java.util.Optional;

import javax.annotation.PostConstruct;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;
import indi.dbfmp.oio.oauth.core.entity.Users;
import indi.dbfmp.oio.oauth.core.innerService.IUsersInnerService;
import indi.dbfmp.oio.oauth.core.interceptor.UserInfoContext;
import inid.dbfmp.oauth.api.dto.PayloadDto;
import inid.dbfmp.oauth.api.enums.StatusEnums;
import inid.dbfmp.oauth.api.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 谷歌验证器服务类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleAuthenticatorService {

    private GoogleAuthenticator gAuth;

    private final GoogleAuthRepository googleAuthRepository;

    private final IUsersInnerService usersInnerService;

    @PostConstruct
    public void initAuthConfig(){
        GoogleAuthenticatorConfig config = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder().setWindowSize(
                10).build();
        gAuth = new GoogleAuthenticator(config);
        gAuth.setCredentialRepository(googleAuthRepository);
        log.info("谷歌验证器服务初始化完成！");
    }
    
    /**
     * 为用户开启两步认证
     * @return 两步认证url
     */
    public String register() {
        PayloadDto userContext = UserInfoContext.getUserInfo();
        if (null == userContext) {
            return null;
        }
        Users queryUsers = usersInnerService.getById(userContext.getUserId());
        if(null == queryUsers){
            throw new CommonException("用户不存在");
        }
        if(StatusEnums.VALID.getCode() == queryUsers.getOpenTowStepAuth()){
            if(StrUtil.isBlank(queryUsers.getAuthUrl())){
                throw new CommonException("用户两步认证状态不正确！请联系管理员");
            }
            return queryUsers.getAuthUrl();
        }
        String userName = userContext.getUserName()+"-"+queryUsers.getPhone();
        GoogleAuthenticatorKey key = gAuth.createCredentials(userName);
        String url = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("OIO统一认证", userName, key);
        Users updateUsers = Users.builder()
            .id(userContext.getUserId())
            .openTowStepAuth(StatusEnums.VALID.getCode())
            .authUrl(url)
        .build();
        usersInnerService.updateById(updateUsers);
        log.info("更新用户两步认证码成功！{}",updateUsers);
        return url;
    }

    /**
     * 检查用户是否开启两步认证
     * @param userId 用户id，为空时从上下文中获取
     * @return
     */
    public boolean checkOpenTowStepAuth(String userId){
        if(StrUtil.isBlank(userId)){
            userId = Optional.ofNullable(UserInfoContext.getUserInfo())
            .map(PayloadDto::getUserId)
            .orElseThrow(()->new CommonException("不要乱来，拿不到用户ID"));
        }
        Users queryUsers = usersInnerService.getById(userId);
        return StatusEnums.VALID.getCode() == queryUsers.getOpenTowStepAuth();
    }

    /**
     * 验证用户两步验证码是否正确
     * @param code 验证码
     */
    public void codeValid(String userId,int code) {
        Users queryUsers = usersInnerService.getById(userId);
        String userName = queryUsers.getNickName()+"-"+queryUsers.getPhone();
        boolean isCodeValid = gAuth.authorizeUser(userName, code);
        if (!isCodeValid) {
            throw new CommonException("验证码不正确！");
        } 
    }
}
