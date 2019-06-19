package com.wangxd.example.rabbitMQ.impl;

import com.alibaba.fastjson.JSONObject;
import com.wangxd.example.rabbitMQ.MQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * 消息生产者实现类
 */
@Service
public class MQProducerImpl implements MQProducer {

	private static final Logger logger = LoggerFactory.getLogger(MQProducerImpl.class);

	@Autowired
	private AmqpTemplate amqpTemplate;

	/**
     * 发送消息
     */
	@Override
	public void sendMQ(String routingKey, Object object)throws Exception {
		//调用api发送消息
		amqpTemplate.convertAndSend(routingKey, object);
		
		//发送成功
		String resMsg = "";
		
		if(object instanceof String){
			resMsg = object.toString();
		 }else{
			resMsg = JSONObject.toJSONString(object);
		} 

		logger.info("【MQ】send to [routingKey]{},[message]{}", routingKey, resMsg);

	}
	
}
