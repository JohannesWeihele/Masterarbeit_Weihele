package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.masterarbeit_weihele.databinding.ActivityCreateaccountBinding;

public class CreateAccountActivity extends WakeLockActivity {

    private ActivityCreateaccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateaccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hideDownIcon();
    }

    public void hideDownIcon(){
        ScrollView createAccountScroll = findViewById(R.id.createAccout_Scroll);
        ImageView downIcon = findViewById(R.id.down_icon);

        createAccountScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (createAccountScroll.getChildAt(0).getBottom() <= (createAccountScroll.getHeight() + createAccountScroll.getScrollY())) {
                    downIcon.setVisibility(View.GONE);
                } else {
                    downIcon.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void createAccount(View v){
        EditText nameView = findViewById(R.id.accountName);
        EditText ageView = findViewById(R.id.accountAge);
        EditText bodysizeView = findViewById(R.id.accountBodysize);
        EditText bodyweightView = findViewById(R.id.accountBodyweight);

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

            Toast.makeText(getApplicationContext(), "Account wurde angelegt", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CreateAccountActivity.this, FunctionsActivity.class);
            startActivity(intent);
        }

    }
}