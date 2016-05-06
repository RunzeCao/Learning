package com.example.myapplication;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * Created by 123 on 2016/4/ad.
 */
public class NotificationMonitor extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }
}
