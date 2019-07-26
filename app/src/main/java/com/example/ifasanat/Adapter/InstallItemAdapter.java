package com.example.ifasanat.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.ifasanat.Activities.AddInstallWellActivity;
import com.example.ifasanat.DataModel.ItemInstallDataModel;
import com.example.ifasanat.Interface.IClickListener;
import com.example.ifasanat.R;
import com.example.ifasanat.VariableKeys.VariableKeys;

import java.util.ArrayList;
import java.util.List;

public class InstallItemAdapter extends RecyclerView.Adapter<InstallItemAdapter.InstallViewHolder> {


    private Context context;
    private List<ItemInstallDataModel> lists = new ArrayList<>();
    private int lastPosition = -1;
    IClickListener iClickListener;


    public InstallItemAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<ItemInstallDataModel> lists){
        this.lists.addAll(lists);
        notifyDataSetChanged();
    }



    @Override
    public void onViewDetachedFromWindow(@NonNull InstallViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @NonNull
    @Override
    public InstallViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_installed_items, viewGroup, false);
        Typeface typefaceight = Typeface.createFromAsset(context.getAssets(), "iransanslight.ttf");
        Typeface typefaceBold = Typeface.createFromAsset(context.getAssets(), "iransansbold.ttf");
        return new InstallViewHolder(view, typefaceight, typefaceBold);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull InstallViewHolder holder, int position) {
        final ItemInstallDataModel list = lists.get(position);

        if (String.valueOf(list.getId()).trim().equals("") || String.valueOf(list.getId()).trim().equals("null")){
            holder.textItemInstallNumber.setText("ناموجود");
        }else {
            holder.textItemInstallNumber.setText(String.valueOf(list.getId()));
        }

        if (String.valueOf(list.getKonturNumber()).trim().equals("") || String.valueOf(list.getKonturNumber()).trim().equals("null")){
            holder.textKontur.setText("ناموجود");
        }else {
            holder.textKontur.setText(String.valueOf(list.getKonturNumber()));
        }

        if (String.valueOf(list.getPhoneNumber()).trim().equals("") || String.valueOf(list.getPhoneNumber()).trim().equals("null")){
            holder.textPhoneNumber.setText("ناموجود");
        }else {
            holder.textPhoneNumber.setText(String.valueOf(list.getPhoneNumber()));
        }

        if (String.valueOf(list.getInstallTime()).trim().equals("") || String.valueOf(list.getInstallTime()).trim().equals("null")){
            holder.textInstallTime.setText("ناموجود");
        }else {
            holder.textInstallTime.setText(String.valueOf(list.getInstallTime()));
        }

        if (String.valueOf(list.getCostumerName()).trim().equals("") ||
                String.valueOf(list.getCostumerName()).equals("null") ||
                list.getAddress().trim().equals("null null")){
            holder.textOwner.setText("ناموجود");
        }else {
            holder.textOwner.setText(list.getCostumerName());
        }

        if (list.getAddress().trim().equals("") || list.getAddress().trim().equals("null null") || list.getAddress().trim().equals("null")){
            holder.textAddress.setText("ناموجود");
        }else {
            holder.textAddress.setText(list.getAddress());
        }

        switch (Integer.parseInt(list.getState())) {
            case 1:
                holder.textSituation.setText("تایید شده");
                holder.textSituation.setBackground(context.getDrawable(R.drawable.border_accepted));
                break;
            case 0:
                holder.textSituation.setText("در حال بررسی");
                holder.textSituation.setBackground(context.getDrawable(R.drawable.border_pending));
                break;
            case 2:
                holder.textSituation.setText("عدم تایید");
                holder.textSituation.setBackground(context.getDrawable(R.drawable.border_denied));
                break;
        }


        holder.buttonShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddInstallWellActivity.start(context,String.valueOf(list.getId()), VariableKeys.ItemInstallActivity,list.getAddress(),list.getCostumerName());

            }
        });



        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class InstallViewHolder extends RecyclerView.ViewHolder {
        TextView textItemInstallDefine, textItemInstallNumber, textKonturDefine, textKontur, textPhoneNumberDefine, textPhoneNumber;
        TextView textSituationDefine, textSituation, textInstallTimeDefine, textInstallTime, textOwnerDefine, textOwner, textAddresDefine, textAddress;
        Button buttonShowDetails;

        public InstallViewHolder(@NonNull View itemView, Typeface typeFaceLight, Typeface typeFaceBold) {
            super(itemView);
            textItemInstallDefine = itemView.findViewById(R.id.recycler_view_textView_install_number_define);
            textItemInstallNumber = itemView.findViewById(R.id.recycler_view_text_view_install_number);
            textKonturDefine = itemView.findViewById(R.id.recycler_view_textView_kontur_define);
            textKontur = itemView.findViewById(R.id.recycler_view_text_view_kontur);
            textPhoneNumberDefine = itemView.findViewById(R.id.recycler_view_text_view_phone_define);
            textPhoneNumber = itemView.findViewById(R.id.recycler_view_text_view_phone);
            textSituationDefine = itemView.findViewById(R.id.recycler_view_text_view_situation_define);
            textSituation = itemView.findViewById(R.id.recycler_view_text_view_situation);
            textInstallTimeDefine = itemView.findViewById(R.id.recycler_view_text_view_install_time_define);
            textInstallTime = itemView.findViewById(R.id.recycler_view_text_view_install_time);
            textOwnerDefine = itemView.findViewById(R.id.recycler_view_text_view_owner_define);
            textOwner = itemView.findViewById(R.id.recycler_view_text_view_owner);
            textAddresDefine = itemView.findViewById(R.id.recycler_view_text_view_address_define);
            textAddress = itemView.findViewById(R.id.recycler_view_text_view_address);

            buttonShowDetails = itemView.findViewById(R.id.recycler_view_button_see_details);


            textItemInstallDefine.setTypeface(typeFaceBold);
            textKonturDefine.setTypeface(typeFaceBold);
            textPhoneNumberDefine.setTypeface(typeFaceBold);
            textSituationDefine.setTypeface(typeFaceBold);
            textInstallTimeDefine.setTypeface(typeFaceBold);
            textOwnerDefine.setTypeface(typeFaceBold);
            textAddresDefine.setTypeface(typeFaceBold);


            textItemInstallNumber.setTypeface(typeFaceLight);
            textKontur.setTypeface(typeFaceLight);
            textPhoneNumber.setTypeface(typeFaceLight);
            textSituation.setTypeface(typeFaceLight);
            textInstallTime.setTypeface(typeFaceLight);
            textOwner.setTypeface(typeFaceLight);
            textAddress.setTypeface(typeFaceLight);

            buttonShowDetails.setTypeface(typeFaceLight);


        }
    }
}
