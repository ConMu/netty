package com.demo1.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Date;

/**
 * 机器人服务端, 用于接收指令, 并对指令作出新响应
 * 创建两个NioEventLoopGroup实例，一个用来接受客户端的连接；另一个用来进行SocketChannel读写；
 * 创建ServerBootstrap，用于启动NIO服务端的辅助启动类；
 * 调用创建ServerBootstrap的group方法，将两个NioEventLoopGrou作为参数传入；
 * 创建NioServerSocketChannel，并设置TCP参数，将backlog设置为1024（服务端在接收客户端的TCP连接的时候，同一时间只能处理一个，多了会将请求放在队列里，backlog指定了队列大小）；
 * 绑定ChildChanelHandler，创建pipeline， 处理网络I/O事件、消息编解码等；
 * 线程组阻塞、关闭；
 * @author mucongcong
 * @date 2022/07/05 10:07
 * @since
 **/
public class RobotServer {
    public static void bind(int port) throws InterruptedException {
        // 用于服务端接受客户端连接
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        // 用于进行SocketChannel的网络读写
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // 启动NIO服务端辅助启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 用于处理网络I/O事件,如记录日志、对消息进行编解码等
                    .childHandler(new ChildChanelHandler());

            // 绑定端口, 同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();
            // 等待服务端端口关闭
            future.channel().closeFuture().sync();
        } finally {
            // 优雅关闭线程池资源
            parentGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    /**
     * ChildChanelHandler可完成消息编解码、心跳、流量控制等功能
     */
    private static class ChildChanelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel channel) throws Exception {
            channel.pipeline().addLast(new SimpleChannelInboundHandler() {
                @Override
                protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object command) throws Exception {
                    // 读取指令
                    ByteBuf byteBuf = (ByteBuf) command;
                    byte[] req = new byte[byteBuf.readableBytes()];
                    byteBuf.readBytes(req);
                    // 打印读取的内容
                    System.out.println("Robot Server receive a command: " + new String(req, "UTF-8"));

                    // 处理指令
                    String result = "hello,你好!我叫Robot。";
                    if ("hello".equals(command)) {
                        result = new Date(result).toString();
                    }
                    // 将消息先放到缓冲数组中, 再全部发送到SocketChannel中
                    ByteBuf resp = Unpooled.copiedBuffer(result.getBytes());
                    channelHandlerContext.writeAndFlush(resp);
                }
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RobotServer.bind(8080);
    }
}
