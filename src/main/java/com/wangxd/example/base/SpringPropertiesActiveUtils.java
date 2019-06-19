package com.wangxd.example.base;


import com.wangxd.example.constant.SystemEnvConstants;
import com.wangxd.example.utils.StringUtils;

/**
 * spring属性active环境工具类.
 */
public class SpringPropertiesActiveUtils {

	public static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";
	
	/**
	 * 获取系统配置信息.
	 * @param key
	 * @return
	 */
	public static String getSystemProperty(String key){
		return System.getProperty(key);
	}
	
	/**
	 * 获取当前启用系统参数.
	 * @return
	 */
	public static String getActiveProfiles(){
		
		return getSystemProperty(SPRING_PROFILES_ACTIVE);
	}
	
	/**
	 * 获取当前系统纯净环境参数 runtask- run,uattask- uat.
	 */
	public static String getCurrentCleanActiveProfiles(){
		
		return getCleanActiveProfiles(getActiveProfiles());
	}
	
	/**
	 * 获取系统纯净环境参数runtask- run,uattask- uat.
	 * @param activeProfiles
	 * 
	 */
	public static String getCleanActiveProfiles(String activeProfiles){
		
		String cleanEnv = null;
		if(isRunEnv(activeProfiles)){
			cleanEnv = SystemEnvConstants.RUN_ENV;
		}else if(isProEnv(activeProfiles)){
			cleanEnv = SystemEnvConstants.PRO_ENV;
		}else if(isTestEnv(activeProfiles)){
			cleanEnv = SystemEnvConstants.TEST_ENV;
		}else if(isDevEnv(activeProfiles)){
			cleanEnv = SystemEnvConstants.DEV_ENV;
		}else{
			cleanEnv = activeProfiles;
		}
		return cleanEnv;
	}
	
	
	/**
	 * 是否是确认的系统环境.
	 * @param env
	 * @return
	 */
	public static boolean isConfirmEnv(String env){
		
		String activeProfiles = getActiveProfiles();
		return isConfirmEnv(activeProfiles,env);
	}
	
	/**
	 * 是否是确认的系统环境.
	 * @param env
	 * @return
	 */
	public static boolean isConfirmEnv(String activeProfiles,String env){
		if (StringUtils.indexOf(activeProfiles, env) >= 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否生产环境.
	 * @return
	 */
	public static boolean isRunEnv(){
		
		return isConfirmEnv(SystemEnvConstants.RUN_ENV);
	}
	
	/**
	 * 是否生产环境.
	 * @return
	 */
	public static boolean isRunEnv(String activeProfiles){
		
		return isConfirmEnv(activeProfiles,SystemEnvConstants.RUN_ENV);
	}
	
	/**
	 * 是否生产环境.
	 * @return
	 */
	public static boolean isProEnv(){
		
		return isConfirmEnv(SystemEnvConstants.PRO_ENV);
	}
	
	/**
	 * 是否生产环境.
	 * @return
	 */
	public static boolean isProEnv(String activeProfiles){
		
		return isConfirmEnv(activeProfiles,SystemEnvConstants.PRO_ENV);
	}
	
	/**
	 * 是否测试环境.
	 * @return
	 */
	public static boolean isTestEnv(){
		
		return isConfirmEnv(SystemEnvConstants.TEST_ENV);
	}
	
	/**
	 * 是否测试环境.
	 * @return
	 */
	public static boolean isTestEnv(String activeProfiles){
		
		return isConfirmEnv(activeProfiles,SystemEnvConstants.TEST_ENV);
	}
	
	/**
	 * 是否开发环境.
	 * @return
	 */
	public static boolean isDevEnv(){
		
		return isConfirmEnv(SystemEnvConstants.DEV_ENV);
	}
	
	/**
	 * 是否开发环境.
	 * @return
	 */
	public static boolean isDevEnv(String activeProfiles){
		
		return isConfirmEnv(activeProfiles,SystemEnvConstants.DEV_ENV);
	}
	
	
}
