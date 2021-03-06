package indi.dbfmp.oio.oauth.core.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.dbfmp.oio.oauth.core.dto.condition.UserPermissionCondition;
import indi.dbfmp.oio.oauth.core.dto.condition.UserRoleCondition;
import indi.dbfmp.oio.oauth.core.dto.webDto.GrantNewRolesDto;
import indi.dbfmp.oio.oauth.core.dto.webDto.RemoveRolesDto;
import indi.dbfmp.oio.oauth.core.entity.UserPermission;
import indi.dbfmp.oio.oauth.core.entity.UserRole;
import indi.dbfmp.oio.oauth.core.innerService.IUserRoleInnerService;
import indi.dbfmp.oio.oauth.core.service.impl.UserRoleService;
import indi.dbfmp.oio.oauth.core.uitls.QueryWrapperUtil;
import indi.dbfmp.validator.core.annotation.ValidateBefore;
import indi.dbfmp.web.common.dto.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户-角色表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    private IUserRoleInnerService userRoleInnerService;
    @Autowired
    private UserRoleService userRoleService;

    //查
    @RequestMapping("/get")
    public CommonResult<?> get(@RequestBody UserRoleCondition condition) {
        IPage<UserRole> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.buildQueryWrapper(condition, queryWrapper);
        return CommonResult.success(userRoleInnerService.page(page, queryWrapper));
    }

    //查
    @RequestMapping("/get/{id}")
    public CommonResult<?> getById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return CommonResult.success(null);
        }
        return CommonResult.success(userRoleInnerService.getById(id));
    }

    //授权
    @RequestMapping("/grantNewRolesToUser")
    @ValidateBefore
    public CommonResult<?> grantNewRolesToUser(@RequestBody GrantNewRolesDto grantNewRolesDto) {
        return CommonResult.success(userRoleService.grantNewRolesToUser(grantNewRolesDto.getUserId(),grantNewRolesDto.getRoleIdList()));
    }

    //移除角色
    @RequestMapping("/removeRolesFromUser")
    @ValidateBefore
    public CommonResult<?> removeRolesFromUser(@RequestBody RemoveRolesDto removeRolesDto) {
        return CommonResult.success(userRoleService.removeRolesFromUser(removeRolesDto.getUserId(),removeRolesDto.getRoleIdList()));
    }

}
