package com.wangxd.example.utils;

import com.wangxd.example.redis.RedisService;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 各种锁工具类
 */
public class LockUtil {
	
	/**
	 * 简单的分布式乐观锁
	 */
	public static abstract class SimpleOptLock {
		/** RedisKey */
		String key;
		/** 过期的时间 */
		int expiredSecond;
		RedisService redisService;
		
		public SimpleOptLock(String sKey, int expiredSecond, RedisService redisService) {
			this.key = sKey;
			this.expiredSecond = expiredSecond;
			this.redisService = redisService;
		}
		
		/**
		 * 如果没有存在key，回调此函数
		 */
		protected abstract void onGetLock();
		/**
		 * 如果存在key，回调此函数
		 */
		protected abstract void onFail();
		
		private void execute() {
			try {
				long incr = redisService.incr(key, expiredSecond);
				if (incr <= 1) {
					// 没有执行
					onGetLock();
				} else { 
					// 正在执行
					onFail();
				}
			} finally {
				redisService.del(key);
			}
		}
		public void trigger() {
			execute();
		} 
	}
	
	/**
	 * <code>SimPageHelper</code> 分页查询，直到查询到所有
	 */
	public static abstract class SimPageHelper<T> {

		public abstract List<T> query(int iPageSize, int iPageNo);
		
		/**
		 * 查询并汇总每次查询的结果
		 * @param pageSize
		 * @return
		 */
		public List<T> triggerQryForResult(int pageSize) {
			return this.trigger(pageSize, true);
		}
		/**
		 * @param pageSize
		 * @param summarizedResult
		 * @return
		 */
		private List<T> trigger(int pageSize, boolean summarizedResult) {
			int iCurrCount = 0;
			// 页码 , 从1开始
			int pageNo = 1;
			List<T> lstResult = new ArrayList<>(pageSize);
			do {
				List<T> lstTmp = this.query(pageSize, pageNo++);
				if (CollectionUtils.isNotEmpty(lstTmp) && summarizedResult) {
					lstResult.addAll(lstTmp);
				}
				// 当前查询的条数
				iCurrCount = CollectionUtils.isEmpty(lstTmp) ? 0 : lstTmp.size();
			} while (iCurrCount >= pageSize);

			return lstResult;
		}
		/**
		 * 查询不进行汇总查询的结果
		 */
		public void triggerQry(int pageSize) {
			trigger(pageSize, false);
		}
	}
}
