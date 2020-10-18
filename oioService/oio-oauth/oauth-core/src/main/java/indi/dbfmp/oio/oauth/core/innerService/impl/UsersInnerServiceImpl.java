package indi.dbfmp.oio.oauth.core.innerService.impl;

import indi.dbfmp.oio.oauth.core.entity.Users;
import indi.dbfmp.oio.oauth.core.mapper.UsersMapper;
import indi.dbfmp.oio.oauth.core.innerService.IUsersInnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-12
 */
@Service
public class UsersInnerServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersInnerService {

}
