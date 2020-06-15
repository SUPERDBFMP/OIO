package indi.dbfmp.oio.core.innerService.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.dbfmp.oio.core.mapper.RolesMapper;
import indi.dbfmp.oio.core.innerService.IRolesService;
import indi.dbfmp.oio.inner.entity.Roles;
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
public class IRolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements IRolesService {

}
