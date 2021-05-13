package com.tonytaotao.algorithm.leecode;

import java.util.ArrayList;
import java.util.List;

/**
 *  Z 字形变换
 *  将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列，然后，需要从左往右逐行读取，产生出一个新的字符串
 *
 *  PAYPALISHIRING
 *
 *  P       A       H       N
 *  A   P   L   S   I   I   G
 *  Y       I       R
 *
 *  PAHNAPLSIIGYIR
 */
public class LC0006 {

    public String convert(String s, int numRows) {

        if (numRows == 1) {
            return s;
        }

        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i< Math.min(numRows, s.length()); i++) {
            rows.add(new StringBuilder());
        }

        int currentRow = 0;
        boolean goingDown = false;
        for (char c : s.toCharArray()) {
            rows.get(currentRow).append(c);
            if (currentRow == 0 || currentRow == numRows - 1) {
                goingDown = !goingDown;
            }
            if (goingDown) {
                currentRow = currentRow + 1;
            } else {
                currentRow = currentRow - 1;
            }
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String s = "PAYPALISHIRING";
        int nums = 3;
        System.out.println(new LC0006().convert(s, nums));
    }
}
