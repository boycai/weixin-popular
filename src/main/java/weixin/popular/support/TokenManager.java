package weixin.popular.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;
import weixin.popular.error.WxErrorException;
import weixin.popular.support.handler.DefaultWeixinHandler;
import weixin.popular.support.handler.WeixinHandler;

import com.boycai.base.Utils;
import com.boycai.plugin.spring.util.SpringUtils;

/**
 * TokenManager token 自动刷新
 * 
 * @author LiYi
 *
 */
public class TokenManager {
    private static final Logger logger = LoggerFactory.getLogger(TokenManager.class);
    private static WeixinHandler weixinHandler;

    static {
        WeixinHandler handler = SpringUtils.getBeanOrNull(WeixinHandler.class);
        if (handler == null) {
            handler = new DefaultWeixinHandler();
        }
        setWeixinHandler(handler);
    }

    /**
     * 已废弃。请调用：{@link #addSecret(String, String)} <br>
     * 
     * @param jedisPool
     * @param appId
     * @param secret
     */
    @Deprecated
    public static void init(Pool<Jedis> jedisPool, final String appId, final String secret) {
        setJedisPool(jedisPool);
        //
        addSecret(appId, secret);
        // ticket
        // TicketManager.init(Pool<Jedis>, appId);
        logger.debug("appId: " + appId);
    }

    public static void setJedisPool(Pool<Jedis> jedisPool) {
        weixinHandler.setJedisPool(jedisPool);
    }

    public static void addSecret(String appId, String secret) {
        if (Utils.isEmpty(appId) || Utils.isEmpty(secret)) {
            return;
        }
        weixinHandler.addSecret(appId, secret);
    }

    public static String getSecret(String appId) {
        return weixinHandler.getSecret(appId);
    }

    // private static String prefixKey(String appId) {
    // return KEY_PREFIX + appId;
    // }

    public static synchronized void updateAccessToken(String appId, String accessToken, int expiresInSeconds) {
        weixinHandler.updateAccessToken(appId, accessToken, expiresInSeconds);
    }

    public static void expireToken(String appId) {
        weixinHandler.expireToken(appId);
    }

    public static boolean isTokenExpired(String appId) {
        // return jedis.ttl(KEY_PREFIX + appId) < 2;// 2s内过期
        return weixinHandler.isTokenExpired(appId);
    }

    public static String getTokenInCache(String appId) {
        return weixinHandler.getTokenInCache(appId);
    }

    /**
     * 获取 access_token
     * 
     * @param appId
     *            appId
     * @return token
     * @throws WxErrorException
     */
    public static String getToken(String appId) throws WxErrorException {
        return weixinHandler.getToken(appId);
    }

    public static String getTokenOrNull(String appId) {
        try {
            return weixinHandler.getToken(appId);
        } catch (WxErrorException e) {
        }
        return null;
    }

    /**
     * 强制刷新 （15s内只会刷新一次）
     * 
     * @param appId
     * @return
     * @throws WxErrorException
     */
    public static String getTokenForceRefresh(String appId) throws WxErrorException {
        return weixinHandler.getTokenForceRefresh(appId);
    }

    /**
     * 发生以下情况时说明access_token无效 (需要刷新token了)<br>
     * 40001 获取access_token时AppSecret错误，或者access_token无效<br>
     * 42001 access_token超时<br>
     * 40014 不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口<br>
     * 
     * @param appId
     * @param errcode
     * @return
     */
    public static boolean isTokenInvalid(String errcode) {
        return errcode != null && ("40001".equals(errcode) || "42001".equals(errcode) || "40014".equals(errcode));
    }

    // public static boolean isTokenExpired(String errcode) {
    // return errcode != null && "42001".equals(errcode) ||
    // "40014".equals(errcode);
    // }

    public static String refreshInvalidToken(String appId, String accessToken, String errcode) throws WxErrorException {
        if (isTokenInvalid(errcode)) {
            accessToken = getTokenForceRefresh(appId);
        }
        return accessToken;
    }

    public static void setWeixinHandler(WeixinHandler handler) {
        if (handler != null) {
            weixinHandler = handler;
            TicketManager.setWeixinHandler(handler);
        }
    }

}