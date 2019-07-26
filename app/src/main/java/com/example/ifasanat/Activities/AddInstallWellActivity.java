package com.example.ifasanat.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ifasanat.Adapter.CapturesAdapter;
import com.example.ifasanat.Adapter.PolumpAdapter;
import com.example.ifasanat.ApiService.ApiService;
import com.example.ifasanat.DataModel.AddInstallWellActivityDataModel;
import com.example.ifasanat.DataModel.CapturesDataModel;
import com.example.ifasanat.Dialog.NetworkStateNumberPickerFragment;
import com.example.ifasanat.Dialog.NumberPickerFragment;
import com.example.ifasanat.Interface.IDataExtractor;
import com.example.ifasanat.R;
import com.example.ifasanat.UserSharedPrefManager.UserSharedPrefManager;
import com.example.ifasanat.VariableKeys.VariableKeys;
import com.example.ifasanat.WizardFragments.WizardCaptureImageFragment;
import com.example.ifasanat.WizardFragments.WizardFragment;
import com.example.ifasanat.WizardReposotories.WizardRepository;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AddInstallWellActivity extends AppCompatActivity implements NumberPickerFragment.OnCompleteListener
        , NetworkStateNumberPickerFragment.OnCompleteListenerNetworkState {
    private static final String TAG = "AddInstallWellActivity";

    Toolbar toolbar;
    ImageView backwardImage;
    TextView toolbarText;
    CoordinatorLayout coordinatorLayout;

    TextInputLayout textKonturSerial, textKoturName, textViewWellOwner, textViewWellAddress,
            textInstallerName, textPolompNumber, textPhoneNumber, textManagerName, textReceivedTimeInstaller, textInstallTime;
    TextInputLayout textProjectName, textTabloDetail, textPrice, textNetworkState, textDescription;
    TextView textTabloHasBreaker, textItemHasUsed, textPictureList;

    TextInputEditText editKonturSerial, editKonturName, editWellOwner, editWellAddress,
            editInstallerName, editPolompNumber, editPhoneNumber, editManagerName, editReceivedTime, editInstallTime;
    TextInputEditText editProject, editTabloDetail, editPrice, editNetworkState, editDescription;
    Button buttonSaveData, buttonWizard;
    FloatingActionButton fabAddImage;

    RecyclerView recyclerView;
    RecyclerView recyclerViewPolumpNumber;
    PolumpAdapter polumpAdapter;
    CapturesAdapter adapter;

    ApiService apiService;
    CheckBox checkBoxHasBreaker;
    int whichClicked = 0;

    View view;
    UserSharedPrefManager sharedPrefManager;
    String id, activity, wellId, address, customerName, installationId_;
    String pathToFile;

    ProgressBar progressBar;
    ProgressDialog pd;


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: activity");
        if (activity.equals(VariableKeys.CustomerDetailsActivity)) {
            //there is not an installation and we need a wizard page
            adapter.clear();
            List<WizardFragment> wfs = WizardRepository.getWizardFragments();
            adapter.setWizardFragments(wfs);
            if (wfs != null) {
                for (int i = 0; i < wfs.size(); i++) {
                    WizardFragment wf = wfs.get(i);
                    if (wf == null) return;

                    boolean b = wf instanceof WizardCaptureImageFragment;
                    if (b) {
                        WizardCaptureImageFragment wcf = (WizardCaptureImageFragment) wf;
                        adapter.add(i, wcf.getCaptureImageModel());
                    }
                }
            }


        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: activity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: activity");
    }


    @Override
    public void onBackPressed() {
        finish();
        adapter.clear();
        Log.i(TAG, "onBackPressed: activity");
        super.onBackPressed();
    }

    @SuppressLint({"ClickableViewAccessibility", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_item_details);
        Log.i(TAG, "onCreate: activity");

        id = Objects.requireNonNull(getIntent().getExtras()).getString(VariableKeys.WellId);
        address = Objects.requireNonNull(getIntent().getExtras()).getString(VariableKeys.CustomerAddress);
        activity = Objects.requireNonNull(getIntent().getExtras()).getString(VariableKeys.FromActivity);
        customerName = Objects.requireNonNull(getIntent().getExtras()).getString(VariableKeys.CustomerName);
        init();

        apiService = new ApiService(AddInstallWellActivity.this);
        //there was an Installation before and wizard page is Gone
        if (activity.equals(VariableKeys.ItemInstallActivity)) {

            buttonWizard.setVisibility(View.GONE);
            apiService.getInstallationDetails(id);
            toolbarText.setText(R.string.installed_items_detail);

            //get data from server and set it to items
            apiService.DataAddInstallWell(new IDataExtractor<AddInstallWellActivityDataModel>() {
                @Override
                public void ExtractData(AddInstallWellActivityDataModel model) {
                    progressBar.setVisibility(View.VISIBLE);
                    setUpData(model);
                    wellId = model.getWellId();
                    progressBar.setVisibility(View.GONE);
                }
            });


        } else if (activity.equals(VariableKeys.CustomerDetailsActivity)) {
            //there is not an installation and we need a wizard page
            toolbarText.setText(R.string.add_install);
            buttonSaveData.setText(R.string.save_change);
            buttonWizard.setVisibility(View.VISIBLE);
            textPictureList.setVisibility(View.GONE);
            fabAddImage.setVisibility(View.GONE);
            WizardRepository.setWizardFragments(null);

        }


        setSupportActionBar(toolbar);

        //image captures lists_
        adapter = new CapturesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.HORIZONTAL, false));

        apiService.DataImage(new ApiService.ImageData() {
            @Override
            public void getData(List<CapturesDataModel> lists) {
                adapter.addImage(lists);
            }
        });

        backwardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editKonturSerial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                apiService.getMeterName(s.toString());
                editKonturName.setText("در حال بررسی");
            }

            @Override
            public void afterTextChanged(Editable s) {
                editKonturName.setText("در حال بررسی");
                apiService.getMessage(new IDataExtractor<String>() {
                    @Override
                    public void ExtractData(String s) {
                        editKonturName.setText(s);
                    }
                });
            }
        });

        editKonturSerial.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() <= (editKonturSerial.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width() + 100)) {
                        // your action here
                        barCodeReader();
                        return true;
                    }
                }
                return false;
            }
        });


        editReceivedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NumberPickerFragment numberPickerFragment = new NumberPickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString(VariableKeys.Date, editReceivedTime.getText().toString());
                numberPickerFragment.setArguments(bundle);
                numberPickerFragment.show(getSupportFragmentManager(), "TA");
                whichClicked = 0;

            }
        });

        editInstallTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPickerFragment numberPickerFragment = new NumberPickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString(VariableKeys.Date, editInstallTime.getText().toString());
                numberPickerFragment.setArguments(bundle);
                numberPickerFragment.show(getSupportFragmentManager(), "TAG");
                whichClicked = 1;
            }
        });

        editNetworkState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkStateNumberPickerFragment numberPickerFragment = new NetworkStateNumberPickerFragment();
                numberPickerFragment.show(getSupportFragmentManager(), "TAG");
            }
        });


        buttonSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (activity.equals(VariableKeys.ItemInstallActivity)) {
                    //there was an Installation and here we can edit it
                    try {
                        progressDialogShow(pd, null);
                        apiService.editInstallationDetails(id, setJson(wellId));
                    } catch (JSONException e) {
                        progressDialogShow(pd, "لطفا دوباره سعی کنید");
                        e.printStackTrace();
                    }
                    // upload image added
                    for (int i = 0; i < adapter.getItemCount(); i++) {
                        if (adapter.getImageAdded(i) != null) {
                            apiService.uploadImage(id, adapter.getImageAdded(i));
                        }
                    }

                    apiService.getMessage(new IDataExtractor<String>() {
                        @Override
                        public void ExtractData(String s) {
                            if (s.equals("done")) {
                                Toast.makeText(AddInstallWellActivity.this, R.string.successfulEdit, Toast.LENGTH_SHORT).show();
                            }
                            progressDialogDismiss(pd);
                            AddInstallWellActivity.this.finish();
                        }
                    });
                } else if (activity.equals(VariableKeys.CustomerDetailsActivity)) {
                    //there is no installation and add a new installation
                    try {
                        wellId = Objects.requireNonNull(getIntent().getExtras()).getString(VariableKeys.WellId);
                        if (setJson(wellId) != null) {
                            progressDialogShow(pd, null);
                            apiService.addInstallation(setJson(wellId));

                            apiService.AddNewInstallation(new ApiService.IaddNewInstallation() {
                                @Override
                                public void addNewInstallation(String addMessage, String installationId) {
                                    if (addMessage.equals("done")) {
                                        Toast.makeText(AddInstallWellActivity.this, R.string.successfulInstall, Toast.LENGTH_SHORT).show();
                                        installationId_ = installationId;
                                    }
                                    int counter = 0;
                                    //here we have to upload image to server
                                    for (int i = 0; i < adapter.getItemCount(); i++) {
                                        apiService.uploadImage(installationId_ , adapter.getImageList(i));
                                    }

                                    progressDialogShow(pd, null);
                                    if (counter != adapter.getItemCount()) {
                                        counter++;
                                        Log.d(TAG, "progressBarDismiss: Active");
                                        if (counter == adapter.getItemCount()) {
                                            progressDialogDismiss(pd);
                                            AddInstallWellActivity.this.finish();
                                        }
                                    }
                                    if (adapter.getItemCount() == 0) {
                                        progressDialogDismiss(pd);
                                        AddInstallWellActivity.this.finish();
                                    }
                                }
                            });

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        editPolompNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    //Perform your Actions here.

                    polumpAdapter.addData(Objects.requireNonNull(editPolompNumber.getText()).toString());
                    editPolompNumber.setText("");
                }
                return handled;
            }
        });

        buttonWizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddInstallWellActivity.this, WizardActivity.class));
            }
        });

        fabAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"گرفتن عکس", "انتخاب از گالری", "لغو"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AddInstallWellActivity.this);
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
        });
    }

    private void init() {

        pd = new ProgressDialog(AddInstallWellActivity.this);
        progressBar = findViewById(R.id.progress_bar);
        sharedPrefManager = new UserSharedPrefManager(AddInstallWellActivity.this);

        coordinatorLayout = findViewById(R.id.coordinator);

        toolbar = findViewById(R.id.toolbar);
        toolbarText = findViewById(R.id.toolbar_text_view);
        backwardImage = findViewById(R.id.toolbar_backward_icon);

        checkBoxHasBreaker = findViewById(R.id.checkbox_tablo_has_breaker);

        textKonturSerial = findViewById(R.id.text_view_meter_serial);
        textKoturName = findViewById(R.id.text_view_meter_name);

        textViewWellOwner = findViewById(R.id.text_view_well_owner);
        textViewWellAddress = findViewById(R.id.text_view_well_address);

        textPhoneNumber = findViewById(R.id.text_view_phone_detail);
        textInstallerName = findViewById(R.id.text_view_installer_name);
        textPolompNumber = findViewById(R.id.text_view_polomp_number);
        textManagerName = findViewById(R.id.text_view_manager_name);
        textReceivedTimeInstaller = findViewById(R.id.text_view_time_receiver_installer);
        textInstallTime = findViewById(R.id.text_view_install_time);
        textProjectName = findViewById(R.id.text_view_project_name);
        textTabloDetail = findViewById(R.id.text_view_tablo_details);
        textTabloHasBreaker = findViewById(R.id.text_view_tablo_has_breaker);
        textPrice = findViewById(R.id.text_view_price);
        textNetworkState = findViewById(R.id.text_view_network_state);
        textItemHasUsed = findViewById(R.id.text_view_item_has_used);
        textDescription = findViewById(R.id.text_view_description);
        textPictureList = findViewById(R.id.text_view_picture_list);


        editKonturSerial = findViewById(R.id.edit_text_meter_serial);
        editKonturName = findViewById(R.id.text_view_meter_type_);
        editWellOwner = findViewById(R.id.edit_text_well_owner);
        editWellAddress = findViewById(R.id.edit_text_well_address);
        editInstallerName = findViewById(R.id.edit_text_installer_name);
        editPhoneNumber = findViewById(R.id.edit_text_phone_number);
        editManagerName = findViewById(R.id.edit_text_manager_name);
        editReceivedTime = findViewById(R.id.edit_text_time_receiver_installer);
        editInstallTime = findViewById(R.id.edit_text_install_time);
        editProject = findViewById(R.id.edit_text_project_name);
        editTabloDetail = findViewById(R.id.edit_text_tablo_details);
        editPrice = findViewById(R.id.edit_text_price);
        editNetworkState = findViewById(R.id.edit_text_network_state);
        editDescription = findViewById(R.id.edit_text_description);
        editPolompNumber = findViewById(R.id.edit_text_polomp_number);


        buttonSaveData = findViewById(R.id.button_save_data);
        buttonWizard = findViewById(R.id.button_wizard);
        fabAddImage = findViewById(R.id.button_add_image);

        recyclerView = findViewById(R.id.recyclerViewCaptures);
        recyclerViewPolumpNumber = findViewById(R.id.recyclerView);

        editWellAddress.setText(address);
        editInstallerName.setText(sharedPrefManager.getName());
        editWellOwner.setText(customerName);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "iransanslight.ttf");
        setFont(typeface);

        setUpRecyclerViewPolump();
    }

    private void setFont(Typeface typeface) {

        toolbarText.setTypeface(typeface);
        textKonturSerial.setTypeface(typeface);
        textKoturName.setTypeface(typeface);
        textViewWellOwner.setTypeface(typeface);
        textViewWellAddress.setTypeface(typeface);
        textInstallerName.setTypeface(typeface);
        textPhoneNumber.setTypeface(typeface);
        textPolompNumber.setTypeface(typeface);
        textManagerName.setTypeface(typeface);
        textReceivedTimeInstaller.setTypeface(typeface);
        textInstallTime.setTypeface(typeface);
        textProjectName.setTypeface(typeface);
        textTabloDetail.setTypeface(typeface);
        textTabloHasBreaker.setTypeface(typeface);
        textPrice.setTypeface(typeface);
        textItemHasUsed.setTypeface(typeface);
        textDescription.setTypeface(typeface);
        textPictureList.setTypeface(typeface);


        editKonturSerial.setTypeface(typeface);
        editWellOwner.setTypeface(typeface);
        editWellAddress.setTypeface(typeface);
        editKonturName.setTypeface(typeface);
        editInstallerName.setTypeface(typeface);
        editPhoneNumber.setTypeface(typeface);
        editPolompNumber.setTypeface(typeface);
        editManagerName.setTypeface(typeface);
        editReceivedTime.setTypeface(typeface);
        editInstallTime.setTypeface(typeface);
        editProject.setTypeface(typeface);
        editTabloDetail.setTypeface(typeface);
        editPrice.setTypeface(typeface);
        editDescription.setTypeface(typeface);

        buttonSaveData.setTypeface(typeface);
        buttonWizard.setTypeface(typeface);

        toolbarText.setText(R.string.installed_items_detail);


    }

    private void setUpData(AddInstallWellActivityDataModel list) {


        if (list.getSnNumber().equals("null")) {
            editKonturSerial.setText("");
        } else {
            editKonturSerial.setText(list.getSnNumber());
        }

        if (address.trim().equals("") || address.equals("null null") || address.trim().equals("null")) {
            editWellAddress.setText("نا موجود");
        } else {
            editWellAddress.setText(address);
        }

        if (list.getSnName().trim().equals("") || list.getSnName().equals("null null") || list.getSnName().trim().equals("null")) {
            editKonturName.setText("نا موجود");
        } else {
            editKonturName.setText(list.getSnName());
        }

        editInstallerName.setText(sharedPrefManager.getName());

        if (customerName.trim().equals("") || customerName.equals("null null") || customerName.trim().equals("null")) {
            editWellOwner.setText("ناموجود");
        } else {
            editWellOwner.setText(customerName);
        }

        if (list.getPlumbsSerialNumber().equals("") || list.getPlumbsSerialNumber().equals("null")) {
        } else {
            polumpAdapter.addData(list.getPlumbsSerialNumber());
        }
        if (list.getSimcardPhoneNumber().trim().equals("") || list.getSimcardPhoneNumber().equals("null")) {
            editPhoneNumber.setText("ناموجود");
        } else {
            editPhoneNumber.setText(list.getSimcardPhoneNumber());
        }

        if (list.getProjectManagerName().trim().equals("") || list.getProjectManagerName().equals("null") || list.getProjectManagerName().equals("null null")) {
            editManagerName.setText("ناموجود");
        } else {
            editManagerName.setText(list.getProjectManagerName());
        }

        if (list.getDeliveryMoment().trim().equals("") || list.getDeliveryMoment().equals("null") || list.getDeliveryMoment().equals("null null")) {
            editReceivedTime.setText("ناموجود");
        } else {
            editReceivedTime.setText(list.getDeliveryMoment());
        }
        if (list.getInstallationMoment().trim().equals("") || list.getInstallationMoment().equals("null") || list.getInstallationMoment().equals("null null")) {
            editInstallTime.setText("ناموجود");
        } else {
            editInstallTime.setText(list.getDeliveryMoment());
        }

        if (list.getProject().trim().equals("") || list.getProject().equals("null") || list.getProject().equals("null null")) {
            editProject.setText("ناموجود");
        } else {
            editProject.setText(list.getProject());
        }

        if (list.getNetworkState().trim().equals("") || list.getNetworkState().equals("null") || list.getNetworkState().equals("null null")) {
            editNetworkState.setText("ناموجود");
        } else {
            editNetworkState.setText(list.getNetworkState());
        }

        if (list.getBreakerInfo().trim().equals("") || list.getBreakerInfo().equals("null") || list.getBreakerInfo().equals("null null")) {
            editTabloDetail.setText("ناموجود");
        } else {
            editTabloDetail.setText(list.getBreakerInfo());
        }

        if (list.getAmount().trim().equals("") || list.getAmount().equals("null") || list.getAmount().equals("null null")) {
            editPrice.setText("ناموجود");
        } else {
            editPrice.setText(list.getAmount());
        }
        if (list.getDescription().trim().equals("") || list.getDescription().equals("null") || list.getDescription().equals("null null")) {
            editDescription.setText("ناموجود");
        } else {
            editDescription.setText(list.getDescription());
        }

        if (list.getUsesItems().equals("false")) {
            checkBoxHasBreaker.setChecked(false);
        } else {
            checkBoxHasBreaker.setChecked(true);
        }

    }

    private void barCodeReader() {
        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.CODE_128);
        integrator.setPrompt("");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();

    }

    private JSONObject setJson(String wellId) throws JSONException {
        JSONObject json = new JSONObject();

        if (editKonturSerial.getText().toString().trim().equals("") || editPhoneNumber.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.completeSn_PhoneNumber, Toast.LENGTH_SHORT).show();
            return null;

        } else {
            json.put("Sn", editKonturSerial.getText().toString());
            json.put("SimcardPhoneNumber", editPhoneNumber.getText().toString());
            if (editPrice.getText().toString().trim().equals("")) {
                json.put("Amount", 0);
            } else {
                json.put("Amount", Integer.parseInt(editPrice.getText().toString()));
            }
            if (editReceivedTime.getText().toString().isEmpty()) {
                json.put("DeliveryMoment_", "1397/1/1");
            } else {
                json.put("DeliveryMoment_", editReceivedTime.getText().toString());
            }
            if (editInstallTime.getText().toString().isEmpty()) {
                json.put("InstallationMoment_", "1397/1/1");
            } else {
                json.put("InstallationMoment_", editInstallTime.getText().toString());
            }
            if (editNetworkState.getText().toString().isEmpty()) {
                json.put("NetworkState", "0");
            } else {
                json.put("NetworkState", editNetworkState.getText().toString());
            }
            json.put("WellId", wellId);
            json.put("InstallerName", Objects.requireNonNull(editInstallerName.getText()).toString());
            json.put("PlumbsSerialNumber", Objects.requireNonNull(editPolompNumber.getText()).toString());
            json.put("ProjectManagerName", Objects.requireNonNull(editManagerName.getText()).toString());
            json.put("project", Objects.requireNonNull(editProject.getText()).toString());
            json.put("tabloDetail", Objects.requireNonNull(editTabloDetail.getText()).toString());
            json.put("Description", Objects.requireNonNull(editDescription.getText()).toString());
            json.put("HasBreaker", checkBoxHasBreaker.isChecked());
            return json;
        }

    }

    private void setUpRecyclerViewPolump() {
        polumpAdapter = new PolumpAdapter(AddInstallWellActivity.this);
        recyclerViewPolumpNumber.setAdapter(polumpAdapter);
        recyclerViewPolumpNumber.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onComplete(String time) {

        if (whichClicked == 0) {

            editReceivedTime.setText(time);

        } else if (whichClicked == 1) {

            editInstallTime.setText(time);
        }
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
        if (takePic.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            photoFile = createPhotoFileDirection();

            if (photoFile != null) {
                pathToFile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(this
                        , "com.example.ifasanat.Activities.WizardActivity", photoFile);

                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePic, 1);
            }


        }
    }

    private File createPhotoFileDirection() {
        String name = new SimpleDateFormat("yyyymm").format(new Date());
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
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                editKonturSerial.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        CapturesDataModel list = new CapturesDataModel();
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                //capture image from camera
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                list.setImagePath(pathToFile);
                adapter.add(-2, list);
            }
            if (requestCode == 3) {
                //choose image from gallery
                assert data != null;
                Uri pickedImage = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(pickedImage, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                list.setImagePath(cursor.getString(column_index));
                adapter.add(-2, list);
                cursor.close();
            }
        }
    }

    @Override
    public void onCompleteNetworkState(String time) {
        editNetworkState.setText(time);
    }

    public static void start(Context context, String wellId, String fromActivity, String address, String customerName) {
        Intent intent = new Intent(context, AddInstallWellActivity.class);
        intent.putExtra(VariableKeys.WellId, wellId);
        intent.putExtra(VariableKeys.FromActivity, fromActivity);
        intent.putExtra(VariableKeys.CustomerAddress, address);
        intent.putExtra(VariableKeys.CustomerName, customerName);
        context.startActivity(intent);
    }


    private void progressDialogShow(ProgressDialog progressDialog, String message) {
        if (message == null || message.trim().isEmpty()) {
            progressDialog.setMessage("لطفا شکیبا باشید");
        } else {
            progressDialog.setMessage(message);
        }
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


    }

    private void progressDialogDismiss(ProgressDialog progressDialog) {
        progressDialog.cancel();
        progressDialog.dismiss();
    }
}
