package com.example.ifasanat.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ifasanat.Adapter.InstallItemAdapter;
import com.example.ifasanat.ApiService.ApiService;
import com.example.ifasanat.DataModel.ItemInstallDataModel;
import com.example.ifasanat.Interface.IDataExtractor;
import com.example.ifasanat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ss.com.infinitescrollprovider.InfiniteScrollProvider;
import ss.com.infinitescrollprovider.OnLoadMoreListener;

public class FilterActivity extends AppCompatActivity {
    Toolbar toolbar;
    CoordinatorLayout coordinatorLayout;
    TextView toolbarText, textSituation;
    TextInputLayout textKonturSerial, textWellDetail, textPhoneDetail, textInstallerName, textProjectName;
    TextView textCheckBoxDenied, textCheckBoxPending, textCheckboxAccepted;
    ImageView backwardImage;
    TextInputEditText editKonturSerial, editWellDetail, editPhoneDetail, editInstallerName, editProjectName;
    Button buttonFilter;
    CheckBox checkBoxAccepted, checkBoxPending, checkBoxDenied;
    ProgressBar progressBar, progressBarBt;
    ApiService apiService;

    RecyclerView recyclerView;
    InstallItemAdapter adapter;

    LinearLayoutManager linearLayoutManager;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "iransanslight.ttf");
        init(typeface);
        setSupportActionBar(toolbar);


        backwardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterActivity.this.onBackPressed();
            }
        });

        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                getDataFromServer();
                setupRecyclerView();
            }
        });

    }

    private void init(Typeface typeface) {
        toolbar = findViewById(R.id.toolbar);
        toolbarText = findViewById(R.id.toolbar_text_view);
        backwardImage = findViewById(R.id.toolbar_backward_icon);
        coordinatorLayout = findViewById(R.id.coordinator);
        textKonturSerial = findViewById(R.id.text_view_meter_serial);
        textWellDetail = findViewById(R.id.text_view_well_detail);
        textPhoneDetail = findViewById(R.id.text_view_phone_detail);
        textInstallerName = findViewById(R.id.text_view_installer_name);
        textProjectName = findViewById(R.id.text_view_project);
        textSituation = findViewById(R.id.text_view_situation);

        textCheckBoxDenied = findViewById(R.id.text_view_checkbox_denied);
        textCheckBoxPending = findViewById(R.id.text_view_checkbox_pending);
        textCheckboxAccepted = findViewById(R.id.text_view_checkbox_accepted);
        checkBoxAccepted = findViewById(R.id.checkbox_accepted);
        checkBoxPending = findViewById(R.id.checkbox_pending);
        checkBoxDenied = findViewById(R.id.checkbox_denied);

        editKonturSerial = findViewById(R.id.edit_text_meter_serial);
        editWellDetail = findViewById(R.id.edit_text_well_detail);
        editPhoneDetail = findViewById(R.id.edit_text_phone_detail);
        editInstallerName = findViewById(R.id.edit_text_installer_name);
        editProjectName = findViewById(R.id.edit_text_project_name);


        buttonFilter = findViewById(R.id.button_filter);

        progressBar = findViewById(R.id.progress_bar);
        progressBarBt = findViewById(R.id.progress_bar_bt);


        toolbarText.setText(R.string.search_customer);

        toolbarText.setTypeface(typeface);
        textKonturSerial.setTypeface(typeface);
        textWellDetail.setTypeface(typeface);
        textPhoneDetail.setTypeface(typeface);
        textInstallerName.setTypeface(typeface);
        textProjectName.setTypeface(typeface);
        textSituation.setTypeface(typeface);

        textCheckBoxDenied.setTypeface(typeface);
        textCheckBoxPending.setTypeface(typeface);
        textCheckboxAccepted.setTypeface(typeface);


        recyclerView = findViewById(R.id.recyclerView);

//        editKonturSerial.setTypeface(typeface);
//        editWellDetail.setTypeface(typeface);
//        editPhoneDetail.setTypeface(typeface);
//        editInstallerName.setTypeface(typeface);
//        editProjectName.setTypeface(typeface);
//        buttonFilter.setTypeface(typeface);


        apiService = new ApiService(FilterActivity.this);


    }

    private void getDataFromServer() {

        apiService.SetFilter(String.valueOf(page), setFilter());
        apiService.DataExtractor(new IDataExtractor<List<ItemInstallDataModel>>() {

            @Override
            public void ExtractData(List<ItemInstallDataModel> list) {
                if (list.size() > 0) {
                    hideItems();
                    adapter.addData(list);
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (adapter.getItemCount() > 0) {

                    } else {
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(FilterActivity.this, R.string.not_found, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    private void hideItems() {

        textKonturSerial.setVisibility(View.GONE);
        textWellDetail.setVisibility(View.GONE);
        textPhoneDetail.setVisibility(View.GONE);
        textInstallerName.setVisibility(View.GONE);
        textProjectName.setVisibility(View.GONE);
        textSituation.setVisibility(View.GONE);

        textCheckBoxDenied.setVisibility(View.GONE);
        textCheckBoxPending.setVisibility(View.GONE);
        textCheckboxAccepted.setVisibility(View.GONE);
        checkBoxAccepted.setVisibility(View.GONE);
        checkBoxPending.setVisibility(View.GONE);
        checkBoxDenied.setVisibility(View.GONE);

        editKonturSerial.setVisibility(View.GONE);
        editWellDetail.setVisibility(View.GONE);
        editPhoneDetail.setVisibility(View.GONE);
        editInstallerName.setVisibility(View.GONE);
        editProjectName.setVisibility(View.GONE);


        buttonFilter.setVisibility(View.GONE);


        progressBar.setVisibility(View.GONE);
        progressBarBt.setVisibility(View.GONE);

        recyclerView.setVisibility(View.VISIBLE);


    }


    private void setupRecyclerView() {

        //set Recycler View
        adapter = new InstallItemAdapter(this);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        InfiniteScrollProvider infiniteScrollProvider = new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerView, new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                progressBarBt.setVisibility(View.VISIBLE);
                page = page + 20;
                apiService.SetFilter(String.valueOf(page), setFilter());
                progressBarBt.setVisibility(View.GONE);
            }
        });


    }


    private JSONObject setFilter() {


        JSONObject jsonObject = new JSONObject();
        try {

            JSONArray jsonArray = new JSONArray();
            jsonObject.put("SnNumber", String.valueOf(editKonturSerial.getText().toString()));
            jsonObject.put("WellDetails", editWellDetail.getText().toString());
            jsonObject.put("PhoneNumber", String.valueOf(editPhoneDetail.getText().toString()));
            jsonObject.put("InstallerName", editInstallerName.getText().toString());
            jsonObject.put("ProjectName", editProjectName.getText().toString());

            if (checkBoxAccepted.isChecked()) {
                jsonArray.put(1);

            }
            if (checkBoxPending.isChecked()) {
                jsonArray.put(0);

            }
            if (checkBoxDenied.isChecked()) {
                jsonArray.put(2);

            }
            if (jsonArray.length() == 0) {
                jsonArray.put(1);
            }

            jsonObject.put("States", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject;


    }

}
