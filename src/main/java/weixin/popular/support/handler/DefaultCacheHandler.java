package weixin.popular.support.handler;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;
import weixin.popular.util.JedisUtil;

import com.boycai.plugin.redis.RedisUtils;

public class DefaultCacheHandler implements CacheHandler {

	protected static final Map<String, String> secretMap = new HashMap<String, String>();
	// private static Map<String, String> tokenMap = new
	// ConcurrentHashMap<String, String>();
	protected static JedisUtil jedis;

	protected static void initRedis() {
		if (jedis == null) {
			// JedisPool pool2 = RedisUtils.getJedisPool();
			Pool<Jedis> pool2 = RedisUtils.getJedisPool();
			if (pool2 != null) {
				jedis = new JedisUtil(pool2);
			}
		}
	}

	public DefaultCacheHandler() {
		initRedis();
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

}