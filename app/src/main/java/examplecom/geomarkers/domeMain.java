package examplecom.geomarkers;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class domeMain extends Activity implements Runnable {
    private LocationManager locationManager;
    private Context mContext;
    private double[] latitudes;
    private double[] longitudes;
    int[] signal;
    private double radiusDome;



    public domeMain(Context mContext) {
        this.mContext = mContext;
    }
    public void run() {
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        worker();

    }
    protected  void worker(){
        int i = 0;
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("geomarkers", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            int columnLatitudeIndex = cursor.getColumnIndex("latitude");
            int columnLongitudeIndex = cursor.getColumnIndex("longitude");
            int columnSignalIndex = cursor.getColumnIndex("signal");
            do {
                latitudes[i]= cursor.getDouble(columnLatitudeIndex);
                longitudes[i]= cursor.getDouble(columnLongitudeIndex);
                signal[i]= cursor.getInt(columnSignalIndex);
                i++;
            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        dbHelper.close();
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_COARSE_LOCATION);
            ContextCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000 * 10, 10, locationListener);
            radiusDome = 0.001000;
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                    locationListener);
            radiusDome = 0.001800;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ContextCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_COARSE_LOCATION);
                ContextCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_FINE_LOCATION);
            }
            locationManager.removeUpdates(locationListener);
        }catch (Exception e){
            Log.d("Unexpected_Error",e.toString());}

    }
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            domeMaths(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            onResume();
        }

        @Override
        public void onProviderEnabled(String provider) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ContextCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_COARSE_LOCATION);
                ContextCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_FINE_LOCATION);
            }
            domeMaths(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    };
    private void domeMaths(Location geolocation){

        worker();

        if(latitudes.length!=longitudes.length){
            latitudes=null;
            longitudes=null;
            worker();
        }
        if (geolocation!=null){
            for (int i = 0; i < latitudes.length; i++) {
                double positiveLatitudeLimit = latitudes[i]+radiusDome;
                double negativeLatitudeLimit =latitudes[i]-radiusDome;
                double positiveLongitudeLimit =longitudes[i]+radiusDome;
                double negativeLongitudeLimit =longitudes[i]-radiusDome;
                if(geolocation.getLatitude()<positiveLatitudeLimit&geolocation.getLatitude()>negativeLatitudeLimit){
                    if(geolocation.getLongitude()<positiveLongitudeLimit&geolocation.getLongitude()>negativeLongitudeLimit){


                        //вот тут должно быть оповещение


                    }
                }
            }
        }
    }
}