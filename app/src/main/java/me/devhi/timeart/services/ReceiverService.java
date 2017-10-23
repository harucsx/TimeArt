package me.devhi.timeart.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import java.util.Date;

import static java.lang.Thread.sleep;

public class ReceiverService extends Service {
    public ReceiverService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleStart(intent, startId);
        return START_NOT_STICKY;
    }

    void handleStart(Intent intent, int startId) {
        // TODO : Implement service
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    Log.i("TimeArt", "handle servicer service : " + new Date());

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
}
