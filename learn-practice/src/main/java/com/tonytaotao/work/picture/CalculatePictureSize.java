package com.tonytaotao.work.picture;

import com.alibaba.excel.EasyExcel;

public class CalculatePictureSize {

    public static void main(String[] args) {
        String fileName = "C:\\Users\\wujintao\\Downloads\\export_result.xlsx";
        EasyExcel.read(fileName, PictureRead.class, new PictureReadListener()).sheet().doRead();
    }

}
