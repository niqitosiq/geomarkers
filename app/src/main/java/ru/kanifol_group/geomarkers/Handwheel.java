package ru.kanifol_group.geomarkers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by DN on 27.03.2017.
 */

public class Handwheel extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {


    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //domeMain serviceDome = new domeMain(this,"Service");
        //return super.onStartCommand(intent, flags, startId);
        return Service.START_STICKY;
    }
    @Override
    public void onDestroy() {
    }
}