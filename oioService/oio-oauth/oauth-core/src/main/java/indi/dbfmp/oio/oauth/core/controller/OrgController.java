package indi.dbfmp.oio.oauth.core.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.dbfmp.oio.oauth.core.dto.condition.OrgCondition;
import indi.dbfmp.oio.oauth.core.dto.webDto.BatchDelDto;
import indi.dbfmp.oio.oauth.core.entity.Org;
import indi.dbfmp.oio.oauth.core.innerService.IOrgInnerService;
import indi.dbfmp.validator.core.annotation.ValidateBefore;
import indi.dbfmp.validator.core.group.UpdateGroup;
import indi.dbfmp.web.common.dto.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    //增
    @RequestMapping("/add")
    @ValidateBefore
    public CommonResult<?> add(@RequestBody Org saveOrg) {
        boolean saveResult = orgInnerService.save(saveOrg);
        return CommonResult.success(saveResult);
    }

    //删
    @RequestMapping("/del/{id}")
    public CommonResult<?> del(@PathVariable("id") String id) {
        return CommonResult.success(orgInnerService.removeById(id));
    }

    //批量删
    @RequestMapping("/batchDel")
    @ValidateBefore
    public CommonResult<?> batchDel(@RequestBody BatchDelDto batchDelDto) {
        return CommonResult.success(orgInnerService.removeByIds(batchDelDto.getDelIds()));
    }

    //改
    @RequestMapping("/update")
    @ValidateBefore(groups = UpdateGroup.class)
    public CommonResult<?> update(@RequestBody Org saveOrg) {
        return CommonResult.success(orgInnerService.updateById(saveOrg));
    }

    //查
    @RequestMapping("/get")
    public CommonResult<?> get(@RequestBody OrgCondition orgCondition) {
        IPage<Org> page = new Page<>(orgCondition.getPageNum(),orgCondition.getPageSize());
        QueryWrapper<Org> queryWrapper = new QueryWrapper<>();
        return CommonResult.success(orgInnerService.page(page,new LambdaQueryWrapper<Org>()
                .eq(StrUtil.isNotBlank(orgCondition.getId()),Org::getId,orgCondition.getId())
                .eq(StrUtil.isNotBlank(orgCondition.getOrgCode()),Org::getOrgCode,orgCondition.getOrgCode())
                .eq(StrUtil.isNotBlank(orgCondition.getOrgName()),Org::getOrgName,orgCondition.getOrgName())
                .eq(StrUtil.isNotBlank(orgCondition.getOrgType()),Org::getOrgType,orgCondition.getOrgType())
                .ge(null != orgCondition.getCreateDateTimeStart(),Org::getCreateDate,orgCondition.getCreateDateTimeStart())
                .le(null != orgCondition.getCreateDateTimeEnd(),Org::getCreateDate,orgCondition.getCreateDateTimeEnd())
                .ge(null != orgCondition.getUpdateDateTimeStart(),Org::getCreateDate,orgCondition.getUpdateDateTimeStart())
                .le(null != orgCondition.getUpdateDateTimeEnd(),Org::getCreateDate,orgCondition.getUpdateDateTimeEnd())
        ));
    }


}
