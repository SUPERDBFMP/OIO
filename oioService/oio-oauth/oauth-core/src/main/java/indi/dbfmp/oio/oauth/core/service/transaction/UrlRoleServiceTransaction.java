package indi.dbfmp.oio.oauth.core.service.transaction;

import cn.hutool.core.collection.CollectionUtil;
import indi.dbfmp.oio.oauth.core.entity.UrlPermission;
import indi.dbfmp.oio.oauth.core.entity.UrlRole;
import indi.dbfmp.oio.oauth.core.innerService.IUrlPermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUrlRoleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  url角色事务服务
 * </p>
 *
 * @author dbfmp
 * @name: UrlRoleTransactionService
 * @since 2020/11/23 下午11:46
 */
@Slf4j
@Service
public class UrlRoleServiceTransaction {

    @Autowired
    private IUrlRoleInnerService urlRoleInnerService;
    @Autowired
    private IUrlPermissionInnerService urlPermissionInnerService;

    @Transactional(rollbackFor = Exception.class)
    public void grantRolesToUrlByGroup(List<UrlRole> urlRoleList,List<UrlPermission> urlPermissionList) {
        if (CollectionUtil.isNotEmpty(urlRoleList)) {
            urlRoleInnerService.saveBatch(urlRoleList);
        }
        if (CollectionUtil.isNotEmpty(urlPermissionList)) {
            urlPermissionInnerService.saveBatch(urlPermissionList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void grantRolesToUrl(UrlRole urlRole, List<UrlPermission> urlPermissionList) {
        if (null != urlRole) {
            urlRoleInnerService.save(urlRole);
        }
        if (CollectionUtil.isNotEmpty(urlPermissionList)) {
            urlPermissionInnerService.saveBatch(urlPermissionList);
        }
    }

}
