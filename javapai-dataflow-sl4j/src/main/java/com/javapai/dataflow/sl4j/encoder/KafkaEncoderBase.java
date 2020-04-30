package com.javapai.dataflow.sl4j.encoder;

import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;

public abstract class KafkaEncoderBase<E> extends ContextAwareBase implements IKafkaEncoder<E>, LifeCycle {

	private boolean started = false;

	@Override
	public void start() {
		started = true;
	}

	@Override
	public void stop() {
		started = false;
	}

	@Override
	public boolean isStarted() {
		return started;
	}

}
