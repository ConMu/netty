package com.netty.demo2.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务
 *
 * @author mucongcong
 * @date 2022/07/05 11:41
 * @since
 **/
public class NettyServerHandler04 extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //当有耗时高的业务时- 通过异步解决
        //提交该channel对于的NIOEventLoop的taskQueue上

        //2.用户自定义定时任务-》任务提交到scheduleTaskQueue
        ctx.channel().eventLoop().schedule(() -> {
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello , scheduleTaskQueue阻塞3秒",  CharsetUtil.UTF_8));
        },5, TimeUnit.SECONDS);
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
