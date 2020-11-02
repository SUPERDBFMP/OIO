package indi.dbfmp.oio.oauth.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import indi.dbfmp.validator.core.group.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @NAME: BaseEntity
 * @AUTHOR huangtai
 * @DATE: 2020/6/14 8:43 下午
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@SuperBuilder
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 855245085046801803L;

    @NotBlank(message = "主键不能为空",groups = UpdateGroup.class)
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Date createDate;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Date updateDate;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 版本号
     */
    @NotNull(message = "version不能为空",groups = UpdateGroup.class)
    @Version
    private Integer version;

    //@TableLogic
    private Integer deleted;

    public static final String ID = "id";

    public static final String CREATE_BY = "create_by";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_BY = "update_by";

    public static final String UPDATE_DATE = "update_date";

    public static final String REMARKS = "remarks";

    public static final String VERSION = "version";

    public static final String DELETED = "deleted";

}
