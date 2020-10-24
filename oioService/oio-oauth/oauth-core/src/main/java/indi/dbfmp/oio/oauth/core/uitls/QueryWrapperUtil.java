package indi.dbfmp.oio.oauth.core.uitls;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.dto.condition.BaseCondition;
import indi.dbfmp.oio.oauth.core.enums.WrapperTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  查询条件构造器工具
 * </p>
 *
 * @author dbfmp
 * @name: QueryWrapperUtil
 * @since 2020/10/24 1:17 下午
 */
public class QueryWrapperUtil {

    private final static ConcurrentHashMap<String,ConditionCache> conditionCacheMap = new ConcurrentHashMap<>();

    public static  <T> void buildQueryWrapper(BaseCondition condition, QueryWrapper<T> queryWrapper) {
        Field[] fields = ReflectUtil.getFields(condition.getClass());
        ConditionCache conditionCache = conditionCacheMap.getOrDefault(condition.getClass().getName(),new ConditionCache());
        List<String> groupByColumnName = conditionCache.getGroupByColumnName();
        List<String> orderByColumnName = conditionCache.getOrderByColumnName();
        WrapperTypes orderByType = conditionCache.getOrderByType();
        for (Field field : fields) {
            Object value = ReflectUtil.getFieldValue(condition,field);
            WrapperCondition wrapperCondition = field.getAnnotation(WrapperCondition.class);
            if (null == wrapperCondition) {
                continue;
            }
            if (wrapperCondition.orderByColumn()) {
                orderByColumnName.add(wrapperCondition.columnName());
                orderByType = wrapperCondition.orderByType();
            }
            if (wrapperCondition.groupByColumn()) {
                groupByColumnName.add(wrapperCondition.columnName());
            }
            if (WrapperTypes.EQ.equals(wrapperCondition.wrapperType())) {
                queryWrapper.eq(value instanceof String ? StrUtil.isNotBlank((String)value)
                                : null != value
                        ,wrapperCondition.columnName()
                        ,value);
            }
            if (WrapperTypes.NE.equals(wrapperCondition.wrapperType())) {
                queryWrapper.ne(value instanceof String ? StrUtil.isNotBlank((String)value)
                                : null != value
                        ,wrapperCondition.columnName()
                        ,value);
            }
            if (WrapperTypes.GT.equals(wrapperCondition.wrapperType())) {
                queryWrapper.gt(value instanceof String ? StrUtil.isNotBlank((String)value)
                                : null != value
                        ,wrapperCondition.columnName()
                        ,value);
            }
            if (WrapperTypes.GE.equals(wrapperCondition.wrapperType())) {
                queryWrapper.ge(value instanceof String ? StrUtil.isNotBlank((String)value)
                                : null != value
                        ,wrapperCondition.columnName()
                        ,value);
            }
            if (WrapperTypes.LT.equals(wrapperCondition.wrapperType())) {
                queryWrapper.lt(value instanceof String ? StrUtil.isNotBlank((String)value)
                                : null != value
                        ,wrapperCondition.columnName()
                        ,value);
            }
            if (WrapperTypes.LE.equals(wrapperCondition.wrapperType())) {
                queryWrapper.le(value instanceof String ? StrUtil.isNotBlank((String)value)
                                : null != value
                        ,wrapperCondition.columnName()
                        ,value);
            }
            if (WrapperTypes.LIKE.equals(wrapperCondition.wrapperType())) {
                queryWrapper.like(value instanceof String ? StrUtil.isNotBlank((String)value)
                                : null != value
                        ,wrapperCondition.columnName()
                        ,value);
            }
            if (WrapperTypes.NOT_LIKE.equals(wrapperCondition.wrapperType())) {
                queryWrapper.notLike(value instanceof String ? StrUtil.isNotBlank((String)value)
                                : null != value
                        ,wrapperCondition.columnName()
                        ,value);
            }
            if (WrapperTypes.IN.equals(wrapperCondition.wrapperType())) {
                queryWrapper.in(value instanceof String ? StrUtil.isNotBlank((String)value)
                                : value instanceof Collection ? CollectionUtil.isNotEmpty((Collection<?>) value)
                                : null != value
                        ,wrapperCondition.columnName()
                        ,value);
            }
        }
        if (CollectionUtil.isNotEmpty(groupByColumnName)) {
            String[] groupByArray = new String[groupByColumnName.size()];
            groupByArray = groupByColumnName.toArray(groupByArray);
            queryWrapper.groupBy(groupByArray);
        }
        if (CollectionUtil.isNotEmpty(orderByColumnName)) {
            String[] orderByArray = new String[orderByColumnName.size()];
            orderByArray = orderByColumnName.toArray(orderByArray);
            queryWrapper.orderBy(true, WrapperTypes.ASC.equals(orderByType),orderByArray);
        }
        conditionCache.setGroupByColumnName(groupByColumnName);
        conditionCache.setOrderByColumnName(orderByColumnName);
        conditionCache.setOrderByType(orderByType);
        conditionCacheMap.putIfAbsent(condition.getClass().getName(),conditionCache);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ConditionCache{
        private List<String> groupByColumnName = new ArrayList<>();
        private List<String> orderByColumnName = new ArrayList<>();
        private WrapperTypes orderByType = WrapperTypes.DESC;
    }

}
