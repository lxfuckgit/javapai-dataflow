package com.javapai.dataflow.ubt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javapai.dataflow.ubt.param.UBTRecord;

@RestController
public final class UBTController {
	/*默认UBT Topic Name*/
	private static final String TOPIC_UBT = "DATAFLOW_TOPIC_UBT";
	
	/* 默认序列化实例 */
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@RequestMapping("/ubt/ubtRecord")
	public void ubtRecord(@RequestBody UBTRecord dto) {
		try {
			kafkaTemplate.send(TOPIC_UBT, objectMapper.writeValueAsString(dto));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/ubt/ubtBatchRecord")
	public void ubtBatchRecord(@RequestBody List<UBTRecord> dto) {
		try {
			kafkaTemplate.send(TOPIC_UBT, objectMapper.writeValueAsString(dto));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
