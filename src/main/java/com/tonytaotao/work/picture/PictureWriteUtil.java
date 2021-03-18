package com.tonytaotao.work.picture;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PictureWriteUtil {

    public static boolean flag = true;

    static ExcelWriter excelWriter = EasyExcel.write("C:\\Users\\wujintao\\Downloads\\1.xlsx", PictureWrite.class).excelType(ExcelTypeEnum.XLSX).build();
    static WriteSheet writeSheet = EasyExcel.writerSheet("图片尺寸数据").build();

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void dealData(List<PictureRead> list) {

        try {

            List<PictureWrite> pictureWriteList = new ArrayList<>();

            for (PictureRead pictureRead : list) {
                URL url = new URL(pictureRead.getPictureUrl());
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                BufferedImage image = ImageIO.read(connection.getInputStream());

                PictureWrite pictureWrite = new PictureWrite();
                pictureWrite.setProductCode(pictureRead.getProductCode());
                pictureWrite.setPictureVersion(pictureRead.getPictureVersion());
                pictureWrite.setPictureUrl(pictureRead.getPictureUrl());
                pictureWrite.setWidthSize(image.getWidth());
                pictureWrite.setHeightSize(image.getHeight());

                System.out.println("结果数据：" + pictureWrite);

                pictureWriteList.add(pictureWrite);
            }

            excelWriter.write(pictureWriteList, writeSheet);
            if (!flag) {
                excelWriter.finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
