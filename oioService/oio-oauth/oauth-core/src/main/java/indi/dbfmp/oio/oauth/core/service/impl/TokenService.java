package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nimbusds.jose.JOSEException;
import indi.dbfmp.oio.oauth.core.component.RedisUtil;
import indi.dbfmp.oio.oauth.core.constants.TokenRedisConstants;
import indi.dbfmp.oio.oauth.core.dto.redisDto.OauthCodeDto;
import indi.dbfmp.oio.oauth.core.entity.Client;
import indi.dbfmp.oio.oauth.core.entity.Users;
import indi.dbfmp.web.common.dto.ResultCode;
import inid.dbfmp.oauth.api.exception.CommonException;
import indi.dbfmp.oio.oauth.core.exception.ResetPasswordException;
import indi.dbfmp.oio.oauth.core.innerService.IClientInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUsersInnerService;
import inid.dbfmp.oauth.api.dto.PayloadDto;
import inid.dbfmp.oauth.api.dto.VerifyTokenDto;
import inid.dbfmp.oauth.api.enums.StatusEnums;
import inid.dbfmp.oauth.api.exception.JwtExpiredException;
import inid.dbfmp.oauth.api.exception.JwtInvalidException;
import inid.dbfmp.oauth.api.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.stereotype.Service;
import indi.dbfmp.oio.oauth.core.dto.webDto.*;

import java.net.MalformedURLException;
import java.text.ParseException;


/**
 * <p>
 * 鉴权服务
 * </p>
 *
 * @author dbfmp
 * @name: OauthService
 * @since 2020/10/11 7:15 下午
 */
@Slf4j
@Service
public class TokenService {

    @Autowired
    private IClientInnerService clientInnerService;
    @Autowired
    private IUsersInnerService usersInnerService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedissonClient redissonClient;

    @Value("${jwtRsaKeyPath}")
    private String jwtRsaKeyPath;
    @Value("${jwtRsaKey}")
    private String jwtRsaKey;
    private volatile FileUrlResource jwtKey;

    public boolean verifyUserByPwd(String username, String pwd) {
        return true;
    }

    public String getOauthCode(String username, String pwd, String appType, String clientId) {
        //1、查询有效client
        Client queryClient = clientInnerService.getOne(new LambdaQueryWrapper<Client>().eq(Client::getClientId, clientId).select(Client::getAccessFlag));
        if (null == queryClient) {
            log.info("getOauthCode->无效的ClientId:{}", clientId);
            throw new CommonException("无效的客户端！");
        }
        if (!String.valueOf(StatusEnums.VALID.getCode()).equals(queryClient.getAccessFlag())) {
            log.info("getOauthCode->被封禁的ClientId:{}", clientId);
            throw new CommonException("客户端被封禁，请联系管理员！");
        }
        //查询用户
        Users queryUser = usersInnerService.getOne(new LambdaQueryWrapper<Users>().eq(Users::getPhone, username)
                .select(Users::getPassword, Users::getLoginFlag, Users::getNickName, Users::getUserId,Users::getDefaultPwd));
        //todo 使用加密的密码
        if (null == queryUser || !queryUser.getPassword().equals(pwd)) {
            throw new CommonException("用户名或密码错误！");
        }
        if (!queryUser.getLoginFlag().equals(StatusEnums.VALID.getCode())) {
            throw new CommonException("您的账号被封禁了，请联系联系客服解封！");
        }
        if (queryUser.getDefaultPwd().equals(StatusEnums.VALID.getCode())) {
            throw new ResetPasswordException();
        }
        //颁发OauthCode
        OauthCodeDto oauthCodeDto = OauthCodeDto.builder()
                .oauthCode(IdUtil.objectId())
                .appType(appType)
                .clientId(clientId)
                .userId(queryUser.getUserId())
                .userNickName(queryUser.getNickName())
                .build();
        if (redisUtil.set(oauthCodeDto.getOauthCode(), oauthCodeDto, 5 * 60)) {
            //存入redis
            return oauthCodeDto.getOauthCode();
        } else {
            log.error("保存授权码至redis失败！oauthCodeDto:{}", oauthCodeDto);
            throw new CommonException("颁发授权码失败！请稍后再试");
        }

    }

    public TokenDto getToken(AuthCodeTokenDto authCodeTokenDto) {
        //校验授权码是否存在
        OauthCodeDto oauthCodeDto = (OauthCodeDto) redisUtil.get(authCodeTokenDto.getAuthCode());
        if (null == oauthCodeDto) {
            throw new CommonException("授权码不存在，请重新申请！");
        }
        OauthCodeDto redisOauthCodeDto = OauthCodeDto.builder().clientId(authCodeTokenDto.getClientId()).build();
        if (!redisUtil.setIfPresent(authCodeTokenDto.getAuthCode(), redisOauthCodeDto)) {
            //数据相同了，证明另一个线程获取到了token
            //todo 优化成都可以返回token
            throw new CommonException("不要并发获取token");
        }
        //查询client数据
        Client queryClient = clientInnerService.getOne(new LambdaQueryWrapper<Client>().eq(Client::getClientId, authCodeTokenDto.getClientId()).select(Client::getClientSecretKey,Client::getOrgId));
        if (null == queryClient) {
            throw new CommonException("授权客户端信息不存在！请联系管理员！");
        }
        if (!queryClient.getClientSecretKey().equals(authCodeTokenDto.getClientPwd()) || !oauthCodeDto.getAppType().equals(authCodeTokenDto.getAppType())) {
            throw new CommonException("授权客户端数据不正确，请检查！");
        }
        //颁发token
        PayloadDto tokenPayLoad = PayloadDto.builder()
                .appType(authCodeTokenDto.getAppType())
                .clientId(authCodeTokenDto.getClientId())
                .jti(IdUtil.objectId())
                .userId(oauthCodeDto.getUserId())
                .userName(oauthCodeDto.getUserNickName())
                .orgId(queryClient.getOrgId())
                .build();
        PayloadDto refreshTokenPayLoad = PayloadDto.builder()
                .appType(authCodeTokenDto.getAppType())
                .clientId(authCodeTokenDto.getClientId())
                .jti(IdUtil.objectId())
                .userId(oauthCodeDto.getUserId())
                .userName(oauthCodeDto.getUserNickName())
                .orgId(queryClient.getOrgId())
                .build();
        tokenPayLoad.setOtherTokenId(refreshTokenPayLoad.getJti());
        refreshTokenPayLoad.setOtherTokenId(tokenPayLoad.getJti());

        try {
            this.initJwtKey();
            String token = JwtTokenUtil.generateTokenByRSA(tokenPayLoad, JwtTokenUtil.getDefaultRSAKey(jwtKey, jwtRsaKey), 30 * 60);
            String refreshToken = JwtTokenUtil.generateTokenByRSA(refreshTokenPayLoad, JwtTokenUtil.getDefaultRSAKey(jwtRsaKeyPath, jwtRsaKey), 120 * 60);
            if (!redisUtil.set(StrUtil.format(TokenRedisConstants.TOKEN_KEY, oauthCodeDto.getUserId(), authCodeTokenDto.getAppType(), authCodeTokenDto.getClientId()), token, 30 * 60) ||
                    !redisUtil.set(StrUtil.format(TokenRedisConstants.REFRESH_TOKEN_KEY, oauthCodeDto.getUserId(), authCodeTokenDto.getAppType(), authCodeTokenDto.getClientId()), refreshToken, 120 * 60)) {
                redisUtil.del(StrUtil.format(TokenRedisConstants.TOKEN_KEY, oauthCodeDto.getUserId(), authCodeTokenDto.getAppType(),
                        authCodeTokenDto.getClientId()), StrUtil.format(TokenRedisConstants.REFRESH_TOKEN_KEY, oauthCodeDto.getUserId(), authCodeTokenDto.getAppType(), authCodeTokenDto.getClientId()));
                throw new CommonException("储存token失败！");
            }
            //删除授权码
            redisUtil.del(authCodeTokenDto.getAuthCode());
            return TokenDto.builder()
                    .token(token)
                    .refreshToken(refreshToken)
                    .expiresIn(30 * 60)
                    .build();
        } catch (Exception e) {
            log.error("颁发token失败！", e);
            throw new CommonException("颁发token失败！");
        }
    }

    public VerifyTokenDto verifyToken(String token) {
        PayloadDto payloadDto;
        try {
            this.initJwtKey();
            payloadDto = JwtTokenUtil.verifyTokenByRSA(token, JwtTokenUtil.getDefaultRSAKey(jwtKey, jwtRsaKey));
        } catch (JwtInvalidException | MalformedURLException e) {
            //bug 抛不出去的异常，要做刷新的旧token还是有效的
            throw new CommonException(e.getMessage());
        } catch (JwtExpiredException e){
            throw new CommonException(ResultCode.TOKEN_EXPIRED,e.getMessage());
        } catch (ParseException | JOSEException e) {
            log.error("校验token异常！", e);
            throw new CommonException("系统异常，请稍后再试～");
        }
        //查询redis
        String redisToken = (String) redisUtil.get(StrUtil.format(TokenRedisConstants.TOKEN_KEY, payloadDto.getUserId(), payloadDto.getAppType(), payloadDto.getClientId()));
        if (!token.equals(redisToken)) {
            RLock lock = redissonClient.getLock(StrUtil.format(TokenRedisConstants.LOCK_REFRESH_TOKEN_KEY, payloadDto.getUserId(), payloadDto.getAppType(), payloadDto.getClientId()));
            try {
                lock.lock();
                String tokenId = (String) redisUtil.get(StrUtil.format(TokenRedisConstants.OLD_TOKEN_KEY, payloadDto.getUserId(), payloadDto.getAppType(), payloadDto.getClientId()));
                PayloadDto verifyTokenDto = JwtTokenUtil.getPayloadDto(token);
                if (verifyTokenDto.getJti().equals(tokenId)) {
                    return VerifyTokenDto.builder().payloadDto(payloadDto).token(redisToken).build();
                }
            } catch (ParseException e) {
                log.error("校验token转换异常！",e);
            } finally {
                lock.unlock();
            }
            throw new CommonException("身份验证过期，请重新登录！");
        }
        return VerifyTokenDto.builder().payloadDto(payloadDto).token(redisToken).build();
    }

    public VerifyTokenDto verifyRefreshToken(String token) {
        PayloadDto payloadDto;
        try {
            this.initJwtKey();
            payloadDto = JwtTokenUtil.verifyTokenByRSA(token, JwtTokenUtil.getDefaultRSAKey(jwtKey, jwtRsaKey));
        } catch (JwtInvalidException | JwtExpiredException | MalformedURLException e) {
            throw new CommonException(e.getMessage());
        } catch (ParseException | JOSEException e) {
            log.error("校验token异常！", e);
            throw new CommonException("身份校验失败，请重新登录试试～");
        }
        //查询redis
        String redisToken = (String) redisUtil.get(StrUtil.format(TokenRedisConstants.REFRESH_TOKEN_KEY, payloadDto.getUserId(), payloadDto.getAppType(), payloadDto.getClientId()));
        if (!token.equals(redisToken)) {
            //不相等，则查询是否为旧refreshToken
            String tokenId = (String) redisUtil.get(StrUtil.format(TokenRedisConstants.OLD_REFRESH_TOKEN_KEY, payloadDto.getUserId(), payloadDto.getAppType(), payloadDto.getClientId()));
            try {
                PayloadDto verifyTokenDto = JwtTokenUtil.getPayloadDto(redisToken);
                if (null != tokenId && StrUtil.equals(tokenId, verifyTokenDto.getJti())) {
                    return VerifyTokenDto.builder().payloadDto(payloadDto).token(token).build();
                }
            } catch (ParseException e) {
                log.error("校验token异常！", e);
                throw new CommonException("身份校验失败，请重新登录试试～");
            }
        }
        return VerifyTokenDto.builder().payloadDto(payloadDto).token(token).build();
    }

    public TokenDto refreshToken(RefreshDto refreshDto) {
        VerifyTokenDto verifyTokenDto = this.verifyRefreshToken(refreshDto.getRefreshToken());
        PayloadDto payloadDto = verifyTokenDto.getPayloadDto();
        //todo 检查client
        RLock lock = redissonClient.getLock(StrUtil.format(TokenRedisConstants.LOCK_REFRESH_TOKEN_KEY, payloadDto.getUserId(), payloadDto.getAppType(), payloadDto.getClientId()));
        try {
            lock.lock();
            //获取旧的reFreshToken
            String tokenId = (String) redisUtil.get(StrUtil.format(TokenRedisConstants.OLD_REFRESH_TOKEN_KEY, payloadDto.getUserId(), payloadDto.getAppType(), payloadDto.getClientId()));
            if (payloadDto.getJti().equals(tokenId)) {
                //旧refreshToken直接返回新的token
                String token = (String) redisUtil.get(StrUtil.format(TokenRedisConstants.TOKEN_KEY, payloadDto.getUserId(), payloadDto.getAppType(), payloadDto.getClientId()));
                String refreshToken = (String) redisUtil.get(StrUtil.format(TokenRedisConstants.REFRESH_TOKEN_KEY, payloadDto.getUserId(), payloadDto.getAppType(), payloadDto.getClientId()));
                return TokenDto.builder()
                        .token(token)
                        .refreshToken(refreshToken)
                        .expiresIn(30 * 60)
                        .build();
            }
            //保存旧tokenId
            if (!redisUtil.set(StrUtil.format(TokenRedisConstants.OLD_REFRESH_TOKEN_KEY, payloadDto.getUserId(), payloadDto.getAppType(), payloadDto.getClientId()), payloadDto.getJti(), 5 * 60)) {
                log.error("保存刷新tokenId到redis失败！refreshToken:{}",refreshDto.getRefreshToken());
                throw new CommonException("刷新token失败！");
            }
            if (!redisUtil.set(StrUtil.format(TokenRedisConstants.OLD_TOKEN_KEY, payloadDto.getUserId(), payloadDto.getAppType(), payloadDto.getClientId()), payloadDto.getOtherTokenId(), 5 * 60)) {
                log.error("保存旧tokenId到redis失败！tokenId:{}",payloadDto.getOtherTokenId());
                throw new CommonException("刷新token失败！");
            }
            //颁发token
            PayloadDto tokenPayLoad = PayloadDto.builder()
                    .appType(payloadDto.getAppType())
                    .clientId(payloadDto.getClientId())
                    .jti(IdUtil.objectId())
                    .userId(payloadDto.getUserId())
                    .userName(payloadDto.getUserName())
                    .orgId(payloadDto.getOrgId())
                    .build();
            PayloadDto refreshTokenPayLoad = PayloadDto.builder()
                    .appType(payloadDto.getAppType())
                    .clientId(payloadDto.getClientId())
                    .jti(IdUtil.objectId())
                    .userId(payloadDto.getUserId())
                    .userName(payloadDto.getUserName())
                    .orgId(payloadDto.getOrgId())
                    .build();
            tokenPayLoad.setOtherTokenId(refreshTokenPayLoad.getJti());
            refreshTokenPayLoad.setOtherTokenId(tokenPayLoad.getJti());
            String token = JwtTokenUtil.generateTokenByRSA(tokenPayLoad, JwtTokenUtil.getDefaultRSAKey(jwtKey, jwtRsaKey), 30 * 60);
            String refreshToken = JwtTokenUtil.generateTokenByRSA(refreshTokenPayLoad, JwtTokenUtil.getDefaultRSAKey(jwtRsaKeyPath, jwtRsaKey), 120 * 60);
            if (!redisUtil.set(StrUtil.format(TokenRedisConstants.TOKEN_KEY, tokenPayLoad.getUserId(), tokenPayLoad.getAppType(), tokenPayLoad.getClientId()), token, 30 * 60) ||
                    !redisUtil.set(StrUtil.format(TokenRedisConstants.REFRESH_TOKEN_KEY, tokenPayLoad.getUserId(), tokenPayLoad.getAppType(), tokenPayLoad.getClientId()), refreshToken, 120 * 60)) {
                redisUtil.del(StrUtil.format(TokenRedisConstants.TOKEN_KEY, tokenPayLoad.getUserId(), tokenPayLoad.getAppType(),
                        tokenPayLoad.getClientId()), StrUtil.format(TokenRedisConstants.REFRESH_TOKEN_KEY, tokenPayLoad.getUserId(), tokenPayLoad.getAppType(), tokenPayLoad.getClientId()));
                throw new CommonException("储存token失败！");
            }
            return TokenDto.builder()
                    .token(token)
                    .refreshToken(refreshToken)
                    .expiresIn(30 * 60)
                    .build();
        } catch (MalformedURLException | JOSEException e) {
            log.error("颁发token失败！",e);
            throw new CommonException("颁发token失败！");
        } finally {
            lock.unlock();
        }
    }

    private void initJwtKey() {
        if (null == jwtKey) {
            synchronized (this) {
                if (null == jwtKey) {
                    try {
                        jwtKey = new FileUrlResource(jwtRsaKeyPath);
                    } catch (MalformedURLException e) {
                        log.error("加载jwt文件失败！",e);
                    }
                }
            }
        }
    }

}
