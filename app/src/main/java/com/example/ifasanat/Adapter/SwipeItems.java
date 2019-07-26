package com.example.ifasanat.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.ifasanat.Interface.ITouchHelper;

public class SwipeItems extends ItemTouchHelper.SimpleCallback {

    private ITouchHelper iTouchHelper;

    public SwipeItems ( ITouchHelper iTouchHelper ) {
        super(ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT, ItemTouchHelper.UP|ItemTouchHelper.DOWN);
//        super(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        this.iTouchHelper = iTouchHelper;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        iTouchHelper.onItemMove(viewHolder.getAdapterPosition(), viewHolder1.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        iTouchHelper.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
