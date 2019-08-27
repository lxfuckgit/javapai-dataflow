package com.javapai.dataflow.collector.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.javapai.dataflow.collector.business.UBTBusiness;
import com.javapai.dataflow.collector.dao.UBTEventRepository;
//import com.javapai.module.monitor.ubt.DateUtil;
//import com.javapai.module.monitor.ubt.business.UBTBusiness;
//import com.javapai.module.monitor.ubt.dao.UBTEventESDao;
//import com.javapai.module.monitor.ubt.domain.Event;
//import com.mobanker.eagleeye.matrix.ubt.business.impl.UBTrackEventBusinessImpl.QueryUbtEventTask;
//import com.mobanker.eagleeye.matrix.ubt.dao.UBTEventESDao;
import com.javapai.dataflow.collector.domain.UBTEvent;
import com.javapai.dataflow.collector.utils.DateUtil;

@Service
public class UBTEventServiceImpl implements UBTEventService {
	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(UBTEventServiceImpl.class);
	LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
	
	public static final String pattern = "yyMMddHHmmss";

	private ExecutorService executor = Executors.newFixedThreadPool(10);

//	@Autowired
//	private LBSBusiness lbsBusiness;
	
	@Autowired
	private UBTBusiness ubtBusiness;

	/**
	 * 
	 */
	public void insertUbtEvent(final UBTEvent event) {
		// TODO Auto-generated method stub
		// UBTEventDao.save(event);
		// UBTEventESDao.save(event);
		
//		lbsBusiness.addLBS(event);

		UBTEventTast task = new UBTEventTast(Arrays.asList(event));
		executor.execute(task);
	}

	/**
	 * 
	 */
	public void insertUbtEvents(List<UBTEvent> events) {
		// TODO Auto-generated method stub
		// UBTEventDao.save(events);
		// UBTEventESDao.save(events);
		
//		lbsBusiness.addLBS(events);

		UBTEventTast userSaveTask = new UBTEventTast(events);
		executor.execute(userSaveTask);
	}

	public List<UBTEvent> queryUbtEvents(String appId, Date startDate, Date endDate, String... actions) {
		// TODO Auto-generated method stub
//        if ((endDate.getTime() - startDate.getTime()) > DateUtil.MILLIS1DAY) {
//            throw new RuntimeException("查询时间区间间隔不能大于24小时");
//        }

        List<UBTEvent> retVals = new ArrayList<UBTEvent>();
        List<String> minutes = DateUtil.getMinutes(startDate, endDate);

        String action = null;
        if (actions != null) {
            for (String str : actions) {
                if (action == null) {
                    action = str;
                } else {
                    action += str;
                }
            }
        }

        List<QueryUbtEventTask> tasks = new ArrayList<QueryUbtEventTask>();
        for (int i = 0; i < minutes.size() - 1; i++) {
            String startDateStr = minutes.get(i);
            String endDateStr = minutes.get(i + 1);
            String key = appId + "_" + startDateStr + "_" + action;

            QueryUbtEventTask task = new QueryUbtEventTask(key, startDateStr, endDateStr, appId, actions);
            tasks.add(task);
        }

        List<Future<List<UBTEvent>>> futures = null;
		try {
			futures = executor.invokeAll(tasks);
			for (Future<List<UBTEvent>> future : futures) {
	            List<UBTEvent> events = future.get();
	            if (events != null && events.size() > 0) {
	                retVals.addAll(events);
	            }
	        }
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        
        return retVals;
        
//		return UBTrackEventBusiness.queryUbtEvents(domain, startDate, endDate, actions);
	}

	class UBTEventTast implements Runnable {
		private List<UBTEvent> events;

		public UBTEventTast(List<UBTEvent> events) {
			this.events = events;
		}

		public void run() {
			// TODO Auto-generated method stub
			for (UBTEvent event : events) {
				if (StringUtils.isEmpty(event.getSourceId())) {
					logger.error("-------->UBTEvent的distinctId为空!");
					return;
				}
				// User user = new User();
				// user.setDomain(event.getDomain());
				// user.setId(event.getDistinctId());
				// user.setUsername(event.getOriginalId());
				// userDao.save(user);
				
				ubtBusiness.addUbtEvent(event);

				// if (Action.SignUp.getCode().equals(event.getAction()) ||
				// Action.SignIn.getCode().equals(event.getAction())) {
				// if (StringUtils.isEmpty(event.getOriginalId())) {
				// logger.error("注册或登录中的originalId为空" +
				// JSONObject.toJSONString(event));
				// } else {
				// user.setId(event.getOriginalId());
				// user.setUsername(event.getDistinctId());
				// userDao.update(user);
				// }
				// }
			}

		}
	}
	
	class QueryUbtEventTask implements Callable<List<UBTEvent>> {
        private String key;
        private String startDateStr;
        private String endDateStr;
        private String domain;
        private String[] actions;

        public QueryUbtEventTask(String key, String startDateStr, String endDateStr, String domain, String... actions) {
            this.key = key;
            this.startDateStr = startDateStr;
            this.endDateStr = endDateStr;
            this.domain = domain;
            this.actions = actions;
        }

        public List<UBTEvent> call() throws Exception {
            String str = null;//(String) redisTempalte.opsForValue().get(key);
            if (str == null) {
                logger.info("QueryUbtEventTask Redis hit null: " + key);
                //List<Event> events = esDao.queryUbtEvents(DateUtil.getDate(pattern, startDateStr), DateUtil.getDate(pattern, endDateStr), domain, actions);
//                if (events != null && events.size() > 0) {
//                	redisTempalte.opsForValue().set(key, JSONArray.toJSONString(events));
//					redisTempalte.expire(key, 1, TimeUnit.DAYS);
//                }
//                return events;
                return null;
            } else {
                return JSONArray.parseArray(str, UBTEvent.class);
            }
        }

    }

	
	@Autowired
	private UBTEventRepository ubtEventRepository;
	
	@Override
	public List<UBTEvent> queryTodayUbtEvents(String appId, String... actions) {
		// TODO Auto-generated method stub
		return ubtEventRepository.findByAppId(appId);
	}

}
