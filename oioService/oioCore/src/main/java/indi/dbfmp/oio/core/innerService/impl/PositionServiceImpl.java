package indi.dbfmp.oio.core.innerService.impl;

import indi.dbfmp.oio.core.mapper.PositionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.dbfmp.oio.core.innerService.IPositionService;
import indi.dbfmp.oio.inner.entity.Position;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dbfmp
 * @since 2020-06-14
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

}
