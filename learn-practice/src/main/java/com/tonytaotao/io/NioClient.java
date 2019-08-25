package com.tonytaotao.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Scanner;

public class NioClient {


    private static CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
    private static CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();



    public static void main(String[] args) {

        String name = "";

        Client client1 = new Client("A", decoder, encoder);
        new Thread(client1).start();

        Scanner scanner = new Scanner(System.in);
        try {

            while (scanner.hasNextLine()) {
                String readLine = scanner.nextLine();
                if ("".equals(readLine)) {
                    continue;
                }

                if ("bye".equals(readLine)) {
                    client1.close();
                }

                if ("".equals(name)) {
                    name = readLine;
                    readLine = name + "#@#";
                } else {
                    readLine = name + "#@#" + readLine;
                }

                client1.send(readLine);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



class Client implements Runnable {

    private String clientName;
    private CharsetDecoder decoder;
    private CharsetEncoder encoder;

    private ByteBuffer byteBuffer;
    private Selector selector = null;
    private SocketChannel socketChannel = null;
    private SelectionKey selectionKey = null;

    private boolean flag = true;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Client(String clientName, CharsetDecoder decoder, CharsetEncoder encoder) {

        this.clientName = clientName;
        this.decoder = decoder;
        this.encoder = encoder;

        try {

            byteBuffer = ByteBuffer.allocate(1024);
            selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));

            selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("客户端" + clientName + "正在运行");

        try {

            while (this.flag) {
                int num = 0;
                try {
                    num = selector.select();
                } catch (Exception e) {

                }
                if (num > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        if (key.isConnectable()) {

                            SocketChannel client = (SocketChannel) key.channel();
                            if (client.isConnectionPending()) {
                                client.finishConnect();
                            }
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);

                        } else if (key.isReadable()) {

                            try {
                                SocketChannel channel = (SocketChannel) key.channel();
                                int readNum = 0;
                                String msg = "";
                                while ((readNum = channel.read(byteBuffer)) > 0) {
                                    byteBuffer.flip();
                                    msg = msg + decoder.decode(byteBuffer).toString();

                                }

                                key.interestOps(SelectionKey.OP_READ);

                                if ("user-repeat".equals(msg)) {
                                    System.out.println("用户已存在");
                                }

                                if (!"".equals(msg)) {
                                    System.out.println("客户端" + clientName + "收到服务器消息:" + msg);
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            if (this.socketChannel != null && this.socketChannel.isOpen()) {
                try {
                    this.socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (this.selector != null && this.selector.isOpen()) {
                try {
                    this.selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void send(String msg) {
        try {
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            channel.write(encoder.encode(CharBuffer.wrap(msg)));
        } catch (Exception e) {
            System.out.println("客户端" + clientName + "发送消息失败, 消息为:" + msg);
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            selector.close();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
