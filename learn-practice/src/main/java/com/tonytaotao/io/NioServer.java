package com.tonytaotao.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class NioServer {

    public static HashSet<String> userList = new HashSet<>();


    public static void main(String[] args) {
        new Thread(new Server()).start();
    }

}

class Server implements Runnable {

    private static CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
    private static CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();

    private boolean flag = true;

    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel = null;

    private List<SocketChannel> clientList = null;

    public Server() {

        try {
            selector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(8080));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            clientList = new ArrayList<>();

            System.out.println("服务器初始化完毕，开始接受客户端连接");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {

        while (this.flag) {

            int num = 0;
            try {
                num = selector.select();
            } catch (IOException e) {
                System.out.println("服务器选择器轮询出错");
                e.printStackTrace();
            }

            if (num > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()) {
                        SocketChannel client = null;
                        try {
                            ServerSocketChannel server = (ServerSocketChannel) key.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);

                            key.interestOps(SelectionKey.OP_ACCEPT);

                            System.out.println("客户端" + client.getRemoteAddress() + "已成功建立连接！");

                            client.write(encoder.encode(CharBuffer.wrap("您已连接服务器,请输入你的名字")));

                            clientList.add(client);

                        } catch (IOException e) {

                            System.out.println("服务器配置客户端连接出错");
                            if (client != null) {
                                try {
                                    client.close();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }

                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                        StringBuilder content = new StringBuilder();
                        try {
                            while (client.read(byteBuffer) > 0) {
                                byteBuffer.flip();
                                content.append(decoder.decode(byteBuffer));
                            }

                            key.interestOps(SelectionKey.OP_READ);

                            if (content.length() > 0) {
                                System.out.println("服务器收到客户端" + client.getRemoteAddress() +"数据:" + content);

                                String[] arrayContent = content.toString().split("#@#");
                                try {

                                    // 注册
                                    if (arrayContent != null && arrayContent.length == 1) {
                                        String name = arrayContent[0];
                                        if (NioServer.userList.contains(name)) {
                                            client.write(encoder.encode(CharBuffer.wrap("user-repeat")));
                                        } else {
                                            NioServer.userList.add(name);
                                            String msg = "欢迎" + name + "加入群聊";
                                            broadCast(selector, client, msg);
                                        }
                                    } else if (arrayContent != null && arrayContent.length >= 1) {
                                        // 发送消息
                                        String name = arrayContent[0];
                                        String msg = name + "说:" + arrayContent[1];
                                        broadCast(selector, client, msg);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (IOException e) {
                            try {
                                System.out.println("客户端" + client.getRemoteAddress() + "已断开连接");
                                client.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                            int length = clientList.size();
                            for (int i = 0 ; i< length ; i++) {
                                if (clientList.get(i).equals(client)) {
                                    clientList.remove(i);
                                    break;
                                }
                            }
                        }

                    }
                }
            }
        }

    }

    public static void broadCast(Selector selector, SocketChannel ownerClient, String content) throws Exception{
        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel) {
                SocketChannel client = (SocketChannel) channel;
                client.write(encoder.encode(CharBuffer.wrap(content)));
            }


        }
    }
}
