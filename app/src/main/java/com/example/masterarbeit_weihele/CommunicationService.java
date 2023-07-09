package com.example.masterarbeit_weihele;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.masterarbeit_weihele.databinding.ActivityCommunicationBinding;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IAgoraEventHandler;
import io.agora.rtc2.IMetadataObserver;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;

public class CommunicationService extends Service {

    private final IBinder binder = new CommunicationBinder();
    private ActivityCommunicationBinding binding;
    private Handler handler;
    private SharedPreferencesVals sharedPreferencesVals = new SharedPreferencesVals(this);
    private String AppId = "ee00498594584239a1280448fe70713c";
    private String selfToken = "007eJxTYFjmuHeSuq53hIgNd0mGZr4vo+V1+9ySOTr/FDY9W8Via6XAkJpqYGBiaWFqaWJqYWJkbJloaGRhYGJikZZqbmBuaJy8p3tVSkMgI8OU820MjFAI4nMweOVnJOblpRYzMAAAeHEdLg==";
    private String selfChannelName = "";
    private String currentChannelName = "";
    private boolean pushToTalkPreference = true;
    private boolean isInChannel = false;

    private RtcEngine agoraEngine;

    private static final int PERMISSION_REQ_ID = 22;
    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO
    };


    public class CommunicationBinder extends Binder {
        public CommunicationService getService() {
            return CommunicationService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        updatePreferences();
        selfChannelName = sharedPreferencesVals.accountName;
        pushToTalkPreference = sharedPreferencesVals.pushToTalkVal;

        setupAgoraEngine();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions((Activity) getApplicationContext(), REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }
        joinChannel(selfChannelName, selfToken);

        return START_STICKY;
    }

    private boolean checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[0]) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void setupAgoraEngine() {
        try {
            RtcEngineConfig configSelf = new RtcEngineConfig();
            configSelf.mContext = getBaseContext();
            configSelf.mAppId = AppId;
            configSelf.mEventHandler = mRtcEventHandler;
            agoraEngine = RtcEngine.create(configSelf);
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
            showToast("Channel verlassen");
        }
    };

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void joinChannel(String channelName, String token) {
        ChannelMediaOptions optionsSelf = new ChannelMediaOptions();
        optionsSelf.autoSubscribeAudio = true;
        optionsSelf.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
        optionsSelf.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING;

        if (isInChannel) {
            agoraEngine.leaveChannel();
            isInChannel = false;
        }

        currentChannelName = channelName;
        agoraEngine.joinChannel(token, channelName, 0, optionsSelf);
        isInChannel = true;

        if(currentChannelName.equals(selfChannelName)){
            agoraEngine.muteLocalAudioStream(false);
        } else {
            if(pushToTalkPreference){
                agoraEngine.muteLocalAudioStream(true);
            } else {
                agoraEngine.muteLocalAudioStream(false);
            }

        }

    }

    public void muteMicrophon(boolean isMuted){
        agoraEngine.muteLocalAudioStream(isMuted);
    }

    public boolean getPushToTalkPreference(){
        return pushToTalkPreference;
    }

    public void updatePreferences(){
        sharedPreferencesVals.getAccountPreferenceVals();
        sharedPreferencesVals.getCommunicationPreferenceVals();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (agoraEngine != null) {
            agoraEngine.leaveChannel();
            isInChannel = false;
            agoraEngine.destroy();
        }
    }
}
