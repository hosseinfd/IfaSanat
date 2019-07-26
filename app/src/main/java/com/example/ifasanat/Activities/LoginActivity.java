package com.example.ifasanat.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ifasanat.ApiService.ApiService;
import com.example.ifasanat.Interface.IDataExtractor;
import com.example.ifasanat.R;
import com.example.ifasanat.UserSharedPrefManager.UserSharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button buttonLogin;
    TextView textViewUserName, textViewPassword;
    EditText editTextUserName, editTextPassword;

    ImageView imageLogo;
    ApiService apiService;
    UserSharedPrefManager sharedPrefManager;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefManager = new UserSharedPrefManager(LoginActivity.this);

        if (sharedPrefManager.getToken() != null) {
            launchHomeScreen();
            finish();
        }
        setContentView(R.layout.activity_login);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "iransanslight.ttf");
        init(typeface);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextUserName.getText().toString().trim().equals("") || editTextPassword.getText().toString().trim().equals("")) {

                    Toast.makeText(LoginActivity.this, R.string.usernameOrPasswordCantBeEmpty, Toast.LENGTH_SHORT).show();
                }
                apiService.Login(editTextUserName.getText().toString(), editTextPassword.getText().toString(), new IDataExtractor<String>() {

                    @Override
                    public void ExtractData(String s) {
                        if (s != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                sharedPrefManager.saveToken(jsonObject.getString("token"));
                                sharedPrefManager.saveName(jsonObject.getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(LoginActivity.this, ItemInstallActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.usernameOrPasswordWrong, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();

    }

    private void init(Typeface typeface) {

        coordinatorLayout = findViewById(R.id.coordinator);
        buttonLogin = findViewById(R.id.button_login);
        textViewUserName = findViewById(R.id.text_view_user_name);
        textViewPassword = findViewById(R.id.text_view_password);
        editTextUserName = findViewById(R.id.edit_text_user_name);
        editTextPassword = findViewById(R.id.edit_text_password);
        imageLogo = findViewById(R.id.image_view_logo);

        buttonLogin.setTypeface(typeface);
        textViewUserName.setTypeface(typeface);
        textViewPassword.setTypeface(typeface);
        editTextUserName.setTypeface(typeface);
        editTextPassword.setTypeface(typeface);

        Glide.with(LoginActivity.this).load(R.drawable.logo).into(imageLogo);

        apiService = new ApiService(LoginActivity.this);
//        sharedPrefManager = new UserSharedPrefManager(LoginActivity.this);
    }

    private void launchHomeScreen() {
        sharedPrefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(LoginActivity.this, ItemInstallActivity.class));

    }

}
