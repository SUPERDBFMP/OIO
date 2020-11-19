package indi.dbfmp.oio.oauth.core.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.dbfmp.oio.oauth.core.dto.condition.GroupCondition;
import indi.dbfmp.oio.oauth.core.dto.condition.GroupUserCondition;
import indi.dbfmp.oio.oauth.core.entity.Event;
import indi.dbfmp.oio.oauth.core.entity.GroupUser;
import indi.dbfmp.oio.oauth.core.entity.Groups;
import indi.dbfmp.oio.oauth.core.enums.EventStatus;
import indi.dbfmp.oio.oauth.core.enums.EventTypes;
import indi.dbfmp.oio.oauth.core.event.update.GroupsUpdateEvent;
import indi.dbfmp.oio.oauth.core.event.update.GroupsUpdateEventListener;
import indi.dbfmp.oio.oauth.core.innerService.IGroupUserInnerService;
import indi.dbfmp.oio.oauth.core.service.impl.GroupUserService;
import indi.dbfmp.oio.oauth.core.uitls.QueryWrapperUtil;
import indi.dbfmp.validator.core.annotation.ValidateBefore;
import indi.dbfmp.validator.core.group.UpdateGroup;
import indi.dbfmp.web.common.dto.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 分组-用户表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-11-17
 */
@RestController
@RequestMapping("/groupUser")
public class GroupUserController {

    @Autowired
    private IGroupUserInnerService groupUserInnerService;
    @Autowired
    private GroupUserService groupUserService;

    //增
    @RequestMapping("/addUserGroup/{userId}/{groupId}")
    @ValidateBefore
    public CommonResult<?> add(@PathVariable("userId") String userId,@PathVariable("groupId")String groupId) {
        return CommonResult.success(groupUserService.saveUserToNewGroup(userId,groupId));
    }

    //删
    @RequestMapping("/delUserGroup/{userId}/{groupId}")
    public CommonResult<?> del(@PathVariable("userId") String userId,@PathVariable("groupId")String groupId) {
        return CommonResult.success(groupUserService.removeUserFromGroup(userId,groupId));
    }

    /*//批量删
    @RequestMapping("/batchDel")
    @ValidateBefore
    public CommonResult<?> batchDel(@RequestBody BatchDelDto batchDelDto) {
        return CommonResult.success(groupsInnerService.removeByIds(batchDelDto.getDelIds()));
    }*/


    //查
    @RequestMapping("/get")
    public CommonResult<?> get(@RequestBody GroupUserCondition condition) {
        IPage<GroupUser> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        QueryWrapper<GroupUser> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.buildQueryWrapper(condition, queryWrapper);
        return CommonResult.success(groupUserInnerService.page(page, queryWrapper));
    }

    //查
    @RequestMapping("/get/{id}")
    public CommonResult<?> getById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return CommonResult.success(null);
        }
        return CommonResult.success(groupUserInnerService.getById(id));
    }

}
