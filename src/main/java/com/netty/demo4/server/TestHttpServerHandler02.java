package com.netty.demo4.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 自定义处理器：TestHttpServerHandler 用于过滤资源
 *
 * @author mucongcong
 * @date 2022/07/05 12:40
 * @since
 **/
public class TestHttpServerHandler02 extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断msg 是不是 httpRequest请求
        if (msg instanceof HttpRequest) {

            System.out.println("msg类型 = " + msg.getClass());
            System.out.println("客户端地址 ：" + ctx.channel().remoteAddress());

            //过滤信息
            HttpRequest httpRequest = (HttpRequest) msg;
            //获取URI资源标识符，过滤指定值
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了favicon.ico资源，不做响应");
                return;
            }
            //回复信息给浏览器[http协议信息]

            ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);
            //构造一http的响应，HttpResponse
            //设置响应头信息：版本+状态码+内容
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            //设置响应头信息：返回类型CONTENT_TYPE
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;CharsetUtil.UTF_8");
            //长度
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            //将构建好的响应信息返回
            ctx.writeAndFlush(response);
        }
    }
}
