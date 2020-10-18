package indi.dbfmp.oio.oauth.core.innerService.impl;

import indi.dbfmp.oio.oauth.core.entity.UserRole;
import indi.dbfmp.oio.oauth.core.mapper.UserRoleMapper;
import indi.dbfmp.oio.oauth.core.innerService.IUserRoleInnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色表 服务实现类
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Service
public class UserRoleInnerServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleInnerService {

}
