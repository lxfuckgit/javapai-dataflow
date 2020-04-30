package com.javapai.dataflow.sl4j.context;

import ch.qos.logback.core.spi.ContextAwareBase;

public abstract class ContextStrategy {
//public abstract class ContextStrategy extends ContextAwareBase {
	public abstract void buildContext();
//	public abstract MessageSender buildContext();

}
