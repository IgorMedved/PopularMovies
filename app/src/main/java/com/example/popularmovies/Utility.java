package com.example.popularmovies;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by Igor on 11/30/2016.
 */

public class Utility
{
    public static int getWidth (Activity activity)
    {
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int getHeight(Activity activity)
    {
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }


    // this code was taken from http://stackoverflow.com/questions/4319212/how-can-one-detect-airplane-mode-on-android
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isAirplaneModeOn(Context context)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        }
        else
        {
            return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }
}
