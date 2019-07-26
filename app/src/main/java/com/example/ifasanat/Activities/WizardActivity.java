package com.example.ifasanat.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ifasanat.Adapter.ViewPagerAdapter;
import com.example.ifasanat.DataModel.CapturesDataModel;
import com.example.ifasanat.R;
import com.example.ifasanat.WizardFragments.WizardCaptureImageFragment;
import com.example.ifasanat.WizardFragments.WizardFragment;
import com.example.ifasanat.WizardReposotories.WizardRepository;

import java.util.List;

public class WizardActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private static final String TAG = "WizardActivity";

    ViewPager mPager;
    ViewPagerAdapter adapter;
    TextView toolbarText, textPagination;
    ImageButton buttonBack, buttonNext;
    ImageView imageBack, menuButton;
    PopupMenu popUpMenu;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Log.i(TAG, "onBackPressed: WizardActivity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizzard);
        Log.i(TAG, "onCreate: WizardActivity");
        getPermission();
        init();

        adapter = WizardRepository.getWizardAdapter(getSupportFragmentManager());
        if (adapter.getFragments().size() == 0) {
            addFragment(adapter);
            WizardRepository.setWizardFragments(adapter.getFragments());
        }

        mPager.setAdapter(adapter);


        mPager.addOnPageChangeListener(this);
        buttonNext.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: WizardActivity");
        adapter = WizardRepository.getWizardAdapter(getSupportFragmentManager());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: WizardActivity");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_button_back:
                currentPageMines();
                break;
            case R.id.image_button_next:
                currentPagePlus();
                break;
            case R.id.toolbar_backward_icon:
                finish();
                break;
            case R.id.image_popup_menu:
                popUpMenu.show();
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        setToolbarText(adapter.getTitle(mPager.getCurrentItem()));
        setPaginationText(String.format("%s از %s", i + 1, adapter.getCount()));
        setPaginationButtonsVisibility();

        setUpMenuItem(i);
    }

    @Override
    public void onPageSelected(int i) {
        setUpMenuItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    private void addFragment(@NonNull ViewPagerAdapter adapter) {
        adapter.addFragment(WizardFragment.newInstance("روبرو نمای کلی(از فاصله دور)", new CapturesDataModel()));
        adapter.addFragment(WizardFragment.newInstance("سمت چپ", new CapturesDataModel()));
        adapter.addFragment(WizardFragment.newInstance("سمت راست", new CapturesDataModel()));
        adapter.addFragment(WizardFragment.newInstance("سمت بالا", new CapturesDataModel()));
        adapter.addFragment(WizardFragment.newInstance("از سیم کشی کنتور",new CapturesDataModel()));
        adapter.addFragment(WizardFragment.newInstance("پلمپ شماره یک",new CapturesDataModel()));
        adapter.addFragment(WizardFragment.newInstance("پلمپ شماره دو",new CapturesDataModel()));
        adapter.addFragment(WizardFragment.newInstance("پلمپ شماره سه",new CapturesDataModel()));
        adapter.addFragment(WizardFragment.newInstance("صورت جلسه تحویل و امضا شده",new CapturesDataModel()));
        adapter.addFragment(WizardFragment.newInstance("از لوله خروجی کنتور که به کجا رفته",new CapturesDataModel()));
        adapter.addFragment(WizardFragment.newInstance("صفحه روشن کنتور صفحه یک",new CapturesDataModel()));
        adapter.addFragment(WizardFragment.newInstance("صفحه روشن کنتور صفحه سه",new CapturesDataModel()));
        adapter.addFragment(WizardFragment.newInstance("از صفحه دو بعد از ارسال Log",new CapturesDataModel()));
        adapter.addFragment(WizardFragment.newInstance("از صفحه سه بعد از ارسال Log",new CapturesDataModel()));
    }

    private void init() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "iransanslight.ttf");
        textPagination = findViewById(R.id.text_view_pagination);
        textPagination.setTypeface(typeface);
        buttonBack = findViewById(R.id.image_button_back);
        buttonNext = findViewById(R.id.image_button_next);
        imageBack = findViewById(R.id.toolbar_backward_icon);
        menuButton = findViewById(R.id.image_popup_menu);
        mPager = findViewById(R.id.view_pager);
        toolbarText = findViewById(R.id.toolbar_text_view);
        toolbarText.setTypeface(typeface);
    }

    private void getPermission() {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}
                        , 2);
            }
        } catch (Exception e) {
            super.onBackPressed();
        }
    }

    public void setToolbarText(String title) {
        toolbarText.setText(title);
    }

    public void setPaginationText(String text) {
        textPagination.setText(text);
    }

    private void setPaginationButtonsVisibility() {
        buttonNext.setVisibility(View.VISIBLE);
        buttonBack.setVisibility(View.VISIBLE);
        //if last fragment show nextButton src change to checkout and select to finish
        if (mPager.getCurrentItem() == adapter.getCount() - 1) {
            buttonNext.setImageResource(R.drawable.ic_check_white_24dp);
        } else {
            buttonNext.setImageResource(R.drawable.ic_arrow_forward_white_24dp);
        }
        //if we are in first page , back button is invisible
        if (mPager.getCurrentItem() == 0) {
            buttonBack.setVisibility(View.INVISIBLE);
        }
    }

    private void currentPagePlus() {
        //check the position is not the last one
        if (mPager.getCurrentItem() != adapter.getCount() - 1) {
            int position = mPager.getCurrentItem() + 1;
            mPager.setCurrentItem(position);

        } else {
            //if last page is show and click in next button data will be saved and page closed
            finish();
            Toast.makeText(this, ":|", Toast.LENGTH_SHORT).show();
        }
    }

    private void currentPageMines() {
        //check position is not the first one
        if (mPager.getCurrentItem() > 0) {
            int position = mPager.getCurrentItem() - 1;
            mPager.setCurrentItem(position);
        }
    }

    private void setOnMenuItemClick(final PopupMenu menu) {

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 1:
                        takeNewPicture(mPager.getCurrentItem());
                        break;
                    case 2:
                        alertDialog(WizardActivity.this,mPager.getCurrentItem());
                        break;
                }
                return true;
            }
        });
    }

    private void setUpMenuItem(int position) {

        View view = findViewById(R.id.image_popup_menu);
        popUpMenu = adapter.getMenu(position, WizardActivity.this, view);

        if (popUpMenu == null) {
            menuButton.setVisibility(View.INVISIBLE);
        } else {
            menuButton.setVisibility(View.VISIBLE);
            menuButton.setOnClickListener(WizardActivity.this);
            popUpMenu.getMenu().add(1, 1, 1, "بارگذاری عکس جدید");
            popUpMenu.getMenu().add(2, 2, 2, "پاک کردن عکس");
            setOnMenuItemClick(popUpMenu);
        }
    }

    private void alertDialog(Context context, final int position) {
        new AlertDialog.Builder(context)
                .setMessage(R.string.accept_delete_image)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       deleteImage(position);
                        WizardRepository.getWizardFragments().get(mPager.getCurrentItem()).onResume();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void deleteImage(int position) {

        List<WizardFragment> wfs = WizardRepository.getWizardFragments();
        if (wfs != null) {
            WizardFragment wf = wfs.get(position);
            if (wf == null) return;
            boolean b = wf instanceof WizardCaptureImageFragment;
            if (b) {
                WizardCaptureImageFragment wcf = (WizardCaptureImageFragment) wf;
                wcf.clear();
            }
        }

    }

    private void takeNewPicture(int position){
        List<WizardFragment> wfs = WizardRepository.getWizardFragments();
        if (wfs != null) {
            WizardFragment wf = wfs.get(position);
            if (wf == null) return;
            boolean b = wf instanceof WizardCaptureImageFragment;
            if (b) {
                WizardCaptureImageFragment wcf = (WizardCaptureImageFragment) wf;
                wcf.showDialog(this);
            }

        }
    }

}
