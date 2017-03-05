package com.github.adolphli.netty.wrapper.rpc;

import com.github.adolphli.netty.wrapper.protocol.Message;
import com.github.adolphli.netty.wrapper.util.MessageUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * RequestProcessor 实现类
 */
public class DefaultHandlerContext implements HandlerContext {

    private final ChannelHandlerContext ctx;
    private final int msgId;

    public DefaultHandlerContext(ChannelHandlerContext ctx, int msgId) {
        this.ctx = ctx;
        this.msgId = msgId;
    }

    public void sendResponse(Object response) throws Exception {
        Message message = MessageUtil.convertToMessage(msgId, response);
        ctx.writeAndFlush(message);
    }
}
