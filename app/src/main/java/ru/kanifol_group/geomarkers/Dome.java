package ru.kanifol_group.geomarkers;
import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;


import java.lang.Math;

public class Dome implements LocationListener {
    private LocationManager locationManager;
    private Context context;
    private long timeOld;
    private int accuracy = 10;
    private static long frequency = 10;
    private boolean asNotService = false;
    private boolean firstIteration = true;
    private double latitude;
    private double longitude;
    private final int lengthCircleEarth = 6372795; //in meters

    public Dome(Context context) {
        this.context = context;
    }
    public void startAsService(){
        terminateListener();
        setListners();
    }
    public LatLng getLocOneTime(){
        asNotService = true;
        terminateListener();
        setListners();
        return new LatLng(latitude,longitude);


    }
    protected void changeFrequency(Location location){
        frequency = Math.round(accuracy/(getDistance(latitude,longitude,location.getLatitude(),location.getLongitude())/((SystemClock.elapsedRealtime()-timeOld)*1000)));
        if(frequency<1){frequency=1;}
        if(frequency>60){frequency=30;}
    }
    protected void terminateListener() {
        try{
            ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_COARSE_LOCATION);
            ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION);
            locationManager.removeUpdates(this);
        }catch (Exception e){}
    }
        @Override
        public void onLocationChanged(Location location) {
            if(!firstIteration){
                if(!asNotService) {
                    domeCore(location);
                }
                changeFrequency(location);
            }else {firstIteration = false;}
            timeOld = SystemClock.elapsedRealtime();
            latitude = location.getLatitude();
            longitude = location.getLongitude();

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
            ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_COARSE_LOCATION);
            ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION);
            if(!firstIteration){
                if(!asNotService) {
                    domeCore(locationManager.getLastKnownLocation(provider));
                }
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    protected void setListners() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_COARSE_LOCATION);
            ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION);

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)==true) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000 * frequency, 1000, this);

        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)==true) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000 * frequency, 1000,
                    this);

        }
    }
    private double getDistance(double latitudeOld,double longitudeOld,double latitudeNew,double longitudeNew){
        double latO_rad = latitudeOld*Math.PI/180;
        double lonO_rad = longitudeOld*Math.PI/180;
        double latN_rad = latitudeNew*Math.PI/180;
        double lonN_rad = longitudeNew*Math.PI/180;

        double cosOldLat = Math.cos(latO_rad);
        double cosNewLat = Math.cos(latN_rad);
        double sinOldLat = Math.sin(latO_rad);
        double sinNewLat = Math.sin(latN_rad);

        double cosDeltaLongitudes = Math.cos(lonN_rad-lonO_rad);
        double sinDeltaLongitudes = Math.sin(lonN_rad-lonO_rad);

        double distance = (Math.atan(Math.pow(cosNewLat*sinDeltaLongitudes,2)+Math.pow(cosOldLat*sinNewLat-sinOldLat*cosNewLat*cosDeltaLongitudes,2)/(sinOldLat*sinNewLat)+(cosOldLat*cosNewLat*cosDeltaLongitudes)))*lengthCircleEarth;

        return distance;
    }
    private void domeCore(Location locationNow){
        DBHelper database = new DBHelper(context);
        Cursor cursor = database.getLocationInfo();
        int latColumn = cursor.getColumnIndex("latitude");
        int lonColumn = cursor.getColumnIndex("longitude");
        int radiusColumn = cursor.getColumnIndex("radius");
        do{

            if (getDistance(
                    cursor.getDouble(latColumn),
                    cursor.getDouble(lonColumn),
                    locationNow.getLatitude(),
                    locationNow.getLongitude())<=cursor.getInt(radiusColumn)){


                //вызов нотайсов


            }

        }while(cursor.moveToNext());
        database.close();
        cursor.close();


    }







}
