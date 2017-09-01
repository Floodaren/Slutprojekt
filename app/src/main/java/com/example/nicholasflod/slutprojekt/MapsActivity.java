package com.example.nicholasflod.slutprojekt;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng start;
    private String longitude;
    private String latitude;
    private int radID;
    MySQLiteHelper myDb = new MySQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        Bundle extras = getIntent().getExtras();
        radID = extras.getInt("radID");
        latitude = extras.getString("latitude");
        longitude = extras.getString("longitude");
        if (latitude != null && longitude != null)
        {
            Double doubleLatitud = Double.parseDouble(extras.getString("latitude"));
            Double doubleLongitud = Double.parseDouble(extras.getString("longitude"));
            start = new LatLng(doubleLatitud,doubleLongitud);
        }
        else
        {
            start = new LatLng(57.70887, 11.97456);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(start));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start,8));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //Skapar ett marker-objekt
                MarkerOptions markerOptions = new MarkerOptions();
                // Skapar positionen för markeringen
                markerOptions.position(latLng);
                // Tar bort den förra markören
                mMap.clear();
                // Animation för att flytta markeringen till den nya valda positionen
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                // Lägger till en markeringen där användaren trycker
                Marker markör = mMap.addMarker(markerOptions);
                latLng = markerOptions.getPosition();
                String markeringsPosition = latLng.toString();
                myDb.insertPosition(radID,markeringsPosition);
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

    }
}
