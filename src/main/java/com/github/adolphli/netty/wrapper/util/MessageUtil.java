package com.github.adolphli.netty.wrapper.util;

import com.github.adolphli.netty.wrapper.protocol.Message;

import java.io.IOException;

/**
 * 工具类， 用于将具体对象和Message对象互相转换
 */
public class MessageUtil {
    /**
     * 将传输对象转换为Message对象
     *
     * @param obj
     * @return
     */
    public static Message convertToMessage(int msgId, Object obj) throws IOException {
        return new Message(msgId, null, HessianSerializer.serialize(obj));
    }

    /**
     * 从Message中得到传输对象
     *
     * @param message
     * @return
     */
    public static Object getTransferObject(Message message) throws IOException {
        return HessianSerializer.deserialize(message.getBody());
    }
}
