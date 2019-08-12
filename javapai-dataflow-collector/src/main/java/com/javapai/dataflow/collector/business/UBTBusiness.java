package com.javapai.dataflow.collector.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javapai.dataflow.collector.dao.EventRepository;
import com.javapai.dataflow.collector.domain.Event;

@Service
public class UBTBusiness {
	@Autowired
	private EventRepository eventRepository;
	
	public void addUbtEvent(Event event) {
		eventRepository.save(event);
	}

}
