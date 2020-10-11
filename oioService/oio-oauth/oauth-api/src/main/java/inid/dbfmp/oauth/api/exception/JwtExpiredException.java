package inid.dbfmp.oauth.api.exception;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: JwtExpiredException
 * @since 2020/10/11 4:18 下午
 */
public class JwtExpiredException extends RuntimeException{

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public JwtExpiredException(String message) {
        super(message);
    }
}
