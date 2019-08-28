package com.tonytaotao.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NioServer {

    private static Selector selector;

    /**
     * 客户端列表
     * */
    private List<SocketChannel> clientList = new ArrayList<>();

    /**
     * 是否读取包头
     */
    private boolean readHeadBoo = true;

    /**
     * 实际包体大小
     * */
    private int dataSize = 0;

    /**
     * 已读取包体大小
     */
    private int existSize = 0;

    /**
     * 缓存已读取包体数据
     */
    private ByteBuffer cacheBuffer = ByteBuffer.allocate(10);


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

            // 捕获客户端主动断开连接的异常
            try {

                if (readHeadBoo) { // 读取包头
                    ByteBuffer headBuffer = ByteBuffer.allocate(4);

                    client.read(headBuffer);

                    if (!headBuffer.hasRemaining()) {  // headBuffer已经没有空位，证明已经读取了完整的包头

                        headBuffer.flip(); // 将数据写入channel

                        dataSize = byteArrayToInt(headBuffer.array()); //计算实际包体大小

                        System.out.println("服务器已收到客户端" + client.getRemoteAddress() +"的包头数据：" + dataSize);

                        readHeadBoo = false;
                        headBuffer.clear();

                        client = (SocketChannel) key.channel();
                        client.register(selector, SelectionKey.OP_READ);
                    }

                } else { // 读取包体

                    ByteBuffer dataBuffer = ByteBuffer.allocate(10);

                    if (client.read(dataBuffer) > 0) {

                        dataBuffer.flip();

                        int currentSize = dataBuffer.array().length;

                        if (currentSize >= dataSize) { // 一次取出包体
                            String receiveText = String.valueOf(Charset.forName("UTF-8").decode(dataBuffer).array());
                            System.out.println("服务器已收到客户端" + client.getRemoteAddress() +"的包体数据：" + receiveText);

                            String responseMsg = client.getRemoteAddress() + "说：" + receiveText;
                            broadCast(clientList, client, responseMsg, true);

                            System.out.println("服务器转发" +client.getRemoteAddress() +"的消息[" + receiveText + "]成功");

                            readHeadBoo = true;
                            dataSize = 0;

                            client = (SocketChannel) key.channel();
                            client.register(selector, SelectionKey.OP_READ);

                        } else { // 多次取出包体

                            existSize = existSize + currentSize;

                            // 判断已取包体是否超出缓存大小
                            if (existSize > cacheBuffer.capacity()) {
                                ByteBuffer tempBuffer = cacheBuffer;

                                cacheBuffer = ByteBuffer.allocate(tempBuffer.capacity() * 2);
                                if (tempBuffer.position() > 0) {
                                    tempBuffer.flip();
                                    cacheBuffer.put(tempBuffer);
                                }

                            }

                            cacheBuffer.put(dataBuffer);

                            if (existSize >= dataSize) { // 最后一次取完包体
                                cacheBuffer.flip();

                                String receiveText = String.valueOf(Charset.forName("UTF-8").decode(cacheBuffer).array());
                                System.out.println("服务器已收到客户端" + client.getRemoteAddress() +"的包体数据：" + receiveText);

                                String responseMsg = client.getRemoteAddress() + "说：" + receiveText;
                                broadCast(clientList, client, responseMsg, true);

                                System.out.println("服务器转发" +client.getRemoteAddress() +"的消息[" + receiveText + "]成功");

                                readHeadBoo = true;
                                dataSize = 0;
                                existSize = 0;
                                cacheBuffer.clear();

                                client = (SocketChannel) key.channel();
                                client.register(selector, SelectionKey.OP_READ);

                            } else { // 继续等待下一次取包体
                                dataBuffer.clear();
                            }

                        }

                    }

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

        byte[] bytes = msg.getBytes();

        ByteBuffer sBuffer = ByteBuffer.allocate(bytes.length);
        sBuffer.put(bytes);
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


    private int  byteArrayToInt(byte[] bytes) {

        //return bytes[3] & 0xFF | (bytes[2] & 0xFF) << 8 | (bytes[1] & 0xFF) << 16 | (bytes[0] & 0xFF) << 24;

        int value = 0;
        // 由高位到低位
        for (int i = 0, length = bytes.length; i < length; i++) {
            int shift = (length - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;// 往高位游
        }
        return value;
    }


}

