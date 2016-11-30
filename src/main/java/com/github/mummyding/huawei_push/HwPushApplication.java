package com.github.mummyding.huawei_push;

import android.app.Application;
import android.content.Context;

import com.huawei.android.pushagent.api.PushManager;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by dingqinying on 16/11/28.
 */
public class HwPushApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        PushManager.deregisterToken(getApplicationContext(), "");
        super.onTerminate();
    }


}
