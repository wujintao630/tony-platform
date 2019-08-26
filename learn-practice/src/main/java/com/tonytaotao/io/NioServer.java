package com.tonytaotao.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NioServer {

    /*接受数据缓冲区*/
    private static ByteBuffer sBuffer = ByteBuffer.allocate(1024);
    /*发送数据缓冲区*/
    private static ByteBuffer rBuffer = ByteBuffer.allocate(1024);

    /*映射客户端channel */
    private Map<String, SocketChannel> clientsMap = new HashMap<String, SocketChannel>();

    private static Selector selector;

    public void init() {
        try {

            selector = Selector.open();

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(8080));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("服务器初始化完毕，开始接受客户端连接");

            while (true) {
                try {

                    selector.select();

                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    for (SelectionKey key : selectionKeys) {
                        handle(key);
                    }

                    selectionKeys.clear();

                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handle(SelectionKey key) throws IOException {


        if (key.isAcceptable()) {

            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);

            System.out.println("客户端" + client.getRemoteAddress() + "已成功建立连接！");

        } else if (key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();

            rBuffer.clear();

            if (client.read(rBuffer) > 0) {
                rBuffer.flip();

                String receiveText = String.valueOf(Charset.forName("UTF-8").decode(rBuffer).array());
                System.out.println("服务器收到消息：" + receiveText);

                broadCast(client, "服务器已收到消息");

                client = (SocketChannel) key.channel();
                client.register(selector, SelectionKey.OP_READ);
            }

        }

    }

    private void broadCast (SocketChannel client,String msg) throws IOException {
        sBuffer.clear();
        sBuffer.put(msg.getBytes());
        sBuffer.flip();

        client.write(sBuffer);
    }

    public static void main(String[] args) {
        new NioServer().init();
    }

}

