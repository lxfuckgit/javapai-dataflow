package com.javapai.dataflow.sl4j.context;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import com.javapai.dataflow.sl4j.config.KafkaConfig;
//import com.javapai.dataflow.sl4j.producer.KafkaSender;
//import com.javapai.dataflow.sl4j.producer.MessageSender;

/**
 * 配置项均参考： {@link org.apache.kafka.clients.producer.ProducerConfig}
 * 
 * @author lx
 *
 */
public class KafkaContextStrategy extends ContextStrategy {
	private KafkaConfig config;

	public KafkaConfig getConfig() {
		return config;
	}

	public KafkaContextStrategy() {
		super();
	}

	@Override
	public void buildContext() {
		// TODO Auto-generated method stub
		Properties props = new Properties();
	}

//	@Override
//	public MessageSender buildContext() {
//		// TODO Auto-generated method stub
//		Properties props = new Properties();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.getConfig().getAddresses());
//        props.put(ProducerConfig.CLIENT_ID_CONFIG, this.getConfig().getClient());
//        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, this.getConfig().getConnectionTimeout());
//        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, this.getConfig().getMaxBlockMs());
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerSerializer");
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//        
//        MessageSender sender = new KafkaSender(this.getConfig().getTopic(),new KafkaProducer<>(props));
////        ContextStrategy.sender = sender;
//        return sender;
//	}

}
