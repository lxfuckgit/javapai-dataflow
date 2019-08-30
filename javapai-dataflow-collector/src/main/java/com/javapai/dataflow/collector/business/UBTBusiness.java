package com.javapai.dataflow.collector.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javapai.dataflow.collector.dao.UBTEventRepository;
import com.javapai.dataflow.collector.domain.UBTEvent;

@Service
public class UBTBusiness {
//  @Autowired
//  private UBTEventESDao esDao;
	
	// @Autowired
	// private UBTEventDao UBTEventDao;
	//
	// @Autowired
	// private UBTEventESDao UBTEventESDao;
	
	@Autowired
	private UBTEventRepository eventRepository;
	
	public void addUbtEvent(UBTEvent event) {
		eventRepository.save(event);
	}

}
