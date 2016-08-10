package examplecom.geomarkers;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.support.v4.content.PermissionChecker;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;



/**
 * Created by DN on 04.08.2016.
 */
public class MapActivity extends Activity implements OnMapReadyCallback{
    Location location;
    GoogleMap map;
    private LatLng coordinates;
    double latitude;
    double longitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapma);
        mapFragment.getMapAsync(this);
        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude",0);

}

    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        if(latitude !=0 & longitude !=0){
            LatLng pastMarker = new LatLng(latitude,longitude);
            googleMap.addMarker(new MarkerOptions().position(pastMarker));
        }
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(latLng));
                    googleMap.addCircle(new CircleOptions().center(latLng).radius(30).fillColor(0xffff00ff).strokeColor(0xffff0000));
                    coordinates = latLng;
            }
        });



    }

    public void myLocation(View v){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean mIsGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean mIsNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(mIsGPSEnabled){
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }if(mIsNetworkEnabled) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if(location!=null) {
            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
            map.addMarker(new MarkerOptions().position(myLocation).title("it's my location!"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 19));
        }
        }








    public void sendCoordinates(View v){
        if(coordinates!=null) {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("latitude", coordinates.latitude);
            intent.putExtra("longitude", coordinates.longitude);
            int id = getIntent().getIntExtra("id", 0);
            intent.putExtra("id",id);
            startActivity(intent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Укажите местоположение", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}

















