package com.example.masterarbeit_weihele;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.masterarbeit_weihele.databinding.ActivityNavigationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class NavigationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowLongClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private ActivityNavigationBinding binding;
    private MapView mapView;
    private GoogleMap googleMap;
    private LatLng selectedMarkerLatLng;
    private Marker lastMarker;
    private Polyline lastPolyline;
    List<Marker> clientList = new ArrayList<>(); // Liste initialisieren

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

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
        LatLng client_one = new LatLng(37.421606238636436, -122.08620999008417);
        LatLng client_two = new LatLng(37.42012362454773, -122.08417184650897);
        LatLng client_three = new LatLng(37.41985042413669, -122.0768279582262);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        Marker marker_one = googleMap.addMarker(markerOptions.position(client_one).title("Jonas"));
        Marker marker_two = googleMap.addMarker(markerOptions.position(client_two).title("Sabrina"));
        Marker marker_three = googleMap.addMarker(markerOptions.position(client_three).title("Alex"));

        clientList.add(marker_one);
        clientList.add(marker_two);
        clientList.add(marker_three);

        marker_one.setInfoWindowAnchor(0.5f, 1.0f);
        marker_two.setInfoWindowAnchor(0.5f, 1.0f);
        marker_three.setInfoWindowAnchor(0.5f, 1.0f);
    }


    private void getUserPosition(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
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

        MarkerOptions test = new MarkerOptions();

        for (Marker m : clientList) {
            if (Objects.equals(m.getTitle(), "Sabrina")) {
                m.remove();
                test.position(m.getPosition());
                test.title(m.getTitle());
                test.icon(BitmapDescriptorFactory.fromResource(R.drawable.alert_icon));
                Marker testmarker = googleMap.addMarker(test);
                testmarker.showInfoWindow();
            }
        }
    }

}
