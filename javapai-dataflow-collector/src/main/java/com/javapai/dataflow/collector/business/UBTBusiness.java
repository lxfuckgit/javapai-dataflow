package com.javapai.dataflow.collector.business;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.javapai.dataflow.collector.dao.UBTEventRepository;
import com.javapai.dataflow.collector.domain.UBTEvent;

import ru.yandex.clickhouse.ClickHouseConnection;

@Service
public class UBTBusiness {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//  @Autowired
//  private UBTEventESDao esDao;
	
	// @Autowired
	// private UBTEventDao UBTEventDao;
	//
	// @Autowired
	// private UBTEventESDao UBTEventESDao;
	
	@Value("${spring.clickhouse.status:-disabled}")
	private String status;
	
	@Autowired
	private ClickHouseConnection connection;
	
	@Autowired
	private UBTEventRepository eventRepository;
	
	public void addUbtEvent(UBTEvent event) {
		eventRepository.save(event);
		// sync data to chouse.
		if ("enabled".equalsIgnoreCase(status)) {
			addUbtEventToChouse(event);
		}
	}
	
	public boolean addUbtEventToChouse(UBTEvent event) {
		String sql = "INSERT INTO data_flow.ubt_event (id, appId, sourceId,userId,timestamp,action,event) VALUES (?,?,?,?,?,?,?);";
		PreparedStatement ps=null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, null != event.getId() ? event.getId() : UUID.randomUUID().toString());
			ps.setString(2, event.getAppId());
			ps.setString(3, event.getSourceId());
			ps.setString(4, event.getUserId());
			ps.setString(5, sdf.format(new Date(event.getTimestamp())));
			ps.setString(6, event.getAction());
			ps.setString(7, event.getEvent());
			ps.addBatch();
			ps.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
