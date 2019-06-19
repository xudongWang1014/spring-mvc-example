package com.wangxd.example.base;

/**
 * 统一业务异常封装
 */
public class BaseException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**错误码**/
	private int bizCode;
	/**业务简单描述**/
	private String msg;
	/**业务数据**/
	private Object data;
	/**具体异常对象**/
	private Throwable throwable;


	public BaseException(){}
	
	public BaseException(String msg){
		super(msg);
		this.msg=msg;
	}
	public BaseException(int bizCode, String msg){
		super(msg);
		this.bizCode = bizCode;
		this.msg =  msg;
	}
	public BaseException(Throwable exception, int bizCode){
		super(exception);
		this.bizCode = bizCode;
		this.throwable =  exception;
	}
	public BaseException(Throwable exception, String msg){
		super(msg,exception);
		this.msg =  msg;
		this.throwable =  exception;
	}
	public BaseException(Throwable exception, int bizCode , String msg){
		super(msg,exception);
		this.bizCode = bizCode;
		this.throwable =  exception;
		this.msg =  msg;
	}
	public BaseException(Throwable exception, int bizCode , String msg, Object data){
		super(msg,exception);
		this.bizCode = bizCode;
		this.throwable =  exception;
		this.msg =  msg;
		this.data =  data;
	}
	public int getBizCode() {
		return bizCode;
	}
	public String getBizCodeStr() {
		return bizCode+"";
	}
	public void setBizCode(int bizCode) {
		this.bizCode = bizCode;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String exMsg) {
		this.msg = exMsg;
	}
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
