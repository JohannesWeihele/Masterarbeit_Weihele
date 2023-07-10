package com.example.masterarbeit_weihele.Recycler.CommandRecycler;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterarbeit_weihele.R;

public class CommandViewHolder extends RecyclerView.ViewHolder {

    Button commandView;

    public CommandViewHolder(@NonNull View itemView) {
        super(itemView);
        commandView = itemView.findViewById(R.id.command_item);
    }
}
