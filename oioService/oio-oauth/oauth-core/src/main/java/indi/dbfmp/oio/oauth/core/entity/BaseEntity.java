package indi.dbfmp.oio.oauth.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * @NAME: BaseEntity
 * @AUTHOR huangtai
 * @DATE: 2020/6/14 8:43 下午
 **/
@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 855245085046801803L;

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
    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

}
