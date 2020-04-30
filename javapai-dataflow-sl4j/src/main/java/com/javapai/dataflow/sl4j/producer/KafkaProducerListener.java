package com.javapai.dataflow.sl4j.producer;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;

public class KafkaProducerListener implements ProducerListener<String, String>{
	private static Logger log = LoggerFactory.getLogger(KafkaProducerListener.class);

	@Override
	public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
		//设置使用log  在kafkaAppender中过滤掉producer生产的消息
//		log.info("kafka send succ");
		//开启消费
	}

	@Override
	public void onError(String topic, Integer partition, String key, String value, Exception exception) {
		//记录发送失败日志
		log.warn("kafka send error [ topic:"+topic+" partition:" + partition +" key:"+key+"  value:"+value +"]");
		//记录失败次数
//		KafkaProducerFactory.recordError();
		//重新发送
//		KafkaProducerFactory.getKafkaTemplate().sendDefault(value);
	}

	@Override
	public boolean isInterestedInSuccess() {
		//设置使用log  在kafkaAppender中过滤掉producer生产的消息
//		log.info("kafka 监听器启动...");
		return true;
	}

}
