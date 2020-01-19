package com.javapai.dataflow.sl4j.config;

import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.spi.AppenderAttachable;

import com.javapai.dataflow.sl4j.delivery2.AsynchronousDeliveryStrategy;
import com.javapai.dataflow.sl4j.delivery2.DeliveryStrategy;
//import com.github.danielwegener.logback.kafka.delivery.AsynchronousDeliveryStrategy;
//import com.github.danielwegener.logback.kafka.delivery.DeliveryStrategy;
//import com.github.danielwegener.logback.kafka.keying.KeyingStrategy;
//import com.github.danielwegener.logback.kafka.keying.NoKeyKeyingStrategy;
import com.javapai.dataflow.sl4j.key.KeyingStrategy;
import com.javapai.dataflow.sl4j.key.NoKeyKeyingStrategy;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;

/**
 * 
 * Kafka-Appender抽象处理类。<br>
 * <br>
 * Function：用于初始化kafka配置项及相关Kafka数据流处理策略。<br>
 *
 * @param <E>
 */
public abstract class KafkaAppenderConfig<E> extends UnsynchronizedAppenderBase<E> implements AppenderAttachable<E> {
	/*
	 * 数据编码。
	 */
	protected Encoder<E> encoder = null;
	/*
	 * 
	 */
	protected KeyingStrategy<? super E> keyingStrategy = null;
	/*
	 * 数据分发策略。<br>
	 */
	protected DeliveryStrategy deliveryStrategy;
	/*
	 * kafka信息。
	 */
	protected String servers = null;
	protected String topic = null;
	protected String group = null;
	protected Integer partition = null;
	protected Map<String, Object> producerConfig = new HashMap<String, Object>();
	
	/**/
	protected boolean appendTimestamp = true;

	protected boolean checkPrerequisites() {
		boolean errorFree = true;
		if (servers == null) {
			addError("No \"" + ProducerConfig.BOOTSTRAP_SERVERS_CONFIG + "\" set for the appender named [\"" + name + "\"].");
			errorFree = false;
		}

		if (topic == null) {
			/**
			 * addError("No topic set for the appender named [\"" + name + "\"].");
			 * errorFree = false;
			 */
			topic = "sl4j-log";
		}

		if (encoder == null) {
			addError("No encoder set for the appender named [\"" + name + "\"].");
			errorFree = false;
		}

		if (keyingStrategy == null) {
			addInfo("No explicit keyingStrategy set for the appender named [\"" + name + "\"]. Using default NoKeyKeyingStrategy.");
			keyingStrategy = new NoKeyKeyingStrategy();
		}

		if (deliveryStrategy == null) {
			addInfo("No explicit deliveryStrategy set for the appender named [\"" + name + "\"]. Using default asynchronous strategy.");
			deliveryStrategy = new AsynchronousDeliveryStrategy();
		}
		
        // setting these as config values sidesteps an unnecessary warning (minor bug in KafkaProducer)
		this.producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		this.producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
		this.producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

		return errorFree;
	}

	public void setEncoder(Encoder<E> encoder) {
		this.encoder = encoder;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public void setServers(String servers) {
		this.servers = servers;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setProducerConfig(Map<String, Object> producerConfig) {
		this.producerConfig = producerConfig;
	}

	public void setKeyingStrategy(KeyingStrategy<? super E> keyingStrategy) {
		this.keyingStrategy = keyingStrategy;
	}

	public Map<String, Object> getProducerConfig() {
		return producerConfig;
	}

	public void setDeliveryStrategy(DeliveryStrategy deliveryStrategy) {
		this.deliveryStrategy = deliveryStrategy;
	}

	public void setPartition(Integer partition) {
		this.partition = partition;
	}

	public boolean isAppendTimestamp() {
		return appendTimestamp;
	}

	public void setAppendTimestamp(boolean appendTimestamp) {
		this.appendTimestamp = appendTimestamp;
	}

}
