package indi.dbfmp.oio.oauth.core.event.update;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.OrgRole;
import indi.dbfmp.oio.oauth.core.entity.UserOrg;
import indi.dbfmp.oio.oauth.core.innerService.IOrgRoleInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserOrgInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <p>
 *   组织机构更新事件
 * </p>
 *
 * @author dbfmp
 * @name: OrgUpdateEventListner
 * @since 2020/10/24 8:30 下午
 */
@Slf4j
@Component
public class OrgUpdateEventListener {

    @Autowired
    private IUserOrgInnerService userOrgInnerService;
    @Autowired
    private IOrgRoleInnerService orgRoleInnerService;

    @Async("updateMiddleTablePool")
    @EventListener
    public void orgUpdateEvent(OrgUpdateEvent orgUpdateEvent) {
        log.info("收到org更新事件,orgUpdateEvent:{}",orgUpdateEvent);
        if (StrUtil.isBlank(orgUpdateEvent.getId())) {
            log.warn("orgUpdateEvent,警告！更新事件主键id为空！");
            return;
        }
        UserOrg userOrg = UserOrg.builder().orgId(orgUpdateEvent.getId()).orgName(orgUpdateEvent.getOrgName()).build();
        userOrgInnerService.update(userOrg,new LambdaQueryWrapper<UserOrg>().eq(UserOrg::getOrgId,orgUpdateEvent.getId()));
        OrgRole orgRole = OrgRole.builder().orgId(orgUpdateEvent.getId()).orgName(orgUpdateEvent.getOrgName()).build();
        orgRoleInnerService.update(orgRole,new LambdaQueryWrapper<OrgRole>().eq(OrgRole::getOrgId,orgUpdateEvent.getId()));
        log.info("orgUpdateEvent,更新结束");
    }

}
