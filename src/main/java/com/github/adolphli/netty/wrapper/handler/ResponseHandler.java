package com.github.adolphli.netty.wrapper.handler;

import com.github.adolphli.netty.wrapper.protocol.Message;
import com.github.adolphli.netty.wrapper.util.ResponseFutureManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端Response处理器
 * 会根据RequestId选择正确的ResponseFuture
 */
public class ResponseHandler extends SimpleChannelInboundHandler<Message> {

    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        ResponseFutureManager.get(msg.getId()).putResponse(msg);
    }
}
