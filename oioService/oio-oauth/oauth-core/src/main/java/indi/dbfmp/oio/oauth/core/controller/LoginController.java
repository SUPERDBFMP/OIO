package indi.dbfmp.oio.oauth.core.controller;

import cn.hutool.core.util.StrUtil;
import indi.dbfmp.oio.oauth.core.dto.webDto.AuthCodeTokenDto;
import indi.dbfmp.oio.oauth.core.dto.webDto.RefreshDto;
import indi.dbfmp.oio.oauth.core.dto.webDto.TokenDto;
import indi.dbfmp.oio.oauth.core.innerService.IUsersInnerService;
import indi.dbfmp.oio.oauth.core.service.impl.TokenService;
import indi.dbfmp.oio.oauth.core.dto.webDto.LoginDto;
import indi.dbfmp.validator.core.annotation.ValidateBefore;
import indi.dbfmp.web.common.dto.CommonResult;
import inid.dbfmp.oauth.api.dto.VerifyTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 *  授权码模式controller登录
 * </p>
 *
 * @author dbfmp
 * @name: LoginController
 * @since 2020/10/11 6:37 下午
 */
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/authCode")
    public ModelAndView getAuthCode(@RequestParam("client_id") String clientId,@RequestParam("redirect_url") String redirectUrl,@RequestParam("app_type") String appType) {
        if (StrUtil.isBlank(clientId) || StrUtil.isBlank(redirectUrl)) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("errorParams");
            modelAndView.addObject("errorMsg","client_id或redirect_url不能为空");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("clientId",clientId);
        modelAndView.addObject("redirectUrl",redirectUrl);
        modelAndView.addObject("appType",appType);
        return modelAndView;
    }

    @PostMapping("/loginByPwd")
    @ValidateBefore
    @ResponseBody
    public CommonResult<?> loginByPwd(@RequestBody LoginDto loginDto) {
        log.info("登录信息:{}",loginDto);
        String code = tokenService.getOauthCode(loginDto.getUserName(),loginDto.getPwd(),loginDto.getAppType(),loginDto.getClientId());
        String redirectUrl = loginDto.getRedirectUrl() + "?code=" + code;
        return CommonResult.success(redirectUrl);
    }

    @PostMapping("/getToken")
    @ValidateBefore
    @ResponseBody
    public CommonResult<?> getToken(@RequestBody AuthCodeTokenDto authCodeTokenDto) {
        TokenDto tokenDto = tokenService.getToken(authCodeTokenDto);
        return CommonResult.success(tokenDto);
    }

    @PostMapping("/verifyToken")
    @ValidateBefore
    @ResponseBody
    public CommonResult<?> verifyToken(@RequestBody TokenDto tokenDto) {
        VerifyTokenDto verifyTokenDto = tokenService.verifyToken(tokenDto.getToken());
        return CommonResult.success(verifyTokenDto);
    }

    @PostMapping("/refreshToken")
    @ValidateBefore
    @ResponseBody
    public CommonResult<?> refreshToken(@RequestBody RefreshDto refreshDto) {
        TokenDto tokenDto = tokenService.refreshToken(refreshDto);
        return CommonResult.success(tokenDto);
    }

}
