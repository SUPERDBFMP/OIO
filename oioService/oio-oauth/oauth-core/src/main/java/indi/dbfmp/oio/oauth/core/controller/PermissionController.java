package indi.dbfmp.oio.oauth.core.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.dbfmp.oio.oauth.core.dto.condition.PermissionCondition;
import indi.dbfmp.oio.oauth.core.dto.webDto.BatchDelDto;
import indi.dbfmp.oio.oauth.core.entity.Event;
import indi.dbfmp.oio.oauth.core.entity.Permission;
import indi.dbfmp.oio.oauth.core.enums.EventStatus;
import indi.dbfmp.oio.oauth.core.enums.EventTypes;
import indi.dbfmp.oio.oauth.core.event.update.PermissionUpdateEvent;
import indi.dbfmp.oio.oauth.core.event.update.PermissionUpdateEventListener;
import indi.dbfmp.oio.oauth.core.innerService.IEventInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IPermissionInnerService;
import indi.dbfmp.oio.oauth.core.uitls.QueryWrapperUtil;
import indi.dbfmp.validator.core.annotation.ValidateBefore;
import indi.dbfmp.validator.core.group.UpdateGroup;
import indi.dbfmp.web.common.dto.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 权限详情表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private IPermissionInnerService permissionInnerService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private IEventInnerService eventInnerService;

    //增
    @RequestMapping("/add")
    @ValidateBefore
    public CommonResult<?> add(@RequestBody Permission add) {
        boolean saveResult = permissionInnerService.save(add);
        return CommonResult.success(saveResult);
    }

    //删
    @RequestMapping("/del/{id}")
    public CommonResult<?> del(@PathVariable("id") String id) {
        return CommonResult.success(permissionInnerService.removeById(id));
    }

    //批量删
    @RequestMapping("/batchDel")
    @ValidateBefore
    public CommonResult<?> batchDel(@RequestBody BatchDelDto batchDelDto) {
        return CommonResult.success(permissionInnerService.removeByIds(batchDelDto.getDelIds()));
    }

    //改
    @RequestMapping("/update")
    @ValidateBefore(groups = UpdateGroup.class)
    public CommonResult<?> update(@RequestBody Permission permission) {
        boolean updateResult = permissionInnerService.updateById(permission);
        PermissionUpdateEvent permissionUpdateEvent = PermissionUpdateEvent.builder()
                .id(permission.getId())
                .permissionCode(permission.getPermissionCode())
                .permissionName(permission.getPermissionName())
                .orgId(permission.getOrgId())
                .orgName(permission.getOrgName())
                .build();
        Event event = Event.builder()
                .eventBeanName(PermissionUpdateEventListener.class.getSimpleName())
                .eventParams(JSONObject.toJSONString(permissionUpdateEvent))
                .eventStatus(EventStatus.PROCESSING.name())
                .eventType(EventTypes.PermissionUpdate.name())
                .build();
        eventInnerService.save(event);
        permissionUpdateEvent.setEventId(event.getId());
        eventPublisher.publishEvent(permissionUpdateEvent);
        return CommonResult.success(updateResult);
    }

    //查
    @RequestMapping("/get")
    public CommonResult<?> get(@RequestBody PermissionCondition condition) {
        IPage<Permission> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.buildQueryWrapper(condition, queryWrapper);
        return CommonResult.success(permissionInnerService.page(page, queryWrapper));
    }

    //查
    @RequestMapping("/get/{id}")
    public CommonResult<?> getById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return CommonResult.success(null);
        }
        return CommonResult.success(permissionInnerService.getById(id));
    }

}
