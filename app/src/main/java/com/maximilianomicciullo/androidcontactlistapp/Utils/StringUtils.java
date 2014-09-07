package com.maximilianomicciullo.androidcontactlistapp.Utils;

/**
 * Created by maximilianomicciullo on 07/09/14.
 */
public class StringUtils {
    public static Boolean checkString(String value){
        if( !(value != null && !value.isEmpty()) ){
            return true;
        }else{
            return false;
        }
    }
}
