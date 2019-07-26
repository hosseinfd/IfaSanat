package com.example.ifasanat.WizardFragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ifasanat.DataModel.CapturesDataModel;
import com.example.ifasanat.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.example.ifasanat.VariableKeys.VariableKeys.KEY_WIZARD_MODEL;

public class WizardCaptureImageFragment extends WizardFragment implements View.OnClickListener {
    private static final String TAG = "WizardCaptureImageFragm";

    public WizardCaptureImageFragment() {
        // Required empty public constructor
    }


    CapturesDataModel model;
    ImageView imageView, imageViewCaptureEmpty;
    Button buttonCapture;
    String pathToFile;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle args = getArguments();
        if (args == null) throw new AssertionError();
        model = args.getParcelable(KEY_WIZARD_MODEL);
        if (model == null) throw new AssertionError();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.capture_image_fragment_wizzard, container, false);
        init(view);

        buttonCapture.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();
        if (args == null) throw new AssertionError();
        model = args.getParcelable(KEY_WIZARD_MODEL);
        if (model == null) throw new AssertionError();
        if (model.getImagePath() != null) {

            imageViewCaptureEmpty.setVisibility(View.GONE);
            buttonCapture.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);

            Glide.with(Objects.requireNonNull(getActivity()))
                    .load(model.getImagePath())
                    .into(imageView);

        } else {
            imageViewCaptureEmpty.setVisibility(View.VISIBLE);
            buttonCapture.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);

            Glide.with(Objects.requireNonNull(getActivity()))
                    .load(R.drawable.ic_cloud_upload_black_24dp)
                    .into(imageViewCaptureEmpty);
        }

    }

    private void init(View view) {

        imageView = view.findViewById(R.id.image_view_capture);
        imageViewCaptureEmpty = view.findViewById(R.id.image_view_capture_empty);
        buttonCapture = view.findViewById(R.id.text_view_capture_empty);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "iransanslight.ttf");
        buttonCapture.setTypeface(typeface);
    }

    private void chooseImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        File pictureDirectories = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectories.getPath();
        Uri uri = Uri.parse(pictureDirectoryPath);
        intent.setDataAndType(uri, "image/*");
        startActivityForResult(intent, 3);
    }

    private void chooseImageFromCamera() {

        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            photoFile = createPhotoFileDirection();

            if (photoFile != null) {
                pathToFile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(getActivity()
                        , "com.example.ifasanat.Activities.WizardActivity", photoFile);

                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePic, 1);
            }


        }
    }

    private File createPhotoFileDirection() {
        String name = new Date() + getTitle();
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
                //capture image from camera
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                model.setImagePath(pathToFile);
            }
            if (requestCode == 3) {
                //choose image from gallery
                assert data != null;
                Uri pickedImage = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(pickedImage, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                model.setImagePath(cursor.getString(column_index));
                cursor.close();
            }
        }
        Glide.with(Objects.requireNonNull(getActivity())).load(model.getImagePath()).into(imageView);
    }

    public CapturesDataModel getCaptureImageModel() {
        if (model != null && model.getImagePath() != null) {
            return model;
        } else return null;
    }

    public void clear() {
        this.model.setImagePath(null);
    }

    @Override
    public PopupMenu getMenu(Context context, View view) {
        PopupMenu popUpMenu = new PopupMenu(context, view);
        if (model.getImagePath() != null) {
            return popUpMenu;
        } else return null;
    }

    @Override
    public void onClick(View v) {
        showDialog(getActivity());
    }

    public void showDialog(Context context){
        final CharSequence[] options = {"گرفتن عکس", "انتخاب از گالری", "لغو"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("انتخاب عکس");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("گرفتن عکس")) {
                    chooseImageFromCamera();
                } else if (options[item].equals("انتخاب از گالری")) {
                    chooseImageFromGallery();
                } else if (options[item].equals("لغو")) {

                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }
}
