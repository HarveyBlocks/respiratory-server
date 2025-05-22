package org.harvey.respiratory.server.util;

/**
 * Redis有关的常量
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-06-01 15:17
 */
public class RedisConstants {
    public static final String LOCK_KEY_PREFIX = "respiratory:lock:";
    public static final String NORMAL_KEY_PREFIX = "respiratory:normal:";
    public static final String LOGIN_CODE_KEY = NORMAL_KEY_PREFIX + "login:code:";
    public static final Long LOGIN_CODE_TTL = 3 * 60L;
    public static final String QUERY_USER_KEY = NORMAL_KEY_PREFIX + "user:query:token:";
    public static final Long QUERY_USER_TTL = 30 * 60L;

    public static final Long CACHE_NULL_TTL = 2L;
    public static final String USER_LOCK_KEY = LOCK_KEY_PREFIX + "user:";
    public static final long LOCK_TTL = 6 * 60L;
    public static final String NULL_DATA = "";
    public static final long VALUE_CACHE_TTL = 5 * 60L;

}
