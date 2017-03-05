package com.github.adolphli.netty.wrapper.rpc;

import com.github.adolphli.netty.wrapper.protocol.Message;

/**
 * 等待客户端调用结果的Future对象
 */
public interface ResponseFuture {

    /**
     * 等待调用结果， 最长等待timeout ms
     *
     * @param timeout
     * @return
     */
    Message waitResponse(final long timeout) throws Exception;

    /**
     * 为Future对象设置调用结果
     *
     * @param message
     */
    void putResponse(Message message);
}
