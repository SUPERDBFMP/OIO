package indi.dbfmp.oio.oauth.core.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.dbfmp.oio.oauth.core.dto.condition.OrgCondition;
import indi.dbfmp.oio.oauth.core.entity.Event;
import indi.dbfmp.oio.oauth.core.entity.Org;
import indi.dbfmp.oio.oauth.core.enums.EventStatus;
import indi.dbfmp.oio.oauth.core.enums.EventTypes;
import indi.dbfmp.oio.oauth.core.event.update.OrgUpdateEvent;
import indi.dbfmp.oio.oauth.core.event.update.OrgUpdateEventListener;
import indi.dbfmp.oio.oauth.core.innerService.IEventInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IOrgInnerService;
import indi.dbfmp.oio.oauth.core.service.impl.EventService;
import indi.dbfmp.oio.oauth.core.uitls.QueryWrapperUtil;
import indi.dbfmp.validator.core.annotation.ValidateBefore;
import indi.dbfmp.validator.core.group.UpdateGroup;
import indi.dbfmp.web.common.dto.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 组织机构表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Slf4j
@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    private IOrgInnerService orgInnerService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private EventService eventService;

    //增
    @RequestMapping("/add")
    @ValidateBefore
    public CommonResult<?> add(@RequestBody Org saveOrg) {
        boolean saveResult = orgInnerService.save(saveOrg);
        return CommonResult.success(saveResult);
    }

    /*//删
    @RequestMapping("/del/{id}")
    public CommonResult<?> del(@PathVariable("id") String id) {
        return CommonResult.success(orgInnerService.removeById(id));
    }

    //批量删
    @RequestMapping("/batchDel")
    @ValidateBefore
    public CommonResult<?> batchDel(@RequestBody BatchDelDto batchDelDto) {
        return CommonResult.success(orgInnerService.removeByIds(batchDelDto.getDelIds()));
    }*/

    //改
    @RequestMapping("/update")
    @ValidateBefore(groups = UpdateGroup.class)
    public CommonResult<?> update(@RequestBody Org saveOrg) {
        boolean updateResult = orgInnerService.updateById(saveOrg);
        OrgUpdateEvent orgUpdateEvent = OrgUpdateEvent.builder()
                .id(saveOrg.getId())
                .orgCode(saveOrg.getOrgCode())
                .orgName(saveOrg.getOrgName())
                .orgType(saveOrg.getOrgType())
                .build();
        Event event = eventService.createProcessingEvent(OrgUpdateEventListener.class.getSimpleName(),JSONObject.toJSONString(orgUpdateEvent),EventTypes.OrgUpdate);
        orgUpdateEvent.setEventId(event.getId());
        eventPublisher.publishEvent(orgUpdateEvent);
        return CommonResult.success(updateResult);
    }

    //查
    @RequestMapping("/get")
    public CommonResult<?> get(@RequestBody OrgCondition orgCondition) {
        IPage<Org> page = new Page<>(orgCondition.getPageNum(), orgCondition.getPageSize());
        QueryWrapper<Org> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.buildQueryWrapper(orgCondition, queryWrapper);
        return CommonResult.success(orgInnerService.page(page, queryWrapper));
    }

    //查
    @RequestMapping("/get/{id}")
    public CommonResult<?> getById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return CommonResult.success(null);
        }
        return CommonResult.success(orgInnerService.getById(id));
    }


}
