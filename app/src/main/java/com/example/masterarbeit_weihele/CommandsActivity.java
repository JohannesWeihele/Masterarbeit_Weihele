package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        List<Command_Item> items = new ArrayList<Command_Item>();
        items.add(new Command_Item("Abschnitt A löschen"));
        items.add(new Command_Item("Abschnitt B löschen"));
        items.add(new Command_Item("Abschnitt C löschen"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CommandAdapter adapter = new CommandAdapter(getApplicationContext(), items);
        CommandItemTouchHelperCallback touchHelperCallback = new CommandItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        adapter.setItemTouchHelper(itemTouchHelper);
        recyclerView.setAdapter(adapter);

    }
}