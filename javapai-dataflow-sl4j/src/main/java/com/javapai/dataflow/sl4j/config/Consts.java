package com.javapai.dataflow.sl4j.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 配置
 *
 * @author Genesis
 * @since 1.0
 */
public interface Consts {

    //默认字符编码
//    String DEFAULT_CHARSET = Charsets.UTF_8.name();
	String DEFAULT_CHARSET ="UTF-8";
    //默认日期格式化
    DateFormat DEFAULT_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ");

    //默认Topic
    String DEFAULT_TOPIC = "log-stash";

    //
    String DEFAULT_KAFKA_CLIENT = "LogstashClient";
    //
    String DEFAULT_KEY_SERIALIZER = "org.apache.kafka.common.serialization.IntegerSerializer";
    //
    String DEFAULT_VALUE_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";

    int DEFAULT_CONNECTION_TIMEOUT = 5000;
}
