package com.github.adolphli.netty.wrapper.util;

import com.github.adolphli.netty.wrapper.rpc.ResponseFuture;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 工具类，用于管理ResponseFuture
 */
public class ResponseFutureManager {

    private static final ConcurrentMap<Integer, ResponseFuture> responseFutureMap = new ConcurrentHashMap<Integer, ResponseFuture>();

    public static void put(Integer id, ResponseFuture responseFuture) {
        responseFutureMap.put(id, responseFuture);
    }

    public static ResponseFuture get(Integer id) {
        return responseFutureMap.remove(id);
    }
}
