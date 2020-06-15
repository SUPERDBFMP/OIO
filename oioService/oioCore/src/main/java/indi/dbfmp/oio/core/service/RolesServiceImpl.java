package indi.dbfmp.oio.core.service;

import indi.dbfmp.oio.core.innerService.IRolesService;
import indi.dbfmp.oio.core.innerService.impl.IRolesServiceImpl;
import indi.dbfmp.oio.inner.entity.Roles;
import indi.dbfmp.oio.inner.service.RolesService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name RolesServiceImpl
 * @since 2020/6/15 11:00 下午
 */
@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private IRolesServiceImpl iRolesServiceImpl;

    @Override
    public Roles getRoles() {
        return iRolesServiceImpl.getById(1);
    }
}
