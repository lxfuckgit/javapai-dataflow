package com.javapai.dataflow.collector.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.javapai.dataflow.collector.controller.dto.ExportEventDTO;
import com.javapai.dataflow.collector.controller.dto.QueryEventDTO;
import com.javapai.dataflow.collector.controller.vo.UbtEventVO;
import com.javapai.dataflow.collector.domain.UBTEvent;
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
    public List<UbtEventVO> queryUbtEvent(@RequestBody QueryEventDTO dto) {
		logger.info("query ubt event:{},{},{}", dto.getAppId(),dto.getStartDate(), dto.getEndDate());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<UBTEvent> list = ubtEventService.queryUbtEvent(dto.getAppId(),dto.getEvent(), dto.getStartDateLong(), dto.getEndDateLong(), 0, 100);
		return list.stream().map(mapper ->{
			UbtEventVO vo = new UbtEventVO();
			BeanUtils.copyProperties(mapper, vo);
			vo.setDateTime(sdf.format(new Date(mapper.getTimestamp())));
			return vo;
		}).collect(Collectors.toList());
		
//		if(StringUtils.isEmpty(endDate)) {
//			endDate = new java.util.Date();
//		}
		
		/*
		String pattern = "yyyyMMddHHmmss";
		java.util.Date start = null, end = null;
		try {
			start = new java.text.SimpleDateFormat(pattern).parse(dto.getStartDate());
			end = new SimpleDateFormat(pattern).parse(dto.getEndDate());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		String[] actions = null;
		if (dto.getAction() != null) {
			actions = dto.getAction().split("[,]");
		}

        return ubtEventService.queryUbtEvents(dto.getAppId(), start, end, actions);
        */
    }
    
    @RequestMapping(value = "/ubt/exportUbtEvent", method = RequestMethod.POST)
    public List<UbtEventVO> exportUbtEvent(@RequestBody ExportEventDTO dto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<UBTEvent> list = ubtEventService.queryUbtEvent(dto.getAppId(),dto.getEvent(), dto.getStartDateLong(), dto.getEndDateLong());
		return list.stream().map(mapper ->{
			UbtEventVO vo = new UbtEventVO();
			BeanUtils.copyProperties(mapper, vo);
			vo.setDateTime(sdf.format(new Date(mapper.getTimestamp())));
			return vo;
		}).collect(Collectors.toList());
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
    public List<UBTEvent> queryTodayUbtEvent(@RequestBody String appId) {
    	return ubtEventService.queryTodayUbtEvents(appId, "");
    }

}
