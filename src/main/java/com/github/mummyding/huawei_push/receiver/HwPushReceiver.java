package com.github.mummyding.huawei_push.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mummyding.huawei_push.MainActivity;
import com.github.mummyding.huawei_push.R;
import com.huawei.android.pushagent.api.PushEventReceiver;
import com.huawei.android.pushagent.PushReceiver;

/**
 * Created by dingqinying on 16/11/28.
 */
public class HwPushReceiver extends PushEventReceiver {

    private static final String TAG = "HwPushReceiver";
    private static final  int ID_MESSAGE = 1000;

    @Override
    public void onToken(Context context, String token, Bundle extras) {
        String belongId = extras.getString("belongId");
        String content = "获取token和belongId成功，token = " + token + ",belongId = " + belongId;
        Log.d(TAG, content);
    }


    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            String content = "收到一条Push消息： " + new String(msg, "UTF-8");
            Log.d(TAG, content);
            showPushMessageNotification(context, ID_MESSAGE, content, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onEvent(Context context, PushReceiver.Event event, Bundle extras) {
        if (PushReceiver.Event.NOTIFICATION_OPENED.equals(event) || PushReceiver.Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(PushReceiver.BOUND_KEY.pushNotifyId, 0);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
            String content = "收到通知附加消息： " + extras.getString(PushReceiver.BOUND_KEY.pushMsgKey);
            Log.d(TAG, content);
            showPushMessageNotification(context, ID_MESSAGE, content, content);
        } else if (PushReceiver.Event.PLUGINRSP.equals(event)) {
            final int TYPE_LBS = 1;
            final int TYPE_TAG = 2;
            int reportType = extras.getInt(PushReceiver.BOUND_KEY.PLUGINREPORTTYPE, -1);
            boolean isSuccess = extras.getBoolean(PushReceiver.BOUND_KEY.PLUGINREPORTRESULT, false);
            String message = "";
            if (TYPE_LBS == reportType) {
                message = "LBS report result :";
            } else if (TYPE_TAG == reportType) {
                message = "TAG report result :";
            }
            Log.d(TAG, message + isSuccess);
            showPushMessageNotification(context, ID_MESSAGE, message, message);
        }
        super.onEvent(context, event, extras);
    }

    private void showPushMessageNotification(final Context context, final int id, final String title, final String alert) {
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder nb = new Notification.Builder(context);
        nb.setTicker(alert);
        nb.setSmallIcon(R.drawable.huawei_logo);
        nb.setSmallIcon(R.drawable.huawei_logo);
        nb.setWhen(System.currentTimeMillis());
        nb.setContentTitle(title);
        nb.setContentText(alert);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        nb.setContentIntent(pendingIntent);
        Notification notification;
        if (Build.VERSION.SDK_INT >= 16) {
            notification = nb.build();
        } else {
            notification = nb.getNotification();
        }
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        nm.notify(id, notification);
        Toast.makeText(context, title, Toast.LENGTH_LONG).show();

    }
}
