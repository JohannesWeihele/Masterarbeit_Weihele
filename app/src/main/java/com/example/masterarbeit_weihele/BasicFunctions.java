package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;

public class BasicFunctions {

    Context context;

    public BasicFunctions(Context context) {

        this.context = context;
    }

    public void hideDownIcon(){
        Activity activity = (Activity) context;
        ScrollView createAccountScroll = activity.findViewById(R.id.createAccout_Scroll);
        ImageView downIcon = activity.findViewById(R.id.down_icon);

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

}
