package com.example.masterarbeit_weihele.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.masterarbeit_weihele.Classes.BasicFunctions;
import com.example.masterarbeit_weihele.R;
import com.example.masterarbeit_weihele.databinding.ActivityMainBinding;


public class MainActivity extends WakeLockActivity {

    private ActivityMainBinding binding;
    private int missionID = 1234;

    private final BasicFunctions basicFunctions = new BasicFunctions(this);
    private boolean isBackButtonPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        basicFunctions.getTime();
        basicFunctions.changeActivityOnRotation(null, FunctionsActivity.class);
    }

    public void openCreateAccountView(View v) {
        Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void openFunctionsView(View v){
        EditText mission_ID_View = findViewById(R.id.mission_id);

        if(mission_ID_View.getText().toString().isEmpty()){
            mission_ID_View.setError("ID notwendig");
        } else {
            int ID = Integer.parseInt(mission_ID_View.getText().toString());
            if(ID == missionID){
                Toast.makeText(getApplicationContext(), "Anmeldung erfolgreich", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, FunctionsActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Keinen Account gefunden", Toast.LENGTH_SHORT).show();
            }
        }
    }
}