package com.javapai.dataflow.collector.controller.dto;

import org.springframework.util.StringUtils;

import com.javapai.dataflow.collector.utils.DateUtil;

public class ExportEventDTO {
	/**
	 * 
	 */
	private String appId;
	/**
	 * <br>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 */
	private String startDate;
	/**
	 * <br>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 */
	private String endDate;
	private String event;
	private String action;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public long getStartDateLong() {
		if(StringUtils.isEmpty(getStartDate())) {
			return 0L;
		} else {
			return DateUtil.convertTimeToLong(getStartDate());
		}
	};
	
	public long getEndDateLong() {
		if(StringUtils.isEmpty(getEndDate())) {
			return 0L;
		} else {
			return DateUtil.convertTimeToLong(getEndDate());
		}
	};

}
