package indi.dbfmp.oio.core.innerService.impl;

import indi.dbfmp.oio.core.mapper.UserPositionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.dbfmp.oio.core.innerService.IUserPositionService;
import indi.dbfmp.oio.inner.entity.UserPosition;
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
public class UserPositionServiceImpl extends ServiceImpl<UserPositionMapper, UserPosition> implements IUserPositionService {

}
