package com.javapai.dataflow.collector.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.javapai.dataflow.collector.domain.Event;
import com.javapai.dataflow.collector.service.UBTEventService;

@Component
@DependsOn("kafkaTopicConifg")
public class UBTConsumer {
	/**/
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UBTEventService ubtEventService;

	/**
	 * 
	 * @param record
	 */
	@KafkaListener(id = "ubt-comsumer", topics = { "${kafka_topic_ubt}" })
	public void listen(ConsumerRecord<String, String> record) {
		logger.debug("------------->kafka的topic: " + record.topic());
		logger.debug("------------->kafka的key: " + record.key());
		logger.debug("------------->kafka的value: " + record.value());
		if (null == record.key()) {
			ubtEventService.insertUbtEvent(JSONObject.parseObject(record.value(), Event.class));
		} else if ("batch".equals(record.key())) {
			ubtEventService.insertUbtEvents(JSONArray.parseArray(record.value(), Event.class));
		}
	}

}
