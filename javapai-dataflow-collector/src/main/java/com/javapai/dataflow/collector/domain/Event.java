package com.javapai.dataflow.collector.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 用于定义各页面事件（Page Event）。
 * 
 * @author pooja
 *
 */
@Document(indexName = "event", type = "ubt")
public class Event {
	@Id
    private String id;
	private String appId;
	private String eventId;
	private String eventName;
	private String eventDesc;
	private String createTime;
	private String createLogin;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateLogin() {
		return createLogin;
	}

	public void setCreateLogin(String createLogin) {
		this.createLogin = createLogin;
	}

}
