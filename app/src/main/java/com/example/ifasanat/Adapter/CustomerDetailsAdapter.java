package com.example.ifasanat.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ifasanat.Activities.AddWellActivity;
import com.example.ifasanat.Activities.AddInstallWellActivity;
import com.example.ifasanat.DataModel.CustomerDetailsDataModel;
import com.example.ifasanat.R;
import com.example.ifasanat.VariableKeys.VariableKeys;

import java.util.ArrayList;
import java.util.List;

public class CustomerDetailsAdapter extends RecyclerView.Adapter<CustomerDetailsAdapter.CustomerDetailsViewHolder> {

    Context context;
    List<CustomerDetailsDataModel> lists = new ArrayList<>();
    String customerName;

    public CustomerDetailsAdapter(Context context) {
        this.context = context;

    }

    public void addData(List<CustomerDetailsDataModel> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public void customerName(String customerName) {
        this.customerName = customerName;
    }

    @NonNull
    @Override
    public CustomerDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_costumer_details_items, viewGroup, false);
        Typeface typefaceight = Typeface.createFromAsset(context.getAssets(), "iransanslight.ttf");
        Typeface typefaceBold = Typeface.createFromAsset(context.getAssets(), "iransansbold.ttf");

        return new CustomerDetailsViewHolder(view, typefaceight, typefaceBold);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomerDetailsViewHolder holder, final int poition) {
        final CustomerDetailsDataModel list = lists.get(poition);


        if (list.getTwelveCode().equals("")) {
            holder.textTwelveCode.setText("ندارد");
        } else {
            holder.textTwelveCode.setText(list.getTwelveCode());
        }

        holder.textUtmx.setText(list.getUtmx());
        holder.textUtmy.setText(list.getUtmy());

        holder.textOstan.setText(list.getProvince());
        holder.textCity.setText(list.getCity());
        holder.textVillage.setText(list.getVillage());
        holder.textCityPart.setText(list.getCity());

        holder.buttonSeeDatails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddWellActivity.start(context, String.valueOf(list.getId()), null);

            }
        });
        holder.buttonAddIstallitionForWell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String province,city,village,state;
                province = list.getProvince().equals("ناموجود") ? "" : list.getProvince();
                city = list.getCity().equals("ناموجود") ? "" : list.getCity();
                village = list.getVillage().equals("ناموجود") ? "" : list.getVillage();
                state = list.getState().equals("ناموجود") ? "" : list.getState();

                @SuppressLint({"NewApi", "LocalSuppress"}) String address = (province + city + village +  state).isEmpty()? "ناموجود" : province + city + village +  state ;
                AddInstallWellActivity.start(context, String.valueOf(list.getId()), VariableKeys.CustomerDetailsActivity, address, customerName);

            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class CustomerDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView textUtmx, textUtmy, textTwelveCode, textOstan, textCity, textVillage, textCityPart;
        TextView textUtmxDefine, textUtmyDefine, textTwelveCodeDefine, textOstanDefine, textCityDefine, textVillageDefine, textCityPartDefine;
        Button buttonSeeDatails, buttonAddIstallitionForWell;

        public CustomerDetailsViewHolder(@NonNull View itemView, Typeface typefaceight, Typeface typefaceBold) {
            super(itemView);
            textUtmx = itemView.findViewById(R.id.recycler_view_text_view_utmx);
            textUtmy = itemView.findViewById(R.id.recycler_view_text_view_utmy);
            textTwelveCode = itemView.findViewById(R.id.recycler_view_text_view_twelve_code);
            textOstan = itemView.findViewById(R.id.recycler_view_text_view_ostan);
            textCity = itemView.findViewById(R.id.recycler_view_text_view_city);
            textVillage = itemView.findViewById(R.id.recycler_view_text_view_village);
            textCityPart = itemView.findViewById(R.id.recycler_view_text_view_city_part);


            textUtmxDefine = itemView.findViewById(R.id.recycler_view_text_view_utmx_define);
            textUtmyDefine = itemView.findViewById(R.id.recycler_view_textView_utmy_define);
            textTwelveCodeDefine = itemView.findViewById(R.id.recycler_view_text_view_twelve_code_define);
            textOstanDefine = itemView.findViewById(R.id.recycler_view_text_view_ostan_define);
            textCityDefine = itemView.findViewById(R.id.recycler_view_text_view_city_define);
            textVillageDefine = itemView.findViewById(R.id.recycler_view_text_view_village_define);
            textCityPartDefine = itemView.findViewById(R.id.recycler_view_text_view_city_part_define);

            buttonSeeDatails = itemView.findViewById(R.id.recycler_view_button_see_details);
            buttonAddIstallitionForWell = itemView.findViewById(R.id.recycler_view_button_add_install_well);

            textUtmx.setTypeface(typefaceight);
            textUtmy.setTypeface(typefaceight);
            textTwelveCode.setTypeface(typefaceight);
            textOstan.setTypeface(typefaceight);
            textCity.setTypeface(typefaceight);
            textVillage.setTypeface(typefaceight);
            textCityPart.setTypeface(typefaceight);


            textUtmxDefine.setTypeface(typefaceBold);
            textUtmyDefine.setTypeface(typefaceBold);
            textTwelveCodeDefine.setTypeface(typefaceBold);
            textOstanDefine.setTypeface(typefaceBold);
            textCityDefine.setTypeface(typefaceBold);
            textVillageDefine.setTypeface(typefaceBold);
            textCityPartDefine.setTypeface(typefaceBold);

            buttonSeeDatails.setTypeface(typefaceight);
            buttonAddIstallitionForWell.setTypeface(typefaceight);


        }
    }
}
