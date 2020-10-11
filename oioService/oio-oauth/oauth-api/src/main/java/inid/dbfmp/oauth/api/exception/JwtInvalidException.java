package inid.dbfmp.oauth.api.exception;

/**
 * <p>
 *  token校验异常
 * </p>
 *
 * @author dbfmp
 * @name: JwtInvalidException
 * @since 2020/10/11 4:17 下午
 */
public class JwtInvalidException  extends RuntimeException{

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public JwtInvalidException(String message) {
        super(message);
    }
}
