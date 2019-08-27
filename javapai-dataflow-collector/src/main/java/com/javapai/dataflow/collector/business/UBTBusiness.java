package com.javapai.dataflow.collector.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javapai.dataflow.collector.dao.EventRepository;
import com.javapai.dataflow.collector.domain.UBTEvent;

@Service
public class UBTBusiness {
	@Autowired
	private EventRepository eventRepository;
	
	public void addUbtEvent(UBTEvent event) {
		eventRepository.save(event);
	}

}
