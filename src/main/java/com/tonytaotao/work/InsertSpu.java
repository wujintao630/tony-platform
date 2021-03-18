package com.tonytaotao.work;

import com.alibaba.excel.EasyExcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertSpu {

    public static void main(String[] args) {

        // 1.读取六级分类数据
        String categoryFileName = "C:\\Users\\wujintao\\Downloads\\export_result.xlsx";
        List<HashMap<Integer, String>> categoryList = EasyExcel.read(categoryFileName).sheet(0).doReadSync();
        System.out.println(categoryList);

        Map<String,  HashMap<Integer, String>> categoryMap = new HashMap<>();
        for (HashMap<Integer, String> map : categoryList) {
            categoryMap.put(map.get(0), map);
        }

        // 读取待处理的数据
        String fileName = "C:\\Users\\wujintao\\Downloads\\要新建的化妆品spu完整信息表.xlsx";
        List<HashMap<Integer, String>> list = EasyExcel.read(fileName).sheet(0).doReadSync();
        System.out.println(list);

        Integer spu_code_int = 4108990;


        Integer spu_category = 4 ;
        Integer product_type = 0;
        String instruction_spec = "无";
        String production_address = "";
        String brand_enterprise = "";
        Integer dosage_form = 11;
        Integer business_scope = 1154;
        String business_scope_multi = "1154";
        Integer storage_cond = 0;
        Integer shading_attr = 6;
        String storage = "";
        String origin_place = "";
        String special_attr = "";
        String fourth_category_str = "";
        Integer in_rate = 1088;
        Integer out_rate = 1088;
        String market_author = "";
        Integer rate_discount = 0;
        String spu_source = "批量导入";
        Integer sku_count = 0;
        String create_user = "init";
        String create_time = "2021-02-23 14:00:00";
        String create_institution_name = "";
        String update_user = "init";
        String update_time = "2021-02-23 14:00:00";
        Integer large_category = 0;
        String quality_standard = "";
        Integer whether_supervision = 0;
        Integer approval_img_count = 0;
        String standard_codes = "";
        Integer chronic_diseases_variety = -1;
        Integer disable_status  = 1;
        String remark = "";
        for (HashMap<Integer, String> map : list) {

            String spu_code = String.valueOf(++spu_code_int);

            String key = map.get(6) + "-" + map.get(7) + "-" + map.get(8) + "-" + map.get(9) + "-" + map.get(10) + "-" + map.get(11);
            HashMap<Integer, String> category = categoryMap.get(key);

            Integer first_category = Integer.parseInt(category.get(1));
            Integer second_category = Integer.parseInt(category.get(2));
            Integer third_category = Integer.parseInt(category.get(3));
            Integer fourth_category = Integer.parseInt(category.get(4));
            Integer five_category = Integer.parseInt(category.get(5));
            Integer six_category = Integer.parseInt(category.get(6));
            String category_code = category.get(7);

            String general_name = map.get(3);
            String general_name_code = ChineseIntoPinYin.ToFirstChar(general_name).toUpperCase();
            String approval_no = map.get(1);
            String tax_category_code = map.get(12);
            String manufacturer_name = map.get(13);


            String ss = "INSERT INTO me_spu(spu_code, spu_category, product_type, general_name, general_name_code, instruction_spec, approval_no, manufacturer, production_address, brand_enterprise, dosage_form, business_scope, business_scope_multi, storage_cond, shading_attr, storage, origin_place, special_attr, first_category, second_category, third_category, fourth_category, five_category, six_category, category_code, fourth_category_str, tax_category_code, in_rate, out_rate, market_author, rate_discount, spu_source, sku_count, create_user, create_time, create_institution_name, update_user, update_time, large_category, quality_standard, whether_supervision, approval_img_count, standard_codes, chronic_diseases_variety, disable_status, remark) VALUES (" +
                    "'%s', %d, %d, '%s', '%s', '%s', '%s', (select id from me_total_dictionary where dict_name = '%s' and is_valid = 1 ), '%s', '%s', %d, %d, '%s', %d, %d, '%s', '%s', '%s', %d, %d, %d, %d, %d, %d, '%s', '%s', '%s', %d, %d, '%s', %d, '%s', %d, '%s', '%s', '%s', '%s', '%s', %d, '%s', %d, %d, '%s', %d, %d, '%s');";


            System.out.printf(ss, spu_code, spu_category, product_type, general_name, general_name_code, instruction_spec, approval_no, manufacturer_name, production_address, brand_enterprise, dosage_form, business_scope, business_scope_multi, storage_cond, shading_attr, storage, origin_place, special_attr, first_category, second_category, third_category, fourth_category, five_category, six_category, category_code, fourth_category_str, tax_category_code, in_rate, out_rate, market_author, rate_discount, spu_source, sku_count, create_user, create_time, create_institution_name, update_user, update_time, large_category, quality_standard, whether_supervision, approval_img_count, standard_codes, chronic_diseases_variety, disable_status, remark);
            System.out.println();

        }
    }

}


    //String spuCode = serialNumberService.getSerialNumber(SerialNumberTypeEnum.SPU, totalDictionaryDto.getSimpleCode());

