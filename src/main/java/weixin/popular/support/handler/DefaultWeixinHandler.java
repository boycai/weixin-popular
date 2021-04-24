package weixin.popular.support.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;
import weixin.popular.api.TicketAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.ticket.Ticket;
import weixin.popular.bean.token.Token;
import weixin.popular.error.WxErrorException;
import weixin.popular.support.TokenManager;
import weixin.popular.util.JedisUtil;

import com.boycai.plugin.redis.RedisUtils;

public class DefaultWeixinHandler implements WeixinHandler {

    protected final Map<String, String> secretMap = new HashMap<String, String>();
    // private static Map<String, String> tokenMap = new
    // ConcurrentHashMap<String, String>();
    protected JedisUtil jedis;

    public DefaultWeixinHandler() {
        TokenManager.setWeixinHandler(this);
        //
        Pool<Jedis> pool2 = getJedisPool();
        if (pool2 != null) {
            this.jedis = new JedisUtil(pool2);
        }
    }

    protected Pool<Jedis> getJedisPool() {
        return RedisUtils.getJedisPool();
    }

    @Override
    public void setJedisPool(Pool<Jedis> pool) {
        if (pool != null) {
            if (jedis == null || jedis.getPool() != pool) {
                jedis = new JedisUtil(pool);
            }
        }
    }

    @Override
    public void addSecret(String appId, String secret) {
        secretMap.put(appId, secret);
    }

    @Override
    public String getSecret(String appId) {
        return secretMap.get(appId);
    }

    @Override
    public boolean tryLock(String lockKey, String lockValue, int lockSeconds) {
        return jedis.setIfNotExist(lockKey, lockValue, lockSeconds);
    }

    @Override
    public String getCache(String key) {
        return jedis.get(key);
    }

    @Override
    public void setCache(String key, String value, int expiresInSeconds) {
        jedis.setex(key, value, expiresInSeconds);
    }

    @Override
    public boolean hasCache(String key) {
        return jedis.ttl(key) > 2;// 有效期大于2s时
    }

    @Override
    public void delCache(String key) {
        jedis.expire(key, 0);
    }

    // -----------------------------------

    protected final Logger logger = LoggerFactory.getLogger(DefaultWeixinHandler.class);

    protected final String TOKEN_KEY_PREFIX = "wechat_access_token_";
    protected final String LOCK_KEY_PREFIX = "wechat_access_token_lock_";
    protected final int LOCK_TIME = 15;// 15s后过期
    protected final int GET_WAITING_TIME = 6;// 获取时最大等待时间6s
    protected Lock tokenLock = new ReentrantLock();

    // -----------------------------------
    protected final String JSAPI_TICKET_KEY_PREFIX = "wechat_jsapi_ticket_";
    protected final String JSAPI_LOCK_KEY_PREFIX = "wechat_jsapi_ticket_lock_";
    protected Lock ticketJsapiLock = new ReentrantLock();

    protected final String WXCARD_TICKET_KEY_PREFIX = "wechat_wxcard_ticket_";
    protected final String WXCARD_LOCK_KEY_PREFIX = "wechat_wxcard_ticket_lock_";
    protected Lock ticketWxcardLock = new ReentrantLock();

    protected final String TICKET_TYPE_JSAPI = "jsapi";
    protected final String TICKET_TYPE_WXCARD = "wx_card";

    // -----------------------------------

    /**
     * 获取 access_token
     * 
     * @param appId
     *            appId
     * @return token
     * @throws WxErrorException
     */
    @Override
    public String getToken(String appId) throws WxErrorException {
        if (appId == null || appId.isEmpty()) {
            return null;
        }
        String accessToken = getTokenInCache(appId);
        if (accessToken == null) {
            return getTokenForceRefresh(appId);
        }
        return accessToken;
    }

    @Override
    public String getTokenInCache(String appId) {
        return getCache(TOKEN_KEY_PREFIX + appId);
    }

    /**
     * 强制刷新 （15s内只会刷新一次）
     * 
     * @param appId
     * @return
     * @throws WxErrorException
     */
    @Override
    public String getTokenForceRefresh(String appId) throws WxErrorException {
        try {
            tokenLock.lock();

            // token即将或者已经过期
            boolean success = tryLock(LOCK_KEY_PREFIX + appId, "", LOCK_TIME);
            if (success) {
                // 获取锁成功
                Token token = TokenAPI.token(appId, getSecret(appId));
                // {"access_token":"ACCESS_TOKEN","expires_in":7200}
                if (!token.isSuccess() || token.getAccess_token() == null) {
                    throw new WxErrorException(token);
                }
                updateAccessToken(appId, token.getAccess_token(), token.getExpires_in());
                logger.info("access_token refresh success. appId: " + appId);
                return token.getAccess_token();
            } else {
                // 获取锁失败
                // if(isTokenExpired(appId)) { }
                String accessToken = getTokenInCache(appId);
                if (accessToken == null) {
                    int t = 0;
                    while (accessToken == null && t++ < GET_WAITING_TIME) {
                        Thread.sleep(1000);
                        accessToken = getTokenInCache(appId);
                    }
                }
                return accessToken;
            }
        } catch (WxErrorException e) {
            logger.error("access_token refresh error. appId: " + appId + ", result: " + String.valueOf(e.getError()));
            throw e;
        } catch (Exception ex) {
            logger.error("access_token refresh error. appId: " + appId, ex);
        } finally {
            tokenLock.unlock();
        }
        return getTokenInCache(appId);
    }

    @Override
    public synchronized void updateAccessToken(String appId, String accessToken, int expiresInSeconds) {
        setCache(TOKEN_KEY_PREFIX + appId, accessToken, expiresInSeconds - 100);
    }

    @Override
    public void expireToken(String appId) {
        delCache(TOKEN_KEY_PREFIX + appId);
    }

    @Override
    public boolean isTokenExpired(String appId) {
        // return jedis.ttl(KEY_PREFIX + appId) < 2;// 2s内过期
        return !hasCache(TOKEN_KEY_PREFIX + appId);
    }

    // -----------------------------------

    @Override
    public synchronized void updateJsapiTicket(String appId, String ticket, int expiresInSeconds) {
        setCache(JSAPI_TICKET_KEY_PREFIX + appId, ticket, expiresInSeconds - 100);
    }

    @Override
    public void expireJsapiTicket(String appId) {
        delCache(JSAPI_TICKET_KEY_PREFIX + appId);
    }

    @Override
    public boolean isJsapiTicketExpired(String appId) {
        return !hasCache(JSAPI_TICKET_KEY_PREFIX + appId);
    }

    @Override
    public String getJsapiTicketInCache(String appId) {
        return getCache(JSAPI_TICKET_KEY_PREFIX + appId);
    }

    @Override
    public synchronized void updateWxCardTicket(String appId, String ticket, int expiresInSeconds) {
        setCache(WXCARD_TICKET_KEY_PREFIX + appId, ticket, expiresInSeconds - 100);
    }

    @Override
    public void expireWxCardTicket(String appId) {
        delCache(WXCARD_TICKET_KEY_PREFIX + appId);
    }

    @Override
    public boolean isWxCardTicketExpired(String appId) {
        return !hasCache(WXCARD_TICKET_KEY_PREFIX + appId);
    }

    @Override
    public String getWxCardTicketInCache(String appId) {
        return getCache(WXCARD_TICKET_KEY_PREFIX + appId);
    }

    /**
     * 获取 JsapiTicket
     * 
     * @param appId
     *            appId
     * @return token
     * @throws WxErrorException
     */
    @Override
    public String getJsapiTicket(String appId) throws WxErrorException {
        if (appId == null || appId.isEmpty()) {
            return null;
        }
        String ticket = getJsapiTicketInCache(appId);
        if (ticket == null) {
            return getJsapiTicketForceRefresh(appId);
        }
        return ticket;
    }

    /**
     * 获取 WxCard Ticket
     * 
     * @param appId
     *            appId
     * @return token
     * @throws WxErrorException
     */
    @Override
    public String getWxCardTicket(String appId) throws WxErrorException {
        if (appId == null || appId.isEmpty()) {
            return null;
        }
        String ticket = getWxCardTicketInCache(appId);
        if (ticket == null) {
            return getWxCardTicketForceRefresh(appId);
        }
        return ticket;
    }

    /**
     * 强制刷新 （15s内只会刷新一次）
     * 
     * @param appId
     * @return
     * @throws WxErrorException
     */
    @Override
    public String getJsapiTicketForceRefresh(String appId) throws WxErrorException {
        try {
            ticketJsapiLock.lock();
            // token即将或者已经过期
            boolean isLocked = tryLock(JSAPI_LOCK_KEY_PREFIX + appId, "", LOCK_TIME);
            if (isLocked) {
                // 获取锁成功
                String access_token = TokenManager.getToken(appId);
                Ticket ticket = TicketAPI.ticketGetticket(access_token, TICKET_TYPE_JSAPI);
                if (!ticket.isSuccess() || ticket.getTicket() == null) {
                    throw new WxErrorException(ticket);
                }
                updateJsapiTicket(appId, ticket.getTicket(), ticket.getExpires_in());
                logger.info("jsapi_ticket refresh success. appId: " + appId);
                return ticket.getTicket();
            } else {
                // 获取锁失败
                String ticket = getJsapiTicketInCache(appId);
                if (ticket == null) {
                    int t = 0;
                    while (ticket == null && t++ < GET_WAITING_TIME) {
                        Thread.sleep(1000);
                        ticket = getJsapiTicketInCache(appId);
                    }
                }
                return ticket;
            }
        } catch (WxErrorException e) {
            logger.error("jsapi_ticket refresh error. appId: " + appId + ", result: " + String.valueOf(e.getError()));
            throw e;
        } catch (Exception e) {
            logger.error("jsapi_ticket refresh error. appId: " + appId, e);
        } finally {
            ticketJsapiLock.unlock();
        }
        return getJsapiTicketInCache(appId);
    }

    /**
     * 强制刷新 （15s内只会刷新一次）
     * 
     * @param appId
     * @return
     * @throws WxErrorException
     */
    @Override
    public String getWxCardTicketForceRefresh(String appId) throws WxErrorException {
        try {
            ticketWxcardLock.lock();

            // token即将或者已经过期
            boolean success = tryLock(WXCARD_LOCK_KEY_PREFIX + appId, "", LOCK_TIME);
            if (success) {
                // 获取锁成功
                String access_token = TokenManager.getToken(appId);
                Ticket ticket = TicketAPI.ticketGetticket(access_token, TICKET_TYPE_WXCARD);
                if (!ticket.isSuccess() || ticket.getTicket() == null) {
                    throw new WxErrorException(ticket);
                }
                updateWxCardTicket(appId, ticket.getTicket(), ticket.getExpires_in());
                logger.info("wxcard_ticket refresh success. appId: " + appId);
                return ticket.getTicket();
            } else {
                // 获取锁失败
                String ticket = getWxCardTicketInCache(appId);
                if (ticket == null) {
                    int t = 0;
                    while (ticket == null && t++ < GET_WAITING_TIME) {
                        Thread.sleep(1000);
                        ticket = getWxCardTicketInCache(appId);
                    }
                }
                return ticket;
            }
        } catch (WxErrorException e) {
            logger.error("wxcard_ticket refresh error. appId: " + appId + ", result: " + String.valueOf(e.getError()));
            throw e;
        } catch (Exception e) {
            logger.error("wxcard_ticket refresh error. appId: " + appId, e);
        } finally {
            ticketWxcardLock.unlock();
        }
        return getWxCardTicketInCache(appId);
    }

}