package com.tonytaotao.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

public class NioServer {

    /**
     * 接受数据缓冲区
     * */
    private static ByteBuffer sBuffer = ByteBuffer.allocate(1024);

    /**
     * 发送数据缓冲区
     * */
    private static ByteBuffer rBuffer = ByteBuffer.allocate(1024);

    /**
     * 客户端列表
     * */
    private List<SocketChannel> clientList = new ArrayList<>();

    private static Selector selector;

    public static void main(String[] args) {
        new NioServer().init();
    }

    public void init() {
        try {

            selector = Selector.open();

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(8080));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("服务器初始化完毕，开始接受客户端连接!");

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

            if (!clientList.contains(client)) {
                clientList.add(client);
            }

            // 单独回复给客户端
            String responseMsg = "您已加入聊天室，尽情聊天吧";
            sendMsg(client, responseMsg);

            // 发送给其他客户端
            responseMsg = "欢迎" + client.getRemoteAddress() + "加入聊天室！";
            broadCast(clientList, client, responseMsg, false);

        } else if (key.isValid() && key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();

            rBuffer.clear();

            // 捕获客户端主动断开连接的异常
            try {
                if (client.read(rBuffer) > 0) {
                    rBuffer.flip();

                    String receiveText = String.valueOf(Charset.forName("UTF-8").decode(rBuffer).array());
                    System.out.println("服务器收到消息：" + receiveText);

                    String responseMsg = client.getRemoteAddress() + "说：" + receiveText;
                    broadCast(clientList, client, responseMsg, true);

                    System.out.println("服务器转发" +client.getRemoteAddress() +"的消息[" + receiveText + "]成功");

                    client = (SocketChannel) key.channel();
                    client.register(selector, SelectionKey.OP_READ);
                }
            } catch (IOException e) {

                System.out.println(client.getRemoteAddress() + "已下线！");

                String responseMsg = "来自服务器的消息：" + client.getRemoteAddress() + "已下线！";

                clientList.remove(client);

                key.cancel();
                client.socket().close();
                client.close();

                broadCast(clientList, client, responseMsg, false);
            }

        }

    }

    /**
     * 给客户端发消息
     * @param client
     * @param msg
     * @throws IOException
     */
    private void sendMsg(SocketChannel client, String msg) throws IOException {

        sBuffer.clear();
        sBuffer.put(msg.getBytes());
        sBuffer.flip();

        client.write(sBuffer);
    }

    /**
     * 广播消息
     * @param clientList
     * @param ownClient
     * @param msg
     * @param sendOwnBoo
     * @throws IOException
     */
    private void broadCast(List<SocketChannel> clientList, SocketChannel ownClient, String msg, boolean sendOwnBoo) throws IOException {

        if (sendOwnBoo) {
            for(SocketChannel client : clientList) {
                sendMsg(client, msg);
            }
        } else {
            for(SocketChannel client : clientList) {
                if (!client.equals(ownClient)) {
                    sendMsg(client, msg);
                }
            }

        }

    }


}

