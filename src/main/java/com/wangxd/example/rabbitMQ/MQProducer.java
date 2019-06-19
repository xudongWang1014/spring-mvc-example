package com.wangxd.example.rabbitMQ;

/***
 * 消息生产者
 */
public interface MQProducer {
	
	/**
	 * 发送消息
	 */
	void sendMQ(String routingKey, Object object)throws Exception;
}
