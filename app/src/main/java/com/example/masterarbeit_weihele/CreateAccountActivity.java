package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.masterarbeit_weihele.databinding.ActivityCreateaccountBinding;

public class CreateAccountActivity extends Activity {

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
        Intent intent = new Intent(CreateAccountActivity.this, FunctionsActivity.class);
        startActivity(intent);
    }
}