package com.example.masterarbeit_weihele;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CommandItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final CommandAdapter adapter;

    public CommandItemTouchHelperCallback(CommandAdapter adapter) {
        this.adapter = adapter;
    }


    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return 0;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // Hole die Position des geschwippten Items
        int swipedPosition = viewHolder.getAdapterPosition();
        System.out.println("Swipe Action");
        // Entferne das Item aus der Liste
        adapter.removeItem(swipedPosition);
    }

}
