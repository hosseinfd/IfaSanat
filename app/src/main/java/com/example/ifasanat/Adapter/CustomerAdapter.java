package com.example.ifasanat.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.ifasanat.Activities.CustomerDetailsActivity;
import com.example.ifasanat.DataModel.CustomerDateModel;
import com.example.ifasanat.R;
import com.example.ifasanat.VariableKeys.VariableKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    Context context;
    List<CustomerDateModel> lists = new ArrayList<>();

    private int lastPosition = 0;

    public CustomerAdapter(Context context) {
        this.context = context;
    }

    public void AddData(List<CustomerDateModel> lists){
        this.lists .addAll(lists);
        notifyDataSetChanged();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull CustomerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_costumer, viewGroup, false);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "iransanslight.ttf");

        return new CustomerViewHolder(view, typeface);
    }





    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int i) {
        final CustomerDateModel list = lists.get(i);

        if ( list.getCustomerName().trim().equals("") ) {
            holder.textCustomerName.setText(list.getCompanyName());
        } else {
            holder.textCustomerName.setText(list.getCustomerName());
        }


        JSONArray jsonArray;
        String address = null;
        try {
            jsonArray = new JSONArray(list.getCustomerAddress());
            if (jsonArray.length()==0){
                holder.textCustomerAddress.setText("آدرسی ثبت نشده است");
            }else {
                JSONObject json = jsonArray.getJSONObject(0);
                address = json.getString("Address");
                if (address.trim().equals("")){
                    holder.textCustomerAddress.setText("آدرسی ثبت نشده است");
                }else {

                    holder.textCustomerAddress.setText(address);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Random r = new Random();
        int red = r.nextInt(255 - 0 + 1) + 0;
        int green = r.nextInt(255 - 0 + 1) + 0;
        int blue = r.nextInt(255 - 0 + 1) + 0;

        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.RECTANGLE);
        draw.setColor(Color.rgb(red, green, blue));
        holder.view.setBackground(draw);


        final String finalAddress = address;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CustomerDetailsActivity.class);
                if (list.getCustomerName().trim().equals("")){

                    intent.putExtra(VariableKeys.CustomerName,list.getCompanyName());
                }else {
                    intent.putExtra(VariableKeys.CustomerName,list.getCustomerName());
                }



                intent.putExtra(VariableKeys.CustomerAddress, finalAddress);
                intent.putExtra(VariableKeys.CustomerId,String.valueOf(list.getId()));
                context.startActivity(intent);
            }
        });
        if (i != lists.size()) {
            Animation animation = AnimationUtils.loadAnimation(context,

                    (i > lastPosition) ? R.anim.up_from_bottom
                            : R.anim.down_from_top);
            holder.itemView.startAnimation(animation);
            lastPosition = i;
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textCustomerName, textCustomerAddress;

        public CustomerViewHolder(@NonNull View itemView, Typeface typeface) {
            super(itemView);
            view = itemView.findViewById(R.id.recycler_view_view_color);
            textCustomerName = itemView.findViewById(R.id.recycler_view_text_view_customer_name);
            textCustomerAddress = itemView.findViewById(R.id.recycler_view_text_view_customer_address);

            textCustomerName.setTypeface(typeface);
            textCustomerAddress.setTypeface(typeface);
        }
    }
}
