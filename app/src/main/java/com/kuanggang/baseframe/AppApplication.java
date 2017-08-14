package com.kuanggang.baseframe;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * @author KG on 2017/6/5.
 */

public class AppApplication extends Application {

    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        Logger.init("GankApp").logLevel(BuildConfig.isDebug ? LogLevel.FULL : LogLevel.NONE);
    }
}
