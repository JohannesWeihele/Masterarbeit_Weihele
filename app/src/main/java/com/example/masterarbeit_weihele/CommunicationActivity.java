package com.example.masterarbeit_weihele;

import android.os.Bundle;
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
    TextView communicationOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCommunicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void communicationClick(View v){
        Button clickedButton = (Button) v;
         communicationOption = findViewById(R.id.communcation_option_text);
        switch (clickedButton.getTag().toString()){
            case "communication_btn_contactall":
                setContentView(R.layout.activity_communication_talk);
                communicationOption = findViewById(R.id.communcation_option_text);
                communicationOption.setText("An Alle");
                break;
            case "communication_btn_contactleader":
                setContentView(R.layout.activity_communication_talk);
                communicationOption = findViewById(R.id.communcation_option_text);
                communicationOption.setText("An Einsatzleiter");
                break;
            case "communication_btn_contact":
                setContentView(R.layout.activity_communication_contact);
                createRecycler();
                break;
        }
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

    @Override
    public void onContactClick(Contact_Item contact) {
        setContentView(R.layout.activity_communication_talk);
        communicationOption = findViewById(R.id.communcation_option_text);
        communicationOption.setText(contact.getContact_name());
    }


}