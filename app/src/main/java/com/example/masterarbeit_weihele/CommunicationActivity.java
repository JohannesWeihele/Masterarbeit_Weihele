package com.example.masterarbeit_weihele;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterarbeit_weihele.CommunicationRecycler.ContactAdapter;
import com.example.masterarbeit_weihele.CommunicationRecycler.Contact_Item;
import com.example.masterarbeit_weihele.CommunicationRecycler.OnContactClickListener;
import com.example.masterarbeit_weihele.databinding.ActivityCommunicationBinding;

import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.ChannelMediaOptions;

import java.util.ArrayList;
import java.util.List;

public class CommunicationActivity extends WakeLockActivity implements OnContactClickListener {

    private ActivityCommunicationBinding binding;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);

    private TextView communicationOption;
    private final String appId = "ee00498594584239a1280448fe70713c";
    private String channelName = "1234";
    private String token = "007eJxTYDjM/OV/wiFnr7jyVJ1ejgiRHb+mXxA84Fr+/RdDyKclRSYKDKmpBgYmlhamliamFiZGxpaJhkYWBiYmFmmp5gbmhsbJ14uXpjQEMjJwvDRgZWSAQBCfhcHQyNiEgQEAMnAd0g==";

    //--> HashMap mit "ChannelName, Token" und dann für jeden Namen eins bereitstellen + einsatzleiter + Alle
    private int uid = 0;
    private boolean isJoined = false;
    private RtcEngine agoraEngine;
    private static final int PERMISSION_REQ_ID = 22;
    private static final String[] REQUESTED_PERMISSIONS =
            {
                    Manifest.permission.RECORD_AUDIO
            };

    private boolean checkSelfPermission()
    {
        if (ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[0]) !=  PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }
        return true;
    }

    void showMessage(String message) {
        runOnUiThread(() ->
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }


    private void setupVoiceSDKEngine() {
        try {
            RtcEngineConfig config = new RtcEngineConfig();
            config.mContext = getBaseContext();
            config.mAppId = appId;
            config.mEventHandler = mRtcEventHandler;
            agoraEngine = RtcEngine.create(config);
        } catch (Exception e) {
            throw new RuntimeException("Check the error.");
        }
    }

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        // Listen for the remote user joining the channel.
        public void onUserJoined(int uid, int elapsed) {
            runOnUiThread(()->Toast.makeText(getApplicationContext(), "User joined", Toast.LENGTH_SHORT).show());
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            // Successfully joined a channel
            isJoined = true;
            showMessage("Joined Channel " + channel);
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            // Listen for remote users leaving the channel
            showMessage("Remote user offline " + uid + " " + reason);
        }

        @Override
        public void onLeaveChannel(RtcStats 	stats) {
            // Listen for the local user leaving the channel
            isJoined = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCommunicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }

        setupVoiceSDKEngine();
        basicFunctions.changeActivityOnRotation(NavigationActivity.class, EnvironmentActivity.class);
    }

    private void joinChannel() {
        ChannelMediaOptions options = new ChannelMediaOptions();
        options.autoSubscribeAudio = true;
        // Set both clients as the BROADCASTER.
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
        // Set the channel profile as BROADCASTING.
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING;

        // Join the channel with a temp token.
        // You need to specify the user ID yourself, and ensure that it is unique in the channel.
        agoraEngine.joinChannel(token, channelName, uid, options);
    }

    public void communicationClick(View v){
        Button clickedButton = (Button) v;
         communicationOption = findViewById(R.id.communcation_option_text);
        switch (clickedButton.getTag().toString()){
            case "communication_btn_contactall":
                setContentView(R.layout.activity_communication_talk);
                communicationOption = findViewById(R.id.communcation_option_text);
                communicationOption.setText("An Alle");
                joinChannel();
                break;
            case "communication_btn_contactleader":
                setContentView(R.layout.activity_communication_talk);
                communicationOption = findViewById(R.id.communcation_option_text);
                communicationOption.setText("An Einsatzleiter");
                joinChannel();
                break;
            case "communication_btn_contact":
                setContentView(R.layout.activity_communication_contact);
                createRecycler();
                break;
        }
    }

    @Override
    public void onContactClick(Contact_Item contact) {
        setContentView(R.layout.activity_communication_talk);
        communicationOption = findViewById(R.id.communcation_option_text);
        communicationOption.setText(contact.getContact_name());
        joinChannel();
    }

    public void createRecycler(){
        RecyclerView recyclerView = findViewById(R.id.contact_recyclerview);
        TextView noContactsView = findViewById(R.id.no_contacts_textview);

        List<Contact_Item> items = new ArrayList<>();

        if(items.size() != 0){
            noContactsView.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ContactAdapter adapter = new ContactAdapter(getApplicationContext(), items, this);
            recyclerView.setAdapter(adapter);
        } else {
            noContactsView.setVisibility(View.VISIBLE);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        agoraEngine.leaveChannel();

        // Destroy the engine in a sub-thread to avoid congestion
        new Thread(() -> {
            RtcEngine.destroy();
            agoraEngine = null;
        }).start();
    }

    @Override
    public void onBackPressed() {
        basicFunctions.loadActivity(FunctionsActivity.class);
        super.onBackPressed();
    }




}