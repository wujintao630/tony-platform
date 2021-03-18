package com.tonytaotao.work.picture;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class PictureReadListener extends AnalysisEventListener<PictureRead> {

    private static final int BATCH_COUNT = 50;
    List<PictureRead> list = new ArrayList<>(BATCH_COUNT);

    @Override
    public void invoke(PictureRead data, AnalysisContext context) {

        System.out.println("解析数据：" + data);

        list.add(data);

        if (list.size() >= BATCH_COUNT) {
            System.out.println("当前处理数据量50");
            PictureWriteUtil.dealData(list);
            list.clear();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

        PictureWriteUtil.flag = false;
        PictureWriteUtil.dealData(list);
        System.out.println("处理完成");

    }

}
