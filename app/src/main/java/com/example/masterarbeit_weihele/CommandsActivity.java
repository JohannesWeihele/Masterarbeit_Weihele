package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterarbeit_weihele.CommandRecycler.CommandAdapter;
import com.example.masterarbeit_weihele.CommandRecycler.CommandItemTouchHelperCallback;
import com.example.masterarbeit_weihele.CommandRecycler.Command_Item;
import com.example.masterarbeit_weihele.databinding.ActivityCommandsBinding;

import java.util.ArrayList;
import java.util.List;

public class CommandsActivity extends Activity{

    private ActivityCommandsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCommandsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createRecycler();
    }

    public void createRecycler(){
        RecyclerView recyclerView = findViewById(R.id.commands_recyclerview);
        TextView noCommandsView = findViewById(R.id.no_commands_textview);

        List<Command_Item> items = new ArrayList<>();
        items.add(new Command_Item("Abschnitt A löschen"));
        items.add(new Command_Item("Abschnitt B löschen"));
        items.add(new Command_Item("Abschnitt C löschen"));

        if(items.size() != 0){
            noCommandsView.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            CommandAdapter adapter = new CommandAdapter(getApplicationContext(), items, recyclerView);
            CommandItemTouchHelperCallback touchHelperCallback = new CommandItemTouchHelperCallback(adapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
            adapter.setItemTouchHelper(itemTouchHelper);
            recyclerView.setAdapter(adapter);
        } else {
            noCommandsView.setVisibility(View.VISIBLE);
        }
    }
}