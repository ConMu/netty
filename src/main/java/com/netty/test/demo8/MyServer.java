package com.netty.test.demo8;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 编写一个 Netty 群聊系统，实现服务器端和客户端之间的数据简单通讯（非阻塞）
 * 实现多人群聊
 * 服务器端：可以监测用户上线，离线，并实现消息转发功能
 * 客户端：通过 channel 可以无阻塞发送消息给其它所有用户，同时可以接受其它用户发送的消息（有服务器转发得到）
 * 目的：进一步理解 Netty 非阻塞网络编程机制
 *
 * @author mucongcong
 * @date 2022/10/24 11:16
 * @since
 **/
public class MyServer {

    private int port;

    public MyServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // 创建线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128) // SO_BACKLOG 用于处理连接队列大小，客户端顺次执行  SO_KEEPALIVE 一直保持连接活动状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取到pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //向pipeline加入编解码器
                            pipeline.addLast("decoder", new MyMessageDecoder());
                            pipeline.addLast("encoder", new MyMessageEncoder());
                            //加入自己的业务处理handler
                            pipeline.addLast(new MyServerHandlerOptimize());
                        }
                    });
            System.out.println("服务器启动");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            //监听关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new MyServer(6668).run();

    }
}
