package com.github.adolphli.netty.wrapper.handler;

import com.github.adolphli.netty.wrapper.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 对传输对象进行解码， 解码结果为Message对象
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    /**
     * 最大16MB
     */
    private static final int FRAME_MAX_LENGTH = 16 * 1024 * 1024;

    public MessageDecoder() {
        super(FRAME_MAX_LENGTH, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf byteBuf = (ByteBuf) super.decode(ctx, in);
        if (byteBuf == null) {
            return null;
        }

        Object result = handleByteBuf(byteBuf);
        byteBuf.release();
        return result;
    }

    private Object handleByteBuf(ByteBuf byteBuf) {

        int length = byteBuf.readableBytes();
        int msgId = byteBuf.readInt();
        ;
        int headerLength = byteBuf.readInt();
        byte[] headerData = null;
        if (headerLength > 0) {
            headerData = new byte[headerLength];
            byteBuf.readBytes(headerData);
        }

        int bodyLength = length - 8 - headerLength;
        byte[] bodyData = null;
        if (bodyLength > 0) {
            bodyData = new byte[bodyLength];
            byteBuf.readBytes(bodyData);
        }

        return new Message(msgId, headerData, bodyData);
    }
}
