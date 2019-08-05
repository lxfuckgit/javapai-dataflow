package com.javapai.dataflow.ubt.param;

import java.util.HashMap;
import java.util.Map;

public final class UBTRecord {
	/**
	 * 项目名，必选项。<br>
	 * <br>
	 * <strong>根据现场情况自行定义.</strong>
	 */
	private String appId;

	/**
	 * 用户登录标识(注册账号).
	 */
	private String userId;

	/**
	 * 数据来源标识.<br>
	 * <br>
	 * 移动设备填写设备号，其它访问源填写cookiesId.
	 */
	private String sourceId;

	/**
	 * 用户跟目标交互的行为，必选项。<br>
	 * <br>
	 * <strong>根据现场情况自行定义.</strong>比如"点击"、"输入"、"跳转"、"进入"、"滑动"等等。
	 */
	private String action;

	/**
	 * 要监控的目标的事件名称，通常是同一组目标的名字，必选项。<br>
	 * <br>
	 * <strong>根据现场情况自行定义.</strong>比如"产品浏览"、"单期借款"、"借款银行卡"等等。
	 */
	private String event;

	/**
	 * 事件发生的实际时间戳，精确到毫秒；
	 */
	private long timestamp;

	/**
	 * 预留属性;
	 */
	private Map<String, String> properties;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	public void addProperty(String key, String value) {
		if (properties == null) {
			properties = new HashMap<String, String>();
		} else {
			properties.put(key, value);
		}
	}

	public String getProperty(String key) {
		if (properties == null) {
			return null;
		} else {
			return properties.get(key);
		}
	}

}
