package com.maximilianomicciullo.androidcontactlistapp.CommonUtils;

/**
 * Created by maximilianomicciullo on 07/09/14.
 */
public class CommonUtilString {
    public static Boolean checkString(String value){
        if( !(value != null && !value.isEmpty()) ){
            return true;
        }else{
            return false;
        }
    }
}
