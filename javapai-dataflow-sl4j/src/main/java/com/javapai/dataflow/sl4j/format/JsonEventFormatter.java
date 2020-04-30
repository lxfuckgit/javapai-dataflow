package com.javapai.dataflow.sl4j.format;

import ch.qos.logback.classic.spi.ILoggingEvent;

public final class JsonEventFormatter implements Formatter {
	/**
	 * 来源应用
	 */
	private String source;
	/**
	 * 来源应用IP地址
	 */
	private String host;
	/**
	 * 日志文本
	 */
	private String message;
	/**
	 * 时间戳
	 */
//    @JSONField(name = "@timestamp")
	private String timestamp;

	/**
	 * 日志输出类名
	 */
	private String logger;

	/**
	 * 日志级别
	 */
	private String level;

	/**
	 * 日志输出线程
	 */
	private String thread;

	/**
	 * 异常堆栈
	 */
	private String throwable;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getLogger() {
		return logger;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public String getThrowable() {
		return throwable;
	}

	public void setThrowable(String throwable) {
		this.throwable = throwable;
	}

	@Override
	public String format(ILoggingEvent event) {
		// TODO Auto-generated method stub
		return event.getFormattedMessage();
	}

}
