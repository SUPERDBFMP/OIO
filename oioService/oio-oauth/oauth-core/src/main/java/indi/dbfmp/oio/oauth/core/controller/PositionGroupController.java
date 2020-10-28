package indi.dbfmp.oio.oauth.core.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.dbfmp.oio.oauth.core.dto.condition.GroupRoleCondition;
import indi.dbfmp.oio.oauth.core.dto.condition.PositionGroupCondition;
import indi.dbfmp.oio.oauth.core.dto.webDto.GroupPositionDto;
import indi.dbfmp.oio.oauth.core.dto.webDto.GroupRoleDto;
import indi.dbfmp.oio.oauth.core.entity.GroupRole;
import indi.dbfmp.oio.oauth.core.entity.PositionGroup;
import indi.dbfmp.oio.oauth.core.innerService.IGroupRoleInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IPositionGroupInnerService;
import indi.dbfmp.oio.oauth.core.service.impl.GroupPositionService;
import indi.dbfmp.oio.oauth.core.uitls.QueryWrapperUtil;
import indi.dbfmp.validator.core.annotation.ValidateBefore;
import indi.dbfmp.web.common.dto.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 职位-分组表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-20
 */
@RestController
@RequestMapping("/positionGroup")
public class PositionGroupController {

    @Autowired
    private IPositionGroupInnerService positionGroupInnerService;
    @Autowired
    private GroupPositionService groupPositionService;

    //查
    @RequestMapping("/get")
    public CommonResult<?> get(@RequestBody PositionGroupCondition condition) {
        IPage<PositionGroup> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        QueryWrapper<PositionGroup> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.buildQueryWrapper(condition, queryWrapper);
        return CommonResult.success(positionGroupInnerService.page(page, queryWrapper));
    }

    //查
    @RequestMapping("/get/{id}")
    public CommonResult<?> getById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return CommonResult.success(null);
        }
        return CommonResult.success(positionGroupInnerService.getById(id));
    }

    @PostMapping("/addNewPositionToGroup")
    @ValidateBefore
    public CommonResult<?> addNewRoleToGroup(GroupPositionDto groupPositionDto) {
        return CommonResult.success(groupPositionService.addNewPositionToGroup(groupPositionDto.getGroupId(),groupPositionDto.getPositionIdList()));
    }

    @PostMapping("/removePositionsFromGroup")
    @ValidateBefore
    public CommonResult<?> removePositionsFromGroup(GroupPositionDto groupPositionDto) {
        return CommonResult.success(groupPositionService.removePositionsFromGroup(groupPositionDto.getGroupId(),groupPositionDto.getPositionIdList()));
    }

}
