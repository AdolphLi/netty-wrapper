package com.github.adolphli.netty.wrapper.handler;

import com.github.adolphli.netty.wrapper.protocol.Message;
import com.github.adolphli.netty.wrapper.rpc.DefaultHandlerContext;
import com.github.adolphli.netty.wrapper.rpc.RequestProcessor;
import com.github.adolphli.netty.wrapper.util.MessageUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ConcurrentMap;

/**
 * 服务端处理器Handler
 * 会根据传输对象选择正确的RequestProcessor进行处理
 */
public class ProcessHandler extends SimpleChannelInboundHandler<Message> {

    private final ConcurrentMap<String, RequestProcessor<?>> requestProcessors;

    public ProcessHandler(ConcurrentMap<String, RequestProcessor<?>> requestProcessors) {
        this.requestProcessors = requestProcessors;
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final Message msg) throws Exception {
        final Object object = MessageUtil.getTransferObject(msg);
        final RequestProcessor requestProcessor = requestProcessors.get(object.getClass().getName());
        if (requestProcessor != null) {
            requestProcessor.getExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    requestProcessor.handleRequest(new DefaultHandlerContext(ctx, msg.getId()), object);
                }
            });
        }
    }
}
