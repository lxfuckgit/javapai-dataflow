package com.javapai.dataflow.ubt.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javapai.dataflow.ubt.param.UBTRecord;

@RestController
public final class UBTController {
	/**/
	private static final Logger logger = LoggerFactory.getLogger(UBTController.class);
	
	/**/
	@Value(value = "${spring.kafka.default-topic:UBT-Event-Topic}")
	private String kafkaTopic;
	
	/* 默认序列化实例 */
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	/**
	 * 记录用户触发的一个Event及关联的Properties。
	 *
	 * @param body
	 *            事件消息体
	 */
//	@CrossOrigin
	@PostMapping("/ubt/ubtEvent")
	public void ubtEvent(@RequestBody UBTRecord event) {
		if(filterEvent(event)) {
			/**/
			long currentTime = System.currentTimeMillis();
			if (event.getTimestamp() <= 0 || event.getTimestamp() > currentTime) {
				event.setTimestamp(currentTime);
			}
			/**/
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (event.getProperty("_ip") == null) {
				String ip = getRemoteAddr(requestAttributes.getRequest());
				event.addProperty("_ip", ip);
			}
			/**/
			try {
				kafkaTemplate.send(kafkaTopic, objectMapper.writeValueAsString(event));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 记录批量提交的用户触发的一组Event及其关联的Properties
	 *
	 * @param body
	 *            事件消息体集合
	 */
	@PostMapping("/ubt/ubtEventBatch")
	public void ubtEventBatch(@RequestBody List<UBTRecord> events) {
		/**/
		for (Iterator<UBTRecord> iter = events.iterator(); iter.hasNext();) {
			UBTRecord event = iter.next();
			if (!filterEvent(event)) {
				iter.remove();
			}
		}
		
		/**/
		long currentTime = System.currentTimeMillis();
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		String ip = getRemoteAddr(request);
		for (UBTRecord event : events) {
			if (event.getTimestamp() <= 0 || event.getTimestamp() > currentTime) {
				event.setTimestamp(currentTime);
			}

			if (event.getProperty("_ip") == null) {
				event.addProperty("_ip", ip);
			}
		}

		/**/
		if (!events.isEmpty()) {
			try {
				kafkaTemplate.send(kafkaTopic, "batch", objectMapper.writeValueAsString(events));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 记录用户的注册/登录事件及关联的Properties，把注册账号作为用户的唯一标识。
	 *
	 * @param body
	 *            事件消息体
	 */
	@org.springframework.web.bind.annotation.CrossOrigin
	@RequestMapping(value = "/ubtEventLogin", method = RequestMethod.POST)
	public void ubtEventLogin(@RequestBody UBTRecord event) {//@RequestBody String body这样接受会不会更快，不用反向序列化了。
		logger.debug("------------>" + event.toString());
		//trackSignup与trackEvent有什么区别或意义；biz用在什么地方，文档还提到日志埋点或是业务埋点 有用吗
		
		if (filterEvent(event)) {
			long currentTime = System.currentTimeMillis();
			if (event.getTimestamp() <= 0 || event.getTimestamp() > currentTime) {
				event.setTimestamp(currentTime);
			}

			if (event.getProperty("_ip") == null) {
				ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				HttpServletRequest request = requestAttributes.getRequest();
				String ip = getRemoteAddr(request);
				event.addProperty("_ip", ip);
			}
			
			if (!"".equalsIgnoreCase(event.getAction())) {
				logger.warn("------->事件动作并非login,系统自动按login处理......");
				event.setAction("login");
			}

			// UBTrackingService.trackSignup(event);
//			kafkaTemplate.sendDefault("signup", body);
			try {
				kafkaTemplate.send(kafkaTopic, objectMapper.writeValueAsString(event));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	@CrossOrigin
//	@RequestMapping(value = "/getClientIP", method = RequestMethod.GET)
//	@ResponseBody
//	public String getClientIP() {
//		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = requestAttributes.getRequest();
//		return Commons.getRemoteAddr(request);
//	}
	
//	@CrossOrigin
//	@RequestMapping(value = "/getCurrentDate", method = RequestMethod.GET)
//	@ResponseBody
//	public String getCurrentDate() {
//		return new SimpleDateFormat("yyyyMMdd").format(new Date());
//	}
	
	/**
	 * 同步服务端时间辍。<br>
	 * 
	 * @return 服务端时间辍.
	 */
	@RequestMapping(value = "/getCurrentTimestamp")
	@ResponseBody
	public long getCurrentTimestamp() {
		return System.currentTimeMillis();
	}
	
	
	private boolean filterEvent(UBTRecord event) {
		if (StringUtils.isEmpty(event.getAppId())) {
			logger.error("UBT数据({}-{})的appId为空!");
			// logger.error("UBT数据的appId为空" + objectMapper.writeValueAsString(event));
			return false;
		} else if (StringUtils.isEmpty(event.getSourceId())) {
			logger.error("UBT数据({}-{})的sourceId为空!");
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

		logger.info("---->UBT RemoteAddr: " + ip);
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
