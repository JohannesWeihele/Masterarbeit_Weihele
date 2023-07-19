package com.example.masterarbeit_weihele.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.masterarbeit_weihele.Classes.Basics.BasicFunctions;
import com.example.masterarbeit_weihele.R;
import com.example.masterarbeit_weihele.databinding.ActivityCreateaccountBinding;

public class CreateAccountActivity extends WakeLockActivity {

    //Basics
    private ActivityCreateaccountBinding binding;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);

    //Views
    private EditText nameView;
    private EditText ageView;
    private EditText bodysizeView;
    private EditText bodyweightView;

    //Prefixes
    private static final String PREFS_ACCOUNT = "Account";
    private static final String PREFS_ACCOUNTNAME = "accountName";
    private static final String PREFS_ACCOUNTAGE = "accountAge";
    private static final String PREFS_ACCOUNTBODYSIZE = "accountBodysize";
    private static final String PREFS_ACCOUNTBODYWEIGHT = "accountBodyweight";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateaccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        basicFunctions.getTime();
        basicFunctions.hideDownIcon();
    }

    public void createAccount(View v){
        nameView = findViewById(R.id.accountName);
        ageView = findViewById(R.id.accountAge);
        bodysizeView = findViewById(R.id.accountBodysize);
        bodyweightView = findViewById(R.id.accountBodyweight);

        if(!checkFieldsFilled(nameView, ageView, bodysizeView, bodyweightView)) {
            Toast.makeText(getApplicationContext(), "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
        } else {
            setPreferences();
            openFunctionsView();
        }
    }

    public void openFunctionsView() {
        Intent intent = new Intent(CreateAccountActivity.this, FunctionsActivity.class);
        startActivity(intent);
    }

    public boolean checkFieldsFilled(EditText... fields) {
        for (EditText field : fields) {
            if (TextUtils.isEmpty(field.getText())) {
                return false;
            }
        }
        return true;
    }

    public void setPreferences(){
        String accountName = nameView.getText().toString();
        String accountAge = ageView.getText().toString();
        String accountBodysize = bodysizeView.getText().toString();
        String accountBodyweight = bodyweightView.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_ACCOUNT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PREFS_ACCOUNTNAME, accountName);
        editor.putString(PREFS_ACCOUNTAGE, accountAge);
        editor.putString(PREFS_ACCOUNTBODYSIZE, accountBodysize);
        editor.putString(PREFS_ACCOUNTBODYWEIGHT, accountBodyweight);

        try {
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "Account wurde angelegt", Toast.LENGTH_SHORT).show();
    }
}