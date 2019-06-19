package com.wangxd.example.rabbitMQ.impl;

import com.wangxd.example.rabbitMQ.MQConsumer;
import com.wangxd.example.utils.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;



@Service
public class TestConsumerImpl implements MQConsumer {

	@Override
	public void onMessage(Message msg) {
		try {
			String contentEncoding = msg.getMessageProperties().getContentEncoding();
			if(StringUtils.isBlank(contentEncoding)){
				contentEncoding = "UTF-8";
			}
			String message = new String(msg.getBody(), contentEncoding);

			System.out.println("测试消费者:" + message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
