package com.mds.gab.gardenmanager.Helpers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.mds.gab.gardenmanager.R;

public class ActivityUtils {
    private final static int FADE = 0;
    public final static int SLIDE_RIGHT = 1;

    public static void launchActivity(AppCompatActivity activity, Class cls) {
        launchActivity(activity, cls, true, FADE);
    }

    public static void launchActivity(AppCompatActivity activity, Intent intent){
        launchActivity(activity, intent, true, FADE);
    }

    public static void launchActivity(AppCompatActivity activity, Class cls, boolean finish, int animation) {
        Intent intent = new Intent(activity, cls);
        launchActivity(activity, intent, finish, animation);
    }

    private static void launchActivity(AppCompatActivity activity, Intent intent, boolean finish, int animation) {
        activity.startActivity(intent);

        switch(animation){
            case FADE :
                activity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.fade_out);
                break;
            case SLIDE_RIGHT :
                activity.overridePendingTransition(R.anim.slide_in_from_right, 0);
                break;
        }

        activity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.fade_out);
        if(finish){
            activity.finish();
        }
    }
}
