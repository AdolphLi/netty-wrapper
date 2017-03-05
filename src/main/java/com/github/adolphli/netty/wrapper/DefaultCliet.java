package com.github.adolphli.netty.wrapper;

import com.github.adolphli.netty.wrapper.handler.MessageDecoder;
import com.github.adolphli.netty.wrapper.handler.MessageEncoder;
import com.github.adolphli.netty.wrapper.handler.ResponseHandler;
import com.github.adolphli.netty.wrapper.protocol.Message;
import com.github.adolphli.netty.wrapper.rpc.DefaultResponseFuture;
import com.github.adolphli.netty.wrapper.rpc.ResponseFuture;
import com.github.adolphli.netty.wrapper.util.IDGenerator;
import com.github.adolphli.netty.wrapper.util.MessageUtil;
import com.github.adolphli.netty.wrapper.util.ResponseFutureManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Client 接口的默认实现类
 */
public class DefaultCliet implements Client {

    private final String serverAddress;
    private final int port;
    private final Channel channel;
    private final Bootstrap bootstrap = new Bootstrap();

    /**
     * DefaultClient 构造函数
     *
     * @param serverAddress 服务端地址
     * @param port          服务端端口
     * @throws Exception
     */
    public DefaultCliet(String serverAddress, int port) throws Exception {
        this.serverAddress = serverAddress;
        this.port = port;
        this.bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageEncoder(), new MessageDecoder(), new ResponseHandler());
                    }
                });
        channel = this.createChannel();
    }

    public Object invokeSync(Object value, long timeout) throws Exception {
        int id = IDGenerator.nextId();
        ResponseFuture responseFuture = new DefaultResponseFuture();
        ResponseFutureManager.put(id, responseFuture);

        channel.writeAndFlush(MessageUtil.convertToMessage(id, value));

        Message message = responseFuture.waitResponse(timeout);
        return MessageUtil.getTransferObject(message);
    }

    public Channel createChannel() throws Exception {
        ChannelFuture channelFuture = this.bootstrap.connect(serverAddress, port);
        channelFuture.awaitUninterruptibly();
        if (!channelFuture.isSuccess()) {
            throw new Exception("Create Channel error.");
        }
        return channelFuture.channel();
    }
}
