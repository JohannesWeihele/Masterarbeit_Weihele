package com.example.masterarbeit_weihele;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.masterarbeit_weihele.databinding.ActivityEnvironmentBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnvironmentActivity extends Activity {

    private ActivityEnvironmentBinding binding;
    private float temperature = 0.0f;
    private float humidity = 0.0f;
    private float wind_speed = 0.0f;
    private double position_long = 0.0f;
    private double position_lat = 0.0f;
    private String icon = "01d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEnvironmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getCurrentLocation();
    }

    public void getEnvironmentVals() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        System.out.println(position_long);
        System.out.println(position_lat);
        WeatherApiService apiService = retrofit.create(WeatherApiService.class);
        Call<WeatherResponse> call = apiService.getWeatherData(position_lat, position_long, "0e9ad80a98bb47d3df5444d62e712be3");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        WeatherData weatherData = weatherResponse.getWeatherData();
                        WeatherCondition[] weatherConditions = weatherResponse.getWeatherConditions();

                        float temperature_kelvin = weatherData.getTemperature();
                        temperature = temperature_kelvin - 273.15f;
                        humidity = weatherData.getHumidity();
                        wind_speed = weatherResponse.getWind().getSpeed();

                        if (weatherConditions != null && weatherConditions.length > 0) {
                            icon = weatherConditions[0].getIcon();
                        }
                        setEnvironmentVals(temperature, humidity, wind_speed, icon);
                    }
                } else {
                    System.out.println("Keine Wetterdaten gefunden.");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                System.out.println("Keine Wetterrückmeldung erhalten.");
            }
        });
    }

    public void setEnvironmentVals(float temperature, float humidity, float wind_speed, String icon) {
        TextView temperatureView = findViewById(R.id.environment_temperature);
        TextView humidityView = findViewById(R.id.environment_humidity);
        TextView windSpeedView = findViewById(R.id.environment_windspeed);
        ImageView weatherIconView = findViewById(R.id.environment_weatherIcon);
        String iconURL = "https://openweathermap.org/img/w/" + icon + ".png";

        temperatureView.setText(String.format("%.1f", temperature));
        humidityView.setText(String.format("%.0f", humidity));
        windSpeedView.setText(String.format("%.0f", wind_speed));
        Picasso.get().load(iconURL).error(R.drawable.rain_icon).fit().into(weatherIconView);
    }

    private void getCurrentLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                if (location.hasAccuracy() && location.getAccuracy() <= 100) {
                    position_lat = location.getLatitude();
                    position_long = location.getLongitude();
                    getEnvironmentVals();
                } else {
                    System.out.println("Position konnte nicht ermittelt werden.");
                }
            }
        });
    }

}
