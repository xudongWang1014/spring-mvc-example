package com.wangxd.example.rabbitMQ;

import org.springframework.amqp.core.MessageListener;

/***
 * 消费者接口
 */
public interface MQConsumer extends MessageListener {

}
