package com.javapai.dataflow.collector.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javapai.dataflow.collector.controller.dto.GetEventDTO;
import com.javapai.dataflow.collector.dao.EventRepository;
import com.javapai.dataflow.collector.domain.Event;

@Service
public class PageEventService {
	@Autowired
	private EventRepository eventRepository;

	public Boolean addAppEvent(Event entity) {
		// TODO Auto-generated method stub
		List<Event> list = eventRepository.findByAppIdAndEventId(entity.getAppId(), entity.getEventId());
		if (list != null && list.size() > 0) {
			return false;
		} else {
			eventRepository.save(entity);
		}
		return true;
	}

	public List<Event> queryAppEvent(GetEventDTO dto) {
		// TODO Auto-generated method stub
		return eventRepository.findByAppId(dto.getAppId());
	}

}
