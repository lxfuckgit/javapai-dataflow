//package com.javapai.dataflow.sl4j.logback;
//
//import java.util.Date;
//
//import com.javapai.dataflow.sl4j.appender.AbstractAppender;
////import com.javapai.dataflow.sl4j.JSONEvent;
//import com.javapai.dataflow.sl4j.context.ContextStrategy;
////import com.javapai.dataflow.sl4j.context.KafkaContextStrategy;
////import com.javapai.dataflow.sl4j.delivery.AsyncDeliveryStrategy;
////import com.javapai.dataflow.sl4j.delivery.DeliveryStrategy;
////import com.javapai.dataflow.sl4j.producer.MessageSender;
//
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import ch.qos.logback.classic.spi.IThrowableProxy;
//import ch.qos.logback.classic.spi.ThrowableProxyUtil;
//
//public final class LogbackAppender extends AbstractAppender<ILoggingEvent> {
//    private MessageSender sender;
//    private ContextStrategy buildStrategy = new KafkaContextStrategy();
//    private DeliveryStrategy deliveryStrategy = new AsyncDeliveryStrategy();
//    
//	@Override
//	protected void append(ILoggingEvent loggingEvent) {
//		// TODO Auto-generated method stub
//        if (sender != null) {
//			IThrowableProxy tp = loggingEvent.getThrowableProxy();
//            
//            JSONEvent event = new JSONEvent();
//            event.setHost("127.0.0.1");
//            event.setLevel(event.getLevel().toString());
//            deliveryStrategy.send(sender, event);
////            deliveryStrategy.send(sender, JSONEvent.builder().host(this.getHost())
////                    .level(event.getLevel().toString())
////                    .source(this.getSource())
////                    .message(event.getFormattedMessage())
////                    .timestamp(this.getDf().format(new Date(event.getTimeStamp())))
////                    .logger(event.getLoggerName())
////                    .thread(event.getThreadName())
////                    .throwable(tp == null ? null : ThrowableProxyUtil.asString(tp)).build());
//        }
//	}
//
//	@Override
//	public void start() {
//		// TODO Auto-generated method stub
//		sender = buildStrategy.buildContext();
//		if (sender != null) {
//			sender.init();
//		}
//
//		super.start();
//	}
//
//	@Override
//	public void stop() {
//		// TODO Auto-generated method stub
//		if (sender != null) {
//			sender.destroy();
//		}
//
//		super.stop();
//	}
//
//}
