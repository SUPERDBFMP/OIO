package indi.dbfmp.oio.core.service.impl;

import indi.dbfmp.oio.core.entity.Users;
import indi.dbfmp.oio.core.mapper.UsersMapper;
import indi.dbfmp.oio.core.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
