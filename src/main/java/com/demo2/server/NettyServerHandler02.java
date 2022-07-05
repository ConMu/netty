package com.demo2.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 阻塞10s，不进行处理，netty不做特定处理，handler会一样的阻塞
 * NIO虽然是多路复用，但是当线程执行某一个操作，其他操作都要进行等待
 *
 * @author mucongcong
 * @date 2022/07/05 11:16
 * @since
 **/
/*
 * 1.自定义的handler需要继承netty规定的handlerAdapter
 *重写一些方法，自定义的handler才能称为一个handler
 * */
public class NettyServerHandler02 extends ChannelInboundHandlerAdapter {

    //读取客户端发送的数据
    //ChannelHandlerContext是上下文对象，含有管道pipeline,通道channel
    //Object msg客户端发送的数据，默认Object
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 模拟高耗时业务
        Thread.sleep(5 * 1000);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,阻塞5s", CharsetUtil.UTF_8));

        System.out.println("Server go on ...");
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //write加Flush方法，将数据写入缓存并刷新
        //对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
