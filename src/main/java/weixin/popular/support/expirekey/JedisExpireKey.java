package weixin.popular.support.expirekey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;
import weixin.popular.support.ExpireKey;

public class JedisExpireKey implements ExpireKey {
	
	private static Logger logger = LoggerFactory.getLogger(JedisExpireKey.class);

	private Pool<Jedis> pool;

	private static final String DEFAULT_VALUE = "";

	private String perfix = "WP_ExpireKey_";

	public JedisExpireKey() {
	}

	public JedisExpireKey(Pool<Jedis> pool) {
		this.pool = pool;
	}

	public void setPerfix(String perfix) {
		this.perfix = perfix;
	}

	public void setPool(Pool<Jedis> pool) {
		this.pool = pool;
	}

	@Override
	public boolean add(String key, int expire) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.setex(perfix + key, expire, DEFAULT_VALUE);
			return true;
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return false;
	}

	@Override
	public boolean add(String key) {
		return add(key, DEFAULT_EXPIRE);
	}

	@Override
	public boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.exists(perfix + key);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return false;
	}

}
