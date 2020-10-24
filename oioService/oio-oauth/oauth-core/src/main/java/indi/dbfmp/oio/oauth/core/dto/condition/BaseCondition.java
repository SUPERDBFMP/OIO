package indi.dbfmp.oio.oauth.core.dto.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import indi.dbfmp.oio.oauth.core.enums.WrapperTypes;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  基础查询
 * </p>
 *
 * @author dbfmp
 * @name: BaseCondition
 * @since 2020/10/22 11:18 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class BaseCondition implements Serializable {

    private static final long serialVersionUID = -5919830914471574968L;

    private String id;

    @WrapperCondition(columnName = BaseEntity.CREATE_DATE,wrapperType = WrapperTypes.GE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDateTimeStart;

    @WrapperCondition(columnName = BaseEntity.CREATE_DATE,wrapperType = WrapperTypes.LE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDateTimeEnd;

    @WrapperCondition(columnName = BaseEntity.UPDATE_DATE,wrapperType = WrapperTypes.GE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDateTimeStart;

    @WrapperCondition(columnName = BaseEntity.UPDATE_DATE,wrapperType = WrapperTypes.LE,orderByColumn = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDateTimeEnd;

    private Integer pageNum = 1;

    private Integer pageSize = 20;

}
