package indi.dbfmp.oio.oauth.core.innerService.impl;

import indi.dbfmp.oio.oauth.core.entity.UserPermission;
import indi.dbfmp.oio.oauth.core.mapper.UserPermissionMapper;
import indi.dbfmp.oio.oauth.core.innerService.IUserPermissionInnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-权限表 服务实现类
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Service
public class UserPermissionInnerServiceImpl extends ServiceImpl<UserPermissionMapper, UserPermission> implements IUserPermissionInnerService {

}
