package com.example.masterarbeit_weihele;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.masterarbeit_weihele.databinding.ActivityCommunicationBinding;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IAgoraEventHandler;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;

public class AllCommunicationService extends Service {

    private final IBinder binder = new CommunicationBinder();
    private ActivityCommunicationBinding binding;
    private Handler handler;

    private RtcEngine agoraEngine;

    public class CommunicationBinder extends Binder {
        public AllCommunicationService getService() {
            return AllCommunicationService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupVoiceSDKEngine();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        joinChannel();

        return START_STICKY;
    }

    private void setupVoiceSDKEngine() {
        try {
            RtcEngineConfig config = new RtcEngineConfig();
            config.mContext = getBaseContext();
            config.mAppId = "ee00498594584239a1280448fe70713c";
            config.mEventHandler = mRtcEventHandler;
            agoraEngine = RtcEngine.create(config);
        } catch (Exception e) {
            throw new RuntimeException("Check the error.");
        }
    }

    private final IAgoraEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        public void onUserJoined(int uid, int elapsed) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showToast("Nutzer hinzugefügt");
                }
            });
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showToast("Channel " + channel + " erfolgreich beigetreten");
                }
            });
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showToast("Nutzer abgemeldet");
                }
            });
        }

        @Override
        public void onLeaveChannel(RtcStats stats) {
            // Listen for the local user leaving the channel
        }
    };

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void joinChannel() {
        ChannelMediaOptions options = new ChannelMediaOptions();
        options.autoSubscribeAudio = true;
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING;

        agoraEngine.joinChannel("007eJxTYChavfG69Z7LK+52GU/b+frKnaVv93sWBibdXaSnO9tOwX2CAkNqqoGBiaWFqaWJqYWJkbFloqGRhYGJiUVaqrmBuaFxMuv0pSkNgYwMDxw+sjAyQCCIz8LgmJOTysAAAP1nINQ=",
                "Alle", 0, options);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (agoraEngine != null) {
            agoraEngine.leaveChannel();
            agoraEngine.destroy();
        }
    }
}
