package com.javapai.dataflow.sl4j;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class LogbackColorful extends ForegroundCompositeConverterBase<ILoggingEvent> {

	@Override
	protected String getForegroundColorCode(ILoggingEvent event) {
		// TODO Auto-generated method stub
		Level level = event.getLevel();
		switch (level.toInt()) {
		case Level.ERROR_INT:
			return ANSIConstants.RED_FG;
		case Level.WARN_INT:
			return ANSIConstants.YELLOW_FG;
		default:
			return ANSIConstants.DEFAULT_FG;
		}
	}

}
