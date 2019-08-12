package com.javapai.dataflow.collector.service;

import java.util.Date;
import java.util.List;

import com.javapai.dataflow.collector.domain.Event;

public interface UBTEventService {
	
	/**
	 * 记录一条UBT事件行为.<br>
	 * 
	 * @param event
	 */
	public void insertUbtEvent(Event event);

	/**
	 * 记录多条UBT事件行为.<br>
	 * 
	 * @param events
	 */
	public void insertUbtEvents(List<Event> events);

	/**
	 * 按条件查询UBT事件.<br>
	 * 
	 * @param domain
	 *            事件域<br>
	 * @param startDate
	 *            开始时间.<br>
	 * @param endDate
	 *            结束时间.<br>
	 * @param actions
	 *            ubt动作.<br>
	 * @return
	 * @throws Exception
	 */
	public List<Event> queryUbtEvents(String domain, Date startDate, Date endDate, String... actions);
	
	/**
	 * 按条件查询UBT事件.<br>
	 * 
	 * @param appId
	 *            事件域<br>
	 * @param actions
	 *            ubt动作.<br>
	 * @return
	 * @throws Exception
	 */
	public List<Event> queryTodayUbtEvents(String appId, String... actions);

}
