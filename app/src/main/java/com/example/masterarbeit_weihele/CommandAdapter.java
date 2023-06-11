package com.example.masterarbeit_weihele;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class CommandAdapter extends RecyclerView.Adapter<CommandViewHolder> {


    Context context;
    List<Command_Item> items;
    ItemTouchHelper itemTouchHelper;
    public CommandAdapter(Context context, List<Command_Item> items) {
        this.context = context;
        this.items = items;
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }


    @NonNull
    @Override
    public CommandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommandViewHolder(LayoutInflater.from(context).inflate(R.layout.command_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommandViewHolder holder, int position) {
        Command_Item command_item = items.get(position);

        holder.commandView.setText(command_item.getCommand_name());
        holder.commandView.setOnClickListener(view -> {
            if (!command_item.isCompleted) {
                setItemCompleted(command_item, holder.commandView, holder.getAdapterPosition());
            } else {
                setItemIncompleted(command_item, holder.commandView, holder.getBindingAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(view -> {
            itemTouchHelper.startDrag(holder); // Starte den Drag-and-Drop-Vorgang
            return true;
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void moveItemToEnd(int sourcePosition) {
        Command_Item item = items.get(sourcePosition);
        items.remove(sourcePosition);
        items.add(item);
        notifyItemMoved(sourcePosition, items.size() - 1);
    }

    public void moveItemToFront(int sourcePosition) {
        Command_Item item = items.get(sourcePosition);
        items.remove(sourcePosition);
        items.add(0, item);
        notifyItemMoved(sourcePosition, 0);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void setItemCompleted(Command_Item item, Button commandButton, int position){
        Drawable completed_drawable = context.getDrawable(R.drawable.rounded_completed_command_button_background);

        item.setCompleted(true);
        commandButton.setBackground(completed_drawable);
        commandButton.setPaintFlags(commandButton.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        moveItemToEnd(position);
    }

    public void setItemIncompleted(Command_Item item, Button commandButton, int position){
        Drawable incompleted_drawable = context.getDrawable(R.drawable.rounded_command_button_background);

        item.setCompleted(false);
        commandButton.setBackground(incompleted_drawable);
        commandButton.setPaintFlags(commandButton.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        moveItemToFront(position);
    }
}
