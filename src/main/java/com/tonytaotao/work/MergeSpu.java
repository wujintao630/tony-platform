package com.tonytaotao.work;

import com.alibaba.excel.EasyExcel;

import java.util.HashMap;
import java.util.List;

public class MergeSpu {

    public static void main(String[] args) {
        String fileName = "C:\\Users\\wujintao\\Downloads\\合并spu分工1.xlsx";
        List<HashMap<Integer, String>> list = EasyExcel.read(fileName).sheet(0).doReadSync();
        System.out.println(list);

        for (HashMap<Integer, String> map : list) {

            String oldSpuCode = map.get(0);
            String newSpuCode = map.get(2);
            String newApprovalNo =  map.get(3);

            System.out.printf("INSERT INTO me_product_mq (product_code,`type`) SELECT sku_code, 1 FROM me_sku WHERE sku_code IN (select sku_code from me_sku where spu_code = '%s');", oldSpuCode);
            System.out.println();

            System.out.printf("update me_spu set sku_count = 0, disable_status = 2 where spu_code = '%s';", oldSpuCode);
            System.out.println();

            System.out.printf("update me_sku set spu_code = '%s' where spu_code = '%s');", newSpuCode, oldSpuCode);
            System.out.println();

            System.out.printf("update me_sku_remove set spu_code = '%s' where spu_code = '%s');", newSpuCode, oldSpuCode);
            System.out.println();

            System.out.printf("update me_sau set spu_code = '%s' where spu_code = '%s');", newSpuCode, oldSpuCode);
            System.out.println();

            System.out.printf("update me_spu set sku_count = (select count(1) from me_sku where spu_code = '%s' or spu_code = '%s' and disable_status < 2), approval_no = '%s', disable_status = 1 where spu_code = '%s';", newSpuCode, oldSpuCode, newApprovalNo, newSpuCode);
            System.out.println();

            System.out.println();
        }
    }

}
