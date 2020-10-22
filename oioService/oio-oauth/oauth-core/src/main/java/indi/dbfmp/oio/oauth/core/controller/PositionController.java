package indi.dbfmp.oio.oauth.core.controller;


import indi.dbfmp.web.common.dto.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 职位表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/position")
public class PositionController {

    //增
    @RequestMapping("/add")
    public CommonResult<?> add() {
        return null;
    }

    //删
    @RequestMapping("/del")
    public CommonResult<?> del() {
        return null;
    }

    //改
    @RequestMapping("/update")
    public CommonResult<?> update() {
        return null;
    }

    //查
    @RequestMapping("/get")
    public CommonResult<?> get() {
        return null;
    }

}
