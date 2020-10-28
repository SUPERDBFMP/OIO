package indi.dbfmp.oio.oauth.core.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.dbfmp.oio.oauth.core.dto.condition.PositionRoleCondition;
import indi.dbfmp.oio.oauth.core.dto.condition.RolePermissionCondition;
import indi.dbfmp.oio.oauth.core.dto.webDto.PositionRoleDto;
import indi.dbfmp.oio.oauth.core.dto.webDto.RolePermissionDto;
import indi.dbfmp.oio.oauth.core.entity.PositionRole;
import indi.dbfmp.oio.oauth.core.entity.RolePermission;
import indi.dbfmp.oio.oauth.core.innerService.IRolePermissionInnerService;
import indi.dbfmp.oio.oauth.core.service.impl.RolePermissionService;
import indi.dbfmp.oio.oauth.core.uitls.QueryWrapperUtil;
import indi.dbfmp.validator.core.annotation.ValidateBefore;
import indi.dbfmp.web.common.dto.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色-权限表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/rolePermission")
public class RolePermissionController {

    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private RolePermissionService rolePermissionService;

    //查
    @RequestMapping("/get")
    public CommonResult<?> get(@RequestBody RolePermissionCondition condition) {
        IPage<RolePermission> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.buildQueryWrapper(condition, queryWrapper);
        return CommonResult.success(rolePermissionInnerService.page(page, queryWrapper));
    }

    //查
    @RequestMapping("/get/{id}")
    public CommonResult<?> getById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return CommonResult.success(null);
        }
        return CommonResult.success(rolePermissionInnerService.getById(id));
    }

    @PostMapping("/addNewPermissionToRole")
    @ValidateBefore
    public CommonResult<?> addNewPermissionToRole(RolePermissionDto rolePermissionDto) {
        return CommonResult.success(rolePermissionService.addNewPermissionToRole(rolePermissionDto.getGroupId(),rolePermissionDto.getRoleId(),rolePermissionDto.getPermissionIdList()));
    }

    @PostMapping("/removePermissionFromRole")
    @ValidateBefore
    public CommonResult<?> removePermissionFromRole(RolePermissionDto rolePermissionDto) {
        return CommonResult.success(rolePermissionService.removePermissionFromRole(rolePermissionDto.getGroupId(),rolePermissionDto.getRoleId(),rolePermissionDto.getPermissionIdList()));
    }

}
