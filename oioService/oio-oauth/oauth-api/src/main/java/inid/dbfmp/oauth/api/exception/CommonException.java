package inid.dbfmp.oauth.api.exception;

import indi.dbfmp.web.common.dto.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: CommonException
 * @since 2020/10/12 12:40 上午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommonException extends RuntimeException{

    private ResultCode resultCode = ResultCode.FAILED;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CommonException(String message) {
        super(message);
    }

    public CommonException(ResultCode resultCode,String message) {
        super(message);
        this.resultCode = resultCode;
    }
}
