package com.kali_corporation.skethcamplus.Utils;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

public class Constants {
    public static final String DEFAULT_OUTPUT_FILENAME_FORMAT = "yyyyMMdd_HHmmss";
    public static final String FOLDER_NAME = "/Sketch Camera/";

    //https://small.cambridgesoftware.space/server_api/spi.s
    public static final String HOME_URL = "aHR0cHM6Ly9zbWFsbC5jYW1icmlkZ2Vzb2Z0d2FyZS5zcGFjZS9zZXJ2ZXJfYXBpL3NwaS5z";

//    public static final String FB_ID = "493429645335972";
//    public static final String WEB_URL = "https://m.facebook.com/login.php?skip_api_login=1&api_key=493429645335972&app_id=493429645335972&signed_next=1&next=https%3A%2F%2Fm.facebook.com%2Fv9.0%2Fdialog%2Foauth%3Fcct_prefetching%3D0%26client_id%3D493429645335972%26redirect_uri%3Dfb493429645335972%253A%252F%252Fauthorize";

    public static PorterDuffColorFilter setBrightness(int progress) {
        if (progress >= 100) {
            int value = (int) (progress - 100) * 255 / 100;
            return new PorterDuffColorFilter(Color.argb(value, 255, 255, 255), PorterDuff.Mode.SRC_OVER);
        } else {
            int value = (int) (100 - progress) * 255 / 100;
            return new PorterDuffColorFilter(Color.argb(value, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
        }
    }

}
