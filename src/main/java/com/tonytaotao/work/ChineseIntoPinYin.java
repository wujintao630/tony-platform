package com.tonytaotao.work;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wangyanlong
 * @date 2019/9/11 10:01
 * @description
 */
public class ChineseIntoPinYin {


    /**
     * 转中文首字母
     * @param chinese
     * @return
     */
    public static String ToFirstChar(String chinese){
        StringBuilder pinyinStr = new StringBuilder();
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    String str = String.valueOf(newChar[i]).trim();
                    if (StringUtils.isNotBlank(str)){
                        if (str.contains("、") || str.contains(" ")) {
                            continue;
                        }
                        String[] strings = PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat);
                        if (strings != null && strings.length > 0) {
                            pinyinStr.append(strings[0].charAt(0));
                        }

                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                pinyinStr.append(newChar[i]);
            }
        }

        return pinyinStr.toString();
    }

    /**
     * 中文全部转拼音
     * @param chinese
     * @return
     */
    public static String ToPinyin(String chinese) {
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }

}
