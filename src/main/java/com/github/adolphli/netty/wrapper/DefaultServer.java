package com.github.adolphli.netty.wrapper;

import com.github.adolphli.netty.wrapper.handler.MessageDecoder;
import com.github.adolphli.netty.wrapper.handler.MessageEncoder;
import com.github.adolphli.netty.wrapper.handler.ProcessHandler;
import com.github.adolphli.netty.wrapper.rpc.RequestProcessor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Server 接口的默认实现类
 */
public class DefaultServer implements Server {

    private final ServerBootstrap serverBootstrap;
    private final int port;
    private final ConcurrentMap<String, RequestProcessor<?>> requestProcessors = new ConcurrentHashMap<String, RequestProcessor<?>>();
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();

    /**
     * DefaultServer 构造函数
     *
     * @param port 开启端口
     */
    public DefaultServer(int port) {
        this.port = port;
        this.serverBootstrap = new ServerBootstrap();
    }

    public void start() throws Exception {
        this.serverBootstrap.group(bossGroup, new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 65536)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageEncoder(), new MessageDecoder(), new ProcessHandler(requestProcessors));
                    }
                });
        this.serverBootstrap.bind().sync();
    }

    public void registerRequestProcessor(RequestProcessor<?> processor) {
        requestProcessors.put(processor.interest(), processor);
    }

    public void stop() {
        this.bossGroup.shutdownGracefully();
    }
}
