#Log4j配置

#Log4j2配置

#Logback配置
```add pom.xml```

```config logback.xml```
    <appender name="Kafka" class="com.javapai.dataflow.sl4j.logback.LogbackAppender">
        <contextStrategy class="com.javapai.dataflow.sl4j.context.KafkaContextStrategy">
            <config class="com.javapai.dataflow.sl4j.config.KafkaConfig">
            	<!--config parameter can user be:<springProperty /> -->
                <addresses>127.0.0.1:9092</addresses>
                <topic>topic1</topic>
                <group>topic1</group>
            </config>
        </contextStrategy>
        <deliveryStrategy class="com.javapai.dataflow.sl4j.delivery.AsyncDeliveryStrategy"/>
        <source>test-application</source>
    </appender>
  
```实现原理```
ch.qos.logback.core.spi.ContextAwareBase类及其子类 主要为Appender类提供了一个相关参数上下文容器。
--appender里的参数配置项到底怎么读取的?
--测试结果是：优先匹配add+子标签名的方法，没有再匹配set+子标签名的方法。

ch.qos.logback.core.spi.AppenderAttachable接口是一个Append实现。

com.javapai.dataflow.sl4j.format.Formatter接口其子类 主要为ILoggingEvent接口提供了一个可定制的日志格式化。
