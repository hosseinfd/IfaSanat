package com.example.ifasanat.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ifasanat.ApiService.ApiService;
import com.example.ifasanat.DataModel.CustomerDetails;
import com.example.ifasanat.Dialog.NumberPickerFragment;
import com.example.ifasanat.Interface.IDataExtractor;
import com.example.ifasanat.R;
import com.example.ifasanat.VariableKeys.VariableKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddCustomerActivity extends AppCompatActivity implements NumberPickerFragment.OnCompleteListener {
    RecyclerView recyclerView;
    Button buttonSubmitCustomer;

    TextView toolbarText, textCompanyInformation, textUserInformation;
    ImageView backwardImage;
    CoordinatorLayout coordinatorLayout;
    TextInputLayout
            FirstNameLay,
            LastNameLay,
            CompanyNameLay,
            FathersNameLay,
            AddressLay,
            PostCodeLay,
            FaxLay,
            BirthDateLay,
            PhoneLay,
            PhoneNumberLay,
            CompanyNationalNumberLay,
            CompanyRegisterNumberLay,
            CompanyEconomicalNumberLay,
            NationalNumberLay,
            ShenasnameNumberLay,
            RegisteredAtLay;


    TextInputEditText FirstName,
            LastName,
            CompanyName,
            FathersName,
            Address,
            PostCode,
            Fax,
            BirthDate,
            Phone,
            PhoneNumber,
            CompanyNationalNumber,
            CompanyRegisterNumber,
            CompanyEconomicalNumber,
            NationalNumber,
            ShenasnameNumber,
            RegisteredAt;
    ApiService apiService;
    String customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_costumer);
        apiService = new ApiService(AddCustomerActivity.this);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "iransanslight.ttf");
        init(typeface);


        customerId = getIntent().getStringExtra(VariableKeys.CustomerId);
        if (customerId != null) {
            buttonSubmitCustomer.setText(R.string.save_change);
            toolbarText.setText(R.string.customers_details_change);
            apiService.getCustomerDetails(customerId, new ApiService.IgetCustomerDetails() {
                @Override
                public void getCustomer(CustomerDetails customerDetails) {
                    setCustomerData(customerDetails);
                }
            });


        } else {
            buttonSubmitCustomer.setText(R.string.save);

        }

        backwardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonSubmitCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customerId != null) {
                    apiService.editCustomer(customerId, JsonCreator());
                    apiService.getMessage(new IDataExtractor<String>() {
                        @Override
                        public void ExtractData(String s) {
                            if (s.equals("done")) {

                                Toast.makeText(AddCustomerActivity.this, R.string.changed_saved, Toast.LENGTH_SHORT).show();
                                AddCustomerActivity.this.finish();
                            }
                        }
                    });

                } else {
                    if (JsonCreator() != null){
                        apiService.addCustomer(JsonCreator());
                        apiService.getMessage(new IDataExtractor<String>() {
                            @Override
                            public void ExtractData(String s) {
                                if (s.equals("done")) {

                                    Toast.makeText(AddCustomerActivity.this, R.string.add_customer_done, Toast.LENGTH_SHORT).show();
                                    AddCustomerActivity.this.finish();
                                }
                            }
                        });
                    }
                }

            }
        });

        BirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPickerFragment fragment = new NumberPickerFragment();
                fragment.show(getSupportFragmentManager(), "TAG");
            }
        });

    }

    private JSONObject JsonCreator() {

        if (FirstName.getText().toString().trim().equals("") || LastName.getText().toString().trim().equals("")) {
            Toast.makeText(this, R.string.fillParam, Toast.LENGTH_SHORT).show();
            return null;
        } else {

            JSONObject json = new JSONObject();
            try {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Address", Address.getText().toString());
                jsonObject.put("PostalCode", PostCode.getText().toString());
                jsonObject.put("Telephone", Phone.getText().toString());
                jsonObject.put("FAXNumber", Fax.getText().toString());
                jsonObject.put("MobilePhoneNumber", PhoneNumber.getText().toString());
                jsonArray.put(jsonObject);


                json.put("firstName", FirstName.getText().toString());
                json.put("lastName", LastName.getText().toString());
                json.put("companyName", CompanyName.getText().toString());
                json.put("fathersName", FathersName.getText().toString());
                json.put("birthday", BirthDate.getText().toString());
                json.put("companyNationalNumber", CompanyNationalNumber.getText().toString());
                json.put("companyRegisterNumber", CompanyRegisterNumber.getText().toString());
                json.put("companyEconomicalNumber", CompanyEconomicalNumber.getText().toString());
                json.put("nationalNumber", NationalNumber.getText().toString());
                json.put("shenasnameNumber", ShenasnameNumber.getText().toString());
                json.put("registeredAt", RegisteredAt.getText().toString());
                json.put("addresses", jsonArray.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

    }

    private void init(Typeface typeface) {
        coordinatorLayout = findViewById(R.id.coordinator);
        buttonSubmitCustomer = findViewById(R.id.button_submit_costumer);
        recyclerView = findViewById(R.id.recyclerView);
        toolbarText = findViewById(R.id.toolbar_text_view);
        backwardImage = findViewById(R.id.toolbar_backward_icon);


        buttonSubmitCustomer.setTypeface(typeface);
        toolbarText.setTypeface(typeface);
        toolbarText.setText(R.string.add_customer);

        textCompanyInformation = findViewById(R.id.textCompanyInformation);
        textUserInformation = findViewById(R.id.textUserInformation);

        textUserInformation.setTypeface(typeface);
        textCompanyInformation.setTypeface(typeface);


        FirstNameLay = findViewById(R.id.FirstNameLay);
        LastNameLay = findViewById(R.id.LastNameLay);
        CompanyNameLay = findViewById(R.id.CompanyNameLay);
        FathersNameLay = findViewById(R.id.FathersNameLay);
        AddressLay = findViewById(R.id.AddressLay);
        PostCodeLay = findViewById(R.id.PostCodeLay);
        FaxLay = findViewById(R.id.FaxLay);
        BirthDateLay = findViewById(R.id.BirthDateLay);
        PhoneLay = findViewById(R.id.PhoneLay);
        PhoneNumberLay = findViewById(R.id.PhoneNumberLay);

        CompanyNationalNumberLay = findViewById(R.id.CompanyNationalNumberLay);
        CompanyRegisterNumberLay = findViewById(R.id.CompanyRegisterNumberLay);
        CompanyEconomicalNumberLay = findViewById(R.id.CompanyEconomicalNumberLay);
        NationalNumberLay = findViewById(R.id.NationalNumberLay);
        ShenasnameNumberLay = findViewById(R.id.ShenasnameNumberLay);
        RegisteredAtLay = findViewById(R.id.RegisteredAtLay);


        FirstNameLay.setTypeface(typeface);
        LastNameLay.setTypeface(typeface);
        CompanyNameLay.setTypeface(typeface);
        FathersNameLay.setTypeface(typeface);
        CompanyNationalNumberLay.setTypeface(typeface);
        CompanyRegisterNumberLay.setTypeface(typeface);
        CompanyEconomicalNumberLay.setTypeface(typeface);
        NationalNumberLay.setTypeface(typeface);
        ShenasnameNumberLay.setTypeface(typeface);
        RegisteredAtLay.setTypeface(typeface);


        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        CompanyName = findViewById(R.id.CompanyName);
        FathersName = findViewById(R.id.FathersName);
        Address = findViewById(R.id.Address);
        PostCode = findViewById(R.id.PostCode);
        Fax = findViewById(R.id.Fax);
        BirthDate = findViewById(R.id.BirthDate);
        Phone = findViewById(R.id.Phone);
        PhoneNumber = findViewById(R.id.PhoneNumber);
        CompanyNationalNumber = findViewById(R.id.CompanyNationalNumber);
        CompanyRegisterNumber = findViewById(R.id.CompanyRegisterNumber);
        CompanyEconomicalNumber = findViewById(R.id.CompanyEconomicalNumber);
        NationalNumber = findViewById(R.id.NationalNumber);
        ShenasnameNumber = findViewById(R.id.ShenasnameNumber);
        RegisteredAt = findViewById(R.id.RegisteredAt);


        FirstName.setTypeface(typeface);
        LastName.setTypeface(typeface);
        CompanyName.setTypeface(typeface);
        FathersName.setTypeface(typeface);
        CompanyNationalNumber.setTypeface(typeface);
        CompanyRegisterNumber.setTypeface(typeface);
        CompanyEconomicalNumber.setTypeface(typeface);
        NationalNumber.setTypeface(typeface);
        ShenasnameNumber.setTypeface(typeface);
        RegisteredAt.setTypeface(typeface);
        Address.setTypeface(typeface);
        PostCode.setTypeface(typeface);
        Fax.setTypeface(typeface);
        BirthDate.setTypeface(typeface);
        Phone.setTypeface(typeface);
        PhoneNumber.setTypeface(typeface);

    }

    private void setCustomerData(CustomerDetails model) {

        FirstName.setText(model.getFirstName());
        LastName.setText(model.getLastName());
        CompanyName.setText(model.getCompanyName());
        FathersName.setText(model.getFathersName());


        CompanyNationalNumber.setText(model.getCompanyNationalNumber());
        CompanyRegisterNumber.setText(model.getCompanyRegisterNumber());
        CompanyEconomicalNumber.setText(model.getCompanyEconomicalNumber());
        NationalNumber.setText(model.getNationalNumber());
        ShenasnameNumber.setText(model.getShenasnameNumber());
        RegisteredAt.setText(model.getRegisteredAt());
        BirthDate.setText(model.getBirthday());
        Address.setText(model.getAddress());


        JSONArray jsonArray;
        String address, postalCode, FAXNumber = null;
        try {
            jsonArray = new JSONArray(model.getAddress());
            if (jsonArray.length() == 0) {

            } else {
                JSONObject json = jsonArray.getJSONObject(0);
                address = json.getString("Address");
                postalCode = json.getString("PostalCode");
                FAXNumber = json.getString("FAXNumber");

                Address.setText(address);
                PostCode.setText(postalCode);
                Fax.setText(FAXNumber);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void start(Context context, String customerId) {
        Intent intent = new Intent(context, AddCustomerActivity.class);
        intent.putExtra(VariableKeys.CustomerId, customerId);
        context.startActivity(intent);
    }

    @Override
    public void onComplete(String time) {
        BirthDate.setText(time);
    }
}
