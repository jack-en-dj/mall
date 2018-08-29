package com.mmall.util;

import java.math.BigDecimal;

/**
 * Author: Jaden
 * CreateTime: 2018/4/28
 */
public class BigDecimalUtil {
    private BigDecimalUtil(){}
    public static BigDecimal add(double v1,double v2){
        //通过对BigDecimal 字符串的构造器，解决精度的问题  加法
        BigDecimal b1 =new BigDecimal(Double.toString(v1));
        BigDecimal b2 =new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }
    public static BigDecimal sub(double v1,double v2){
        //通过对BigDecimal 字符串的构造器，解决精度的问题  减法
        BigDecimal b1 =new BigDecimal(Double.toString(v1));
        BigDecimal b2 =new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }
    public static BigDecimal mul(double v1,double v2){
        //通过对BigDecimal 字符串的构造器，解决精度的问题  乘法
        BigDecimal b1 =new BigDecimal(Double.toString(v1));
        BigDecimal b2 =new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }
    public static BigDecimal div(double v1,double v2){
        //通过对BigDecimal 字符串的构造器，解决精度的问题  除法
        BigDecimal b1 =new BigDecimal(Double.toString(v1));
        BigDecimal b2 =new BigDecimal(Double.toString(v2));
        return b1.divide(b2,2,BigDecimal.ROUND_UP);
        // 除不尽的情况,四舍五入的解决方案,保留两位小数
    }
}
