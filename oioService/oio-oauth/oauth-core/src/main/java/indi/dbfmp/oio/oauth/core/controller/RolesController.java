package indi.dbfmp.oio.oauth.core.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.dbfmp.oio.oauth.core.dto.condition.GroupCondition;
import indi.dbfmp.oio.oauth.core.dto.condition.RolesCondition;
import indi.dbfmp.oio.oauth.core.dto.webDto.BatchDelDto;
import indi.dbfmp.oio.oauth.core.entity.Event;
import indi.dbfmp.oio.oauth.core.entity.Groups;
import indi.dbfmp.oio.oauth.core.entity.Roles;
import indi.dbfmp.oio.oauth.core.enums.EventStatus;
import indi.dbfmp.oio.oauth.core.enums.EventTypes;
import indi.dbfmp.oio.oauth.core.event.update.GroupsUpdateEvent;
import indi.dbfmp.oio.oauth.core.event.update.OrgUpdateEventListener;
import indi.dbfmp.oio.oauth.core.event.update.RolesUpdateEvent;
import indi.dbfmp.oio.oauth.core.event.update.RolesUpdateEventListener;
import indi.dbfmp.oio.oauth.core.innerService.IEventInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IRolesInnerService;
import indi.dbfmp.oio.oauth.core.service.impl.EventService;
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
 * 角色详情表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private IRolesInnerService rolesInnerService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private EventService eventService;

    //增
    @RequestMapping("/add")
    @ValidateBefore
    public CommonResult<?> add(@RequestBody Roles add) {
        boolean saveResult = rolesInnerService.save(add);
        return CommonResult.success(saveResult);
    }

    //删
    @RequestMapping("/del/{id}")
    public CommonResult<?> del(@PathVariable("id") String id) {
        return CommonResult.success(rolesInnerService.removeById(id));
    }

    //批量删
    @RequestMapping("/batchDel")
    @ValidateBefore
    public CommonResult<?> batchDel(@RequestBody BatchDelDto batchDelDto) {
        return CommonResult.success(rolesInnerService.removeByIds(batchDelDto.getDelIds()));
    }

    //改
    @RequestMapping("/update")
    @ValidateBefore(groups = UpdateGroup.class)
    public CommonResult<?> update(@RequestBody Roles roles) {
        boolean updateResult = rolesInnerService.updateById(roles);
        RolesUpdateEvent rolesUpdateEvent = RolesUpdateEvent.builder()
                .id(roles.getId())
                .roleName(roles.getRoleName())
                .orgId(roles.getOrgId())
                .orgName(roles.getOrgName())
                .build();
        Event event = eventService.createProcessingEvent(RolesUpdateEventListener.class.getSimpleName(),JSONObject.toJSONString(rolesUpdateEvent),EventTypes.RolesUpdate);
        rolesUpdateEvent.setEventId(event.getId());
        eventPublisher.publishEvent(rolesUpdateEvent);
        return CommonResult.success(updateResult);
    }

    //查
    @RequestMapping("/get")
    public CommonResult<?> get(@RequestBody RolesCondition condition) {
        IPage<Roles> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        QueryWrapper<Roles> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.buildQueryWrapper(condition, queryWrapper);
        return CommonResult.success(rolesInnerService.page(page, queryWrapper));
    }

    //查
    @RequestMapping("/get/{id}")
    public CommonResult<?> getById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return CommonResult.success(null);
        }
        return CommonResult.success(rolesInnerService.getById(id));
    }


}
