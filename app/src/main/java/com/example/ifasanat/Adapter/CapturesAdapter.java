package com.example.ifasanat.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ifasanat.Activities.ShowImageActivity;
import com.example.ifasanat.DataModel.CapturesAdapterModel;
import com.example.ifasanat.DataModel.CapturesDataModel;
import com.example.ifasanat.Interface.ITouchHelper;
import com.example.ifasanat.R;
import com.example.ifasanat.WizardFragments.WizardCaptureImageFragment;
import com.example.ifasanat.WizardFragments.WizardFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CapturesAdapter extends RecyclerView.Adapter<CapturesAdapter.CaptureViewHolder> implements ITouchHelper {

    private Context context;
    private List<CapturesAdapterModel> map = new ArrayList<>();
    private List<WizardFragment> wizardFragments;

    public CapturesAdapter(Context context) {
        this.context = context;
    }

    public void addImage(List<CapturesDataModel> lists) {
        if (lists != null) {
            for (CapturesDataModel item :
                    lists) {
                add(item);
            }
            notifyDataSetChanged();
        }
    }

    public void add(int pos, CapturesDataModel model) {
        if (model == null) return;
        map.add(new CapturesAdapterModel(pos, model));
        notifyDataSetChanged();
    }

    public void add(CapturesDataModel model) {
        map.add(new CapturesAdapterModel(model));
        notifyDataSetChanged();
    }

    public void clear() {
        int size = map.size();
        map.clear();
        notifyItemRangeRemoved(0, size);
    }

    @NonNull
    @Override
    public CaptureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_captures_item, viewGroup, false);
        return new CaptureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaptureViewHolder holder, @SuppressLint("RecyclerView") final int i) {
        final CapturesDataModel model = map.get(i).getModel();

        if (model != null && model.getImagePath() != null) {
            Glide.with(context)
                    .load(model.getImagePath())
                    .placeholder(R.drawable.logo)
                    .into(holder.imageView);
        }
        if (model == null && model.getImagePath() == null) {

        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                alertDialog(context, i);
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImageActivity.start(context, model.getImagePath());
            }
        });
    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(map, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(map, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {

        map.remove(position);
        notifyItemRemoved(position);
    }

    public void setWizardFragments(List<WizardFragment> wizardAdapter) {
        this.wizardFragments = wizardAdapter;
    }

    class CaptureViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public CaptureViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_capture);

        }
    }

    public void remove(int position) {

        if (map.get(position).getPos() == -1 ){
            Toast.makeText(context, "شما مجاز به انجام عملیات نمی باشید", Toast.LENGTH_SHORT).show();
        }else {
            if (wizardFragments != null && map.get(position).getPos() >= 0) {
                wizardFragments.get(map.get(position).getPos()).clear();
            }
            map.remove(position);
            notifyItemRemoved(position);
        }
    }

    private void alertDialog(Context context, final int position) {
        new AlertDialog.Builder(context)
                .setMessage(R.string.accept_delete_image)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove(position);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    public String getImageList(int position) {
        return map.get(position).getModel().getImagePath();
    }

    public String getImageAdded(int i){
        if ( map.get(i).getPos() == -2 ){
            return map.get(i).getModel().getImagePath();
        }else {
            return null;
        }
    }

}