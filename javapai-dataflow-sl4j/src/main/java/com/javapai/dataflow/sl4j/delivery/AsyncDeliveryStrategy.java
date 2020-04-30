//package com.javapai.dataflow.sl4j.delivery;
//
//import com.javapai.dataflow.sl4j.JSONEvent;
//import com.javapai.dataflow.sl4j.producer.MessageSender;
//
///**
// * 异步传输策略
// *
// */
//public class AsyncDeliveryStrategy extends DeliveryStrategy {
//    @Override
//    public boolean send(MessageSender sender, JSONEvent event) {
//        return sender.sendAsync(event);
//    }
//}
