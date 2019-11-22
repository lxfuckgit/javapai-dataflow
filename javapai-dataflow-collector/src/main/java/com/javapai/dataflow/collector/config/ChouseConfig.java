package com.javapai.dataflow.collector.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

@Component
public class ChouseConfig {
	private static final Logger logger = LoggerFactory.getLogger(ChouseConfig.class);
	
	@Value("${spring.clickhouse.address}")
	private String address;
	@Value("${spring.clickhouse.username}")
	private String userName;
	@Value("${spring.clickhouse.password}")
	private String clickhousePassword;
	@Value("${spring.clickhouse.db}")
	private String clickhouseDB;
	@Value("${spring.clickhouse.socketTimeout}")
	private Integer socketTimeout;

	@Bean
	public ClickHouseConnection getConnection() {
		ClickHouseConnection conn = null;
		ClickHouseProperties properties = new ClickHouseProperties();
		properties.setUser(userName);
		properties.setPassword(clickhousePassword);
		properties.setDatabase(clickhouseDB);
		properties.setSocketTimeout(socketTimeout);
		String[] url = address.split(",");
		for (int i = 0; i < url.length; i++) {
			ClickHouseDataSource clickHouseDataSource = new ClickHouseDataSource(url[i], properties);
			try {
				conn = clickHouseDataSource.getConnection();
				logger.info("------>cHouse datasource({}) initialization completed.",conn);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("------>cHouse datasource initialization error!");
				return null;
			}
		}
		return conn;
	}
}
