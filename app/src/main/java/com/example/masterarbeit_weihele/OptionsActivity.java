package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.masterarbeit_weihele.databinding.ActivityOptionsBinding;

public class OptionsActivity extends Activity {

    private ActivityOptionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void optionClick(View v){
        Button clickedButton = (Button) v;

        switch (clickedButton.getTag().toString()){
            case "options_btn_vitals":
                setContentView(R.layout.activity_options_vitals);
                break;
            case "options_btn_communication":
                setContentView(R.layout.activity_options_communication);
                break;
            case "options_btn_emergency":
                setContentView(R.layout.activity_options_emergency);
                break;
            case "options_btn_account":
                setContentView(R.layout.activity_options_account);
                getAccountVals();
                break;
        }

    }

    public void getAccountVals(){
        SharedPreferences sharedPreferences = getSharedPreferences("Account", Context.MODE_PRIVATE);

        EditText nameView = findViewById(R.id.options_accountName);
        EditText ageView = findViewById(R.id.options_accountAge);
        EditText bodysizeView = findViewById(R.id.options_accountBodysize);
        EditText bodyweightView = findViewById(R.id.options_accountBodyweight);

        String accountName = sharedPreferences.getString("accountName", "");
        String accountAge = sharedPreferences.getString("accountAge", "");
        String accountBodysize = sharedPreferences.getString("accountBodysize", "");
        String accountBodyweight = sharedPreferences.getString("accountBodyweight", "");

        nameView.setText(accountName);
        ageView.setText(accountAge);
        bodysizeView.setText(accountBodysize);
        bodyweightView.setText(accountBodyweight);
    }

    public void updateAccountVals(View v) {
        EditText nameView = findViewById(R.id.options_accountName);
        EditText ageView = findViewById(R.id.options_accountAge);
        EditText bodysizeView = findViewById(R.id.options_accountBodysize);
        EditText bodyweightView = findViewById(R.id.options_accountBodyweight);

        if (TextUtils.isEmpty(nameView.getText())
                || TextUtils.isEmpty(ageView.getText())
                || TextUtils.isEmpty(bodysizeView.getText())
                || TextUtils.isEmpty(bodyweightView.getText())) {

            Toast.makeText(getApplicationContext(), "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
        } else {
            String accountName = nameView.getText().toString();
            String accountAge = ageView.getText().toString();
            String accountBodysize = bodysizeView.getText().toString();
            String accountBodyweight = bodyweightView.getText().toString();

            SharedPreferences sharedPreferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("accountName", accountName);
            editor.putString("accountAge", accountAge);
            editor.putString("accountBodysize", accountBodysize);
            editor.putString("accountBodyweight", accountBodyweight);
            editor.apply();

            Toast.makeText(getApplicationContext(), "Account aktualisiert", Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_options);
        }
    }
}