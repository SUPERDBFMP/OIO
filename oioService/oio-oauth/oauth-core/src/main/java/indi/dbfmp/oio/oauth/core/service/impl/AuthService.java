package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.dto.PermissionCheckDto;
import indi.dbfmp.oio.oauth.core.dto.RoleCheckDto;
import indi.dbfmp.oio.oauth.core.entity.UrlRole;
import indi.dbfmp.oio.oauth.core.entity.UserRole;
import indi.dbfmp.oio.oauth.core.entity.Users;
import indi.dbfmp.oio.oauth.core.innerService.IUrlRoleInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserRoleInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUsersInnerService;
import inid.dbfmp.oauth.api.enums.StatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


/**
 * <p>
 *  鉴权服务
 * </p>
 *
 * @author dbfmp
 * @name: OauthService
 * @since 2020/10/18 9:59 下午
 */
@Slf4j
@Service
public class AuthService {

    @Autowired
    private IUsersInnerService usersInnerService;
    @Autowired
    private IUrlRoleInnerService urlRoleInnerService;
    @Autowired
    private IUserRoleInnerService userRoleInnerService;

    /**
     * 角色检查
     *
     * todo redis缓存使用
     *
     * @param roleCheckDto roleCheckDto
     * @return 是否拥有权限
     */
    public boolean roleCheck(RoleCheckDto roleCheckDto) {
        //检查用户合法性
        if (usersInnerService.count(new LambdaQueryWrapper<Users>().eq(Users::getUserId, roleCheckDto.getUserId()).eq(Users::getLoginFlag, StatusEnums.VALID.getCode())) <= 0) {
            return false;
        }
        //获取url对应的角色
        List<UrlRole> urlRoleList = urlRoleInnerService.list(new LambdaQueryWrapper<UrlRole>().eq(UrlRole::getUrl,roleCheckDto.getUrl()));
        if (CollectionUtil.isEmpty(urlRoleList)) {
            return false;
        }
        List<String> urlGroupList = urlRoleList.stream().map(UrlRole::getGroupId).collect(toList());
        //根据group获取用户角色
        List<UserRole> userRoleList = userRoleInnerService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,roleCheckDto.getUserId()).in(UserRole::getGroupId,urlGroupList));
        if (CollectionUtil.isEmpty(userRoleList)) {
            return false;
        }
        /*查看用户是否拥有此角色*/
        //url角色分组筛选处理
        Map<String, Set<String>> urlRoleIdSetByGroup = urlRoleList.stream().collect(Collectors.groupingBy(UrlRole::getGroupId,Collectors.mapping(UrlRole::getRoleId,toSet())));
        for (UserRole userRole : userRoleList) {
            Set<String> urlRoleIdSet = urlRoleIdSetByGroup.get(userRole.getGroupId());
            if (urlRoleIdSet.contains(userRole.getRoleId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 权限检查
     * @param permissionCheckDto permissionCheckDto
     * @return 是否拥有权限
     */
    public boolean permissionCheck(PermissionCheckDto permissionCheckDto) {
        return false;
    }

}
