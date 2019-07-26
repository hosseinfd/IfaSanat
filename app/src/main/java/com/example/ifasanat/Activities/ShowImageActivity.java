package com.example.ifasanat.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ifasanat.R;
import com.example.ifasanat.VariableKeys.VariableKeys;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.Objects;

public class ShowImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backwardImage;
    private PhotoView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "iransanslight.ttf");
        init(typeface);

        String hashCode = Objects.requireNonNull(getIntent().getExtras()).getString(VariableKeys.ImageHashCode);
        if (hashCode == null) {

        } else {

            Glide.with(this)
                    .load(hashCode)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.drawable.logo)
                    .into(imageView);

        }
        backwardImage.setOnClickListener(this);
    }

    private void init(Typeface typeface) {
        imageView = findViewById(R.id.show_image);
        TextView toolbarText = findViewById(R.id.toolbar_text_view);
        backwardImage = findViewById(R.id.toolbar_backward_icon);

        toolbarText.setTypeface(typeface);
        toolbarText.setText(R.string.show_image);

    }


    public static void start(Context context, String imageHashCode) {
        Intent intent = new Intent(context, ShowImageActivity.class);
        intent.putExtra(VariableKeys.ImageHashCode, imageHashCode);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        ShowImageActivity.super.onBackPressed();
    }

}
