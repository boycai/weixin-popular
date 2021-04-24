package weixin.popular.support;

import weixin.popular.error.WxErrorException;
import weixin.popular.support.handler.WeixinHandler;

/**
 * TicketManager ticket(jsapi | wx_card) 自动刷新
 * 
 * @author LiYi
 *
 */
public class TicketManager {
    // private static final Logger logger =
    // LoggerFactory.getLogger(TicketManager.class);
    private static WeixinHandler weixinHandler;

    // private static String jsapiTicketPrefixKey(String appId) {
    // return JS_TICKET_KEY_PREFIX + appId;
    // }

    // private static String wxcardTicketPrefixKey(String appId) {
    // return WXCARD_TICKET_KEY_PREFIX + appId;
    // }

    public static synchronized void updateJsapiTicket(String appId, String ticket, int expiresInSeconds) {
        weixinHandler.updateJsapiTicket(appId, ticket, expiresInSeconds);
    }

    public static void expireJsapiTicket(String appId) {
        weixinHandler.expireJsapiTicket(appId);
    }

    public static boolean isJsapiTicketExpired(String appId) {
        return weixinHandler.isJsapiTicketExpired(appId);
    }

    public static String getJsapiTicketInCache(String appId) {
        return weixinHandler.getJsapiTicketInCache(appId);
    }

    public static synchronized void updateWxCardTicket(String appId, String ticket, int expiresInSeconds) {
        weixinHandler.updateWxCardTicket(appId, ticket, expiresInSeconds);
    }

    public static void expireWxCardTicket(String appId) {
        weixinHandler.expireWxCardTicket(appId);
    }

    public static boolean isWxCardTicketExpired(String appId) {
        return weixinHandler.isWxCardTicketExpired(appId);
    }

    public static String getWxCardTicketInCache(String appId) {
        return weixinHandler.getWxCardTicketInCache(appId);
    }

    /**
     * 获取 jsapi ticket
     * 
     * @param appId
     *            appId
     * @return token
     * @throws WxErrorException
     */
    public static String getJsapiTicket(String appId) throws WxErrorException {
        return weixinHandler.getJsapiTicket(appId);
    }

    public static String getJsapiTicketOrNull(String appId) {
        try {
            return weixinHandler.getJsapiTicket(appId);
        } catch (WxErrorException e) {
        }
        return null;
    }

    /**
     * 获取 wxcard ticket
     * 
     * @param appId
     *            appId
     * @return token
     * @throws WxErrorException
     */
    public static String getWxCardTicket(String appId) throws WxErrorException {
        return weixinHandler.getWxCardTicket(appId);
    }

    public static String getWxCardTicketOrNull(String appId) {
        try {
            return weixinHandler.getWxCardTicket(appId);
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
    public static String getJsapiTicketForceRefresh(String appId) throws WxErrorException {
        return weixinHandler.getJsapiTicketForceRefresh(appId);
    }

    /**
     * 强制刷新 （15s内只会刷新一次）
     * 
     * @param appId
     * @return
     * @throws WxErrorException
     */
    public static String getWxCardTicketForceRefresh(String appId) throws WxErrorException {
        return weixinHandler.getWxCardTicketForceRefresh(appId);
    }

    static void setWeixinHandler(WeixinHandler handler) {
        weixinHandler = handler;
    }

}