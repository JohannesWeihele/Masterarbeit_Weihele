package com.example.masterarbeit_weihele.Recycler.CommunicationRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterarbeit_weihele.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {


    Context context;
    List<Contact_Item> items;
    private OnContactClickListener clickListener;

    public ContactAdapter(Context context, List<Contact_Item> items, OnContactClickListener clickListener) {
        this.context = context;
        this.items = items;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.communication_contact_item_view, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact_Item contact = items.get(position);
        holder.contactView.setText(contact.getContact_name());

        holder.contactView.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.onContactClick(contact);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
