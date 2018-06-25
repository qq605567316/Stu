package com.tt.ssm.utils;

public class StringUtilsByMe {
    public static boolean isBlank(String s){
        int strLen;
        if(s==null||(strLen=s.length())==0){
            return true;
        }
        for(int i=0;i<strLen;i++){
            if((Character.isWhitespace(s.charAt(i)) == false)){
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String s){
        return !StringUtilsByMe.isBlank(s);
    }
}
