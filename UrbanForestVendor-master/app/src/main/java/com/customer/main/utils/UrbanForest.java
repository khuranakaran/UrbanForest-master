package com.customer.main.utils;

import android.app.Application;
import android.os.Environment;

import com.balsikandar.crashreporter.CrashReporter;


import java.io.File;

/**
 * Created by Karan on 3/4/19.
 */
public class UrbanForest extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();
        String crashReporterPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "urbanforest.customer";
        CrashReporter.initialize(this, crashReporterPath);
    }
}
