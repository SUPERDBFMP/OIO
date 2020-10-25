package indi.dbfmp.oio.oauth.core.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.dbfmp.oio.oauth.core.dto.condition.RolePermissionCondition;
import indi.dbfmp.oio.oauth.core.dto.condition.UrlPermissionCondition;
import indi.dbfmp.oio.oauth.core.entity.RolePermission;
import indi.dbfmp.oio.oauth.core.entity.UrlPermission;
import indi.dbfmp.oio.oauth.core.innerService.IUrlPermissionInnerService;
import indi.dbfmp.oio.oauth.core.uitls.QueryWrapperUtil;
import indi.dbfmp.web.common.dto.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * url-权限表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/urlPermission")
public class UrlPermissionController {

    @Autowired
    private IUrlPermissionInnerService urlPermissionInnerService;

    //查
    @RequestMapping("/get")
    public CommonResult<?> get(@RequestBody UrlPermissionCondition condition) {
        IPage<UrlPermission> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        QueryWrapper<UrlPermission> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.buildQueryWrapper(condition, queryWrapper);
        return CommonResult.success(urlPermissionInnerService.page(page, queryWrapper));
    }

    //查
    @RequestMapping("/get/{id}")
    public CommonResult<?> getById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return CommonResult.success(null);
        }
        return CommonResult.success(urlPermissionInnerService.getById(id));
    }


}
