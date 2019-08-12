package com.javapai.dataflow.collector.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.javapai.dataflow.collector.domain.Event;
import com.javapai.dataflow.collector.service.UBTEventService;

@Component
public class UBTConsumer {
	/**/
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UBTEventService ubtEventService;
	
	/**
	 * 
	 * @param record
	 */
	@KafkaListener(id="ubt-comsumer", topics = { "DATAFLOW_TOPIC_UBT", "UBT-Event-Topic" })
	public void listen(ConsumerRecord<String, String> record) {
		System.out.println("------->"+record.topic());
		logger.info("------------->kafka的key: " + record.key());
		logger.info("------------->kafka的value: " + record.value());
		ubtEventService.insertUbtEvent(JSONObject.parseObject(record.value(), Event.class));
	}

}
