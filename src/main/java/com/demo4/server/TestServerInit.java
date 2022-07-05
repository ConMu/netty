package com.demo4.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * 自定义了一个TestServerInit类，用于放置多个管道处理器
 *
 * @author mucongcong
 * @date 2022/07/05 12:38
 * @since
 **/
public class TestServerInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器

        //得到管道
        ChannelPipeline pipeline = ch.pipeline();

        //加入netty提供的httpServerCodec  codec=>[coder - decoder]
        //HttpServerCodec是netty提供的编解码器
        pipeline.addLast("MyTestHTTPCodec",new HttpServerCodec());
        //增加一个自定义的handler
        pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());
        ch.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
    }
}
