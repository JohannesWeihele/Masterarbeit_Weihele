package com.example.masterarbeit_weihele;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommandViewHolder extends RecyclerView.ViewHolder {

    Button commandView;

    public CommandViewHolder(@NonNull View itemView) {
        super(itemView);
        commandView = itemView.findViewById(R.id.command_item);
    }
}
