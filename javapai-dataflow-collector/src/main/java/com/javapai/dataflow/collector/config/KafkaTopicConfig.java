package com.javapai.dataflow.collector.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "kafkaTopicConifg")
public class KafkaTopicConfig implements InitializingBean {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value(value = "${spring.kafka.default-topic:UBT-Event-Topic}")
	private String kafkaTopic;

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.setProperty("kafka_topic_ubt", kafkaTopic);
		logger.warn("--------> default topic[{}] for ubt consumer!", kafkaTopic);
	}

}
