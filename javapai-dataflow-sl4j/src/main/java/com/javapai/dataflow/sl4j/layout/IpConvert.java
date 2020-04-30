//package com.sinafenqi.log.layout;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//import ch.qos.logback.classic.pattern.ClassicConverter;
//import ch.qos.logback.classic.spi.ILoggingEvent;
//
//public class IpConvert extends ClassicConverter {     
//	@SuppressWarnings("static-access")
//	@Override
//	public String convert(ILoggingEvent event) {
//		InetAddress ia = null;
//		try {
//			ia = ia.getLocalHost();
//			return ia.getHostAddress(); 
//		} catch (UnknownHostException e) {
//			return "not fund IP";
//		}
//	}
//}
