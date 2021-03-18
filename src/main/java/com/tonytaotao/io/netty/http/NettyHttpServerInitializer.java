package com.tonytaotao.io.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class NettyHttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        channelPipeline.addLast("MyHttpServerHandler", new NettyHttpServerHandler());

    }
}
