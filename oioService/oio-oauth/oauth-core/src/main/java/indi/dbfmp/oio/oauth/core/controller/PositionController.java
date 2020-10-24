package indi.dbfmp.oio.oauth.core.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.dbfmp.oio.oauth.core.dto.condition.PositionCondition;
import indi.dbfmp.oio.oauth.core.dto.webDto.BatchDelDto;
import indi.dbfmp.oio.oauth.core.entity.Position;
import indi.dbfmp.oio.oauth.core.event.update.PositionUpdateEvent;
import indi.dbfmp.oio.oauth.core.innerService.IPositionInnerService;
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
 * 职位表 前端控制器
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private IPositionInnerService positionInnerService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    //增
    @RequestMapping("/add")
    @ValidateBefore
    public CommonResult<?> add(@RequestBody Position add) {
        boolean saveResult = positionInnerService.save(add);
        return CommonResult.success(saveResult);
    }

    //删
    @RequestMapping("/del/{id}")
    public CommonResult<?> del(@PathVariable("id") String id) {
        return CommonResult.success(positionInnerService.removeById(id));
    }

    //批量删
    @RequestMapping("/batchDel")
    @ValidateBefore
    public CommonResult<?> batchDel(@RequestBody BatchDelDto batchDelDto) {
        return CommonResult.success(positionInnerService.removeByIds(batchDelDto.getDelIds()));
    }

    //改
    @RequestMapping("/update")
    @ValidateBefore(groups = UpdateGroup.class)
    public CommonResult<?> update(@RequestBody Position position) {
        boolean updateResult = positionInnerService.updateById(position);
        eventPublisher.publishEvent(PositionUpdateEvent.builder()
                .id(position.getId())
                .positionCode(position.getPositionCode())
                .positionName(position.getPositionName())
                .build());
        return CommonResult.success(updateResult);
    }

    //查
    @RequestMapping("/get")
    public CommonResult<?> get(@RequestBody PositionCondition condition) {
        IPage<Position> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.buildQueryWrapper(condition, queryWrapper);
        return CommonResult.success(positionInnerService.page(page, queryWrapper));
    }

    //查
    @RequestMapping("/get/{id}")
    public CommonResult<?> getById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return CommonResult.success(null);
        }
        return CommonResult.success(positionInnerService.getById(id));
    }

}
