package com.javapai.dataflow.collector.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 用户行为事件
 * <p/>
 */
@Document(indexName = "ubt_event", type = "ubt")
public class UBTEvent  {
	@Id
    private String id;

    /**
     * 项目名/产品名称
     */
    private String appId;

    /**
     * 对用户的标识，对未登录用户，可以填充设备标识、cookieID等，对于登录用户，则应该填充注册账号；
     */
    private String sourceId;

    /**
     * 用户标识(用户注册成功后的记录标识);
     */
    private String userId;

    /**
     * 事件发生的实际时间戳，精确到毫秒；
     */
    private long timestamp;

	/**
	 * 用户跟目标交互的行为，如"点击"、"输入"、"跳转"、"进入"、"滑动"等等。<br>
	 * 该项必选。<br>
	 */
    private String action;

    /**
     * 要监控的目标的事件名称，通常是同一组目标的名字，比如"产品浏览"、"单期借款"、"借款银行卡"等等。该项必选。
     * 业务监控目标的事件名称，需根据不同的业务需求定义；如：进单（OrderIn）<br>
     */
    private String event;

    /**
     * 事件的具体属性，以dict的形式存在。其中以$开头的表明是系统的保留字段，它的类型和中文名已经预先定义好了，自定义属性请不要以$开头；
     * 同时，同一个名称的property，在不同event中，必须保持一致的定义和类型。
     */
    private Map<String, String> properties = new HashMap<String, String>();
    
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
	
    public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public void addProperty(String key, String value) {
        if (properties == null) {
            properties = new HashMap<String, String>();
        }
        properties.put(key, value);
    }

    public String getProperty(String key) {
        if (properties == null)
            return null;
        return properties.get(key);
    }

//    public String getOriginalId() {
//        return originalId;
//    }
//
//    public void setOriginalId(String originalId) {
//        this.originalId = originalId;
//    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
}
