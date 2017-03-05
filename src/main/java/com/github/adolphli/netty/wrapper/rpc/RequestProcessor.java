package com.github.adolphli.netty.wrapper.rpc;

import java.util.concurrent.Executor;

/**
 * 用户处理器接口
 * 如果传输对象的类名等于interest()方法返回值， 框架将在用户提供的线程池中调用handleRequest方法，
 *
 * @param <T>
 */
public interface RequestProcessor<T> {

    /**
     * 对请求进行处理
     */
    void handleRequest(HandlerContext handlerContext, T request);

    /**
     * 返回业务线程池
     */
    Executor getExecutor();

    /**
     * Processor处理的类名
     */
    String interest();
}