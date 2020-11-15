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
import indi.dbfmp.validator.core.annotation.ValidateBefore;
import inid.dbfmp.oauth.api.enums.StatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
    @Autowired
    private RedissonClient redissonClient;

    private final static int roleCheckExpTime = 60 * 60;
    private final static int permissionCheckExpTime = 60 * 60;

    /**
     * url缓存
     */
    private RMap<String,List<String>> urlCacheMap;

    @PostConstruct
    public void initUrlCache() {
        //初始化url缓存

    }

    /**
     * 角色检查
     *
     * 建立URL缓存
     *
     * @param roleCheckDto roleCheckDto
     * @return 是否拥有权限
     */
    @ValidateBefore
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
        RSet<String> userCacheIdSet = redissonClient.getSet(roleCheckDto.getUserId());
        //获取组织机构下对应的所有URL
        List<UrlRole> urlRoleList = urlRoleInnerService.list(urlRoleInnerService.lambdaQuery().eq(UrlRole::getOrgId,roleCheckDto.getOrgId()));
        if (CollectionUtil.isEmpty(urlRoleList)) {
            log.info("orgId:{},下无URL角色配置",roleCheckDto.getOrgId());
            String urlRoleCacheKey = StrUtil.format(OauthRedisConstants.URL_ROLE_CHECK,roleCheckDto.getUrl(),roleCheckDto.getUserId());
            userCacheIdSet.add(urlRoleCacheKey);
            redisUtil.set(urlRoleCacheKey,Boolean.FALSE,roleCheckExpTime);
            return false;
        }
        //匹配url
        List<UrlRole> matchUrlRoleList = new ArrayList<>();
        PathMatcher pathMatcher = new AntPathMatcher();
        for (UrlRole urlRole : urlRoleList) {
            if (pathMatcher.match(urlRole.getUrl(), roleCheckDto.getUrl())) {
                matchUrlRoleList.add(urlRole);
            }
        }
        if (CollectionUtil.isEmpty(matchUrlRoleList)) {
            log.info("无匹配的URL,URL:{}",roleCheckDto.getUrl());
            String urlRoleCacheKey = StrUtil.format(OauthRedisConstants.URL_ROLE_CHECK,roleCheckDto.getUrl(),roleCheckDto.getUserId());
            userCacheIdSet.add(urlRoleCacheKey);
            redisUtil.set(urlRoleCacheKey,Boolean.FALSE,roleCheckExpTime);
            return false;
        }
        //根据orgId获取用户角色
        List<UserRole> userRoleList = userRoleInnerService.list(userRoleInnerService.lambdaQuery().eq(UserRole::getOrgId,roleCheckDto.getOrgId()).eq(UserRole::getUserId,roleCheckDto.getUserId()));
        if (CollectionUtil.isEmpty(userRoleList)) {
            log.info("用户ID:{},OrgId:{},无角色",roleCheckDto.getUserId(),roleCheckDto.getOrgId());
            String urlRoleCacheKey = StrUtil.format(OauthRedisConstants.URL_ROLE_CHECK,roleCheckDto.getUrl(),roleCheckDto.getUserId());
            userCacheIdSet.add(urlRoleCacheKey);
            redisUtil.set(urlRoleCacheKey,Boolean.FALSE,roleCheckExpTime);
            return false;
        }
        /*查看用户是否拥有此角色*/
        for (UrlRole urlRole : matchUrlRoleList) {
            for (UserRole userRole : userRoleList) {
                if (urlRole.getRoleId().equals(userRole.getRoleId())) {
                    String urlRoleCacheKey = StrUtil.format(OauthRedisConstants.URL_ROLE_CHECK,roleCheckDto.getUrl(),roleCheckDto.getUserId());
                    userCacheIdSet.add(urlRoleCacheKey);
                    redisUtil.set(urlRoleCacheKey,Boolean.TRUE,roleCheckExpTime);
                    return true;
                }
            }
        }
        String urlRoleCacheKey = StrUtil.format(OauthRedisConstants.URL_ROLE_CHECK,roleCheckDto.getUrl(),roleCheckDto.getUserId());
        userCacheIdSet.add(urlRoleCacheKey);
        redisUtil.set(urlRoleCacheKey,Boolean.FALSE,roleCheckExpTime);
        return false;
    }

    /**
     * 权限检查
     *
     * @param permissionCheckDto permissionCheckDto
     * @return 是否拥有权限
     */
    @ValidateBefore
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
        RSet<String> userCacheIdSet = redissonClient.getSet(permissionCheckDto.getUserId());
        //获取url对应的权限
        //fixme 可能查询出来结果太多
        List<UrlPermission> urlPermissionList = urlPermissionInnerService.list(urlPermissionInnerService.lambdaQuery().eq(UrlPermission::getOrgId,permissionCheckDto.getOrgId()));
        if (CollectionUtil.isEmpty(urlPermissionList)) {
            log.info("orgId:{},下无URL权限配置",permissionCheckDto.getOrgId());
            String urlPermissionCacheKey = StrUtil.format(OauthRedisConstants.URL_PERMISSION_CHECK,permissionCheckDto.getUrl(),permissionCheckDto.getUserId());
            userCacheIdSet.add(urlPermissionCacheKey);
            redisUtil.set(urlPermissionCacheKey,Boolean.FALSE,permissionCheckExpTime);
            return false;
        }
        //匹配url
        List<UrlPermission> matchUrlPermissionList = new ArrayList<>();
        PathMatcher pathMatcher = new AntPathMatcher();
        for (UrlPermission urlPermission : urlPermissionList) {
            if (pathMatcher.match(urlPermission.getUrl(), permissionCheckDto.getUrl())) {
                matchUrlPermissionList.add(urlPermission);
            }
        }
        //根据orgId获取用户权限
        List<UserPermission> userPermissionList = userPermissionInnerService.list(userPermissionInnerService.lambdaQuery()
                .eq(UserPermission::getOrgId,permissionCheckDto.getOrgId())
                .eq(UserPermission::getUserId,permissionCheckDto.getUserId()));
        if (CollectionUtil.isEmpty(userPermissionList)) {
            log.info("用户ID:{},OrgId:{},无权限",permissionCheckDto.getUserId(),permissionCheckDto.getOrgId());
            String urlRoleCacheKey = StrUtil.format(OauthRedisConstants.URL_PERMISSION_CHECK,permissionCheckDto.getUrl(),permissionCheckDto.getUserId());
            userCacheIdSet.add(urlRoleCacheKey);
            redisUtil.set(urlRoleCacheKey,Boolean.FALSE,roleCheckExpTime);
            return false;
        }
        /*查看用户是否拥有此权限*/
        for (UrlPermission urlPermission : matchUrlPermissionList) {
            for (UserPermission userPermission : userPermissionList) {
                if (urlPermission.getPermissionId().equals(userPermission.getPermissionId())) {
                    String urlRoleCacheKey = StrUtil.format(OauthRedisConstants.URL_PERMISSION_CHECK,permissionCheckDto.getUrl(),permissionCheckDto.getUserId());
                    userCacheIdSet.add(urlRoleCacheKey);
                    redisUtil.set(urlRoleCacheKey,Boolean.TRUE,roleCheckExpTime);
                    return true;
                }
            }
        }
        String urlPermissionCacheKey = StrUtil.format(OauthRedisConstants.URL_PERMISSION_CHECK,permissionCheckDto.getUrl(),permissionCheckDto.getUserId());
        userCacheIdSet.add(urlPermissionCacheKey);
        redisUtil.set(urlPermissionCacheKey,Boolean.FALSE,permissionCheckExpTime);
        return false;
    }

}
