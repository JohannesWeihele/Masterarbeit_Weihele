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
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN; // Erlaubt das Ziehen nach oben und unten
        int swipeFlags = 0; // Deaktiviert das Swipen
        return makeMovementFlags(dragFlags, swipeFlags);
    }
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // Hole die Position des geschwippten Items
        int swipedPosition = viewHolder.getAdapterPosition();

        // Entferne das Item aus der Liste
        adapter.removeItem(swipedPosition);
    }

}
