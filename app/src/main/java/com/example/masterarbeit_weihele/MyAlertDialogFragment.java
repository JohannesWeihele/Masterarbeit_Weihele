package com.example.masterarbeit_weihele;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class MyAlertDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_alert_dialog, null);

        builder.setView(dialogView);

        return builder.create();
    }

    // Methode zum Anzeigen des Dialogfragments
    public void showDialog(FragmentManager fragmentManager) {
        show(fragmentManager, "alertDialog");
    }
}
