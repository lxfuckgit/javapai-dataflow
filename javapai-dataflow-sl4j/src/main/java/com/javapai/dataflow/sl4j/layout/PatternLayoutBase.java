//package com.sinafenqi.log.layout;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import ch.qos.logback.core.Context;
//import ch.qos.logback.core.CoreConstants;
//import ch.qos.logback.core.LayoutBase;
//import ch.qos.logback.core.pattern.Converter;
//import ch.qos.logback.core.pattern.ConverterUtil;
//import ch.qos.logback.core.pattern.PostCompileProcessor;
//import ch.qos.logback.core.pattern.parser.Node;
//import ch.qos.logback.core.pattern.parser.Parser;
//import ch.qos.logback.core.spi.ScanException;
//import ch.qos.logback.core.status.ErrorStatus;
//import ch.qos.logback.core.status.StatusManager;
//import com.sinafenqi.log.KafkaAppender;
//import com.sinafenqi.log.common.AnalysisPattern;
//
//
//abstract public class PatternLayoutBase<E> extends LayoutBase<E> {
//
//	Converter<E> head;
//	String pattern;
//	Map<String,String> keys;
//	protected PostCompileProcessor<E> postCompileProcessor;
//
//	Map<String, String> instanceConverterMap = new HashMap<String, String>();
//	protected boolean outputPatternAsHeader = false;
//
//	/**
//	 * Concrete implementations of this class are responsible for elaborating
//	 * the mapping between pattern words and converters.
//	 * 
//	 * @return A map associating pattern words to the names of converter classes
//	 */
//	abstract public Map<String, String> getDefaultConverterMap();
//
//	/**
//	 * Returns a map where the default converter map is merged with the map
//	 * contained in the context.
//	 */
//	public Map<String, String> getEffectiveConverterMap() {
//		Map<String, String> effectiveMap = new HashMap<String, String>();
//
//		// add the least specific map fist
//		Map<String, String> defaultMap = getDefaultConverterMap();
//		if (defaultMap != null) {
//			effectiveMap.putAll(defaultMap);
//		}
//
//		// contextMap is more specific than the default map
//		Context context = getContext();
//		if (context != null) {
//			@SuppressWarnings("unchecked")
//			Map<String, String> contextMap = (Map<String, String>) context
//					.getObject(CoreConstants.PATTERN_RULE_REGISTRY);
//			if (contextMap != null) {
//				effectiveMap.putAll(contextMap);
//			}
//		}
//		// set the most specific map last
//		effectiveMap.putAll(instanceConverterMap);
//		return effectiveMap;
//	}
//
//	public void start() {
//		if (pattern == null || pattern.length() == 0) {
//			addError("Empty or null pattern.");
//			return;
//		}
//		try {
//			Parser<E> p = new Parser<E>(pattern);
//			if (getContext() != null) {
//				p.setContext(getContext());
//			}
//			Node t = p.parse();
//			Map<String, String> effectiveConverterMap = getEffectiveConverterMap();
//			this.head = p.compile(t, effectiveConverterMap);
//			if (postCompileProcessor != null) {
//				postCompileProcessor.process(getContext(),head);
//			}
//			//返回key-value
//			keys = AnalysisPattern.getKeywordConvertMaps(effectiveConverterMap, t);
//			ConverterUtil.setContextForConverters(getContext(), head);
//			ConverterUtil.startConverters(this.head);
//			super.start();
//		} catch (ScanException sce) {
//			StatusManager sm = getContext().getStatusManager();
//			sm.add(new ErrorStatus("Failed to parse pattern \"" + getPattern() + "\".", this, sce));
//		}
//	}
//
//	public void setPostCompileProcessor(PostCompileProcessor<E> postCompileProcessor) {
//		this.postCompileProcessor = postCompileProcessor;
//	}
//
//	/**
//	 *
//	 * @param head
//	 * @deprecated Use {@link ConverterUtil#setContextForConverters} instead.
//	 *             This method will be removed in future releases.
//	 */
//	protected void setContextForConverters(Converter<E> head) {
//		ConverterUtil.setContextForConverters(getContext(), head);
//	}
//
//	protected String writeLoopOnConverters(E event) {
//		StringBuilder buf = new StringBuilder(128);
//		Converter<E> c = head;
//		while (c != null) {
//			c.write(buf, event);
//			c = c.getNext();
//		}
//		return buf.toString();
//	}
//
//	protected String writeLoopOnConvertersMap(E event) {
//		StringBuilder data = new StringBuilder(128);
//		Converter<E> c = head;
//		Map<String,Object> values = new HashMap<String,Object>();
//		while (c != null) {
//			StringBuilder buf = new StringBuilder(64);
//			c.write(buf, event);
//			values.put( c.getClass().getName(), buf.toString() );
//			c = c.getNext();
//			data.append(buf);
//		}
//		//完整的data数据
//		values.put(KafkaAppender.MSG_FULL_DATA_KEY, data.toString() );
//		keys.put(KafkaAppender.MSG_FULL_DATA_KEY, KafkaAppender.MSG_FULL_DATA_KEY);
//		return AnalysisPattern.convertKeyWords(keys, values);
//	}
//
//	
//	public String getPattern() {
//		return pattern;
//	}
//
//	public void setPattern(String pattern) {
//		this.pattern = pattern;
//	}
//
//	public String toString() {
//		return this.getClass().getName() + "(\"" + getPattern() + "\")";
//	}
//
//	public Map<String, String> getInstanceConverterMap() {
//		return instanceConverterMap;
//	}
//
//	protected String getPresentationHeaderPrefix() {
//		return CoreConstants.EMPTY_STRING;
//	}
//
//	public boolean isOutputPatternAsHeader() {
//		return outputPatternAsHeader;
//	}
//
//	public void setOutputPatternAsHeader(boolean outputPatternAsHeader) {
//		this.outputPatternAsHeader = outputPatternAsHeader;
//	}
//
//	@Override
//	public String getPresentationHeader() {
//		if (outputPatternAsHeader)
//			return getPresentationHeaderPrefix() + pattern;
//		else
//			return super.getPresentationHeader();
//	}
//}
