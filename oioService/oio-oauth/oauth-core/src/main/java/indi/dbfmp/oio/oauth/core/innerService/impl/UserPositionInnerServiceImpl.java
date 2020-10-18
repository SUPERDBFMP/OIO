package indi.dbfmp.oio.oauth.core.innerService.impl;

import indi.dbfmp.oio.oauth.core.entity.UserPosition;
import indi.dbfmp.oio.oauth.core.mapper.UserPositionMapper;
import indi.dbfmp.oio.oauth.core.innerService.IUserPositionInnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-职位表 服务实现类
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Service
public class UserPositionInnerServiceImpl extends ServiceImpl<UserPositionMapper, UserPosition> implements IUserPositionInnerService {

}
