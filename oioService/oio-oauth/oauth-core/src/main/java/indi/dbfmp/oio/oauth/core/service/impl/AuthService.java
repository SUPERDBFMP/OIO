package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.component.RedisUtil;
import indi.dbfmp.oio.oauth.core.constants.OauthRedisConstants;
import indi.dbfmp.oio.oauth.core.dto.PermissionCheckDto;
import indi.dbfmp.oio.oauth.core.dto.RoleCheckDto;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.innerService.*;
import inid.dbfmp.oauth.api.enums.StatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private IUrlPermissionInnerService urlPermissionInnerService;
    @Autowired
    private IUserRoleInnerService userRoleInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;
    @Autowired
    private RedisUtil redisUtil;

    private final static int roleCheckExpTime = 60 * 60;
    private final static int permissionCheckExpTime = 60 * 60;

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
        boolean hasCheck = redisUtil.hasKey(StrUtil.format(OauthRedisConstants.URL_ROLE_CHECK,roleCheckDto.getUrl(),roleCheckDto.getUserId()));
        boolean roleCheck = hasCheck ?
                (Boolean) redisUtil.get(StrUtil.format(OauthRedisConstants.URL_ROLE_CHECK,roleCheckDto.getUrl(),roleCheckDto.getUserId())) : false;
        if (hasCheck) {
            return roleCheck;
        }
        //获取url对应的角色
        List<UrlRole> urlRoleList = urlRoleInnerService.list(new LambdaQueryWrapper<UrlRole>().eq(UrlRole::getUrl,roleCheckDto.getUrl()));
        if (CollectionUtil.isEmpty(urlRoleList)) {
            redisUtil.set(StrUtil.format(OauthRedisConstants.URL_ROLE_CHECK,roleCheckDto.getUrl(),roleCheckDto.getUserId()),Boolean.FALSE,roleCheckExpTime);
            return false;
        }
        List<String> urlGroupList = urlRoleList.stream().map(UrlRole::getGroupId).collect(toList());
        //根据group获取用户角色
        List<UserRole> userRoleList = userRoleInnerService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,roleCheckDto.getUserId()).in(UserRole::getGroupId,urlGroupList));
        if (CollectionUtil.isEmpty(userRoleList)) {
            redisUtil.set(StrUtil.format(OauthRedisConstants.URL_ROLE_CHECK,roleCheckDto.getUrl(),roleCheckDto.getUserId()),Boolean.FALSE,roleCheckExpTime);
            return false;
        }
        /*查看用户是否拥有此角色*/
        //url角色分组筛选处理
        Map<String, Set<String>> urlRoleIdSetByGroup = urlRoleList.stream().collect(Collectors.groupingBy(UrlRole::getGroupId,Collectors.mapping(UrlRole::getRoleId,toSet())));
        for (UserRole userRole : userRoleList) {
            Set<String> urlRoleIdSet = urlRoleIdSetByGroup.get(userRole.getGroupId());
            if (urlRoleIdSet.contains(userRole.getRoleId())) {
                redisUtil.set(StrUtil.format(OauthRedisConstants.URL_ROLE_CHECK,roleCheckDto.getUrl(),roleCheckDto.getUserId()),Boolean.TRUE,roleCheckExpTime);
                return true;
            }
        }
        redisUtil.set(StrUtil.format(OauthRedisConstants.URL_ROLE_CHECK,roleCheckDto.getUrl(),roleCheckDto.getUserId()),Boolean.FALSE,roleCheckExpTime);
        return false;
    }

    /**
     * 权限检查
     *
     * todo redis缓存使用
     *
     * @param permissionCheckDto permissionCheckDto
     * @return 是否拥有权限
     */
    public boolean permissionCheck(PermissionCheckDto permissionCheckDto) {
        //检查用户合法性
        if (usersInnerService.count(new LambdaQueryWrapper<Users>().eq(Users::getUserId, permissionCheckDto.getUserId()).eq(Users::getLoginFlag, StatusEnums.VALID.getCode())) <= 0) {
            return false;
        }
        boolean hasCheck = redisUtil.hasKey(StrUtil.format(OauthRedisConstants.URL_PERMISSION_CHECK,permissionCheckDto.getUrl(),permissionCheckDto.getUserId()));
        boolean permissionCheck =  hasCheck?
                (Boolean) redisUtil.get(StrUtil.format(OauthRedisConstants.URL_PERMISSION_CHECK,permissionCheckDto.getUrl(),permissionCheckDto.getUserId())) : false;
        if (hasCheck) {
            return permissionCheck;
        }
        //获取url对应的权限
        List<UrlPermission> urlPermissionList = urlPermissionInnerService.list(new LambdaQueryWrapper<UrlPermission>().eq(UrlPermission::getUrl,permissionCheckDto.getUrl()));
        if (CollectionUtil.isEmpty(urlPermissionList)) {
            redisUtil.set(StrUtil.format(OauthRedisConstants.URL_PERMISSION_CHECK,permissionCheckDto.getUrl(),permissionCheckDto.getUserId()),Boolean.FALSE,permissionCheckExpTime);
            return false;
        }
        List<String> urlGroupList = urlPermissionList.stream().map(UrlPermission::getGroupId).collect(toList());
        //根据group获取用户权限
        List<UserPermission> userPermissionList = userPermissionInnerService.list(new LambdaQueryWrapper<UserPermission>().eq(UserPermission::getUserId,permissionCheckDto.getUserId()).in(UserPermission::getGroupId,urlGroupList));
        if (CollectionUtil.isEmpty(userPermissionList)) {
            redisUtil.set(StrUtil.format(OauthRedisConstants.URL_PERMISSION_CHECK,permissionCheckDto.getUrl(),permissionCheckDto.getUserId()),Boolean.FALSE,permissionCheckExpTime);
            return false;
        }
        /*查看用户是否拥有此权限*/
        //url权限分组筛选处理
        Map<String, Set<String>> urlPermissionIdSetByGroup = urlPermissionList.stream().collect(Collectors.groupingBy(UrlPermission::getGroupId,Collectors.mapping(UrlPermission::getPermissionId,toSet())));
        for (UserPermission userPermission : userPermissionList) {
            Set<String> urlPermissionIdSet = urlPermissionIdSetByGroup.get(userPermission.getGroupId());
            if (urlPermissionIdSet.contains(userPermission.getPermissionId())) {
                redisUtil.set(StrUtil.format(OauthRedisConstants.URL_PERMISSION_CHECK,permissionCheckDto.getUrl(),permissionCheckDto.getUserId()),Boolean.TRUE,permissionCheckExpTime);
                return true;
            }
        }
        redisUtil.set(StrUtil.format(OauthRedisConstants.URL_PERMISSION_CHECK,permissionCheckDto.getUrl(),permissionCheckDto.getUserId()),Boolean.FALSE,permissionCheckExpTime);
        return false;
    }

}
