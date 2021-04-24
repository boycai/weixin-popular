package weixin.popular.support.handler;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

/**
 * @author Cailin
 */
public interface CacheHandler {

	void setJedisPool(Pool<Jedis> jedisPool);

	void addSecret(String appid, String secret);

	String getSecret(String appId);

	boolean tryLock(String lockKey, String lockValue, int lockSeconds);

	String getCache(String key);

	void setCache(String key, String value, int expiresInSeconds);

	boolean hasCache(String key);

	void delCache(String key);

}