package com.example.ifasanat.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ifasanat.Adapter.CustomerAdapter;
import com.example.ifasanat.ApiService.ApiService;
import com.example.ifasanat.DataModel.CustomerDateModel;
import com.example.ifasanat.Interface.IDataExtractor;
import com.example.ifasanat.R;
import com.example.ifasanat.VariableKeys.VariableKeys;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import ss.com.infinitescrollprovider.InfiniteScrollProvider;
import ss.com.infinitescrollprovider.OnLoadMoreListener;

public class CustomersActivity extends AppCompatActivity {
    Button buttonAddCustomer;
    RecyclerView recyclerView;
    ImageView searchView;
    MaterialSearchView materialSearchView;
    Toolbar toolbar;
    TextView toolbarText;
    ProgressBar progressBar;
    ImageView backwardImage;
    ApiService apiService;
    CustomerAdapter adapter;
    private int page = 0;

    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()){
            materialSearchView.closeSearch();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumers);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "iransanslight.ttf");
        init(typeface);
        setSupportActionBar(toolbar);

        SetUpRecyclerView();
        GetCustomerListServer();

        backwardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddCustomerActivity.class));
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (materialSearchView.isSearchOpen()){
                    materialSearchView.closeSearch();
                }else {
                    materialSearchView.showSearch();
                }
            }
        });

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                SearchActivity.start(CustomersActivity.this, VariableKeys.CostumerActivity, query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void GetCustomerListServer() {

        apiService.GetCustomerList(String.valueOf(page), "");

        apiService.DataCustomerExtractor(new IDataExtractor<List<CustomerDateModel>>() {
            @Override
            public void ExtractData(List<CustomerDateModel> customerDateModels) {
                adapter.AddData(customerDateModels);

            }
        });

    }


    private void SetUpRecyclerView() {
        progressBar.setVisibility(View.VISIBLE);
        adapter = new CustomerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        progressBar.setVisibility(View.GONE);
        InfiniteScrollProvider infiniteScrollProvider = new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerView, new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page = page + 20;
                apiService.GetCustomerList(String.valueOf(page), "");
            }
        });

    }

    private void init(Typeface typeface) {
        buttonAddCustomer = findViewById(R.id.button_add_customer);
        recyclerView = findViewById(R.id.recyclerView);
        materialSearchView = findViewById(R.id.search_view);
        searchView = findViewById(R.id.image_view_search);
        toolbar = findViewById(R.id.toolbar);
        toolbarText = findViewById(R.id.toolbar_text_view);
        backwardImage = findViewById(R.id.toolbar_backward_icon);
        progressBar = findViewById(R.id.progress_bar);

        buttonAddCustomer.setTypeface(typeface);
        toolbarText.setTypeface(typeface);
        toolbarText.setText(R.string.customers);

        apiService = new ApiService(CustomersActivity.this);

    }


}
