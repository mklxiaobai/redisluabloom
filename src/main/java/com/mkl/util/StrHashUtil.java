package com.mkl.util;

import java.io.UnsupportedEncodingException;

/**
 * @author mkl
 * @date 2020/3/30 10:42
 * @description
 */
public class StrHashUtil {
    public static String getHashBiteArry(String str){
        int hashCode = str.hashCode();
        StringBuilder stringBuilder = new StringBuilder();
//        模拟布隆将字符串hash转成定长二进制数组的过程
        String firstHashBiteArry = Integer.toBinaryString(hashCode);
        if(firstHashBiteArry.length()<40){
            StringBuilder zeroBulider = new StringBuilder();
            for(int i=0;i<40-firstHashBiteArry.length();i++){
                zeroBulider.append("0");
            }
            stringBuilder.append(firstHashBiteArry)
                    .append(zeroBulider);
            return stringBuilder.toString();
        }else if(firstHashBiteArry.length()==40){
            return firstHashBiteArry;
        }else {
            return firstHashBiteArry.substring(40-firstHashBiteArry.length());
        }
    }
}
