package com.javapai.dataflow.sl4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.spi.AppenderAttachable;
//import org.apache.commons.lang3.StringUtils;

public abstract class KafkaAppenderConfig<E> {
	public static Map<String, Object> producerConf;

	public static Integer error_producer_warn_num = 10;

	private String topic = "test";
	public final static String TOPIC_NAME = "topic";

	private String group_id = "0";
	private final static String GROUP_ID_NAME = "group.id";

	private Integer retries = 1;
	private final static String RETRIES_NAME = "retries";

	private Integer batch_size = 16384;
	private final static String BATCH_SIZE_NAME = "batch.size";

	private Integer linger_ms = 1;
	private final static String LINGER_MS_NAME = "linger.ms";

	private Integer buffer_memory = 33554432;
	private final static String BUFFER_MEMORY_NAME = "buffer.memory";

	private boolean autoFlush = true;
	public final static String AUTO_FLUSH_NAME = "autoFlush";

	protected boolean checkPrerequisites() {
		boolean errorFree = true;
		// producer config set
		{
			producerConf = new HashMap<String, Object>();
			producerConf.put(GROUP_ID_NAME, group_id);
			producerConf.put(TOPIC_NAME, topic);
			producerConf.put(RETRIES_NAME, retries);
			producerConf.put(BATCH_SIZE_NAME, batch_size);
			producerConf.put(LINGER_MS_NAME, linger_ms);
			producerConf.put(BUFFER_MEMORY_NAME, buffer_memory);
			producerConf.put(AUTO_FLUSH_NAME, autoFlush);
		}

		return errorFree;
	}

	public void setError_producer_warn_num(Integer error_producer_warn_num) {
		KafkaAppenderConfig.error_producer_warn_num = error_producer_warn_num;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public void setRetries(Integer retries) {
		this.retries = retries;
	}

	public void setBatch_size(Integer batch_size) {
		this.batch_size = batch_size;
	}

	public void setLinger_ms(Integer linger_ms) {
		this.linger_ms = linger_ms;
	}

	public void setBuffer_memory(Integer buffer_memory) {
		this.buffer_memory = buffer_memory;
	}

	public void setAutoFlush(boolean autoFlush) {
		this.autoFlush = autoFlush;
	}

}
