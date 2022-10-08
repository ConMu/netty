package com.netty.demo2.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/*
 * 1.自定义的handler需要继承netty规定的handlerAdapter
 *重写一些方法，自定义的handler才能称为一个handler
 * */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    //读取客户端发送的数据
    //ChannelHandlerContext是上下文对象，含有管道pipeline,通道channel
    //Object msg客户端发送的数据，默认Object
    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = "+ctx);
        //将msg转出ByteBuf,ByteBuf是nettty提供的，不是NIO的ByteBuffer
        ByteBuf buf=(ByteBuf) msg;
        System.out.println("客户端发送的消息是："+buf.toString(CharsetUtil.UTF_8));

//        Book book = (Book) msg;
//        System.out.println("客户端发送的消息是："+book);
        System.out.println("客户端地址："+ctx.channel().remoteAddress());

    }
    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //write加Flush方法，将数据写入缓存并刷新
        //对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端",CharsetUtil.UTF_8));



    }

    //处理异常，关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}