package com.github.adolphli.netty.wrapper.rpc;

import com.github.adolphli.netty.wrapper.protocol.Message;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * ResponseFuture的默认实现类
 */
public class DefaultResponseFuture implements ResponseFuture {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private volatile Message message;

    public Message waitResponse(long timeoutMillis) throws Exception {
        this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        if (countDownLatch.getCount() > 0) {
            throw new TimeoutException();
        }
        return message;
    }

    public void putResponse(Message message) {
        this.message = message;
        this.countDownLatch.countDown();
    }
}
