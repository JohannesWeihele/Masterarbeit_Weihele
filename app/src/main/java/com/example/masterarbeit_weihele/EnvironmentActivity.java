package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;
import com.example.masterarbeit_weihele.databinding.ActivityEnvironmentBinding;

public class EnvironmentActivity extends Activity {

    private ActivityEnvironmentBinding binding;
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEnvironmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
