package com.javapai.dataflow.collector.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.javapai.dataflow.collector.controller.dto.GetEventDTO;
import com.javapai.dataflow.collector.domain.Event;
import com.javapai.dataflow.collector.service.PageEventService;

@RestController
public class EventController {
	@Autowired
	private PageEventService eventService;
	
	@PostMapping(value = "/ubt/addAppEvent")
	public Boolean addAppEvent(@RequestBody Event dto) {
		return eventService.addAppEvent(dto);
	}

	@RequestMapping(value = "/ubt/getAppEvent", method = RequestMethod.POST)
	public List<Event> getAppEvent(@RequestBody GetEventDTO dto) {
		return eventService.queryAppEvent(dto);
	}
}
