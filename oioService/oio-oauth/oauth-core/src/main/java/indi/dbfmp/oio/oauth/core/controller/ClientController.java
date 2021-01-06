package indi.dbfmp.oio.oauth.core.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.dbfmp.oio.oauth.core.dto.condition.ClientCondition;
import indi.dbfmp.oio.oauth.core.dto.condition.RolesCondition;
import indi.dbfmp.oio.oauth.core.dto.webDto.BatchDelDto;
import indi.dbfmp.oio.oauth.core.entity.Client;
import indi.dbfmp.oio.oauth.core.entity.Event;
import indi.dbfmp.oio.oauth.core.entity.Roles;
import indi.dbfmp.oio.oauth.core.enums.EventTypes;
import indi.dbfmp.oio.oauth.core.event.update.RolesUpdateEvent;
import indi.dbfmp.oio.oauth.core.event.update.RolesUpdateEventListener;
import indi.dbfmp.oio.oauth.core.innerService.IClientInnerService;
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
 * 接入客户端详情表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-11-11
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private IClientInnerService clientInnerService;

    //增
    @RequestMapping("/add")
    @ValidateBefore
    public CommonResult<?> add(@RequestBody Client client) {
        boolean saveResult = clientInnerService.save(client);
        return CommonResult.success(saveResult);
    }

    //删
    @RequestMapping("/del/{id}")
    public CommonResult<?> del(@PathVariable("id") String id) {
        return CommonResult.success(clientInnerService.removeById(id));
    }

    //批量删
    @RequestMapping("/batchDel")
    @ValidateBefore
    public CommonResult<?> batchDel(@RequestBody BatchDelDto batchDelDto) {
        return CommonResult.success(clientInnerService.removeByIds(batchDelDto.getDelIds()));
    }

    //改
    @RequestMapping("/update")
    @ValidateBefore(groups = UpdateGroup.class)
    public CommonResult<?> update(@RequestBody Client client) {
        Client updateClient = Client.builder()
                .clientName(client.getClientName())
                .clientSecretKey(client.getClientSecretKey())
                .id(client.getId())
                .build();
        boolean updateResult = clientInnerService.updateById(updateClient);
        return CommonResult.success(updateResult);
    }

    //查
    @RequestMapping("/get")
    public CommonResult<?> get(@RequestBody ClientCondition condition) {
        IPage<Client> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.buildQueryWrapper(condition, queryWrapper);
        return CommonResult.success(clientInnerService.page(page, queryWrapper));
    }

    //查
    @RequestMapping("/get/{id}")
    public CommonResult<?> getById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return CommonResult.success(null);
        }
        return CommonResult.success(clientInnerService.getById(id));
    }

}
