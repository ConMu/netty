package com.demo2.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * Netty模型中NioEventLoop线程还有一个TaskQueue任务队列对象
 * TaskQueue有三个使用场景
 *
 * 用户自定义的普通任务
 * 用户自定义定时任务
 * 非当前Reactor线程调用Channel的方法（推送系统）
 *
 * 任务队列可以处理上述高耗时任务的问题
 *
 * @author mucongcong
 * @date 2022/07/05 11:22
 * @since
 **/
public class NettyServerHandler03 extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //当有耗时高的业务时- 通过异步解决
        //提交该channel对于的NIOEventLoop的taskQueue上

        //1.用户自定义的普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000);
                    System.out.println("时间" + new Date());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello , 阻塞5秒",  CharsetUtil.UTF_8));
            }
        });
        //1.用户自定义的普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3 * 1000);
                    System.out.println("时间" + new Date());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello , 阻塞3秒",  CharsetUtil.UTF_8));
            }
        });
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //write加Flush方法，将数据写入缓存并刷新
        //对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端", CharsetUtil.UTF_8));

        System.out.println("开始时间" + new Date());



    }

    //处理异常，关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
