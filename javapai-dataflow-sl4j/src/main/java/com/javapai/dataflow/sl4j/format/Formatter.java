package com.javapai.dataflow.sl4j.format;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 日志事件格式化-工作器。<br>
 * 
 * @author lx
 *
 */
public interface Formatter {
	String getTimestamp();

	/**
	 * 作用:定义一个format方法，接收ILoggingEvent对象，返回字符串。 <br>
	 * 
	 * @param event
	 * @return
	 */
	String format(ILoggingEvent event);
}
