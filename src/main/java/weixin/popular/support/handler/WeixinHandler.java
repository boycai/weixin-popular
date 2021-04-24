package weixin.popular.support.handler;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;
import weixin.popular.error.WxErrorException;

/**
 * @author Cailin
 */
public interface WeixinHandler {

    void setJedisPool(Pool<Jedis> jedisPool);

    void addSecret(String appid, String secret);

    String getSecret(String appId);

    boolean tryLock(String lockKey, String lockValue, int lockSeconds);

    String getCache(String key);

    void setCache(String key, String value, int expiresInSeconds);

    boolean hasCache(String key);

    void delCache(String key);

    // ----------------------------------

    String getToken(String appId) throws WxErrorException;

    String getTokenInCache(String appId);

    String getTokenForceRefresh(String appId) throws WxErrorException;

    void updateAccessToken(String appId, String accessToken, int expiresInSeconds);

    void expireToken(String appId);

    boolean isTokenExpired(String appId);

    // ----------------------------------

    String getJsapiTicket(String appId) throws WxErrorException;

    String getWxCardTicket(String appId) throws WxErrorException;

    String getJsapiTicketInCache(String appId);

    String getWxCardTicketInCache(String appId);

    String getJsapiTicketForceRefresh(String appId) throws WxErrorException;

    String getWxCardTicketForceRefresh(String appId) throws WxErrorException;

    void updateJsapiTicket(String appId, String ticket, int expiresInSeconds);

    void updateWxCardTicket(String appId, String ticket, int expiresInSeconds);

    void expireJsapiTicket(String appId);

    void expireWxCardTicket(String appId);

    boolean isJsapiTicketExpired(String appId);

    boolean isWxCardTicketExpired(String appId);

    // ----------------------------------

}