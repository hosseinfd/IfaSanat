package com.example.ifasanat.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ifasanat.Adapter.CustomerAdapter;
import com.example.ifasanat.Adapter.InstallItemAdapter;
import com.example.ifasanat.ApiService.ApiService;
import com.example.ifasanat.DataModel.CustomerDateModel;
import com.example.ifasanat.DataModel.ItemInstallDataModel;
import com.example.ifasanat.Interface.IDataExtractor;
import com.example.ifasanat.R;
import com.example.ifasanat.VariableKeys.VariableKeys;

import java.util.List;
import java.util.Objects;

import ss.com.infinitescrollprovider.InfiniteScrollProvider;
import ss.com.infinitescrollprovider.OnLoadMoreListener;

public class SearchActivity extends AppCompatActivity {

    ProgressBar prgressBar;
    Toolbar toolbar;
    ImageView imageBack, imageNotFound;
    TextView toolbarText, textViewNotFound;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    InstallItemAdapter adapter;
    CustomerAdapter costumerAdapter;
    ApiService apiService;
    int page = 0;
    String query = "";
    private static String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        query = Objects.requireNonNull(getIntent().getExtras()).getString(VariableKeys.SearchKey);
        activity = Objects.requireNonNull(getIntent().getExtras()).getString(VariableKeys.FromActivity);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "iransanslight.ttf");
        init(typeface);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupRecyclerView();

        getDataFromServer();

    }

    private void init(Typeface typeface) {

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        toolbarText = findViewById(R.id.toolbar_text_view);
        prgressBar = findViewById(R.id.progress_bar);
        toolbarText.setTypeface(typeface);
        toolbarText.setText(R.string.fastSearch);
        imageBack = findViewById(R.id.toolbar_backward_icon);

        textViewNotFound = findViewById(R.id.text_view_not_found);
        imageNotFound = findViewById(R.id.image_view_not_found);

        apiService = new ApiService(SearchActivity.this);


    }

    private void getDataFromServer() {

        if (activity.equals(VariableKeys.CostumerActivity)) {
            apiService.GetCustomerList(String.valueOf(page), query);
            apiService.DataCustomerExtractor(new IDataExtractor<List<CustomerDateModel>>() {
                @Override
                public void ExtractData(List<CustomerDateModel> customerDateModels) {

                    prgressBar.setVisibility(View.VISIBLE);
                    costumerAdapter.AddData(customerDateModels);
                    prgressBar.setVisibility(View.GONE);

                    if (costumerAdapter.getItemCount() > 0) {
                        imageNotFound.setVisibility(View.GONE);
                        textViewNotFound.setVisibility(View.GONE);
                    } else {
                        imageNotFound.setVisibility(View.VISIBLE);
                        textViewNotFound.setVisibility(View.VISIBLE);
                    }



                }
            });
        } else if (activity.equals(VariableKeys.ItemInstallActivity)) {
            apiService.GetInstallationList(String.valueOf(page), query);
            apiService.DataExtractor(new IDataExtractor<List<ItemInstallDataModel>>() {

                @Override
                public void ExtractData(List<ItemInstallDataModel> list) {
                    prgressBar.setVisibility(View.VISIBLE);
                    adapter.addData(list);
                    prgressBar.setVisibility(View.GONE);

                    if (adapter.getItemCount() > 0) {
                        imageNotFound.setVisibility(View.GONE);
                        textViewNotFound.setVisibility(View.GONE);
                    } else {
                        imageNotFound.setVisibility(View.VISIBLE);
                        textViewNotFound.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

    }

    private void setupRecyclerView() {


        if (activity.equals(VariableKeys.CostumerActivity)) {

            costumerAdapter = new CustomerAdapter(SearchActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(costumerAdapter);


            InfiniteScrollProvider infiniteScrollProvider = new InfiniteScrollProvider();
            infiniteScrollProvider.attach(recyclerView, new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    page = page + 20;
                    apiService.GetCustomerList(String.valueOf(page), query);
                }
            });

        }

        if (activity.equals(VariableKeys.ItemInstallActivity)) {

            adapter = new InstallItemAdapter(this);
            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(linearLayoutManager);



            InfiniteScrollProvider infiniteScrollProvider = new InfiniteScrollProvider();
            infiniteScrollProvider.attach(recyclerView, new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {

                    page = page + 20;
                    apiService.GetInstallationList(String.valueOf(page), query);

                }
            });

        }


    }


    public static void start(Context context, String fromActivity, String query) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(VariableKeys.SearchKey, query);
        intent.putExtra(VariableKeys.FromActivity, fromActivity);
        context.startActivity(intent);
    }
}
