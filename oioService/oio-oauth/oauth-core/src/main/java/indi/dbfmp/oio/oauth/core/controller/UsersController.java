package indi.dbfmp.oio.oauth.core.controller;


import indi.dbfmp.oio.oauth.core.dto.webDto.ResetPwdDto;
import indi.dbfmp.oio.oauth.core.service.impl.UserService;
import indi.dbfmp.validator.core.annotation.ValidateBefore;
import indi.dbfmp.web.common.dto.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-11-28
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @RequestMapping("/setNewPwdByDefaultPwd")
    @ValidateBefore
    public CommonResult<?> setNewPwdByDefaultPwd(@RequestBody ResetPwdDto resetPwdDto) {
        userService.setNewPwdByDefaultPwd(resetPwdDto);
        return CommonResult.success("修改密码成功！");
    }

}
