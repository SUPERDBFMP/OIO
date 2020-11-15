package indi.dbfmp.oio.oauth.core.service.impl;

import indi.dbfmp.oio.oauth.core.dto.UrlRoleGroupDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  url角色服务
 * </p>
 *
 * @author dbfmp
 * @name: UrlRoleService
 * @since 2020/11/11 9:51 下午
 */
@Slf4j
@Service
public class UrlRoleService {

    public boolean grantRolesToUrl(String url, List<UrlRoleGroupDto> urlRoleGroupDtoList) {
        //检查role，group正确性
        return false;
    }

}
