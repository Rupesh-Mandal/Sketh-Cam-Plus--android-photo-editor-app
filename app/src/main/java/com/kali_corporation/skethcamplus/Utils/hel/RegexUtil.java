package com.kali_corporation.skethcamplus.Utils.hel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static boolean matchStr(String str){
//        if (Regex.Match(str2, "[|]456[|]").Success)
        Pattern pattern = Pattern.compile("^au");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            return true;
        }
        return false;
    }
}
