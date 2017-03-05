package com.github.adolphli.netty.wrapper;

/**
 * 客户端接口类， 包含进行rpc调用的接口， 此版本只提供同步调用示例
 *
 */
public interface Client {

    /**
     * 进行一次同步rpc调用
     *
     * @param value   调用参数
     * @param timeout 超时时间
     * @return 调用结果
     */
    Object invokeSync(Object value, long timeout) throws Exception;
}
