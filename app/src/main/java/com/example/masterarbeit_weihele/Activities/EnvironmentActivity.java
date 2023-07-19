package com.example.masterarbeit_weihele.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.masterarbeit_weihele.Classes.Basics.BasicFunctions;
import com.example.masterarbeit_weihele.R;
import com.example.masterarbeit_weihele.Classes.WeatherData.WeatherApiInterface;
import com.example.masterarbeit_weihele.Classes.WeatherData.WeatherConditionData;
import com.example.masterarbeit_weihele.Classes.WeatherData.WeatherData;
import com.example.masterarbeit_weihele.Classes.WeatherData.WeatherResponse;
import com.example.masterarbeit_weihele.databinding.ActivityEnvironmentBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnvironmentActivity extends WakeLockActivity {

    //Basics
    private ActivityEnvironmentBinding binding;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);

    //Variables
    private float temperature = 0.0f;
    private float humidity = 0.0f;
    private float wind_speed = 0.0f;
    private double position_long = 0.0f;
    private double position_lat = 0.0f;
    private String icon = "01d";

    //Prefixes
    private static final String PREF_API_KEY = "0e9ad80a98bb47d3df5444d62e712be3";
    private static final String PREF_ICON_LINK = "https://openweathermap.org/img/w/";
    private static final String PREF_DATA_LINK = "https://api.openweathermap.org/data/2.5/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEnvironmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        basicFunctions.changeActivityOnRotation(CommunicationActivity.class, MoreVitalsActivity.class);
        basicFunctions.getTime();

        getCurrentLocation();
    }


    public void getEnvironmentVals() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PREF_DATA_LINK)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiInterface apiService = retrofit.create(WeatherApiInterface.class);
        Call<WeatherResponse> call = apiService.getWeatherData(position_lat, position_long, PREF_API_KEY);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        WeatherData weatherData = weatherResponse.getWeatherData();
                        WeatherConditionData[] weatherConditions = weatherResponse.getWeatherConditions();

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
                    Toast.makeText(getApplicationContext(), "Keine Wetterdaten gefunden.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Keine Wetterrückmeldung erhalten.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("DefaultLocale")
    public void setEnvironmentVals(float temperature, float humidity, float wind_speed, String icon) {
        String iconURL = PREF_ICON_LINK + icon + ".png";

        TextView temperatureView = findViewById(R.id.environment_temperature);
        TextView humidityView = findViewById(R.id.environment_humidity);
        TextView windSpeedView = findViewById(R.id.environment_windspeed);
        ImageView weatherIconView = findViewById(R.id.environment_weatherIcon);

        temperatureView.setText(String.format("%.1f", temperature));
        humidityView.setText(String.format("%.0f", humidity));
        windSpeedView.setText(String.format("%.0f", wind_speed));

        Picasso.get().load(iconURL).error(R.drawable.rain_icon).fit().into(weatherIconView);
    }

    private void getCurrentLocation() {
        Toast.makeText(this, "lädt Daten...", Toast.LENGTH_LONG).show();

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                if (location.hasAccuracy() && location.getAccuracy() <= 100) {
                    position_lat = location.getLatitude();
                    position_long = location.getLongitude();
                    getEnvironmentVals();
                } else {
                    Toast.makeText(getApplicationContext(), "Position konnte nicht ermittelt werden.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
