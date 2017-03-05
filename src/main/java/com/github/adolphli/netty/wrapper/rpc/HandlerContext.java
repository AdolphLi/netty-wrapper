package com.github.adolphli.netty.wrapper.rpc;

/**
 * 提供给RequestProcessor使用的HandlerContext， 用户可以直接使用此接口的sendResponse返回结果
 */
public interface HandlerContext {
    /**
     * 返回调用结果
     *
     * @param response
     * @throws Exception
     */
    void sendResponse(Object response) throws Exception;
}
