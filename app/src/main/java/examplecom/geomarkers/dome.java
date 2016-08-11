package examplecom.geomarkers;


import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.android.gms.maps.model.LatLng;
import android.location.Location;
import android.location.LocationListener;

public class dome extends Service {
    ExecutorService es;

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }
    @Override
    public void onCreate() {


    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        domeMain mr = new domeMain(this,"Service");
        es.execute(mr);
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {



    }







}
