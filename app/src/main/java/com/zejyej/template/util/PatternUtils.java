package com.zejyej.template.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * @author zejyej
 * @desc
 * @date 2018/5/7
 */
public class PatternUtils {

    public static final String DECIMALS = "^[0-9]+\\.{0,1}[0-9]{0,2}$";//验证是否为小数(保留两位有效数字)
    public static final String EMAIL ="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";//验证Email
    public static final String MOBILE_PHONE="^(((10[0-9])|(11[0-9])|(12[0-9])|(13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8})$";
    public static final String PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
    public static final String PHONE="^(((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";//验证手机和电话号码(带区号)
    public static final String IDCARD="^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";//验证中国身份证15位
    public static final String IDCARD2="^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";//验证中国身份证18位
    public static final String CHINESE="^[\u4e00-\u9fa5]$";//验证中文
    public static final String NUMBER_AND_ENGLISH="^[A-Za-z0-9]+$";//数字和26个英文字母
    private static final int[] WEIGHT_FACTOR =new int[]{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
    private static final String[] VERIFY=new String[]{"1","0","X","9","8","7","6","5","4","3","2"};

    public static boolean verifyDecimals(String pattern,String data){
        Pattern str= Pattern.compile(pattern);
        return str.matcher(data).matches();
    }

    public static boolean idCardLastPattern(String idCard){//身份证最后一位校验
        if(TextUtils.isEmpty(idCard)){
            return false;
        }
        if(idCard.length()==15){
            return true;
        }
        String[] idcardStrArray = idCard.split("");
        int idcardArray =0;
        for(int i=0,k=idcardStrArray.length-1;i<k;i++){
            idcardArray+=Integer.parseInt(idcardStrArray[i])*WEIGHT_FACTOR[i];
        }
        int last =idcardArray%11;
        String checkCode = VERIFY[last];

        if(idcardStrArray[17].equalsIgnoreCase(checkCode)){
            return true;
        }else{
            return false;
        }
    }
}
