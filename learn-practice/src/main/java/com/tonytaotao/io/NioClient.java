package com.tonytaotao.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Set;

public class NioClient {

    /**
     * 发送数据缓冲区
     * */
    private static ByteBuffer sBuffer = ByteBuffer.allocate(1024);
    /**
     * 接受数据缓冲区
     * */
    private static ByteBuffer rBuffer = ByteBuffer.allocate(1024);


    private static Selector selector;

    public static void main(String[] args) {

        new NioClient().init();

    }

    public  void init() {
        try {

            selector = Selector.open();

            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            System.out.println("客户端启动成功");

            while (true) {

                selector.select();

                Set<SelectionKey> keySet = selector.selectedKeys();
                for (final SelectionKey key: keySet) {
                    handle(key);
                }
                keySet.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handle(SelectionKey key) throws IOException{

        if (key.isConnectable()) {

            SocketChannel client = (SocketChannel) key.channel();
            if (client.isConnectionPending()) {
                client.finishConnect();
                System.out.println("客户端连接服务器成功");

                new Thread() {
                    @Override
                    public void run() {
                        while (true) {
                            try {

                                InputStreamReader input = new InputStreamReader(System.in);
                                BufferedReader br = new BufferedReader(input);
                                String sendText = br.readLine();

                                sBuffer.clear();
                                sBuffer.put(sendText.getBytes());
                                sBuffer.flip();

                                client.write(sBuffer);

                            } catch (IOException e) {
                                e.printStackTrace();
                                break;
                            }


                        }
                     }
                }.start();

            }

            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);

        } else if (key.isReadable()) {


            SocketChannel client = (SocketChannel) key.channel();

            rBuffer.clear();

            int readNum = 0;
            try {
                if ((readNum = client.read(rBuffer)) > 0) {
                    String receiveText = new String(rBuffer.array(), 0, readNum);

                    System.out.println(receiveText);

                    client = (SocketChannel) key.channel();
                    client.register(selector, SelectionKey.OP_READ);
                }
            } catch (IOException e) {

                key.cancel();
                client.socket().close();
                client.close();

            }



        }

    }
}

