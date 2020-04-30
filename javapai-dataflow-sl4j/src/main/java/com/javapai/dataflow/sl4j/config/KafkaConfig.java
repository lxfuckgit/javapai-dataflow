package com.javapai.dataflow.sl4j.config;

/**
 * Kafka配置参考： {@link org.apache.kafka.clients.producer.ProducerConfig}
 *
 */
public class KafkaConfig {
	/**
	 * 服务器地址
	 */
	private String addresses;

	private String topic = Consts.DEFAULT_TOPIC;
	private String client = Consts.DEFAULT_KAFKA_CLIENT;
	private String keySerializer = Consts.DEFAULT_KEY_SERIALIZER;
	private String valueSerializer = Consts.DEFAULT_VALUE_SERIALIZER;
	private int connectionTimeout = Consts.DEFAULT_CONNECTION_TIMEOUT;
	private int maxBlockMs = 60000;

	public String getAddresses() {
		return addresses;
	}

	public void setAddresses(String addresses) {
		this.addresses = addresses;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getKeySerializer() {
		return keySerializer;
	}

	public void setKeySerializer(String keySerializer) {
		this.keySerializer = keySerializer;
	}

	public String getValueSerializer() {
		return valueSerializer;
	}

	public void setValueSerializer(String valueSerializer) {
		this.valueSerializer = valueSerializer;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getMaxBlockMs() {
		return maxBlockMs;
	}

	public void setMaxBlockMs(int maxBlockMs) {
		this.maxBlockMs = maxBlockMs;
	}

}
