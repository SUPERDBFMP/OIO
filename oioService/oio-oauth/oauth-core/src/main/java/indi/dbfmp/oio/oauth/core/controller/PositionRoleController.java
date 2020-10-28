package indi.dbfmp.oio.oauth.core.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.dbfmp.oio.oauth.core.dto.condition.PositionGroupCondition;
import indi.dbfmp.oio.oauth.core.dto.condition.PositionRoleCondition;
import indi.dbfmp.oio.oauth.core.dto.webDto.GroupRoleDto;
import indi.dbfmp.oio.oauth.core.dto.webDto.PositionRoleDto;
import indi.dbfmp.oio.oauth.core.entity.PositionGroup;
import indi.dbfmp.oio.oauth.core.entity.PositionRole;
import indi.dbfmp.oio.oauth.core.innerService.IPositionRoleInnerService;
import indi.dbfmp.oio.oauth.core.service.impl.PositionRoleService;
import indi.dbfmp.oio.oauth.core.uitls.QueryWrapperUtil;
import indi.dbfmp.validator.core.annotation.ValidateBefore;
import indi.dbfmp.web.common.dto.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 职位-角色表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/position-role")
public class PositionRoleController {

    @Autowired
    private IPositionRoleInnerService positionRoleInnerService;
    @Autowired
    private PositionRoleService positionRoleService;

    //查
    @RequestMapping("/get")
    public CommonResult<?> get(@RequestBody PositionRoleCondition condition) {
        IPage<PositionRole> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        QueryWrapper<PositionRole> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.buildQueryWrapper(condition, queryWrapper);
        return CommonResult.success(positionRoleInnerService.page(page, queryWrapper));
    }

    //查
    @RequestMapping("/get/{id}")
    public CommonResult<?> getById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return CommonResult.success(null);
        }
        return CommonResult.success(positionRoleInnerService.getById(id));
    }

    @PostMapping("/addNewRoleToPosition")
    @ValidateBefore
    public CommonResult<?> addNewRoleToGroup(PositionRoleDto positionRoleDto) {
        return CommonResult.success(positionRoleService.addNewRoleToPosition(positionRoleDto.getGroupId(),positionRoleDto.getPositionId(),positionRoleDto.getRoleIdList()));
    }

    @PostMapping("/removePositionFromGroup")
    @ValidateBefore
    public CommonResult<?> removePositionFromGroup(PositionRoleDto positionRoleDto) {
        return CommonResult.success(positionRoleService.removeRolesFromPosition(positionRoleDto.getGroupId(),positionRoleDto.getPositionId(),positionRoleDto.getRoleIdList()));
    }

}
