package com.tonytaotao.io.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 自定义handler,需要继承netty规范好的某个HandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取实际数据
     * @param ctx   上下文对象，含有管道pipline，通道channel，地址
     * @param msg   客户端发送的消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        // 普通消息
        /*System.out.println("服务器读取线程：" + Thread.currentThread().getName());
        System.out.println("server ctx = " + ctx);

        // 将msg 转成 bytebuf
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println("客户端发送的消息是：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());*/


        // 自定义普通任务 taskQueue
        /*ctx.channel().eventLoop().execute( () -> {
            try {
                Thread.sleep(5 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("[任务队列]hello, 客户端~", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });*/

        // 自定义定时任务 scheduleTaskQueue
        ctx.channel().eventLoop().schedule(() -> {

            ctx.writeAndFlush(Unpooled.copiedBuffer("[定时任务队列]hello, 客户端~", CharsetUtil.UTF_8));

        }, 5, TimeUnit.SECONDS);

        System.out.println("go on ..");

    }

    // 读取数据完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~", CharsetUtil.UTF_8));
    }

    // 处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
