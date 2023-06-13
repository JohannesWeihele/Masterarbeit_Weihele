package com.example.masterarbeit_weihele.CommunicationRecycler;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterarbeit_weihele.R;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    Button contactView;
    ImageView iconView;

    public ContactViewHolder(@NonNull View itemView) {
        super(itemView);
        contactView = itemView.findViewById(R.id.contact_item);
        iconView = itemView.findViewById(R.id.contact_item_icon);
    }
}
