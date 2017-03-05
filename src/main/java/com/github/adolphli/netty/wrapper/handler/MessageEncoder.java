package com.github.adolphli.netty.wrapper.handler;

import com.github.adolphli.netty.wrapper.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 对传输对象进行编码
 * 格式为：总长度(4字节) + msgId(4字节) + Header长度(4字节) + Header内容 + body内容
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {

        out.writeInt(8 + msg.getHeaderLength() + msg.getBodyLength());
        out.writeInt(msg.getId());
        out.writeInt(msg.getHeaderLength());
        if (msg.getHeader() != null) {
            out.writeBytes(msg.getHeader());
        }
        if (msg.getBody() != null) {
            out.writeBytes(msg.getBody());
        }
    }
}
