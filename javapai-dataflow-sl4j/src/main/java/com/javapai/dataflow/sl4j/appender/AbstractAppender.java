package com.javapai.dataflow.sl4j.appender;

import com.javapai.dataflow.sl4j.context.ContextStrategy;
import com.javapai.dataflow.sl4j.delivery2.DeliveryStrategy;

import ch.qos.logback.core.UnsynchronizedAppenderBase;

/**
 * Appender抽象处理。<br>
 * 
 * @author lx
 *
 * @param <E>
 */
public abstract class AbstractAppender<E> extends UnsynchronizedAppenderBase<E> {
	/**
	 * appender数据容器策略。<br>
	 */
	private ContextStrategy contextStrategy;

	/**
	 * appender数据追加策略。<br>
	 */
	private DeliveryStrategy deliveryStrategy;

}
