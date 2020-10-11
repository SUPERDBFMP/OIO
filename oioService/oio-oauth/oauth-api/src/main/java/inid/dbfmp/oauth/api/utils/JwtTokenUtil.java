package inid.dbfmp.oauth.api.utils;

import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import inid.dbfmp.oauth.api.dto.PayloadDto;
import inid.dbfmp.oauth.api.exception.JwtExpiredException;
import inid.dbfmp.oauth.api.exception.JwtInvalidException;
import org.springframework.core.io.FileUrlResource;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.net.MalformedURLException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;

/**
 * <p>
 *  jwt工具类
 * </p>
 *
 * @author dbfmp
 * @name: JwtTokenUtil
 * @since 2020/10/11 4:08 下午
 */
public class JwtTokenUtil {

    public static RSAKey getDefaultRSAKey(String keyFilePath,String keyFileKey) throws MalformedURLException {
        //从classpath下获取RSA秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new FileUrlResource(keyFilePath), keyFileKey.toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("jwt", keyFileKey.toCharArray());
        //获取RSA公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //获取RSA私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey).privateKey(privateKey).build();
    }

    public static String generateTokenByRSA(String payloadStr, RSAKey rsaKey) throws JOSEException {
        //创建JWS头，设置签名算法和类型
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .build();
        //将负载信息封装到Payload中
        Payload payload = new Payload(payloadStr);
        //创建JWS对象
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        //创建RSA签名器
        JWSSigner jwsSigner = new RSASSASigner(rsaKey, true);
        //签名
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    public static PayloadDto verifyTokenByRSA(String token, RSAKey rsaKey) throws JOSEException, ParseException {
        //从token中解析JWS对象
        JWSObject jwsObject = JWSObject.parse(token);
        RSAKey publicRsaKey = rsaKey.toPublicJWK();
        //使用RSA公钥创建RSA验证器
        JWSVerifier jwsVerifier = new RSASSAVerifier(publicRsaKey);
        if (!jwsObject.verify(jwsVerifier)) {
            throw new JwtInvalidException("token签名不合法！");
        }
        String payload = jwsObject.getPayload().toString();
        PayloadDto payloadDto = JSONUtil.toBean(payload, PayloadDto.class);
        if (payloadDto.getExp() < new Date().getTime()) {
            throw new JwtExpiredException("token已过期！");
        }
        return payloadDto;
    }

    public static PayloadDto getPayloadDto(String token) throws ParseException {
        JWSObject jwsObject = JWSObject.parse(token);
        String payload = jwsObject.getPayload().toString();
        return JSONUtil.toBean(payload, PayloadDto.class);
    }



}
