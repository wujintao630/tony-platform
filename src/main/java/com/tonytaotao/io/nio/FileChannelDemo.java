package com.tonytaotao.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {
    public static void main(String[] args) throws Exception{

        String path = "D:\\file.txt";

        //writeFile("hello Tony", path);

        //readFile(path);
        //copyFile(path);
        copyFile2(path);

    }

    public static void writeFile(String str, String path) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(path);

        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();

        fileChannel.write(byteBuffer);

        fileOutputStream.close();
    }

    public static void readFile(String path) throws Exception {
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(path);

        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        fileChannel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();
    }

    public static void copyFile(String path) throws Exception {

        FileInputStream fileInputStream = new FileInputStream(path);
        FileChannel fileChannelInput = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\file_copy.txt");
        FileChannel fileChannelOut = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (true) {
            byteBuffer.clear(); //  清空缓存

            int read = fileChannelInput.read(byteBuffer);
            if (read == -1) {
                break;
            }

            byteBuffer.flip();
            fileChannelOut.write(byteBuffer);
        }


        fileOutputStream.close();
        fileInputStream.close();

    }

    public static void copyFile2(String path) throws Exception {

        FileInputStream fileInputStream = new FileInputStream(path);
        FileChannel fileChannelInput = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\file_copy.txt");
        FileChannel fileChannelOut = fileOutputStream.getChannel();

        fileChannelOut.transferFrom(fileChannelInput, 0, fileChannelInput.size());


        fileOutputStream.close();
        fileInputStream.close();

    }

}
