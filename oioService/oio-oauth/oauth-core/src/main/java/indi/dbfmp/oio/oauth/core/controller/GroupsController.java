package indi.dbfmp.oio.oauth.core.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.dbfmp.oio.oauth.core.dto.condition.GroupCondition;
import indi.dbfmp.oio.oauth.core.entity.Event;
import indi.dbfmp.oio.oauth.core.entity.Groups;
import indi.dbfmp.oio.oauth.core.enums.EventStatus;
import indi.dbfmp.oio.oauth.core.enums.EventTypes;
import indi.dbfmp.oio.oauth.core.event.update.GroupsUpdateEvent;
import indi.dbfmp.oio.oauth.core.event.update.GroupsUpdateEventListener;
import indi.dbfmp.oio.oauth.core.innerService.IEventInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IGroupsInnerService;
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
 * 分组详情表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/groups")
public class GroupsController {

    @Autowired
    private IGroupsInnerService groupsInnerService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private EventService eventService;

    //增
    @RequestMapping("/add")
    @ValidateBefore
    public CommonResult<?> add(@RequestBody Groups groups) {
        boolean saveResult = groupsInnerService.save(groups);
        return CommonResult.success(saveResult);
    }

    /*//删
    @RequestMapping("/del/{id}")
    public CommonResult<?> del(@PathVariable("id") String id) {
        return CommonResult.success(groupsInnerService.removeById(id));
    }

    //批量删
    @RequestMapping("/batchDel")
    @ValidateBefore
    public CommonResult<?> batchDel(@RequestBody BatchDelDto batchDelDto) {
        return CommonResult.success(groupsInnerService.removeByIds(batchDelDto.getDelIds()));
    }*/

    //改
    @RequestMapping("/update")
    @ValidateBefore(groups = UpdateGroup.class)
    public CommonResult<?> update(@RequestBody Groups groups) {
        boolean updateResult = groupsInnerService.updateById(groups);
        GroupsUpdateEvent groupsUpdateEvent = GroupsUpdateEvent.builder()
                .id(groups.getId())
                .groupCode(groups.getGroupCode())
                .groupName(groups.getGroupName())
                .build();
        Event event = eventService.createProcessingEvent(GroupsUpdateEventListener.class.getSimpleName(),JSONObject.toJSONString(groupsUpdateEvent),EventTypes.GroupsUpdate);
        groupsUpdateEvent.setEventId(event.getId());
        eventPublisher.publishEvent(groupsUpdateEvent);
        return CommonResult.success(updateResult);
    }

    //查
    @RequestMapping("/get")
    public CommonResult<?> get(@RequestBody GroupCondition condition) {
        IPage<Groups> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        QueryWrapper<Groups> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.buildQueryWrapper(condition, queryWrapper);
        return CommonResult.success(groupsInnerService.page(page, queryWrapper));
    }

    //查
    @RequestMapping("/get/{id}")
    public CommonResult<?> getById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return CommonResult.success(null);
        }
        return CommonResult.success(groupsInnerService.getById(id));
    }
}
