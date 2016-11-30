package com.github.mummyding.huawei_push;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.huawei.android.pushagent.api.PushManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkPushServer(this);
    }
    private void linkPushServer(Context context) {
        PushManager.requestToken(context);
        Map<String, String> tag = new HashMap<>();
        tag.put("husband", "MummyDing");
        tag.put("wife", "MichelleMeng");
//        PushManager.setTags(this, tag);
        PushManager.enableReceiveNormalMsg(context, true);
        PushManager.enableReceiveNotifyMsg(context, true);
    }
}
