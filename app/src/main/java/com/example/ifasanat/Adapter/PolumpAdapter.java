package com.example.ifasanat.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ifasanat.R;

import java.util.ArrayList;
import java.util.List;

public class PolumpAdapter extends RecyclerView.Adapter<PolumpAdapter.PolumpViewHolder> {

    private Context context;
    private List<String> list = new ArrayList<>();

    public PolumpAdapter(Context context) {
        this.context = context;
    }

    public void addData(String text){
        this.list.add(text);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PolumpViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_polump_number,viewGroup,false);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "iransanslight.ttf");
        return new PolumpViewHolder(view,typeface);
    }

    @Override
    public void onBindViewHolder(@NonNull PolumpViewHolder holder, final int i) {
        if (!list.get(i).equals("")){
            holder.tv.setText(list.get(i));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(context,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PolumpViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView im;
        public PolumpViewHolder(@NonNull View itemView, Typeface typeface) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_recycler_view_polump);
            im = itemView.findViewById(R.id.iv_recycler_view_polump);

            tv.setTypeface(typeface);
        }
    }

    private void alertDialog(Context context,final int position) {
        new AlertDialog.Builder(context)
                .setMessage(R.string.accept_delete_plump)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        notifyItemRemoved(position);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .show();

    }
}
