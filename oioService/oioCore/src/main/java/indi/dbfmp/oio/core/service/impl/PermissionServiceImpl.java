package indi.dbfmp.oio.core.service.impl;

import indi.dbfmp.oio.core.entity.Permission;
import indi.dbfmp.oio.core.mapper.PermissionMapper;
import indi.dbfmp.oio.core.service.IPermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
