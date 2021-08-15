package com.kali_corporation.skethcamplus.Utils.hel.al;

import android.content.Context;

import com.kali_corporation.skethcamplus.Utils.hel.sp.SPUtils;
import com.kali_corporation.skethcamplus.Utils.gad.DesCbcUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

public class RandomUtil {
    private static final String key = "18sz4n3r7xo2px2a";

    public static String randomMixStr(int length) {
        String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        // create a super set of all characters
        String allCharacters = alphabetsInLowerCase + alphabetsInUpperCase + numbers;
        // initialize a string to hold result
        StringBuffer randomString = new StringBuffer();
        // loop for 10 times
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // generate a random number between 0 and length of all characters
            int randomIndex = random.nextInt(allCharacters.length());
            // retrieve character at index and add it to result
            randomString.append(allCharacters.charAt(randomIndex));
        }
        return randomString.toString();
    }

    public static String randomAuStr(int length) {

        String numbers = "0123456789";
        // create a super set of all characters
        String allCharacters = numbers;
        // initialize a string to hold result
        StringBuffer randomString = new StringBuffer();
        // loop for 10 times
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // generate a random number between 0 and length of all characters
            int randomIndex = random.nextInt(allCharacters.length());
            // retrieve character at index and add it to result
            randomString.append(allCharacters.charAt(randomIndex));
        }
        if (length<2){
            return "c"+randomString.toString();
        }
        return "au"+randomString.toString();
    }

    public static String randomInt(int length) {

        String numbers = "0123456789";
        // create a super set of all characters
        String allCharacters = numbers;
        // initialize a string to hold result
        StringBuffer randomString = new StringBuffer();
        // loop for 10 times
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // generate a random number between 0 and length of all characters
            int randomIndex = random.nextInt(allCharacters.length());
            // retrieve character at index and add it to result
            randomString.append(allCharacters.charAt(randomIndex));
        }
        return randomString.toString();
    }

    public static ArrayList getRandomList(Context context) throws UnsupportedEncodingException {
        ArrayList arrayList = new ArrayList();
        String one = URLEncoder.encode(DesCbcUtil.encodeValue(key, SPUtils.getInstance(context).getAppInfo().getString(SPUtils.keyA,"")), "UTF-8");
        String two = URLEncoder.encode(DesCbcUtil.encodeValue(key,SPUtils.getInstance(context).getAppInfo().getString(SPUtils.keyB,"")), "UTF-8");
        String three = URLEncoder.encode(DesCbcUtil.encodeValue(key,SPUtils.getInstance(context).getAppInfo().getString(SPUtils.keyC,"")), "UTF-8");
        arrayList.add(one);
        arrayList.add(two);
        arrayList.add(three);
        return arrayList;
    }

}
