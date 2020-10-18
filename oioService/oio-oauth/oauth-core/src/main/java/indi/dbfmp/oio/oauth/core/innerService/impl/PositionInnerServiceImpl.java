package indi.dbfmp.oio.oauth.core.innerService.impl;

import indi.dbfmp.oio.oauth.core.entity.Position;
import indi.dbfmp.oio.oauth.core.mapper.PositionMapper;
import indi.dbfmp.oio.oauth.core.innerService.IPositionInnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 职位表 服务实现类
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Service
public class PositionInnerServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionInnerService {

}
