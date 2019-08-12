package com.javapai.dataflow.collector.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.javapai.dataflow.collector.controller.dto.QueryEventDTO;
import com.javapai.dataflow.collector.domain.Event;
import com.javapai.dataflow.collector.service.UBTEventService;

@RestController
public class UBTQueryController {
	private  final Logger logger = LoggerFactory.getLogger(UBTQueryController.class);
	
	@Autowired
	private UBTEventService ubtEventService;
	
	/**
	 * 
	 * @param appId
	 * @param startDate
	 * @param endDate
	 * @param action
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/ubt/queryUbtEvent", method = RequestMethod.POST)
    public List<Event> queryUbtEvent(@RequestBody QueryEventDTO dto) {
		logger.info("query ubt event:{},{}", dto.getStartDate(), dto.getEndDate());
		
//		if(StringUtils.isEmpty(endDate)) {
//			endDate = new Date();
//		}
		
		String pattern = "yyyyMMddHHmmss";
		Date start = null, end = null;
		try {
			start = new SimpleDateFormat(pattern).parse(dto.getStartDate());
			end = new SimpleDateFormat(pattern).parse(dto.getEndDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String[] actions = null;
		if (dto.getAction() != null) {
			actions = dto.getAction().split("[,]");
		}

        return ubtEventService.queryUbtEvents(dto.getAppId(), start, end, actions);
    }
    
	
	/**
	 * 
	 * @param appId
	 * @param startDate
	 * @param endDate
	 * @param action
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/ubt/queryTodayUbtEvent", method = RequestMethod.POST)
    public List<Event> queryTodayUbtEvent(@RequestBody String appId) {
    	return ubtEventService.queryTodayUbtEvents(appId, "");
    }

}
