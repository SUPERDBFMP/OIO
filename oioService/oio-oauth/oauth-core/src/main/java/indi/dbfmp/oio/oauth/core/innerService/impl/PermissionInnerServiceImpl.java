package indi.dbfmp.oio.oauth.core.innerService.impl;

import indi.dbfmp.oio.oauth.core.entity.Permission;
import indi.dbfmp.oio.oauth.core.mapper.PermissionMapper;
import indi.dbfmp.oio.oauth.core.innerService.IPermissionInnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限详情表 服务实现类
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Service
public class PermissionInnerServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionInnerService {

}
