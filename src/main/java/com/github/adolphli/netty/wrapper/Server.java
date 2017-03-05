package com.github.adolphli.netty.wrapper;

import com.github.adolphli.netty.wrapper.rpc.RequestProcessor;

/**
 * 服务端接口类， 包含启动服务器，关闭服务器，注册用户处理器等接口
 */
public interface Server {

    /**
     * 注册用户处理器
     *
     * @param processor
     */
    void registerRequestProcessor(RequestProcessor<?> processor);

    /**
     * 启动服务器
     *
     * @throws Exception
     */
    void start() throws Exception;

    /**
     * 关闭服务器
     */
    void stop();
}
