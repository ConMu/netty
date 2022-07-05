package com.demo1.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 创建客户端处理I/O读写的EventLoopGroup；
 * 创建客户端启动辅助类Bootstrap，并将NioEventLoopGroup参数绑定；
 * 设置channel，与服务端不同的是，客户端的channel是NioSocketChannel；
 * 设置TCP参数ChannelOption.TCP_NODELAY； 添加handler类，监听channel，处理事件；
 * 调用connect发起异步连接，等待连接成功； 连接关闭后，客户端主函数退出，释放线程组。
 *
 * @author mucongcong
 * @date 2022/07/05 10:28
 * @since
 **/
public class RobotClient {

    public void connect(int port, String host) throws InterruptedException {
        // 配置客户端NIO线程组, 用于客户端I/O读写
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new SimpleChannelInboundHandler() {

                                        @Override
                                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
                                            ByteBuf byteBuf = (ByteBuf) msg;
                                            byte[] response = new byte[byteBuf.readableBytes()];
                                            byteBuf.readBytes(response);

                                            System.out.println("Client: " + new String(response, "UTF-8"));
                                        }

                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            final ByteBuf firstMsg;
                                            byte[] req = "hello".getBytes();
                                            firstMsg = Unpooled.buffer(req.length);
                                            firstMsg.writeBytes(req);
                                            ctx.channel().writeAndFlush(firstMsg);
                                            super.channelActive(ctx);
                                        }

                                    });
                        }
                    });

            // 发起异步连接
            ChannelFuture future = bootstrap.connect(host, port);
            // 等待客户端关闭
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new RobotClient().connect(8080, "127.0.0.1");
    }
}
