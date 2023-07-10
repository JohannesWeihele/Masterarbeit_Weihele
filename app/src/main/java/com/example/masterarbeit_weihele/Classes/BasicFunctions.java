package com.example.masterarbeit_weihele.Classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.wear.widget.BoxInsetLayout;

import com.example.masterarbeit_weihele.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BasicFunctions {

    private Context context;
    private ProgressDialog progressDialog;


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

    public void changeActivityOnRotation(Class<? extends Activity> rotLeftActivity, Class<? extends Activity> rotRightActivity) {
        Activity activity = (Activity) context;
        BoxInsetLayout mission_id_checkButton = activity.findViewById(R.id.rotation_wrapper);
        mission_id_checkButton.setFocusableInTouchMode(true);
        mission_id_checkButton.requestFocus();
        mission_id_checkButton.setOnGenericMotionListener(new View.OnGenericMotionListener() {
            @Override
            public boolean onGenericMotion(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_SCROLL && event.isFromSource(InputDevice.SOURCE_ROTARY_ENCODER)) {
                    float verticalScroll = event.getAxisValue(MotionEvent.AXIS_SCROLL);

                    if (verticalScroll >= 0.0 && rotLeftActivity != null) {
                        loadActivity(rotLeftActivity);
                    } else if (verticalScroll <= 0.0 && rotRightActivity != null) {
                        loadActivity(rotRightActivity);
                    } else {
                        System.out.println("Keine Action definiert.");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void loadActivity(Class<? extends Activity> newActivity) {
        Activity activity = (Activity) context;
        showProgressDialog(newActivity);
        Intent intent = new Intent(activity, newActivity);
        activity.startActivity(intent);
    }

    private void showProgressDialog(Class<? extends Activity> activity) {

        progressDialog = new ProgressDialog(context);
        String activityName = activity.getSimpleName();

        activityName = activityName.replace("Activity", "");

        if(activityName.equals("Navigation") || activityName.equals("Communication")){
            progressDialog.setMessage("Lädt " + activityName + "...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            System.out.println("Ladebalken nicht notwendig.");
        }
    }

    public void getTime(){
        Calendar calendar = Calendar.getInstance();
        Activity activity = (Activity) context;

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String currentTime = String.format("%02d:%02d", hour, minute);

        TextView time_View = activity.findViewById(R.id.time_textView);
        time_View.setText(currentTime);
    }

}
