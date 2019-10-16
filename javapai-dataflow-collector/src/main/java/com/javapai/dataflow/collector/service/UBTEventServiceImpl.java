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
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections4.IteratorUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.redis.core.RedisTemplate;
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
	
	@Resource
	private RedisTemplate<String, Long> redisTemplate;

	private ExecutorService executor = Executors.newFixedThreadPool(10);

//	@Autowired
//	private LBSBusiness lbsBusiness;
	
	@Autowired
	private UBTBusiness ubtBusiness;

	public void insertUbtEvent(final UBTEvent event) {
		// TODO Auto-generated method stub
		// UBTEventDao.save(event);
		// UBTEventESDao.save(event);
		
//		lbsBusiness.addLBS(event);

		UBTEventTast task = new UBTEventTast(Arrays.asList(event));
		executor.execute(task);
		
		/* pv/uv指标任务 */
		long millSecond = ChronoUnit.MILLIS.between(LocalDateTime.now(), midnight);
		String pvKey = event.getAppId() + "#" + event.getEvent() + "#" + event.getProperty("$ip");
//		if (StringUtils.isEmpty(redisTemplate.opsForValue().get(pvKey))) {
		if (redisTemplate.opsForValue().size(pvKey) == 0L) {
			String key = "pvKey#" + event.getAppId();
			redisTemplate.opsForValue().increment(key, 1L);//increment相对于get(key) + 1当getKey为null时，完了。
//			redisTemplate.opsForValue().set(key, redisTemplate.opsForValue().get(key) + 1);
			redisTemplate.expire(key, millSecond, TimeUnit.MILLISECONDS);
		}
		//当天持续访问(>1次)时，记录持续次数.
		redisTemplate.opsForValue().increment(pvKey, 1);
		redisTemplate.expire(pvKey, millSecond, TimeUnit.MILLISECONDS);

		String uvKey = event.getAppId() + "#" + event.getProperty("$ip");
		if (redisTemplate.opsForValue().size(uvKey) == 0) {
			String key = "uvKey#" + event.getAppId();
			redisTemplate.opsForValue().increment(key, 1);
			redisTemplate.expire(key, millSecond, TimeUnit.MILLISECONDS);
		}
		redisTemplate.opsForValue().increment(uvKey, 1);
		redisTemplate.expire(uvKey, millSecond, TimeUnit.MILLISECONDS);
	}

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
	
	@Override
	public List<UBTEvent> queryUbtEvent(String domain, String event, long startTime, long endTime) {
		// TODO Auto-generated method stub
		BoolQueryBuilder qBuilder = QueryBuilders.boolQuery();
		if (!StringUtils.isEmpty(domain)) {
			qBuilder.must(QueryBuilders.matchQuery("appId", domain));
		}
		if (startTime > 0L) {
			qBuilder.filter(QueryBuilders.rangeQuery("timestamp").gte(startTime));
		}
		if (endTime > 0L) {
			qBuilder.filter(QueryBuilders.rangeQuery("timestamp").lte(endTime));
		}
		if (!StringUtils.isEmpty(event)) {
			qBuilder.filter(QueryBuilders.matchQuery("event", event));
		}
		
		return IteratorUtils.toList(ubtEventRepository.search(qBuilder).iterator());
	}
	
	@Override
	public List<UBTEvent> queryUbtEvent(String domain, long startTime, long endTime, int page, int size) {
		// TODO Auto-generated method stub
		return queryUbtEvent(domain, null, startTime, endTime, page, size);
	}
	
	@Override
	public List<UBTEvent> queryUbtEvent(String domain, String event, long startTime, long endTime, int page, int size) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(page, size);
		
		/* from elasticsearch */
		BoolQueryBuilder qBuilder = QueryBuilders.boolQuery();
		if (!StringUtils.isEmpty(domain)) {
			qBuilder.must(QueryBuilders.matchQuery("appId", domain));
		}
//		if (startTime > 0L || endTime > 0L) {
//			qBuilder.filter(QueryBuilders.rangeQuery("timestamp").gte(startTime).lte(endTime));
//		}
		if (startTime > 0L) {
			qBuilder.filter(QueryBuilders.rangeQuery("timestamp").gte(startTime));
		}
		if (endTime > 0L) {
			qBuilder.filter(QueryBuilders.rangeQuery("timestamp").lte(endTime));
		}
		if (!StringUtils.isEmpty(event)) {
			qBuilder.filter(QueryBuilders.matchQuery("event", event));
		}
		@SuppressWarnings("rawtypes")
		List<SortBuilder> sortList = new ArrayList<>();
//		sortList.add(SortBuilders.fieldSort("appId").order(SortOrder.DESC));
		sortList.add(SortBuilders.fieldSort("timestamp").order(SortOrder.DESC));
		SearchQuery searchQuery = new NativeSearchQuery(QueryBuilders.matchAllQuery(),qBuilder,sortList).setPageable(pageable);
		return ubtEventRepository.search(searchQuery).getContent();
//		return ubtEventRepository.search(qBuilder, pageable).getContent();
		
		/* from hbase */
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
