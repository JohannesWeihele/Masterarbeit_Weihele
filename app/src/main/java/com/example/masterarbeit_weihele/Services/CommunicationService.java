package com.example.masterarbeit_weihele.Services;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.masterarbeit_weihele.Classes.SharedPreferencesVals;
import com.example.masterarbeit_weihele.databinding.ActivityCommunicationBinding;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IAgoraEventHandler;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;

public class CommunicationService extends Service {

    //Basics
    private final IBinder binder = new CommunicationBinder();
    private ActivityCommunicationBinding binding;
    private final SharedPreferencesVals sharedPreferencesVals = new SharedPreferencesVals(this);

    //Variables
    private Handler handler;
    private RtcEngine agoraEngine;
    private String selfChannelName = "";
    private boolean isInChannel = false;
    private boolean pushToTalkPreference = false;

    //Prefixes
    private static final String PREF_APPID = "ee00498594584239a1280448fe70713c";
    private static final int PERMISSION_REQ_ID = 22;
    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO
    };

    //Tokens
    private String SELFTOKEN = "007eJxTYNjCqRV3tE2ZxyPPbeGZHq+dKX+49yyftPTjvvX/V8l/t+dXYEhNNTAwsbQwtTQxtTAxMrZMNDSyMDAxsUhLNTcwNzROFmJbm9IQyMiwtqqSmZEBAkF8Dgav/IzEvLzUYgYGAJZWH8Y=";

    public class CommunicationBinder extends Binder {
        public CommunicationService getService() {
            return CommunicationService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        updatePreferences();
        selfChannelName = sharedPreferencesVals.getAccountName();
        pushToTalkPreference = sharedPreferencesVals.getPushToTalkVal();

        setupAgoraEngine();
        handler = new Handler(Looper.getMainLooper());
    }

    private void setupAgoraEngine() {
        try {
            RtcEngineConfig configSelf = new RtcEngineConfig();
            configSelf.mContext = getBaseContext();
            configSelf.mAppId = PREF_APPID;
            configSelf.mEventHandler = mRtcEventHandler;
            agoraEngine = RtcEngine.create(configSelf);
        } catch (Exception e) {
            throw new RuntimeException("Check the error.");
        }
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
        joinChannel(selfChannelName, SELFTOKEN, pushToTalkPreference);

        return START_STICKY;
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

    private boolean checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[0]) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void joinChannel(String channelName, String token, boolean pushToTalkPreference) {
        ChannelMediaOptions optionsSelf = new ChannelMediaOptions();
        optionsSelf.autoSubscribeAudio = true;
        optionsSelf.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
        optionsSelf.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING;

        if (isInChannel) {
            agoraEngine.leaveChannel();
            isInChannel = false;
        }

        agoraEngine.joinChannel(token, channelName, 0, optionsSelf);
        isInChannel = true;

        if(channelName.equals(selfChannelName)){
            agoraEngine.muteLocalAudioStream(false);
        } else {
            if(pushToTalkPreference){
                agoraEngine.muteLocalAudioStream(true);
            } else {
                agoraEngine.muteLocalAudioStream(false);
            }

        }

    }

    public void updatePreferences(){
        sharedPreferencesVals.fetchAccountPreferenceVals();
        sharedPreferencesVals.fetchCommunicationPreferenceVals();
    }

    public void muteMicrophon(boolean isMuted){
        agoraEngine.muteLocalAudioStream(isMuted);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
