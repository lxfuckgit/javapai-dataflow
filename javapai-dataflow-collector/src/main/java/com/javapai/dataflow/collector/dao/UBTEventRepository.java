package com.javapai.dataflow.collector.dao;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.javapai.dataflow.collector.domain.UBTEvent;

@Repository
public interface UBTEventRepository extends ElasticsearchRepository<UBTEvent, String> {
	public List<UBTEvent> findByAppId(String appId);
}
