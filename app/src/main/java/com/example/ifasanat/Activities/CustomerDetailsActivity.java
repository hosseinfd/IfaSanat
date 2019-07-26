package com.example.ifasanat.Activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ifasanat.Adapter.CustomerDetailsAdapter;
import com.example.ifasanat.ApiService.ApiService;
import com.example.ifasanat.DataModel.CustomerDetails;
import com.example.ifasanat.DataModel.CustomerDetailsDataModel;
import com.example.ifasanat.Interface.IDataExtractor;
import com.example.ifasanat.R;
import com.example.ifasanat.VariableKeys.VariableKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

public class CustomerDetailsActivity extends AppCompatActivity {
    private static final String TAG = "CustomerDetailsActivity";
    View view;
    RecyclerView recyclerView;
    Button buttonAddWell;
    CardView cardView;
    Typeface typeface;
    TextView toolbarText,textViewCustomerName,textViewCustomerAddress , textViewNotFound;
    ImageView backwardImage,imageNotFound;
    LinearLayoutManager linearLayoutManager;
    ApiService apiService;
    CustomerDetailsAdapter adapter;

    String customerId,customerAddress,customerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumer_details);

        customerName = getIntent().getStringExtra(VariableKeys.CustomerName);
        customerAddress = getIntent().getStringExtra(VariableKeys.CustomerAddress);
        customerId =  getIntent().getStringExtra(VariableKeys.CustomerId);


        if (customerAddress!= null){
            if (customerAddress.trim().equals("")){
                customerAddress = "موردی ثبت نشده است";
            }
        }


        init();

        getDataFromServer(customerId);
        setUpRecyclerView();


        buttonAddWell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWellActivity.start(CustomerDetailsActivity.this,null,customerId);
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCustomerActivity.start(CustomerDetailsActivity.this,customerId);
            }
        });
    }

    private GradientDrawable randomViewColor(){
        Random r = new Random();
        int red=r.nextInt(255 - 0 + 1)+0;
        int green=r.nextInt(255 - 0 + 1)+0;
        int blue=r.nextInt(255 - 0 + 1)+0;

        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.RECTANGLE);
        draw.setColor(Color.rgb(red,green,blue));

        return draw;
    }

    private void init(){
        typeface = Typeface.createFromAsset(getAssets(),"iransanslight.ttf");
        view = findViewById(R.id.recycler_view_view_color);
        recyclerView = findViewById(R.id.recyclerView);
        buttonAddWell = findViewById(R.id.button_add_well);
        toolbarText = findViewById(R.id.toolbar_text_view);
        textViewCustomerName = findViewById(R.id.recycler_view_text_view_customer_name);
        textViewCustomerAddress = findViewById(R.id.recycler_view_text_view_customer_address);
        backwardImage = findViewById(R.id.toolbar_backward_icon);
        cardView = findViewById(R.id.card_view);

        textViewNotFound = findViewById(R.id.text_view_not_found);
        imageNotFound = findViewById(R.id.image_view_not_found);

        textViewCustomerName.setText(customerName);
        textViewCustomerAddress.setText(customerAddress);
        view.setBackground(randomViewColor());

        textViewNotFound.setTypeface(typeface);
        toolbarText.setTypeface(typeface);
        toolbarText.setText(R.string.customers_details);
        backwardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        buttonAddWell.setTypeface(typeface);

        apiService = new ApiService(CustomerDetailsActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        apiService.getCustomerDetails(customerId, new ApiService.IgetCustomerDetails() {
            @Override
            public void getCustomer(CustomerDetails customerDetails) {
                textViewCustomerName.setText(String.format("%s %s",customerDetails.getFirstName(),customerDetails.getLastName()));
                JSONArray jsonArray;
                String address =  null;
                try {
                    jsonArray = new JSONArray(customerDetails.getAddress());
                    if (jsonArray.length() == 0) {

                    } else {
                        JSONObject json = jsonArray.getJSONObject(0);
                        address = json.getString("Address");
                        textViewCustomerAddress.setText(address);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getDataFromServer(String customerId){

        apiService.getCustomerWellsList( customerId );
        apiService.DataCustomerWellExtractor(new IDataExtractor<List<CustomerDetailsDataModel>>() {
            @Override
            public void ExtractData(List<CustomerDetailsDataModel> customerDetailsDataModels) {
                adapter.addData(customerDetailsDataModels);

                if (adapter.getItemCount() > 0) {
                    //check for adapter items bigger than zero and send customer name to AddInstallActivity
                    imageNotFound.setVisibility(View.GONE);
                    textViewNotFound.setVisibility(View.GONE);
                    adapter.customerName(customerName);
                } else {
                    imageNotFound.setVisibility(View.VISIBLE);
                    textViewNotFound.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setUpRecyclerView(){

        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new CustomerDetailsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);



    }

}
