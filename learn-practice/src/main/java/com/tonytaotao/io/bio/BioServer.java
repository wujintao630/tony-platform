package com.tonytaotao.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {

    public static void main(String[] args) throws Exception {

        // 直接 telnet  通讯

        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true) {

            final Socket socket = serverSocket.accept();
            System.out.println("收到客户端连接");

            executorService.execute(() -> {
                handler(socket);
            });

        }
    }

    public static void handler(Socket socket) {

        System.out.println("threadId"+ Thread.currentThread().getId());


        try {
            byte[] bytes = new byte[1024];

            InputStream inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    System.out.println("关闭输入流");
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
