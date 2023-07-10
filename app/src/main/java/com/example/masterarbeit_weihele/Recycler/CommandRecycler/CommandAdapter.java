package com.example.masterarbeit_weihele.Recycler.CommandRecycler;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterarbeit_weihele.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandAdapter extends RecyclerView.Adapter<CommandViewHolder> {

    Context context;
    List<Command_Item> items;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;

    public CommandAdapter(Context context, List<Command_Item> items, RecyclerView recyclerView) {
        this.context = context;
        this.items = items;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public CommandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommandViewHolder(LayoutInflater.from(context).inflate(R.layout.command_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommandViewHolder holder, int position) {
        Command_Item command_item = items.get(position);
        Drawable unfocused_drawable = context.getDrawable(R.drawable.rounded_command_button_background);

        holder.commandView.setText(command_item.getCommand_name());

        if(command_item.isCompleted){
            setItemCompleted(command_item, holder.commandView);
        } else if(command_item.isFocused){
            setItemFocused(command_item, holder.commandView);
        } else {
            setItemIncompleted(command_item, holder.commandView);
        }

        holder.commandView.setOnLongClickListener(view -> {
            if(command_item.getIsFocused()){
                command_item.setIsFocused(false);
                holder.commandView.setBackground(unfocused_drawable);
            } else {
                if(!command_item.isCompleted){
                    Command_Item current_item;
                    for(int i = 0; i<items.size(); i++){
                        current_item = items.get(i);
                        current_item.setIsFocused(false);
                        if(!current_item.isCompleted){
                            CommandViewHolder current_holder = (CommandViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                            if (current_holder != null) {
                                current_holder.commandView.setBackground(unfocused_drawable);
                            }
                        }
                    }
                    setItemFocused(command_item, holder.commandView);
                    moveItemToFront(holder.getBindingAdapterPosition());
                }
            }
            return true;
        });

        holder.commandView.setOnClickListener(view -> {
            if(command_item.isFocused){
                setItemIncompleted(command_item, holder.commandView);
                command_item.setIsFocused(false);
                moveItemToFront(holder.getBindingAdapterPosition());
            } else {
                if (!command_item.isCompleted) {
                    setItemCompleted(command_item, holder.commandView);
                    moveItemToEnd(holder.getBindingAdapterPosition());
                } else {
                    setItemIncompleted(command_item, holder.commandView);
                    moveItemToFront(holder.getBindingAdapterPosition());
                }
            }
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
        boolean isFirstItemFocused = items.get(0).isFocused;
        items.remove(sourcePosition);

        if (isFirstItemFocused && sourcePosition != 0) {
            items.add(1, item);
            notifyItemMoved(sourcePosition, 1);

        } else {
            items.add(0, item);
            notifyItemMoved(sourcePosition, 0);
        }

    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void setItemCompleted(Command_Item item, Button commandButton){
        Drawable completed_drawable = context.getDrawable(R.drawable.rounded_completed_command_button_background);
        item.setCompleted(true);
        commandButton.setBackground(completed_drawable);
        commandButton.setPaintFlags(commandButton.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void setItemFocused(Command_Item item, Button commandButton){
        Drawable focused_drawable = context.getDrawable(R.drawable.rounded_button_background);
        item.setIsFocused(true);
        commandButton.setBackground(focused_drawable);
    }

    public void setItemIncompleted(Command_Item item, Button commandButton){
        Drawable incompleted_drawable = context.getDrawable(R.drawable.rounded_command_button_background);

        item.setCompleted(false);
        commandButton.setBackground(incompleted_drawable);
        commandButton.setPaintFlags(commandButton.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }

    public void saveItems(){
        sharedPreferences = context.getSharedPreferences("Commands", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(int i = 0; i<items.size(); i++){
            editor.putBoolean("Item_isFocused_" + i, items.get(i).isFocused);
            editor.putBoolean("Item_isCompleted_" + i, items.get(i).isCompleted);
            editor.putString("Item_Value_" + i, items.get(i).getCommand_name());
        }
        editor.apply();
    }
}
