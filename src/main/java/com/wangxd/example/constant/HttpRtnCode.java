package com.wangxd.example.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 常用HTTP返回code
 */
public enum HttpRtnCode {
	/**
	 * 200, "OK"
	 */
	 OK(200, "OK"),
	/**
	 * 304, "Not Modified"
	 */
	 NOT_MODIFIED(304, "Not Modified"),
	/**
	 * 400, "Bad Request"
	 */	 
	 BAD_REQUEST(400, "Bad Request"),
	/**
	 * 401, "Unauthorized"
	 */
	 UNAUTHORIZED(401, "Unauthorized"),
	/**
	 * 403, "Forbidden"
	 */
	 FORBIDDEN(403, "Forbidden"),
	/**
	 * 404, "Not Found"
	 */
	 NOT_FOUND(404, "Not Found"),
	/**
	 * 405, "Method Not Allowed"
	 */
	 METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	/**
	 * 408, "Request Timeout"
	 */
	 REQUEST_TIMEOUT(408, "Request Timeout"),
	/**
	 * 500, "Server Error"
	 */
	 SERVER_ERROR(500, "Server Error");
	
	 
	  private final int code;
	  private final String msg;
	  
	  private HttpRtnCode(int code, String msg) {
	    this.code = code;
		this.msg = msg;
	  }
	  
	  public int getCode() {
	    return code;
	  }
	  public String getMsg() {
	    return msg;
	  }	
	  
	  public String getCodeStr(){
		  return code+"";
	  }

	  /** 
	    * 根据key获取value 
	   */  
	  public static String getValueByKey(String key) {  
    	 HttpRtnCode[] enums = HttpRtnCode.values();  
         for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getCodeStr().equals(key)) {  
                return enums[i].getMsg();  
            }  
         }  
         return "";  
	  }  
	  
     /** 
       * 转换为MAP集合 
       */  
      public static Map<String, String> toHashMap() {  
        Map<String, String> map = new HashMap<String, String>();  
        HttpRtnCode[] enums = HttpRtnCode.values();  
        for (int i = 0; i < enums.length; i++) {  
            map.put(enums[i].getCodeStr(), enums[i].getMsg());  
         }  
        return map;  
     }  
}
