/**
 * Project Name:ezsmall_mayun
 * File Name:Check.java
 * Package Name:com.wemall.core.tools
 * Date:2018年5月5日下午1:31:32
 * Copyright (c) 2018, yanL All Rights Reserved.
 *
*/

package com.sanbang.utils;

import java.util.ArrayList;

/**
 * Description: <br/>
 * Date: 2018年5月5日 下午1:31:32 <br/>
 * 
 * @author yanL
 * @version
 * @see
 */
public class EvenOddCheck {
    private final boolean EVEN_CHECK = true;

    private final boolean ODD_CHECK = false;

    private boolean flag = ODD_CHECK;

    private static String msg = null;

    public String getMsg() {
        return msg;
    }

    public void setEvenCheck() {
        flag = EVEN_CHECK;
    }

    public void setOddCheck() {
        flag = ODD_CHECK;
    }

    public void toEvenOddCode(ArrayList<Integer> code) {
        msg = "信息源码: ";
        for (int i = 0; i < code.size(); ++i)
            msg += code.get(i);
        int cnt1 = 0;// 数字1的个数
        for (int i = 0; i < code.size(); ++i)
            if (code.get(i) == 1)
                ++cnt1;
        if (flag == EVEN_CHECK) {
            if ((cnt1 & 1) == 1)
                code.add(0, 1);
            else
                code.add(0, 0);
        } else if (flag == ODD_CHECK) {
            if ((cnt1 & 1) == 1)
                code.add(0, 0);
            else
                code.add(0, 1);
        }
        msg += ", 奇偶校验码: ";
        for (int i = 0; i < code.size(); ++i)
            msg += code.get(i);
    }

    /**
     * 奇校验
     * 
     * @param code
     * @return
     */
    public boolean evenOddCheck(String code) {
        msg += ", 接收到信息码: ";
        int cnt1 = 0;// 数字1的个数
        for (int i = 0; i < code.length(); i++) {
            msg += code.substring(i, i + 1);
            if (code.substring(i, i + 1).equals("1"))
                ++cnt1;
        }
        if ((cnt1 & 1) == 1 && flag == ODD_CHECK || (cnt1 & 1) == 0 && flag == EVEN_CHECK) {
            msg += ", 校验结果: 正确!";
            return true;
        } else {
            msg += ", 校验结果: 错误!";
            return false;
        }
    }

    public static String toBinary(String str) {
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toBinaryString(strChar[i]);
        }
        return result;

    }

    public String toOdd(String str) {
        if (new EvenOddCheck().evenOddCheck(str)) {
            str += "0";
        } else {
            str += "1";
        }
        return str;
    }

    public static void main(String[] args) {
        String str = "GYZS0222221";
        System.out.println(new EvenOddCheck().evenOddCheck(str));
        // System.out.println(str = new EvenOddCheck().toOdd(str));
        // System.out.println(new EvenOddCheck().evenOddCheck(str));
    }

}
