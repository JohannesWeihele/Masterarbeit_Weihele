package com.example.masterarbeit_weihele;

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

import com.example.masterarbeit_weihele.CommunicationRecycler.ContactAdapter;
import com.example.masterarbeit_weihele.CommunicationRecycler.Contact_Item;
import com.example.masterarbeit_weihele.CommunicationRecycler.OnContactClickListener;
import com.example.masterarbeit_weihele.databinding.ActivityCommunicationBinding;

import java.util.ArrayList;
import java.util.List;

public class CommunicationActivity extends WakeLockActivity implements OnContactClickListener {

    private ActivityCommunicationBinding binding;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);

    private TextView communicationOption;
    private TextView buttonTextView;
    private Button pushToTalkButton;
    private String channelName = "";
    private String currentToken = "";
    private String leaderChannelToken = "007eJxTYFh3LsL6/K2bE/xnlR3y3H3BIzjWReyhpu00H5djanazDpcqMKSmGhiYWFqYWpqYWpgYGVsmGhpZGJiYWKSlmhuYGxonl3SsSmkIZGTosjzBysgAgSA+L4NrZl5xYklVTmpmSWoRAwMAbmMicA==";
    private String allChannelToken = "007eJxTYFCQCvXyEHEtWBDcr+O4JvDF/mnbE1eqv9LM6/1yXCzgwzYFhtRUAwMTSwtTSxNTCxMjY8tEQyMLAxMTi7RUcwNzQ+PkuI5VKQ2BjAynqyeyMjJAIIjPwuCYk5PKwAAAt5sdtw==";
    private boolean pushToTalkPreference = true;

    private CommunicationService communicationService;
    private CommunicationService.CommunicationBinder communicationBinder;
    private boolean isCommunicationServiceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCommunicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        basicFunctions.changeActivityOnRotation(NavigationActivity.class, EnvironmentActivity.class);

        Intent communicationServiceIntent = new Intent(this, CommunicationService.class);
        bindService(communicationServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void communicationClick(View v){
        Button clickedButton = (Button) v;
        communicationOption = findViewById(R.id.communcation_option_text);

        switch (clickedButton.getTag().toString()){
            case "communication_btn_contactall":
                channelName = "Alle";
                currentToken = allChannelToken;
                setContentView(R.layout.activity_communication_talk);
                setupPushToTalk();
                communicationOption = findViewById(R.id.communcation_option_text);
                communicationOption.setText("An Alle");
                communicationService.joinChannel(channelName, currentToken);
                break;
            case "communication_btn_contactleader":
                channelName = "Einsatzleiter";
                currentToken = leaderChannelToken;
                setContentView(R.layout.activity_communication_talk);
                setupPushToTalk();
                communicationOption = findViewById(R.id.communcation_option_text);
                communicationOption.setText("An Einsatzleiter");
                communicationService.joinChannel(channelName, currentToken);
                break;
            case "communication_btn_contact":
                setContentView(R.layout.activity_communication_contact);
                createRecycler();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pushToTalkPreference = communicationService.getPushToTalkPreference();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setupPushToTalk(){
        buttonTextView = findViewById(R.id.communication_btn_Text);
        pushToTalkButton = findViewById(R.id.pushToTalk_Btn);

        if(pushToTalkPreference){
            buttonTextView.setText("Push To Talk");
            pushToTalkButton.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    communicationService.muteMicrophon(false);
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    communicationService.muteMicrophon(true);
                }
                return false;
            });
        } else {
            buttonTextView.setText("Jetzt Sprechen");
        }
    }

    @Override
    public void onContactClick(Contact_Item contact) {
        channelName = contact.getContact_name();
        currentToken = contact.getToken();
        setContentView(R.layout.activity_communication_talk);
        setupPushToTalk();
        communicationOption = findViewById(R.id.communcation_option_text);
        communicationOption.setText(contact.getContact_name());
        communicationService.joinChannel(channelName, currentToken);
    }

    public void createRecycler(){
        RecyclerView recyclerView = findViewById(R.id.contact_recyclerview);
        TextView noContactsView = findViewById(R.id.no_contacts_textview);

        List<Contact_Item> items = new ArrayList<>();
        items.add(new Contact_Item("Jonas", "007eJxTYPDmvHaNYeZS+QdNn5K/brCfuT+YuZjtgODizpkR6x+uPbZLgSE11cDAxNLC1NLE1MLEyNgy0dDIwsDExCIt1dzA3NA4eW3PqpSGQEYGHi4nVkYGCATxWRm88vMSixkYAHoWHwM="));
        items.add(new Contact_Item("Sabrina", "007eJxTYFjP5/VqQ0F9waq66sd3lVjUXlcpzjvr0seXPfnKjBv/a/IUGFJTDQxMLC1MLU1MLUyMjC0TDY0sDExMLNJSzQ3MDY2T9/esSmkIZGTo/pLNzMgAgSA+O0NwYlJRZl4iAwMA4EkhTQ=="));
        items.add(new Contact_Item("Alex", "007eJxTYOiU57hSxZfcri61b/HPgz8lHhs6ZVSmqcY2f4pjirz654ICQ2qqgYGJpYWppYmphYmRsWWioZGFgYmJRVqquYG5oXHyxZ5VKQ2BjAwG6pdYGBkgEMRnYXDMSa1gYAAA8LweDA=="));

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
        unbindService(serviceConnection);
    }

    @Override
    public void onBackPressed() {
        basicFunctions.loadActivity(FunctionsActivity.class);
        super.onBackPressed();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            communicationBinder = (CommunicationService.CommunicationBinder) service;
            communicationService = communicationBinder.getService();
            isCommunicationServiceBound = true;
            communicationService.updatePreferences();
            pushToTalkPreference = communicationService.getPushToTalkPreference();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
}