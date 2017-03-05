package com.github.adolphli.netty.wrapper.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 工具类， 用于生成唯一ID
 */
public class IDGenerator {

    private static final AtomicInteger id = new AtomicInteger(0);

    public static int nextId() {
        return id.incrementAndGet();
    }

    public static void resetId() {
        id.set(0);
    }
}