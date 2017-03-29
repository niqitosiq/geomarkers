package ru.kanifol_group.geomarkers;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class map extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    Circle circle;
    private LatLng coordinates;
    double latitude;
    double longitude;
    boolean settings=false;
    int radius;
    TextView radiusText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapFragment mapFragment=(MapFragment)getFragmentManager().findFragmentById(R.id.mapma);
        mapFragment.getMapAsync(this);
        radius = getIntent().getIntExtra("radius",50);
        radiusText = (TextView)findViewById(R.id.radius);
        radiusText.setText(radius+" метров");
        latitude = getIntent().getDoubleExtra("latitude",0);
        latitude = getIntent().getDoubleExtra("longitude",0);
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
                circle = googleMap.addCircle(new CircleOptions().center(latLng).radius(radius).strokeColor(Color.argb(255,0,255,0)).fillColor(Color.argb(50,0,255,0)));
                coordinates=latLng;
            }
        });


    }

    public void getLocation(View view){
        map.clear();
        Dome dome=new Dome(this);
        LatLng location = dome.getLocOneTime();

        if(location!=null){
            map.addMarker(new MarkerOptions().position(location).title("it's my location!"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location,19));
        }else{
            Toast toast=Toast.makeText(getApplicationContext(),
                    "Неизвестная ошибка:смотрите логи{TAG:Error:}",Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void encreaseRadius(View view){
        if(radius!=1000) {
            radius += 5;
            circle.setRadius(radius);
            radiusText.setText(radius+" метров");
        }

    }
    public void decreaseRadius(View view){
        if(radius!=25){
            radius -= 5;
            circle.setRadius(radius);
            radiusText.setText(radius+" метров");
        }




    }
    public void done(View view){
        Intent intent = new Intent(this,settings.class);
        if(coordinates!=null) {
            intent.putExtra("latitude", coordinates.latitude);
            intent.putExtra("longitude", coordinates.longitude);
        }
        intent.putExtra("name",getIntent().getStringExtra("name"));
        intent.putExtra("description",getIntent().getStringExtra("description"));
        intent.putExtra("radius",radius);
        intent.putExtra("id",getIntent().getIntExtra("id",0));
        startActivity(intent);
    }
















}
