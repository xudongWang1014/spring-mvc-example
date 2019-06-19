package com.wangxd.example.redis;

import java.util.List;

/**
 * <code>RedisService</code> Redis常用操作接口
 */
public interface RedisService {
	

	
	/**
	 * 将 key 的值设为 value ，当且仅当 key 不存在。
	 * 给定的 key 已经存在，则 SETNX 不做任何动作。
	 * SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。
	 * @param sKey
	 * @param expiredSeconds
	 * @return 设置成功，返回 1 。 设置失败，返回 0 。
	 * 
	 */
	long setnx(String sKey, String value, int... expiredSeconds);
	/**
	 * 从 Redis 2.6.0 版本开始，通过内置的 Lua 解释器，可以使用 EVAL 命令对 Lua 脚本进行求值。<br>
	 * script 参数是一段 Lua 5.1 脚本程序，它会被运行在 Redis 服务器上下文中，这段脚本不必(也不应该)定义为一个 Lua 函数。<br>
	 * numkeys 参数用于指定键名参数的个数。<br>
	 * 键名参数 key [key ...] 从 EVAL 的第三个参数开始算起，表示在脚本中所用到的那些 Redis 键(key)，这些键名参数可以在 Lua 中通过全局变量 KEYS 数组，用 1 为基址的形式访问( KEYS[1] ， KEYS[2] ，以此类推)。
	 * 在命令的最后，那些不是键名参数的附加参数 arg [arg ...] ，可以在 Lua 中通过全局变量 ARGV 数组访问，访问的形式和 KEYS 变量类似( ARGV[1] 、 ARGV[2] ，诸如此类)。
	 * @param key
	 * @param keys
	 * @param args
	 * @return
	 * 
	 */
	Object eval(String key, List<String> keys, List<String> args);
	
	/**
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。<br>
	 * 对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除
	 * @param key
	 * @param value
	 * @param expiredSeconds - 过期的秒数
	 * 
	 */
	void set(String key, final String value, int... expiredSeconds);

	/**
	 * 在 Redis 2.6.12 版本以前， SET 命令总是返回 OK 。<br>
	 * 从 Redis 2.6.12 版本开始， SET 在设置操作成功完成时，才返回 OK 。<br>
	 * 如果设置了 NX 或者 XX ，但因为条件没达到而造成设置操作未执行，那么命令返回空批量回复（NULL Bulk Reply）
	 * 
	 * @param key
	 * @param val  - 转换成JSON格式
	 * @param expiredSeconds
	 * 
	 */
	String set(String key, final Object val, int... expiredSeconds);
	/**
	 * 
	 * 刷新Key的过期时间
	 *
	 * @param sKey
	 * @param expiredSeconds
	 */
	public void expire(String sKey, int... expiredSeconds);

	/**
	 * @param key
	 * 
	 */
	String get(String key);

	/**
	 * 将 key 中储存的数字值增一。<br>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。<br>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。<br>
	 * 本操作的值限制在 64 位(bit)有符号数字表示之内
	 * 
	 * @param key
	 * @param expiredSeconds
	 * @return 执行INCR 命令之后 key 的值
	 * 
	 */
	long incr(String key, int... expiredSeconds);
	/**
	 * 将 key 中储存的数字值增一。<br>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。<br>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。<br>
	 * 本操作的值限制在 64 位(bit)有符号数字表示之内
	 * 
	 * @param key
	 * @return 执行INCR 命令之后 key 的值
	 * 
	 */
	long incr(String key);
	
	/**
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。<br>
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。<br>
	 * 本操作的值限制在 64 位(bit)有符号数字表示之内。<br>
	 * 关于递增(increment) / 递减(decrement)操作的更多信息<br>
	 * 
	 * <pre>
	 * 1.该方法会保留原有过期时间，不会刷新
	 * </pre>
	 * 
	 * @param key 
	 * @return 加上 increment 之后， key 的值。
	 * 
	 */
	long incrBy(String key, long increment, int... expiredSeconds);
	
	/**
	 * @param key
	 * @return 被删除 key的数量
	 * 
	 */
	long del(String key);
}
