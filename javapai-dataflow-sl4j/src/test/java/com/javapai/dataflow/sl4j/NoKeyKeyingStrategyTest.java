//package com.javapai.dataflow.sl4j;
//
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import com.javapai.dataflow.sl4j.key.NoKeyKeyingStrategy;
//
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.nullValue;
//import static org.junit.Assert.assertThat;
//
//
//public class NoKeyKeyingStrategyTest {
//
//    private final NoKeyKeyingStrategy unit = new NoKeyKeyingStrategy();
//
//    @Test
//    public void shouldAlwaysReturnNull() {
//        assertThat(unit.createKey(Mockito.mock(ILoggingEvent.class)), is(nullValue()));
//    }
//
//}
