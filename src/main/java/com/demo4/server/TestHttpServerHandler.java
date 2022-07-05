package com.demo4.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 自定义处理器：TestHttpServerHandler
 *
 * @author mucongcong
 * @date 2022/07/05 12:40
 * @since
 **/
public class TestHttpServerHandler  extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断msg 是不是 httpRequest请求
        if (msg instanceof HttpRequest) {

            System.out.println("msg类型 = " + msg.getClass());
            System.out.println("客户端地址 ：" + ctx.channel().remoteAddress());
            System.out.println("PipeLine hashCode :"+ctx.channel().pipeline().hashCode());
            System.out.println("handler hashCode :"+this.hashCode());

            //回复信息给浏览器[http协议信息]

            ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);
            //构造一http的响应，HttpResponse
            //设置响应头信息：版本+状态码+内容
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            //长度
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //设置响应头信息：返回类型CONTENT_TYPE 解决中文乱码问题
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/html; charset=UTF-8");
            //将构建好的响应信息返回
            ctx.writeAndFlush(response);
        }
    }
}
