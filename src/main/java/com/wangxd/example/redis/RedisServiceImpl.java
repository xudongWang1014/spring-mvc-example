package com.wangxd.example.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;

/**
 * <code>RedisServiceImpl</code> Redis常用操作实现
 */
@Service
public class RedisServiceImpl implements RedisService {
	@Autowired
	private ShardedJedisPool shardedJedisPool;
	@Autowired
	private JedisPool jedisPool;
	
	@Override
	public long setnx(String sKey, final String value, int... aExpiredSeconds) {
		return new SharedExecutor<Long>(sKey) {
			@Override
			Long execute() {
				Long setnx = sharedJedis.setnx(key, value);
				// 设置成功才能设置过期的时间
				if (setnx != null && setnx == 1) {
					if (aExpiredSeconds != null && aExpiredSeconds.length > 0) {
						sharedJedis.expire(key, aExpiredSeconds[0]);
					}
				}
				return setnx;
			}
		}.triggerForResult();
	}
	
	@Override
	public Object eval(final String key, final List<String> keys, final List<String> args) {
		return new JedisExecutor<Object>(key) {
			@Override
			Object execute() {
				return jedis.eval(key, keys, args);
			}
		}.triggerForResult();
	}
	
	@Override
	public long incrBy(String key, final long increment, int... expiredSeconds) {
		return new SharedExecutor<Long>(key, expiredSeconds) {
			@Override
			Long execute() {
				return sharedJedis.incrBy(key, increment);
			}
		}.triggerForResult();
	}
	
	@Override
	public long incr(String sKey, int... expiredSeconds) {
		return new SharedExecutor<Long>(sKey, expiredSeconds) {
			@Override
			Long execute() {
				return sharedJedis.incr(key);
			}
		}.triggerForResult();
	}
	@Override
	public long incr(String sKey) {
		return new SharedExecutor<Long>(sKey) {
			@Override
			Long execute() {
				return sharedJedis.incr(key);
			}
		}.triggerForResult();
	}
	@Override
	public long del(String key) {
		return new SharedExecutor<Long>(key) {
			@Override
			Long execute() {
				return sharedJedis.del(key);
			}
		}.triggerForResult();
	}

	@Override
	public void set(String sKey, final String sValue, int... expiredSeconds) {
		new SharedExecutor<Void>(sKey, expiredSeconds) {
			@Override
			Void execute() {
				sharedJedis.set(key, sValue);
				return null;
			}
		}.triggerForResult();
	}
	@Override
	public void expire(String sKey, int... expiredSeconds) {
		new SharedExecutor<Void>(sKey, expiredSeconds) {
			@Override
			Void execute() {
				sharedJedis.expire(sKey, expiredSeconds[0]);
				return null;
			}
		}.triggerForResult();
	}

	@Override
	public String set(String sKey, final Object val, int... expiredSeconds) {
		return new SharedExecutor<String>(sKey, expiredSeconds) {
			@Override
			String execute() {
				return sharedJedis.set(key, JSONObject.toJSONString(val));
			}
		}.triggerForResult();
	}

	@Override
	public String get(String sKey) {
		return new SharedExecutor<String>(sKey) {
			@Override
			String execute() {
				return sharedJedis.get(key);
			}
		}.triggerForResult();
	}

	/**
	 * <code>Executor</code> SharedJedis执行回调函数
	 */
	abstract class SharedExecutor<T> {
		ShardedJedis sharedJedis;
		/** 键 */
		String key;
		/** 过期的秒数 */
		int[] expiredSeconds;

		public SharedExecutor(String sKey, int... expiredSeconds) {
			this.sharedJedis = shardedJedisPool.getResource();
			this.key = sKey;
			this.expiredSeconds = expiredSeconds;
		}

		/**
		 * 回调函数
		 * 
		 * @return
		 * 
		 */
		abstract T execute();

		public T triggerForResult() {
			T result = null;
			try {
				result = execute();
				// 过期时间设置
				if (expiredSeconds != null && expiredSeconds.length > 0) {
					sharedJedis.expire(key, expiredSeconds[0]);
				}
			} catch (Throwable e) {
				throw new RuntimeException("执行redis命令异常", e);
			} finally {
				if (sharedJedis != null) {
					shardedJedisPool.returnResource(this.sharedJedis);
				}
			}
			return result;
		}
	}
	
	/**
	 * <code>Executor</code> Jedis执行回调函数
	 */
	abstract class JedisExecutor<T> {
		Jedis jedis;
		/** 键 */
		String key;
		/** 过期的秒数 */
		int[] expiredSeconds;

		public JedisExecutor(String sKey, int... expiredSeconds) {
			this.jedis = jedisPool.getResource();
			this.key = sKey;
			this.expiredSeconds = expiredSeconds;
		}

		/**
		 * 回调函数
		 * 
		 * @return
		 * 
		 */
		abstract T execute();

		public T triggerForResult() {
			T result = null;
			try {
				result = execute();
				// 过期时间设置
				if (expiredSeconds != null && expiredSeconds.length > 0) {
					jedis.expire(key, expiredSeconds[0]);
				}
			} catch (Throwable e) {
				throw new RuntimeException("执行redis命令异常", e);
			} finally {
				if (jedis != null) {
					jedisPool.returnResource(this.jedis);
				}
			}
			return result;
		}
	}

	
}
