package com.javapai.dataflow.sl4j.encoder;

import java.nio.charset.Charset;
import ch.qos.logback.core.Layout;

public class KafkaEncoder<E> extends KafkaEncoderBase<E> {
	public KafkaEncoder() {
	}

	public KafkaEncoder(Layout<E> layout, Charset charset) {
		this.layout = layout;
		this.charset = charset;
	}
	
	public KafkaEncoder(Layout<E> layout) {
		this.layout = layout;
	}


	private Layout<E> layout;
	private Charset charset;
	private static final Charset UTF8 = Charset.forName("UTF-8");

	@Override
	public void start() {
		if (charset == null) {
			addInfo("No charset specified for PatternLayoutKafkaEncoder. Using default UTF8 encoding.");
			charset = UTF8;
		}
		super.start();
	}

	@Override
	public String doEncode(E event) {
		return layout.doLayout(event);
	}

	public void setLayout(Layout<E> layout) {
		this.layout = layout;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public Layout<E> getLayout() {
		return layout;
	}

	public Charset getCharset() {
		return charset;
	}
}
