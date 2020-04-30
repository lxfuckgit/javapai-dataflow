package com.javapai.dataflow.sl4j.encoder;

public interface IKafkaEncoder<E> {
	public String doEncode(E event);
}
