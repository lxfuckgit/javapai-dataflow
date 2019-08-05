package com.javapai.dataflow.ubt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javapai.dataflow.ubt.param.UBTRecord;

@RestController
public final class UBTController {
	private static final Logger logger = LoggerFactory.getLogger(UBTController.class);
	
	/*默认UBT Topic Name*/
	private static final String TOPIC_UBT = "DATAFLOW_TOPIC_UBT";
	
	/* 默认序列化实例 */
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@PostMapping("/ubt/ubtEvent")
	public void ubtEvent(@RequestBody UBTRecord event) {
		if(filterEvent(event)) {
			//
			long currentTime = System.currentTimeMillis();
			if (event.getTimestamp() <= 0 || event.getTimestamp() > currentTime) {
				event.setTimestamp(currentTime);
			}
			//
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (event.getProperty("$ip") == null) {
				String ip = getRemoteAddr(requestAttributes.getRequest());
				event.addProperty("$ip", ip);
			}
			//
			try {
				kafkaTemplate.send(TOPIC_UBT, objectMapper.writeValueAsString(event));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	@PostMapping("/ubt/ubtEventBatch")
	public void ubtEventBatch(@RequestBody List<UBTRecord> dto) {
		try {
			kafkaTemplate.send(TOPIC_UBT, objectMapper.writeValueAsString(dto));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	
	private boolean filterEvent(UBTRecord event) {
		if (StringUtils.isEmpty(event.getAppId())) {
			logger.error("UBT数据({}-{})的appId为空!");
			// logger.error("UBT数据的appId为空" + objectMapper.writeValueAsString(event));
			return false;
		} else if (StringUtils.isEmpty(event.getSourceId())) {
			logger.error("UBT数据({}-{})的distinctId为空!");
			// logger.error("UBT数据的distinctId为空" + objectMapper.writeValueAsString(event));
			return false;
		} else if (StringUtils.isEmpty(event.getAction())) {
			logger.error("UBT数据({}-{})的action为空!");
			// logger.error("UBT数据的action为空" +  objectMapper.writeValueAsString(event));
			return false;
		}
		return true;
	}
	
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		logger.info("RemoteAddr: " + ip);
		if (!StringUtils.isEmpty(ip)) {
			String[] ipArray = ip.split(",");
			if (ipArray != null && ipArray.length > 1) {
				return ipArray[0];
			}
			return ip;
		} else {
			return ip;
		}
	}

}
