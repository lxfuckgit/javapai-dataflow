//package com.javapai.dataflow.sl4j.delivery;
//
//import com.javapai.dataflow.sl4j.JSONEvent;
//import com.javapai.dataflow.sl4j.producer.MessageSender;
//
///**
// * 同步传输策略
// *
// */
//public class BlockingDeliveryStrategy extends DeliveryStrategy {
//
//    @Override
//    public boolean send(MessageSender sender, JSONEvent event) {
//        return sender.send(event);
//    }
//}
