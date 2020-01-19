package com.javapai.dataflow.sl4j.delivery2;

public interface FailedDeliveryCallback<E> {
    void onFailedDelivery(E evt, Throwable throwable);
}
