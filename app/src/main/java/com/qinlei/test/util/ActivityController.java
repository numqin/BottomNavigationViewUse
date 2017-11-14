package com.qinlei.test.util;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinlei
 * Created on 2017/11/14
 * Created description : Activity 控制类
 */

public class ActivityController {
    public static List<AppCompatActivity> mActivityList = new ArrayList<>();
    private static AppCompatActivity mCurrentActivity;

    public static void addActivity(AppCompatActivity activity) {
        mActivityList.add(activity);
    }

    public static void removeActivity(AppCompatActivity activity) {
        mActivityList.remove(activity);
    }

    public static void setCurrentActivity(AppCompatActivity activity) {
        mCurrentActivity = activity;
    }

    public static AppCompatActivity getCurrentActivity() {
        return mCurrentActivity;
    }

    public static void finishAll() {
        for (AppCompatActivity activity : mActivityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
