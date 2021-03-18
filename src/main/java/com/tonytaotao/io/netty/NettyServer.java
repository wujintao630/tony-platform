package com.tonytaotao.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) throws Exception {


        // 默认线程数 为 CPU核数 * 2
        // 处理连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 处理读写
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(8);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 使用 NioSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列连接数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        //给pipline增加处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            System.out.println("客户channel id = " + socketChannel.hashCode());

                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 给workerGroup 中的 Eventloop 设置管道处理器

            System.out.println("server is ready");

            // 绑定端口并且同步
            ChannelFuture channelFuture = bootstrap.bind(6666).sync();

            //  监听 绑定是否成功
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("监听端口成功");
                    } else {
                        System.out.println("监听失败");
                    }
                }
            });

           // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
