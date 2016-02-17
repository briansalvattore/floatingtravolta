package com.horses.floating.travolta;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.RelativeLayout;


import jp.co.recruit_lifestyle.android.floatingview.FloatingViewListener;
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewManager;

public class TravoltaService extends Service implements FloatingViewListener{

    private FloatingViewManager floatingViewManager;

    private static final int NOTIFICATION_ID = 9083150;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("InflateParams")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (floatingViewManager != null) {
            return START_STICKY;
        }

        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        final LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout iconView = (RelativeLayout) inflater.inflate(R.layout.widget_travolta, null, false);

        floatingViewManager = new FloatingViewManager(this, this);
        floatingViewManager.setTrashViewEnabled(false);


        final FloatingViewManager.Options options = new FloatingViewManager.Options();
        options.shape = FloatingViewManager.SHAPE_CIRCLE;

        options.floatingViewX = metrics.widthPixels / 2;
        options.floatingViewY = metrics.heightPixels / 2;

        options.moveDirection = FloatingViewManager.MOVE_DIRECTION_NONE;
        floatingViewManager.addViewToWindow(iconView, options);

        startForeground(NOTIFICATION_ID, createNotification());

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        destroy();
        super.onDestroy();
    }

    private void destroy() {
        if (floatingViewManager != null) {
            floatingViewManager.removeAllViewToWindow();
            floatingViewManager = null;
        }
    }

    @Override
    public void onFinishFloatingView() {
        stopSelf();
    }

    private Notification createNotification(){

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setWhen(System.currentTimeMillis());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){

            builder.setSmallIcon(R.drawable.travolta_white);
        }
        else{

            builder.setSmallIcon(R.mipmap.ic_launcher);
        }

        builder.setContentTitle("Floating Travolta");
        builder.setContentText("Slide down for more options");
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MIN);
        builder.setCategory(NotificationCompat.CATEGORY_SERVICE);

        final Intent notifyIntent = new Intent(this, StopActivity.class);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(R.drawable.ic_close_white_24dp, "Stop Travolta", notifyPendingIntent);

        return builder.build();
    }
}