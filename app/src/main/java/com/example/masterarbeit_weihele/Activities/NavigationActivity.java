package com.example.masterarbeit_weihele.Activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.masterarbeit_weihele.Classes.BasicFunctions;
import com.example.masterarbeit_weihele.R;
import com.example.masterarbeit_weihele.databinding.ActivityNavigationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class NavigationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowLongClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);

    private ActivityNavigationBinding binding;
    private MapView mapView;
    private GoogleMap googleMap;
    private LatLng selectedMarkerLatLng;
    private Marker lastMarker;
    private Polyline lastPolyline;
    List<Marker> clientMarkers = new ArrayList<>();
    HashMap<String, LatLng> clientPositions = new HashMap<>();
    private String emergencyName = "Sabrina";
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        basicFunctions.changeActivityOnRotation(EmergencyActivity.class, CommunicationActivity.class);

        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        setupMap();
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnInfoWindowLongClickListener(this);
        getUserPosition();
    }

    private void setupMap() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(true);
            getClientPositions();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this, "Standortberechtigung wurde abgelehnt.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (lastMarker != null) {
            lastMarker.remove();
            lastPolyline.remove();
        }

        if (selectedMarkerLatLng != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
        }

        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        lastMarker = googleMap.addMarker(markerOptions);
        selectedMarkerLatLng = latLng;
        calculateShortestPath();

        showDistanceToSelectedMarker();
    }

    private void calculateShortestPath() {
        if (selectedMarkerLatLng != null) {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    drawRouteOnMap(currentLatLng, selectedMarkerLatLng);
                }
            });
        }
    }

    private void drawRouteOnMap(LatLng startLatLng, LatLng endLatLng) {
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(startLatLng)
                .add(endLatLng)
                .color(Color.RED)
                .width(5f);
        lastPolyline = googleMap.addPolyline(polylineOptions);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(endLatLng, 15f));
    }

    private void showDistanceToSelectedMarker() {
        if (selectedMarkerLatLng != null) {
            Location selectedLocation = new Location("");
            selectedLocation.setLatitude(selectedMarkerLatLng.latitude);
            selectedLocation.setLongitude(selectedMarkerLatLng.longitude);

            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    Location currentLocation = new Location("");
                    currentLocation.setLatitude(location.getLatitude());
                    currentLocation.setLongitude(location.getLongitude());

                    float distance = currentLocation.distanceTo(selectedLocation) / 1000;
                    String distanceText = String.format("%.2f km", distance);

                    Toast.makeText(NavigationActivity.this, "Entfernung zum Marker: " + distanceText, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getClientPositions(){

        //HIER CLIENTDATEN VOM SERVER ABFRAGEN

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        clientPositions.put("Jonas", new LatLng(37.421606238636436, -122.08620999008417));
        clientPositions.put("Sabrina", new LatLng(37.42012362454773, -122.08417184650897));
        clientPositions.put("Alex", new LatLng(37.41985042413669, -122.0768279582262));

        for (Map.Entry<String, LatLng> entry : clientPositions.entrySet()) {
            Marker marker = googleMap.addMarker(markerOptions.position(entry.getValue()).title(entry.getKey()));
            clientMarkers.add(marker);
            marker.setInfoWindowAnchor(0.5f, 1.0f);
        }
    }

    private void getUserPosition(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    System.out.println("Ficken " + currentLatLng);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f));
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onInfoWindowLongClick(@NonNull Marker marker) {
        if (lastMarker != null) {
            lastMarker.remove();
            lastPolyline.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions().position(marker.getPosition());
        lastMarker = googleMap.addMarker(markerOptions);
        selectedMarkerLatLng = marker.getPosition();
        calculateShortestPath();
        showDistanceToSelectedMarker();
    }

    public void getEmergency(View view) {

        MarkerOptions emergencyMarkerOptions = new MarkerOptions();

        for (Marker m : clientMarkers) {
            if (Objects.equals(m.getTitle(), emergencyName)) {
                m.remove();
                emergencyMarkerOptions.position(m.getPosition());
                emergencyMarkerOptions.title(m.getTitle());
                emergencyMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.emergency_icon));
                Marker emergencyMarker = googleMap.addMarker(emergencyMarkerOptions);
                emergencyMarker.showInfoWindow();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(m.getPosition(), 15.0f));
                Toast.makeText(getApplicationContext(), emergencyName + " benötigt Hilfe!", Toast.LENGTH_SHORT).show();
                vibrate();
            }
        }
    }

    @Override
    public void onBackPressed() {
        basicFunctions.loadActivity(FunctionsActivity.class);
        super.onBackPressed();
    }

    private void vibrate() {
        if (vibrator != null && vibrator.hasVibrator()) {
            long[] pattern = {0, 2000, 500, 2000, 500, 2000, 500};
            int repeat = -1;
            vibrator.vibrate(pattern, repeat);
        }
    }

    public void deleteLastMarker(View view) {
        if (lastMarker != null) {
            lastMarker.remove();
            lastPolyline.remove();
            lastMarker = null;
            Toast.makeText(getApplicationContext(), "Marker entfernt", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "kein Marker gesetzt", Toast.LENGTH_SHORT).show();
        }
    }

}
