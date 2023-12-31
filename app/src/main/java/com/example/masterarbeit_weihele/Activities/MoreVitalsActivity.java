package com.example.masterarbeit_weihele.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.masterarbeit_weihele.Classes.Basics.BasicFunctions;
import com.example.masterarbeit_weihele.databinding.ActivityMorevitalsBinding;

public class MoreVitalsActivity extends WakeLockActivity {

    //Basics
    private ActivityMorevitalsBinding binding;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMorevitalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        basicFunctions.hideDownIcon();
        basicFunctions.getTime();
        basicFunctions.changeActivityOnRotation(EnvironmentActivity.class, OptionsActivity.class);
    }

    public void onMoreVitalsClick(View view){
        Toast.makeText(getApplicationContext(), "Zukünftige Funktionalität", Toast.LENGTH_SHORT).show();
    }
}