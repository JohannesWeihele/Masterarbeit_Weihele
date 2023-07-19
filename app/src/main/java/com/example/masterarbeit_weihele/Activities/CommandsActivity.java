package com.example.masterarbeit_weihele.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterarbeit_weihele.Classes.Basics.BasicFunctions;
import com.example.masterarbeit_weihele.Recycler.CommandRecycler.CommandAdapter;
import com.example.masterarbeit_weihele.Recycler.CommandRecycler.Command_Item;
import com.example.masterarbeit_weihele.R;
import com.example.masterarbeit_weihele.databinding.ActivityCommandsBinding;

import java.util.ArrayList;
import java.util.List;

public class CommandsActivity extends WakeLockActivity {

    //Basics
    private ActivityCommandsBinding binding;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);
    private SharedPreferences sharedPreferences;

    //Recycler
    private final List<Command_Item> items = new ArrayList<>();
    private CommandAdapter adapter;
    private boolean containsPreferences;

    //Prefixes
    private static final String PREFS_NAME = "Commands";
    private static final String ITEM_VALUE_PREFIX = "Item_Value_";
    private static final String ITEM_COMPLETED_PREFIX = "Item_isCompleted_";
    private static final String ITEM_FOCUSED_PREFIX = "Item_isFocused_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCommandsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        basicFunctions.changeActivityOnRotation(VitalsActivity.class, EmergencyActivity.class);
        basicFunctions.getTime();

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        containsPreferences = sharedPreferences.contains(ITEM_VALUE_PREFIX + 1);

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

    public void getPreferences(){
        for(int i = 0; i < 3; i++){
            String nameKey = ITEM_VALUE_PREFIX + i;
            String completedKey = ITEM_COMPLETED_PREFIX + i;
            String focusedKey = ITEM_FOCUSED_PREFIX + i;
            items.add(new Command_Item(sharedPreferences.getString(nameKey, ""), sharedPreferences.getBoolean(completedKey, false), sharedPreferences.getBoolean(focusedKey, false)));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.saveItems();
    }
}