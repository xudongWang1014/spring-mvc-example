package com.wangxd.example.utils;

import java.util.UUID;

/**
 * <code>StringUtils</code> 字符串常用方法
 *
 * @author Jimmy
 * @version v1.0.0
 * @since 2017年5月25日
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	/** 分隔符 */
	public static final String SEQ = "_";
	/** 空的字符数组 */
	public static final String[] EMPTY_STRING_ARRAY = new String[]{};
	/** 逗号分隔符 */
	public static final String COMMA_SEQ = ",";
	
	/**
	 * 判断0-N个字符串是否为空
	 * <pre>
	 * 1.参数为空(null or "" " ")。 返回true
	 * 2.只要有一个参数为空，返回true
	 * 3.否则返回false
	 * </pre>
	 * @param arrStr
	 * @return
	 * 
	 */
	public static boolean isContainsBlank(String... arrStr) {
		if (arrStr == null || arrStr.length == 0) {
			return true;
		}
		for (int i = 0; i < arrStr.length; i++) {
			if (isBlank(arrStr[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据指定的最大长度返回结果
	 * 
	 * @param sContent - 需要截取的内容
	 * @param iMaxLen - 最大的长度
	 * @return
	 */
	public static String subString(String sContent, int iMaxLen) {
		if (StringUtils.isEmpty(sContent) || iMaxLen < 0) {
			return sContent;
		}
		int iLen = sContent.length();
		if (iLen < iMaxLen) {
			return sContent;
		}
		return sContent.substring(0, iMaxLen);
	}
	
	/**
	 * 如果为Null则返回默认值
	 * 
	 * @param oT
	 * @param defVal
	 * @return
	 */
	public static <T> T getDefIfNull(T oT, T defVal) {
		if (oT == null) {
			oT = defVal;
		}
		return oT;
	}
	
	/**
	 *  生成业务唯一Key 
	 * @param sPrefix - 前缀
	 * @param randomLen - 随机码的长度， 不包括前缀的长度 (默认长度是12)
	 * @return
	 * 
	 */
	public static String genLogicUniqueKey(String sPrefix, int... randomLen) {
		// 默认的长度是12
		int iRandomLen = (randomLen == null || randomLen.length <= 0) ? 12 : randomLen[0];
		String sRandom = UUID.randomUUID().toString().replaceAll("-", "");
		int iMaxLen = sRandom.length();
		StringBuilder sb = new StringBuilder(sPrefix);
		sb.append(sRandom.substring(iMaxLen - iRandomLen, iMaxLen));
		return sb.toString();
	}
	
	/**
	 * 获取唯一
	 * @return
	 * 
	 */
	public static String uniqueKey() {
		 return UUID.randomUUID().toString().replaceAll("-", "");
	}
	

}
