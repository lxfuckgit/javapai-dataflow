//package com.bsd.framework.logcenter;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class LogbackListener implements ServletContextListener {
//	private final static Logger logger = LoggerFactory.getLogger(LogbackListener.class);
//	
//	@Override
//	public void contextInitialized(ServletContextEvent event) {
//		// TODO Auto-generated method stub
//		String active = "default";
//		
//		if (null != System.getProperty("spring.profiles.active")) {
//			active = System.getProperty("spring.profiles.active");
//			logger.debug("---->当前spring.profiles.active优先级.....:");
//		}
//		if (null != System.getProperty("env")) {
//			active = System.getProperty("env");
//			logger.debug("---->当前evn优先级.....");
//		}
//		
//		// how to set active to profile?
//		System.out.println("---->current profile set:" + active);
//	}
//
//	@Override
//	public void contextDestroyed(ServletContextEvent event) {
//		// TODO Auto-generated method stub
////		WebLogbackConfigurer.shutdownLogging(event.getServletContext());
//	}
//
//}
