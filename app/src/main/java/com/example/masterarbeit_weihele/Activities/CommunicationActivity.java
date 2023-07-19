package com.example.masterarbeit_weihele.Activities;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.BoxInsetLayout;

import com.example.masterarbeit_weihele.Classes.Basics.BasicFunctions;
import com.example.masterarbeit_weihele.Classes.Basics.SharedPreferencesVals;
import com.example.masterarbeit_weihele.Recycler.CommunicationRecycler.ContactAdapter;
import com.example.masterarbeit_weihele.Recycler.CommunicationRecycler.Contact_Item;
import com.example.masterarbeit_weihele.Recycler.CommunicationRecycler.OnContactClickListener;
import com.example.masterarbeit_weihele.Services.CommunicationService;
import com.example.masterarbeit_weihele.R;
import com.example.masterarbeit_weihele.databinding.ActivityCommunicationBinding;

import java.util.ArrayList;
import java.util.List;

public class CommunicationActivity extends WakeLockActivity implements OnContactClickListener {

    //Basics
    private ActivityCommunicationBinding binding;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);
    private final SharedPreferencesVals sharedPreferencesVals = new SharedPreferencesVals(this);

    //Service
    private CommunicationService communicationService;

    //Variables
    private String channelName = "";
    private String currentToken = "";
    private boolean pushToTalkPreference = true;

    //Views
    private TextView communicationOption;

    //Tokens
    private static final String ALL_CHANNEL_TOKEN = "007eJxTYPhYIyLgoCif2PT1a8470zvXxYO6w3NKqx5eCNwk/6Kwb5ECQ2qqgYGJpYWppYmphYmRsWWioZGFgYmJRVqquYG5oXGyCdvalIZARgZJIydmRgYIBPFZGBxzclIZGAAGoh0U";
    private static final String LEADER_CHANNEL_TOKEN = "007eJxTYNCpb7vEvf/yJNZd/B8WhnL6XFfuWLtE88pz4S4GQY+3U3sVGFJTDQxMLC1MLU1MLUyMjC0TDY0sDExMLNJSzQ3MDY2TPdjWpjQEMjJk7HdjYmSAQBCfl8E1M684saQqJzWzJLWIgQEA7lIgyg==";
    private static final String USER_JONAS_CHANNEL_TOKEN = "007eJxTYPC2SdugkBM5a8e2rxm5C2fclO69YFHrX6aVc/izu2FDqaYCQ2qqgYGJpYWppYmphYmRsWWioZGFgYmJRVqquYG5oXFyDtvalIZARoaZSwpYGRkgEMRnZfDKz0ssZmAAABBFHgQ=";
    private static final String USER_SABRINA_CHANNEL_TOKEN = "007eJxTYKhIqLJI3Cd4cKZz+zyGEJ6XXkqvJh7b0J+XFbb23dWPs2cqMKSmGhiYWFqYWpqYWpgYGVsmGhpZGJiYWKSlmhuYGxonR7OtTWkIZGSQKrJhZmSAQBCfnSE4MakoMy+RgQEAfAwfDQ==";
    private static final String USER_ALEX_CHANNEL_TOKEN = "007eJxTYGiwsj+3ebbfjb2KRqt6Vdp6dRdcZ3abzLnRI2ChyqW706YoMKSmGhiYWFqYWpqYWpgYGVsmGhpZGJiYWKSlmhuYGxon17CtTWkIZGSIqxZgYIRCEJ+FwTEntYKBAQDU3hzI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCommunicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        basicFunctions.changeActivityOnRotation(NavigationActivity.class, EnvironmentActivity.class);
        basicFunctions.getTime();

        sharedPreferencesVals.fetchCommunicationPreferenceVals();

        Intent communicationServiceIntent = new Intent(this, CommunicationService.class);
        bindService(communicationServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CommunicationService.CommunicationBinder communicationBinder = (CommunicationService.CommunicationBinder) service;
            communicationService = communicationBinder.getService();
            communicationService.updatePreferences();
            pushToTalkPreference = sharedPreferencesVals.getPushToTalkVal();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    public void createRecycler(){
        RecyclerView recyclerView = findViewById(R.id.contact_recyclerview);
        TextView noContactsView = findViewById(R.id.no_contacts_textview);

        List<Contact_Item> items = new ArrayList<>();
        items.add(new Contact_Item("Jonas", USER_JONAS_CHANNEL_TOKEN));
        items.add(new Contact_Item("Sabrina", USER_SABRINA_CHANNEL_TOKEN));
        items.add(new Contact_Item("Alex", USER_ALEX_CHANNEL_TOKEN));

        if(items.size() != 0){
            noContactsView.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ContactAdapter adapter = new ContactAdapter(getApplicationContext(), items, this);
            recyclerView.setAdapter(adapter);
        } else {
            noContactsView.setVisibility(View.VISIBLE);
        }
    }

    public void communicationClick(View v){
        Button clickedButton = (Button) v;
        communicationOption = findViewById(R.id.communcation_option_text);

        switch (clickedButton.getTag().toString()){
            case "communication_btn_contactall":
                channelName = "Alle";
                currentToken = ALL_CHANNEL_TOKEN;
                setTalkLayout();
                communicationOption = findViewById(R.id.communcation_option_text);
                communicationOption.setText("An Alle");
                communicationService.joinChannel(channelName, currentToken, pushToTalkPreference);
                break;
            case "communication_btn_contactleader":
                channelName = "Einsatzleiter";
                currentToken = LEADER_CHANNEL_TOKEN;
                setTalkLayout();
                communicationOption = findViewById(R.id.communcation_option_text);
                communicationOption.setText("An Einsatzleiter");
                communicationService.joinChannel(channelName, currentToken, pushToTalkPreference);
                break;
            case "communication_btn_contact":
                setContentView(R.layout.activity_communication_contact);
                createRecycler();
                break;
        }
    }

    @Override
    public void onContactClick(Contact_Item contact) {
        channelName = contact.getContact_name();
        currentToken = contact.getToken();
        setTalkLayout();
        communicationOption = findViewById(R.id.communcation_option_text);
        communicationOption.setText(contact.getContact_name());
        communicationService.joinChannel(channelName, currentToken, pushToTalkPreference);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setTalkLayout(){
        setContentView(R.layout.activity_communication_talk);
        TextView buttonTextView = findViewById(R.id.communication_btn_Text);
        Button pushToTalkButton = findViewById(R.id.pushToTalk_Btn);
        BoxInsetLayout talk_button_background = findViewById(R.id.talk_button_background);

        if(pushToTalkPreference){
            buttonTextView.setText("Push To Talk");
            pushToTalkButton.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    talk_button_background.setBackground(getResources().getDrawable(R.drawable.talk_button_pressed));
                    communicationService.muteMicrophon(false);
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    talk_button_background.setBackground(getResources().getDrawable(R.drawable.talk_button));
                    communicationService.muteMicrophon(true);
                }
                return false;
            });
        } else {
            buttonTextView.setText("Jetzt Sprechen");
        }
    }

    @Override
    public void onBackPressed() {
        basicFunctions.loadActivity(FunctionsActivity.class);
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferencesVals.fetchCommunicationPreferenceVals();
        pushToTalkPreference = sharedPreferencesVals.getPushToTalkVal();
    }

    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}