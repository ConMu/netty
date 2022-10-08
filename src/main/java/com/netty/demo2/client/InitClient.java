package com.netty.demo2.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author mucongcong
 * @date 2022/07/08 17:47
 * @since
 **/
public class InitClient {
    public static Bootstrap init(){
        //客户端需要一个事件循环组
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            //创建客户端启动对象Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            //设置相关参数
            bootstrap.group(eventLoopGroup)//设置线程组
                    .channel(NioSocketChannel.class)//设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());//加入自定义处理器
                        }
                    });
            System.out.println("客户端就绪。。。");
            //启动客户端去连接服务器端
            //ChannelFuture涉及到异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9998).sync();
            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
            return bootstrap;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
        return null;
    }
}
