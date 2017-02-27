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
import com.google.android.gms.maps.model.LatLng;
public class domeMain extends Activity implements Runnable {
    private LocationManager locationManager;
    private Context mContext;
    private double[] latitudes = new double[128];
    private double[] longitudes= new double[128];
    private String[] name= new String[128];
    int[] signal = new int[128];
    private double radiusDome;
    public domeMain(Context mContext,String runAs) {
        this.mContext = mContext;
        if (runAs=="Service"){
            run();}
    }
    public void run() {
        terminateListener();
        setListners();
    }
    public LatLng doIfStartAsNotService() {
        String provider = null;
        terminateListener();
        setListners();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_COARSE_LOCATION);
            ContextCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
    int i =0;
        while(true){
            if(i==100){
                break;
            }
            if(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!=null){
                provider = LocationManager.GPS_PROVIDER;
                break;
            }
            if(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!=null){
                provider = LocationManager.NETWORK_PROVIDER;
                break;
            }
            try {
                Thread.sleep(50);
            }catch (Exception e){Log.e("Error:",e.toString());}
            i++;
        }
        try {
            LatLng myCoords = new LatLng(locationManager.getLastKnownLocation(provider).getLatitude(), locationManager.getLastKnownLocation(provider).getLongitude());
            terminateListener();
            return myCoords;
        }catch (Exception e){Log.e("Error:",e.toString());}
        terminateListener();
        return null;
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
            int columnNameIndex = cursor.getColumnIndex("name");
            do {
                latitudes[i]= cursor.getDouble(columnLatitudeIndex);
                longitudes[i]= cursor.getDouble(columnLongitudeIndex);
                signal[i]= cursor.getInt(columnSignalIndex);
                name[i]= cursor.getString(columnNameIndex);
                i++;
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        dbHelper.close();
    }
    protected void setListners() {
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_COARSE_LOCATION);
            ContextCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)==true) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000 * 10, 10, locationListener);
            radiusDome = 0.001000;
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)==true) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                    locationListener);
            radiusDome = 0.001800;
        }
    }
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            domeMaths(location);
        }
        @Override
        public void onProviderDisabled(String provider) {
            terminateListener();
            setListners();
        }
        @Override
        public void onProviderEnabled(String provider) {
            terminateListener();
            setListners();
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
    protected void terminateListener() {
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ContextCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_COARSE_LOCATION);
                ContextCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_FINE_LOCATION);
            }
            locationManager.removeUpdates(locationListener);
        }catch (Exception e){
            Log.d("Unexpected_Error",e.toString());}
    }
    private void domeMaths(Location geolocation){
        worker();
        if(latitudes.length!=longitudes.length){
            latitudes=null;
            longitudes=null;
            signal=null;
            worker();
        }
        if (geolocation!=null){
            for (int i = 0; i < latitudes.length; i++) {
                if(signal[i]==1){
                    double positiveLatitudeLimit = latitudes[i]+radiusDome;
                    double negativeLatitudeLimit =latitudes[i]-radiusDome;
                    double positiveLongitudeLimit =longitudes[i]+radiusDome;
                    double negativeLongitudeLimit =longitudes[i]-radiusDome;
                    if(geolocation.getLatitude()<positiveLatitudeLimit&geolocation.getLatitude()>negativeLatitudeLimit){
                        if(geolocation.getLongitude()<positiveLongitudeLimit&geolocation.getLongitude()>negativeLongitudeLimit){
                            notice alarm = new notice();
                            alarm.notice(mContext, name[i]);
                        }
                    }
                }
            }
        }
    }
}
