package inid.dbfmp.oauth.api.enums;

import lombok.AllArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: StatusEnums
 * @since 2020/10/12 12:29 上午
 */
@AllArgsConstructor
public enum  StatusEnums implements IntEnums {

    VALID(1,"有效"),
    UN_VALID(0,"无效"),
    ;

    private final int code;
    private final String desc;


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}
