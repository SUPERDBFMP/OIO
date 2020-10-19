package indi.dbfmp.oio.oauth.core.constants;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: TokenRedisConstants
 * @since 2020/10/12 10:37 下午
 */
public class TokenRedisConstants {

    /**
     * ${userId}:${appType}:${clientId}
     */
    public final static String TOKEN_KEY = "Token:{}:{}:{}";
    public final static String REFRESH_TOKEN_KEY = "RefreshToken:{}:{}:{}";
    public final static String LOCK_REFRESH_TOKEN_KEY = "LockRefreshToken:{}:{}:{}";
    public final static String OLD_TOKEN_KEY = "OldToken:{}:{}:{}";
    public final static String OLD_REFRESH_TOKEN_KEY = "OldRefreshToken:{}:{}:{}";

}
