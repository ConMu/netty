package com.demo4.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
/**
 * http
 *
 * @author mucongcong
 * @date 2022/07/05 11:57
 * @since
 **/
public class TestServer {

    public static void main(String[] args) throws Exception {
        //两个线程池
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //ServerBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //配置参数
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInit());//将设置的类放入

            ChannelFuture channelFuture = serverBootstrap.bind(9999);

            channelFuture.channel().closeFuture().sync();

        } finally {
            //关闭线程池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
