package com.javapai.dataflow.ubt.param;

import java.util.HashMap;
import java.util.Map;

/**
 * UTB行为数据模型。<br>
 * 
 * @author pooja
 *
 */
public final class UBTRecord {
	/**
	 * 项目标识，必选项。<br>
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
	 * <strong>参考ActionEnum定义.</strong>
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
	 * UBT事件预留属性; <br>
	 * <h4>系统级属性(约定前缀$开头，如$os)：</h4><br>
	 * <blockquote>
	 * <table border=0 cellspacing=3 cellpadding=0>
	 * <tr style="background-color: rgb(204, 204, 255);">
	 * <th align=left>-属性名-
	 * <th align=left>-类型-
	 * <th align=left>-描述-
	 * <tr>
	 * <td><code>$os</code>
	 * <td><code>String</code>
	 * <td><code>操作系统</code>
	 * <tr>
	 * <td><code>$osbanben</code>
	 * <td><code>String</code>
	 * <td><code>操作系统版本</code>
	 * <tr>
	 * <td><code>$appbanben</code>
	 * <td><code>String</code>
	 * <td><code>app版本</code>
	 * <tr>
	 * <td><code>$longitude</code>
	 * <td><code>String</code>
	 * <td><code>经度</code>
	 * <tr>
	 * <td><code>$latitude</code>
	 * <td><code>String</code>
	 * <td><code>纬度</code>
	 * <tr>
	 * <td><code>$appchannel</code>
	 * <td><code>String</code>
	 * <td><code>app下载渠道</code>
	 * <tr>
	 * <td><code>$marketchannel</code>
	 * <td><code>String</code>
	 * <td><code>活动推广渠道</code>
	 * </tr>
	 * </table>
	 * </blockquote>
	 * 
	 * <br>
	 * 业务级属性(用户自定义)
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
