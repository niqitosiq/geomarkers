package examplecom.geomarkers;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
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
public class MapActivity extends Activity implements OnMapReadyCallback,SeekBar.OnSeekBarChangeListener{
        GoogleMap map;
private LatLng coordinates;
        double latitude;
        double longitude;
        boolean settings=false;
        int radius=0;

@Override
public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        MapFragment mapFragment=(MapFragment)getFragmentManager().findFragmentById(R.id.mapma);
        mapFragment.getMapAsync(this);
        latitude=getIntent().getDoubleExtra("latitude",0);
        longitude=getIntent().getDoubleExtra("longitude",0);
        final SeekBar seekBar = (SeekBar)findViewById(R.id.seekBarRadius);
        seekBar.setOnSeekBarChangeListener(this);
        }


    @Override
    public void onProgressChanged(SeekBar seekBar,int progress, boolean fromUser){
        // TODO Auto-generated method stub
        }

@Override
public void onStartTrackingTouch(SeekBar seekBar){
        // TODO Auto-generated method stub
        }

@Override
public void onStopTrackingTouch(SeekBar seekBar){
        radius = seekBar.getProgress();


        }
public void onMapReady(final GoogleMap googleMap){
        map=googleMap;
        if(latitude!=0&longitude!=0){
        LatLng pastMarker=new LatLng(latitude,longitude);
        googleMap.addMarker(new MarkerOptions().position(pastMarker));
        }
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){
@Override
public void onMapLongClick(LatLng latLng){
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.addCircle(new CircleOptions().center(latLng).radius(radius).strokeColor(Color.argb(255,0,255,0)).fillColor(Color.argb(50,0,255,0)));
        coordinates=latLng;
        }
        });


        }

public void myLocation(View v){
        map.clear();
        domeMain object=new domeMain(this,"asNotService");
        LatLng myCoords=object.doIfStartAsNotService();

        if(myCoords!=null){
        map.addMarker(new MarkerOptions().position(myCoords).title("it's my location!"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoords,19));
        }else{
        Toast toast=Toast.makeText(getApplicationContext(),
        "Неизвестная ошибка:смотрите логи{TAG:Error:}",Toast.LENGTH_SHORT);
        toast.show();
        }
        }


public void markerSettings(View v){
        //LinearLayout button1 = (LinearLayout)findViewById(R.id.markerSetting);
        LinearLayout button2=(LinearLayout)findViewById(R.id.findLoc);
        LinearLayout button3=(LinearLayout)findViewById(R.id.addMarker);
        SeekBar radious=(SeekBar)findViewById(R.id.seekBarRadius);
        if(settings==false){
        //button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);
        radious.setVisibility(View.VISIBLE);
        settings=!settings;
        }else{
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        radious.setVisibility(View.GONE);
        settings=!settings;
        }
        }


public void sendCoordinates(View v){
        if(coordinates!=null){
        Intent intent=new Intent(this,SecondActivity.class);
        intent.putExtra("nameField",getIntent().getStringExtra("name"));
        intent.putExtra("descField",getIntent().getStringExtra("description"));
        intent.putExtra("switchPos",getIntent().getBooleanExtra("signalOrNot",false));
        intent.putExtra("radiusNumb",radius);
        intent.putExtra("latitude",coordinates.latitude);
        intent.putExtra("longitude",coordinates.longitude);
        int id=getIntent().getIntExtra("id",0);
        intent.putExtra("id",id);
        startActivity(intent);
        }
        else{
        Toast toast=Toast.makeText(getApplicationContext(),
        "Укажите местоположение",Toast.LENGTH_SHORT);
        toast.show();
        }
        }

        }

















