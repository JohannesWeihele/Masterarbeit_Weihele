package com.example.masterarbeit_weihele.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterarbeit_weihele.Classes.BasicFunctions;
import com.example.masterarbeit_weihele.Recycler.CommandRecycler.CommandAdapter;
import com.example.masterarbeit_weihele.Recycler.CommandRecycler.Command_Item;
import com.example.masterarbeit_weihele.R;
import com.example.masterarbeit_weihele.databinding.ActivityCommandsBinding;

import java.util.ArrayList;
import java.util.List;

public class CommandsActivity extends WakeLockActivity {

    private ActivityCommandsBinding binding;
    CommandAdapter adapter;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);
    SharedPreferences sharedPreferences;
    boolean containsPreferences;
    List<Command_Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCommandsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        basicFunctions.changeActivityOnRotation(VitalsActivity.class, EmergencyActivity.class);
        basicFunctions.getTime();

        sharedPreferences = getSharedPreferences("Commands", Context.MODE_PRIVATE);
        containsPreferences = sharedPreferences.contains("Item_Value_1");

        createRecycler();
    }

    public void createRecycler(){
        RecyclerView recyclerView = findViewById(R.id.commands_recyclerview);
        TextView noCommandsView = findViewById(R.id.no_commands_textview);

        if(containsPreferences){
            getPreferences();
        } else {
            items.add(new Command_Item("Abschnitt A löschen", false, false));
            items.add(new Command_Item("Abschnitt B löschen", false, false));
            items.add(new Command_Item("Abschnitt C löschen", false, false));
        }

        if(items.size() != 0){
            noCommandsView.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new CommandAdapter(getApplicationContext(), items, recyclerView);
            recyclerView.setAdapter(adapter);
        } else {
            noCommandsView.setVisibility(View.VISIBLE);
        }
    }

    //Werden nur einmal benötigt, daher direkter Zugriff statt SharedPreferencesvals
        public void getPreferences(){
            for(int i = 0; i < 3; i++){
                String nameKey = "Item_Value_" + i;
                String completedKey = "Item_isCompleted_" + i;
                String focusedKey = "Item_isFocused_" + i;
                items.add(new Command_Item(sharedPreferences.getString(nameKey, ""), sharedPreferences.getBoolean(completedKey, false), sharedPreferences.getBoolean(focusedKey, false)));
            }
        }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.saveItems();
    }
}