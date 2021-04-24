package weixin.popular.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

public class JedisUtil {

	private static Logger logger = LoggerFactory.getLogger(JedisUtil.class);

	// private JedisPool pool;
	private Pool<Jedis> pool;

	// private static final String DEFAULT_VALUE = "";
	// private static final int DEFAULT_EXPIRE = 12;// 12s

	private static final String STATUS_OK = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_IF_IS_EXIST = "XX";

	private static final String SET_WITH_EXPIRE_SECONDS = "EX";// 秒
	private static final String SET_WITH_EXPIRE_MILLISECONDS = "PX";// 毫秒

	@SuppressWarnings("unused")
	private JedisUtil() {
	}

	public JedisUtil(Pool<Jedis> pool) {
		this.pool = pool;
	}

	public void setPool(Pool<Jedis> pool) {
		this.pool = pool;
	}

	public Pool<Jedis> getPool() {
		return pool;
	}

	public boolean setex(String key, String value, int expire) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			String result = jedis.setex(key, expire, value);
			return STATUS_OK.equals(result);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return false;
	}

	public boolean set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.set(key, value);
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

	/**
	 * 添加一条记录，仅当给定的key不存在时才插入
	 * 
	 * @param String
	 *            key
	 * @param String
	 *            value
	 * @return long 状态码，1插入成功且key不存在，0未插入，key存在
	 */
	public Long setnx(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.setnx(key, value);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null;
	}

	/**
	 * @param key
	 * @param value
	 * @param seconds
	 *            过期时间，单位秒
	 * @return
	 */
	public boolean setIfNotExist(String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			String result = jedis.set(key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_SECONDS, seconds);
			return STATUS_OK.equals(result);
		} catch (Exception e) {
			logger.error("jedis set error, key: {}", key, e);
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.get(key);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null;
	}

	public boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return false;
	}

	public boolean expire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.expire(key, seconds);
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

	public Long ttl(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.ttl(key);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0L;
	}

	public Long del(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.del(key);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0L;
	}

}
