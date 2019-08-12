package com.javapai.dataflow.collector.dao;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.javapai.dataflow.collector.domain.Event;

@Repository
public interface EventRepository extends ElasticsearchRepository<Event, String> {
	public List<Event> findByAppId(String appId);
}
