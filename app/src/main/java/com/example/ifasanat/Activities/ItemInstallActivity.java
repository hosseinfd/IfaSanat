package com.example.ifasanat.Activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ifasanat.Adapter.InstallItemAdapter;
import com.example.ifasanat.ApiService.ApiService;
import com.example.ifasanat.R;
import com.example.ifasanat.UserSharedPrefManager.UserSharedPrefManager;
import com.example.ifasanat.VariableKeys.VariableKeys;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import ss.com.infinitescrollprovider.InfiniteScrollProvider;

public class ItemInstallActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    FloatingActionButton fabMain, fabAddCustomer, fabFilterData;
    ProgressBar progressBar;
    boolean isOpen;
    float translationy = 100;
    CoordinatorLayout coordinatorLayout;
    @SuppressLint("NewApi")
    OvershootInterpolator interpolator = new OvershootInterpolator();
    Toolbar toolbar;
    TextView toolbarText;
    ImageView imageMenu, imageSearchView;
    MaterialSearchView materialSearchView;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    InstallItemAdapter adapter;
    ApiService apiService;
    private int page = 0;
    UserSharedPrefManager sharedPrefManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private ConnectivityListener connectivityListener;
    private Snackbar connectivityMessageSnackBar;


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(connectivityListener);
        apiService.GetInstallationList(String.valueOf(page), "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectivityListener = new ConnectivityListener();
        registerReceiver(connectivityListener, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_install_drawer_layout);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "iransanslight.ttf");
        init(typeface);

        setSupportActionBar(toolbar);
        fabAddCustomer.setAlpha(0f);
        fabFilterData.setAlpha(0f);
        fabAddCustomer.setTranslationY(translationy);
        fabAddCustomer.setTranslationY(translationy);

        fabMain.setOnClickListener(v -> {

            if (isOpen) {
                closeMenu();
            } else {
                openMenu();
            }
        });

        fabFilterData.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), FilterActivity.class)));
        fabAddCustomer.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CustomersActivity.class)));


        getDataFromServer();
        setupRecyclerView();

        imageSearchView.setOnClickListener(v -> {
            if (materialSearchView.isSearchOpen()) {
                materialSearchView.closeSearch();
            } else {
                materialSearchView.showSearch();
            }
        });

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchActivity.start(ItemInstallActivity.this, VariableKeys.ItemInstallActivity, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        imageMenu.setOnClickListener( v -> showDrawerMenu() );

        navigationView.setNavigationItemSelectedListener(this);

    }


    private void init(Typeface typeface) {

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        fabMain = findViewById(R.id.fab);
        fabAddCustomer = findViewById(R.id.fab_add_customer);
        fabFilterData = findViewById(R.id.fab_filter);
        toolbar = findViewById(R.id.toolbar);
        materialSearchView = findViewById(R.id.search_view);
        imageSearchView = findViewById(R.id.image_view_search);
        imageMenu = findViewById(R.id.ic_menu);
        recyclerView = findViewById(R.id.recyclerView);
        toolbarText = findViewById(R.id.toolbar_text_view);
        progressBar = findViewById(R.id.progress_bar);
        toolbarText.setTypeface(typeface);
        toolbarText.setText(R.string.installed_items);

        coordinatorLayout = findViewById(R.id.coordinator);

        apiService = new ApiService(ItemInstallActivity.this);
        sharedPrefManager = new UserSharedPrefManager(this);

    }

    private void getDataFromServer() {

        apiService.GetInstallationList(String.valueOf(page), "");
        apiService.DataExtractor(list -> {
            progressBar.setVisibility(View.VISIBLE);
            adapter.addData(list);
            progressBar.setVisibility(View.GONE);

        });


    }

    private void setupRecyclerView() {

        //set Recycler View
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new InstallItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        InfiniteScrollProvider infiniteScrollProvider = new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerView, () -> {
            page = page + 20;
            apiService.GetInstallationList(String.valueOf(page), "");

        });


    }

    //    when fab menu is close and click to open
    @SuppressLint({"RestrictedApi", "NewApi"})
    private void openMenu() {
        isOpen = true;
        fabMain.animate().setInterpolator(interpolator).rotation(90f).setDuration(300).start();
        fabAddCustomer.animate().setInterpolator(interpolator).translationY(0f).alpha(1f).setDuration(300).start();
        fabFilterData.animate().setInterpolator(interpolator).translationY(0f).alpha(1f).setDuration(300).start();
        fabAddCustomer.setVisibility(View.VISIBLE);
        fabFilterData.setVisibility(View.VISIBLE);
        fabMain.setImageResource(R.drawable.ic_clear_white_24dp);

    }

    //when fab menu is open and click to close
    @SuppressLint({"RestrictedApi", "NewApi"})
    private void closeMenu() {
        isOpen = false;
        fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();
        fabAddCustomer.animate().setInterpolator(interpolator).translationY(0f).alpha(0f).setDuration(300).start();
        fabFilterData.animate().setInterpolator(interpolator).translationY(0f).alpha(0f).setDuration(300).start();
        fabAddCustomer.setVisibility(View.GONE);
        fabFilterData.setVisibility(View.GONE);
        fabMain.setImageResource(R.drawable.ic_more_vert_white_24dp);
    }


    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    private void showDrawerMenu() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(Gravity.START);
        }
    }


    private void alertDialog(Context context) {
        new AlertDialog.Builder(context)
                .setMessage(R.string.confirm_exit)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    sharedPrefManager.logoutUser();
                    finish();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {

                })
                .setCancelable(false)
                .show();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_add_user:
                startActivity(new Intent(ItemInstallActivity.this ,AddCustomerActivity.class));
                break;

            case R.id.menu_notification:
                alertDialog(this);
                break;

            case R.id.menu_setting:
                startActivity(new Intent(ItemInstallActivity.this ,FilterActivity.class));
                break;


            case R.id.menu_filter:
                startActivity(new Intent(ItemInstallActivity.this ,FilterActivity.class));
                break;

            case R.id.menu_exit:
                alertDialog(this);
                break;

        }
        return true;
    }


    private class ConnectivityListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

            if (isConnected) {

                if (connectivityMessageSnackBar != null) {
                    connectivityMessageSnackBar.dismiss();
                }
            } else {

                connectivityMessageSnackBar = Snackbar.make(coordinatorLayout, "دسترسی به اینترنت ندارید، اینترنت خود را بررسی کنید.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("بررسی وضعیت اتصال", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            }
                        });
                // Changing message text color
                connectivityMessageSnackBar.setActionTextColor(Color.GREEN);

                // Changing action button text color
                View sbView = connectivityMessageSnackBar.getView();
                TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                ViewCompat.setLayoutDirection(connectivityMessageSnackBar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                connectivityMessageSnackBar.show();

            }
        }
    }

}
