package com.example.ifasanat.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ifasanat.ApiService.ApiService;
import com.example.ifasanat.DataModel.AddWellDataModel;
import com.example.ifasanat.Interface.IDataExtractor;
import com.example.ifasanat.R;
import com.example.ifasanat.VariableKeys.VariableKeys;

import org.json.JSONException;
import org.json.JSONObject;

public class AddWellActivity extends AppCompatActivity {

    Button buttonSubmitWell;
    ImageView backwardImage;
    ApiService apiService;


    TextView toolbarText, textDocumentInformation, textWellLocation, textDigging, textWellLocation1;

    TextInputEditText
            StateOfUse,
            FileClass, TwelveDigitsCode,
            GlobalAuthenticationCode, UtmX,
            UtmY, Province, City, Village, State,
            RegionName, RegionCode, PlainName,
            DiggingNumber, DiggingMoment,
            DiggingCrookiNumber, DiggingCrookiMoment,
            DiggingType, DiggingWellDepth,
            DiggingDiameterInMeters, DiggingPipeWorkDiameterInMeters,
            PipeWorkMaterial, LatticePipeLongInMeters, LatticePipeFromInMeters,
            LatticePipeToInMeters, WaterTouchedInDepthInMeters,
            StaticDepthAsFinishingPipingInMeters, DiggingStartMoment,
            DiggingFinishMoment, DiggingCompanyName, DiggingEngineerName,
            UsageNumber, UsageMoment, UsageWellDepth, UsageWaterFlowPipeDiametersInMeters,
            PumpInstallationDepth, ElectricitySubscriptionNumber, ElectricitySubscriptionIdentifier,
            DrivingForce, PumpType, LandArea, MaxExtractionInLitersPerSecond, MaxExtractionInCubicMetersPerDay,
            MaxExtractionInCubicMetersPerYear, MaxPowerOfMotorInKiloWats, UptimeHoursPerYear;

    TextInputLayout
            StateOfUseLay,
            FileClassLay, TwelveDigitsCodeLay,
            GlobalAuthenticationCodeLay, UtmXLay, UtmYLay,
            ProvinceLay, CityLay, VillageLay, StateLay,
            RegionNameLay, RegionCodeLay, PlainNameLay,
            DiggingNumberLay, DiggingMomentLay, DiggingCrookiNumberLay,
            DiggingCrookiMomentLay, DiggingTypeLay, DiggingWellDepthLay,
            DiggingDiameterInMetersLay, DiggingPipeWorkDiameterInMetersLay,
            PipeWorkMaterialLay, LatticePipeLongInMetersLay, LatticePipeFromInMetersLay,
            LatticePipeToInMetersLay, WaterTouchedInDepthInMetersLay,
            StaticDepthAsFinishingPipingInMetersLay,
            DiggingStartMomentLay, DiggingFinishMomentLay, DiggingCompanyNameLay,
            DiggingEngineerNameLay,
            UsageNumberLay, UsageMomentLay, UsageWellDepthLay,
            UsageWaterFlowPipeDiametersInMetersLay, PumpInstallationDepthLay,
            ElectricitySubscriptionNumberLay, ElectricitySubscriptionIdentifierLay,
            DrivingForceLay, PumpTypeLay, LandAreaLay, MaxExtractionInLitersPerSecondLay,
            MaxExtractionInCubicMetersPerDayLay, MaxExtractionInCubicMetersPerYearLay,
            MaxPowerOfMotorInKiloWatsLay, UptimeHoursPerYearLay;

    String wellId;
    String customerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_well);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "iransanslight.ttf");
        init(typeface);

        apiService = new ApiService(AddWellActivity.this);

        wellId = getIntent().getStringExtra(VariableKeys.WellId);
        customerId = getIntent().getStringExtra(VariableKeys.CustomerId);

        if (wellId != null) {
            //there was a well and request for get data of that or edit data by pressing submit button
            getWellData(wellId);
            buttonSubmitWell.setText(R.string.save_change);
            toolbarText.setText(R.string.show_well);
        } else {
            // there is no well and submit a new one by pressing submit button
        }

        backwardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonSubmitWell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wellId != null) {
                    //there was a well and request for get data of that
                    editWellData(wellId);
                    buttonSubmitWell.setText(R.string.save_change);
                    toolbarText.setText(R.string.show_well);
                    AddWellActivity.this.finish();
                } else {
                    // there is no well and submit a new one
                    if (customerId != null) {
                        apiService.addWell(customerId, addWell());

                        apiService.getMessage(new IDataExtractor<String>() {
                            @Override
                            public void ExtractData(String s) {
                                if (s.equals("done")){

                                    Toast.makeText(AddWellActivity.this,"عملیات با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                                    AddWellActivity.this.finish();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private JSONObject addWell() {
        JSONObject json = new JSONObject();

        try {
            json.put("StateOfUse", StateOfUse.getText().toString());
            json.put("FileClass", FileClass.getText().toString());
            json.put("TwelveDigitsCode", TwelveDigitsCode.getText().toString());
            json.put("GlobalAuthenticationCode", GlobalAuthenticationCode.getText().toString());
            json.put("UtmX", UtmX.getText().toString());
            json.put("UtmY", UtmY.getText().toString());
            json.put("Province", Province.getText().toString());
            json.put("City", City.getText().toString());
            json.put("Village", Village.getText().toString());
            json.put("State", State.getText().toString());
            json.put("RegionName", RegionName.getText().toString());
            json.put("RegionCode", RegionCode.getText().toString());
            json.put("PlainName", PlainName.getText().toString());
            json.put("DiggingNumber", DiggingNumber.getText().toString());
            json.put("DiggingMoment", DiggingMoment.getText().toString());
            json.put("DiggingCrookiNumber", DiggingCrookiNumber.getText().toString());
            json.put("DiggingCrookiMoment", DiggingCrookiMoment.getText().toString());
            json.put("DiggingType", DiggingType.getText().toString());
            json.put("DiggingWellDepth", DiggingWellDepth.getText().toString());
            json.put("DiggingDiameterInMeters", DiggingDiameterInMeters.getText().toString());
            json.put("DiggingPipeWorkDiameterInMeters", DiggingPipeWorkDiameterInMeters.getText().toString());
            json.put("PipeWorkMaterial", PipeWorkMaterial.getText().toString());
            json.put("LatticePipeLongInMeters", LatticePipeLongInMeters.getText().toString());
            json.put("LatticePipeFromInMeters", LatticePipeFromInMeters.getText().toString());
            json.put("LatticePipeToInMeters", LatticePipeToInMeters.getText().toString());
            json.put("WaterTouchedInDepthInMeters", WaterTouchedInDepthInMeters.getText().toString());
            json.put("StaticDepthAsFinishingPipingInMeters", StaticDepthAsFinishingPipingInMeters.getText().toString());
            json.put("DiggingStartMoment", DiggingStartMoment.getText().toString());
            json.put("DiggingFinishMoment", DiggingFinishMoment.getText().toString());
            json.put("DiggingCompanyName", DiggingCompanyName.getText().toString());
            json.put("DiggingEngineerName", DiggingEngineerName.getText().toString());
            json.put("UsageNumber", UsageNumber.getText().toString());
            json.put("UsageMoment", UsageMoment.getText().toString());
            json.put("UsageWellDepth", UsageWellDepth.getText().toString());
            json.put("UsageWaterFlowPipeDiametersInMeters", UsageWaterFlowPipeDiametersInMeters.getText().toString());
            json.put("PumpInstallationDepth", PumpInstallationDepth.getText().toString());
            json.put("ElectricitySubscriptionNumber", ElectricitySubscriptionNumber.getText().toString());
            json.put("ElectricitySubscriptionIdentifier", ElectricitySubscriptionIdentifier.getText().toString());
            json.put("DrivingForce", DrivingForce.getText().toString());
            json.put("PumpType", PumpType.getText().toString());
            json.put("LandArea", LandArea.getText().toString());
            json.put("MaxExtractionInLitersPerSecond", MaxExtractionInLitersPerSecond.getText().toString());
            json.put("MaxExtractionInCubicMetersPerDay", MaxExtractionInCubicMetersPerDay.getText().toString());
            json.put("MaxExtractionInCubicMetersPerYear", MaxExtractionInCubicMetersPerYear.getText().toString());
            json.put("MaxPowerOfMotorInKiloWats", MaxPowerOfMotorInKiloWats.getText().toString());
            json.put("UptimeHoursPerYear", UptimeHoursPerYear.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    private JSONObject editWell(){
        JSONObject json = new JSONObject();

        try {
            json.put("stateOfUse", StateOfUse.getText().toString());
            json.put("fileClass", FileClass.getText().toString());
            json.put("twelveDigitsCode", TwelveDigitsCode.getText().toString());
            json.put("globalAuthenticationCode", GlobalAuthenticationCode.getText().toString());
            json.put("utmX", UtmX.getText().toString());
            json.put("utmY", UtmY.getText().toString());
            json.put("province", Province.getText().toString());
            json.put("city", City.getText().toString());
            json.put("village", Village.getText().toString());
            json.put("state", State.getText().toString());
            json.put("regionName", RegionName.getText().toString());
            json.put("regionCode", RegionCode.getText().toString());
            json.put("plainName", PlainName.getText().toString());
            json.put("diggingNumber", DiggingNumber.getText().toString());
            json.put("diggingMoment", DiggingMoment.getText().toString());
            json.put("diggingCrookiNumber", DiggingCrookiNumber.getText().toString());
            json.put("diggingCrookiMoment", DiggingCrookiMoment.getText().toString());
            json.put("diggingType", DiggingType.getText().toString());
            json.put("diggingWellDepth", DiggingWellDepth.getText().toString());
            json.put("diggingDiameterInMeters", DiggingDiameterInMeters.getText().toString());
            json.put("diggingPipeWorkDiameterInMeters", DiggingPipeWorkDiameterInMeters.getText().toString());
            json.put("pipeWorkMaterial", PipeWorkMaterial.getText().toString());
            json.put("latticePipeLongInMeters", LatticePipeLongInMeters.getText().toString());
            json.put("latticePipeFromInMeters", LatticePipeFromInMeters.getText().toString());
            json.put("latticePipeToInMeters", LatticePipeToInMeters.getText().toString());
            json.put("waterTouchedInDepthInMeters", WaterTouchedInDepthInMeters.getText().toString());
            json.put("staticDepthAsFinishingPipingInMeters", StaticDepthAsFinishingPipingInMeters.getText().toString());
            json.put("diggingStartMoment", DiggingStartMoment.getText().toString());
            json.put("diggingFinishMoment", DiggingFinishMoment.getText().toString());
            json.put("diggingCompanyName", DiggingCompanyName.getText().toString());
            json.put("diggingEngineerName", DiggingEngineerName.getText().toString());
            json.put("usageNumber", UsageNumber.getText().toString());
            json.put("usageMoment", UsageMoment.getText().toString());
            json.put("usageWellDepth", UsageWellDepth.getText().toString());
            json.put("usageWaterFlowPipeDiametersInMeters", UsageWaterFlowPipeDiametersInMeters.getText().toString());
            json.put("pumpInstallationDepth", PumpInstallationDepth.getText().toString());
            json.put("electricitySubscriptionNumber", ElectricitySubscriptionNumber.getText().toString());
            json.put("electricitySubscriptionIdentifier", ElectricitySubscriptionIdentifier.getText().toString());
            json.put("drivingForce", DrivingForce.getText().toString());
            json.put("pumpType", PumpType.getText().toString());
            json.put("landArea", LandArea.getText().toString());
            json.put("maxExtractionInLitersPerSecond", MaxExtractionInLitersPerSecond.getText().toString());
            json.put("maxExtractionInCubicMetersPerDay", MaxExtractionInCubicMetersPerDay.getText().toString());
            json.put("maxExtractionInCubicMetersPerYear", MaxExtractionInCubicMetersPerYear.getText().toString());
            json.put("maxPowerOfMotorInKiloWats", MaxPowerOfMotorInKiloWats.getText().toString());
            json.put("uptimeHoursPerYear", UptimeHoursPerYear.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private void init(Typeface typeface) {
        buttonSubmitWell = findViewById(R.id.button_submit_well);
        toolbarText = findViewById(R.id.toolbar_text_view);
        backwardImage = findViewById(R.id.toolbar_backward_icon);


        textDocumentInformation = findViewById(R.id.textDocumentInformation);
        textWellLocation = findViewById(R.id.textWellLocation);
        textDigging = findViewById(R.id.textDigging);
        textWellLocation1 = findViewById(R.id.textWellLocation1);

        textDocumentInformation.setTypeface(typeface);
        textWellLocation.setTypeface(typeface);
        textDigging.setTypeface(typeface);
        textWellLocation1.setTypeface(typeface);

        toolbarText.setTypeface(typeface);
        buttonSubmitWell.setTypeface(typeface);
        toolbarText.setText(R.string.add_well);


        StateOfUse = findViewById(R.id.StateOfUse);
        FileClass = findViewById(R.id.FileClass);
        TwelveDigitsCode = findViewById(R.id.TwelveDigitsCode);
        GlobalAuthenticationCode = findViewById(R.id.GlobalAuthenticationCode);
        UtmX = findViewById(R.id.UtmX);
        UtmY = findViewById(R.id.UtmY);
        Province = findViewById(R.id.Province);
        City = findViewById(R.id.City);
        Village = findViewById(R.id.Village);
        State = findViewById(R.id.State);
        RegionName = findViewById(R.id.RegionName);
        RegionCode = findViewById(R.id.RegionCode);
        PlainName = findViewById(R.id.PlainName);
        DiggingNumber = findViewById(R.id.DiggingNumber);
        DiggingMoment = findViewById(R.id.DiggingMoment);
        DiggingCrookiNumber = findViewById(R.id.DiggingCrookiNumber);
        DiggingCrookiMoment = findViewById(R.id.DiggingCrookiMoment);
        DiggingType = findViewById(R.id.DiggingType);
        DiggingWellDepth = findViewById(R.id.DiggingWellDepth);
        DiggingDiameterInMeters = findViewById(R.id.DiggingDiameterInMeters);
        DiggingPipeWorkDiameterInMeters = findViewById(R.id.DiggingPipeWorkDiameterInMeters);
        PipeWorkMaterial = findViewById(R.id.PipeWorkMaterial);
        LatticePipeLongInMeters = findViewById(R.id.LatticePipeLongInMeters);
        LatticePipeFromInMeters = findViewById(R.id.LatticePipeFromInMeters);
        LatticePipeToInMeters = findViewById(R.id.LatticePipeToInMeters);
        WaterTouchedInDepthInMeters = findViewById(R.id.WaterTouchedInDepthInMeters);
        StaticDepthAsFinishingPipingInMeters = findViewById(R.id.StaticDepthAsFinishingPipingInMeters);
        DiggingStartMoment = findViewById(R.id.DiggingStartMoment);
        DiggingFinishMoment = findViewById(R.id.DiggingFinishMoment);
        DiggingCompanyName = findViewById(R.id.DiggingCompanyName);
        DiggingEngineerName = findViewById(R.id.DiggingEngineerName);
        UsageNumber = findViewById(R.id.UsageNumber);
        UsageMoment = findViewById(R.id.UsageMoment);
        UsageWellDepth = findViewById(R.id.UsageWellDepth);
        UsageWaterFlowPipeDiametersInMeters = findViewById(R.id.UsageWaterFlowPipeDiametersInMeters);
        PumpInstallationDepth = findViewById(R.id.PumpInstallationDepth);
        ElectricitySubscriptionNumber = findViewById(R.id.ElectricitySubscriptionNumber);
        ElectricitySubscriptionIdentifier = findViewById(R.id.ElectricitySubscriptionIdentifier);
        DrivingForce = findViewById(R.id.DrivingForce);
        PumpType = findViewById(R.id.PumpType);
        LandArea = findViewById(R.id.LandArea);
        MaxExtractionInLitersPerSecond = findViewById(R.id.MaxExtractionInLitersPerSecond);
        MaxExtractionInCubicMetersPerDay = findViewById(R.id.MaxExtractionInCubicMetersPerDay);
        MaxExtractionInCubicMetersPerYear = findViewById(R.id.MaxExtractionInCubicMetersPerYear);
        MaxPowerOfMotorInKiloWats = findViewById(R.id.MaxPowerOfMotorInKiloWats);
        UptimeHoursPerYear = findViewById(R.id.UptimeHoursPerYear);


        StateOfUseLay = findViewById(R.id.StateOfUseLay);
        FileClassLay = findViewById(R.id.FileClassLay);
        TwelveDigitsCodeLay = findViewById(R.id.TwelveDigitsCodeLay);
        GlobalAuthenticationCodeLay = findViewById(R.id.GlobalAuthenticationCodeLay);
        UtmXLay = findViewById(R.id.UtmXLay);
        UtmYLay = findViewById(R.id.UtmYLay);
        ProvinceLay = findViewById(R.id.ProvinceLay);
        CityLay = findViewById(R.id.CityLay);
        VillageLay = findViewById(R.id.VillageLay);
        StateLay = findViewById(R.id.StateLay);
        RegionNameLay = findViewById(R.id.RegionNameLay);
        RegionCodeLay = findViewById(R.id.RegionCodeLay);
        PlainNameLay = findViewById(R.id.PlainNameLay);
        DiggingNumberLay = findViewById(R.id.DiggingNumberLay);
        DiggingMomentLay = findViewById(R.id.DiggingMomentLay);
        DiggingCrookiNumberLay = findViewById(R.id.DiggingCrookiNumberLay);
        DiggingCrookiMomentLay = findViewById(R.id.DiggingCrookiMomentLay);
        DiggingTypeLay = findViewById(R.id.DiggingTypeLay);
        DiggingWellDepthLay = findViewById(R.id.DiggingWellDepthLay);
        DiggingDiameterInMetersLay = findViewById(R.id.DiggingDiameterInMetersLay);
        DiggingPipeWorkDiameterInMetersLay = findViewById(R.id.DiggingPipeWorkDiameterInMetersLay);
        PipeWorkMaterialLay = findViewById(R.id.PipeWorkMaterialLay);
        LatticePipeLongInMetersLay = findViewById(R.id.LatticePipeLongInMetersLay);
        LatticePipeFromInMetersLay = findViewById(R.id.LatticePipeFromInMetersLay);
        LatticePipeToInMetersLay = findViewById(R.id.LatticePipeToInMetersLay);
        WaterTouchedInDepthInMetersLay = findViewById(R.id.WaterTouchedInDepthInMetersLay);
        StaticDepthAsFinishingPipingInMetersLay = findViewById(R.id.StaticDepthAsFinishingPipingInMetersLay);
        DiggingStartMomentLay = findViewById(R.id.DiggingStartMomentLay);
        DiggingFinishMomentLay = findViewById(R.id.DiggingFinishMomentLay);
        DiggingCompanyNameLay = findViewById(R.id.DiggingCompanyNameLay);
        DiggingEngineerNameLay = findViewById(R.id.DiggingEngineerNameLay);
        UsageNumberLay = findViewById(R.id.UsageNumberLay);
        UsageMomentLay = findViewById(R.id.UsageMomentLay);
        UsageWellDepthLay = findViewById(R.id.UsageWellDepthLay);
        UsageWaterFlowPipeDiametersInMetersLay = findViewById(R.id.UsageWaterFlowPipeDiametersInMetersLay);
        PumpInstallationDepthLay = findViewById(R.id.PumpInstallationDepthLay);
        ElectricitySubscriptionNumberLay = findViewById(R.id.ElectricitySubscriptionNumberLay);
        ElectricitySubscriptionIdentifierLay = findViewById(R.id.ElectricitySubscriptionIdentifierLay);
        DrivingForceLay = findViewById(R.id.DrivingForceLay);
        PumpTypeLay = findViewById(R.id.PumpTypeLay);
        LandAreaLay = findViewById(R.id.LandAreaLay);
        MaxExtractionInLitersPerSecondLay = findViewById(R.id.MaxExtractionInLitersPerSecondLay);
        MaxExtractionInCubicMetersPerDayLay = findViewById(R.id.MaxExtractionInCubicMetersPerDayLay);
        MaxExtractionInCubicMetersPerYearLay = findViewById(R.id.MaxExtractionInCubicMetersPerYearLay);
        MaxPowerOfMotorInKiloWatsLay = findViewById(R.id.MaxPowerOfMotorInKiloWatsLay);
        UptimeHoursPerYearLay = findViewById(R.id.UptimeHoursPerYearLay);


//        StateOfUse.setTypeface(typeface);
//        FileClass.setTypeface(typeface);
//        TwelveDigitsCode.setTypeface(typeface);
//        GlobalAuthenticationCode.setTypeface(typeface);
//        UtmX.setTypeface(typeface);
//        UtmY.setTypeface(typeface);
//        Province.setTypeface(typeface);
//        City.setTypeface(typeface);
//        Village.setTypeface(typeface);
//        State.setTypeface(typeface);
//        RegionName.setTypeface(typeface);
//        RegionCode.setTypeface(typeface);
//        PlainName.setTypeface(typeface);
//        DiggingNumber.setTypeface(typeface);
//        DiggingMoment.setTypeface(typeface);
//        DiggingCrookiNumber.setTypeface(typeface);
//        DiggingCrookiMoment.setTypeface(typeface);
//        DiggingType.setTypeface(typeface);
//        DiggingWellDepth.setTypeface(typeface);
//        DiggingDiameterInMeters.setTypeface(typeface);
//        DiggingPipeWorkDiameterInMeters.setTypeface(typeface);
//        PipeWorkMaterial.setTypeface(typeface);
//        LatticePipeLongInMeters.setTypeface(typeface);
//        LatticePipeFromInMeters.setTypeface(typeface);
//        LatticePipeToInMeters.setTypeface(typeface);
//        WaterTouchedInDepthInMeters.setTypeface(typeface);
//        StaticDepthAsFinishingPipingInMeters.setTypeface(typeface);
//        DiggingStartMoment.setTypeface(typeface);
//        DiggingFinishMoment.setTypeface(typeface);
//        DiggingCompanyName.setTypeface(typeface);
//        DiggingEngineerName.setTypeface(typeface);
//        UsageNumber.setTypeface(typeface);
//        UsageMoment.setTypeface(typeface);
//        UsageWellDepth.setTypeface(typeface);
//        UsageWaterFlowPipeDiametersInMeters.setTypeface(typeface);
//        PumpInstallationDepth.setTypeface(typeface);
//        ElectricitySubscriptionNumber.setTypeface(typeface);
//        ElectricitySubscriptionIdentifier.setTypeface(typeface);
//        DrivingForce.setTypeface(typeface);
//        PumpType.setTypeface(typeface);
//        LandArea.setTypeface(typeface);
//        MaxExtractionInLitersPerSecond.setTypeface(typeface);
//        MaxExtractionInCubicMetersPerDay.setTypeface(typeface);
//        MaxExtractionInCubicMetersPerYear.setTypeface(typeface);
//        MaxPowerOfMotorInKiloWats.setTypeface(typeface);
//        UptimeHoursPerYear.setTypeface(typeface);


        StateOfUseLay.setTypeface(typeface);
        FileClassLay.setTypeface(typeface);
        TwelveDigitsCodeLay.setTypeface(typeface);
        GlobalAuthenticationCodeLay.setTypeface(typeface);
        UtmXLay.setTypeface(typeface);
        UtmYLay.setTypeface(typeface);
        ProvinceLay.setTypeface(typeface);
        CityLay.setTypeface(typeface);
        VillageLay.setTypeface(typeface);
        StateLay.setTypeface(typeface);
        RegionNameLay.setTypeface(typeface);
        RegionCodeLay.setTypeface(typeface);
        PlainNameLay.setTypeface(typeface);
        DiggingNumberLay.setTypeface(typeface);
        DiggingMomentLay.setTypeface(typeface);
        DiggingCrookiNumberLay.setTypeface(typeface);
        DiggingCrookiMomentLay.setTypeface(typeface);
        DiggingTypeLay.setTypeface(typeface);
        DiggingWellDepthLay.setTypeface(typeface);
        DiggingDiameterInMetersLay.setTypeface(typeface);
        DiggingPipeWorkDiameterInMetersLay.setTypeface(typeface);
        PipeWorkMaterialLay.setTypeface(typeface);
        LatticePipeLongInMetersLay.setTypeface(typeface);
        LatticePipeFromInMetersLay.setTypeface(typeface);
        LatticePipeToInMetersLay.setTypeface(typeface);
        WaterTouchedInDepthInMetersLay.setTypeface(typeface);
        StaticDepthAsFinishingPipingInMetersLay.setTypeface(typeface);
        DiggingStartMomentLay.setTypeface(typeface);
        DiggingFinishMomentLay.setTypeface(typeface);
        DiggingCompanyNameLay.setTypeface(typeface);
        DiggingEngineerNameLay.setTypeface(typeface);
        UsageNumberLay.setTypeface(typeface);
        UsageMomentLay.setTypeface(typeface);
        UsageWellDepthLay.setTypeface(typeface);
        UsageWaterFlowPipeDiametersInMetersLay.setTypeface(typeface);
        PumpInstallationDepthLay.setTypeface(typeface);
        ElectricitySubscriptionNumberLay.setTypeface(typeface);
        ElectricitySubscriptionIdentifierLay.setTypeface(typeface);
        DrivingForceLay.setTypeface(typeface);
        PumpTypeLay.setTypeface(typeface);
        LandAreaLay.setTypeface(typeface);
        MaxExtractionInLitersPerSecondLay.setTypeface(typeface);
        MaxExtractionInCubicMetersPerDayLay.setTypeface(typeface);
        MaxExtractionInCubicMetersPerYearLay.setTypeface(typeface);
        MaxPowerOfMotorInKiloWatsLay.setTypeface(typeface);
        UptimeHoursPerYearLay.setTypeface(typeface);


    }


    private void initial(AddWellDataModel list) {
        if (list != null) {
            UtmX.setText(String.valueOf(list.getUtmX()));
            UtmY.setText(String.valueOf(list.getUtmY()));

            Province.setText(list.getProvince());
            City.setText(list.getCity());
            Village.setText(list.getVillage());
            TwelveDigitsCode.setText(list.getTwelveDigitsCode());
            GlobalAuthenticationCode.setText(list.getGlobalAuthenticationCode());
            StateOfUse.setText(list.getStateOfUse());
            FileClass.setText(list.getFileClass());
            State.setText(list.getState());
            RegionCode.setText(list.getRegionCode());
            PlainName.setText(list.getPlainName());
            RegionName.setText(list.getRegionName());
            DiggingNumber.setText(list.getDiggingNumber());
            DiggingMoment.setText(list.getDiggingMoment());
            DiggingCrookiNumber.setText(list.getDiggingCrookiNumber());
            DiggingCrookiMoment.setText(list.getDiggingCrookiMoment());
            DiggingType.setText(list.getDiggingType());
            DiggingWellDepth.setText(list.getDiggingWellDepth());
            DiggingDiameterInMeters.setText(list.getDiggingDiameterInMeters());
            DiggingPipeWorkDiameterInMeters.setText(list.getDiggingPipeWorkDiameterInMeters());
            PipeWorkMaterial.setText(list.getPipeWorkMaterial());
            LatticePipeLongInMeters.setText(list.getLatticePipeLongInMeters());
            LatticePipeFromInMeters.setText(list.getLatticePipeFromInMeters());
            LatticePipeToInMeters.setText(list.getLatticePipeToInMeters());

            WaterTouchedInDepthInMeters.setText(list.getWaterTouchedInDepthInMeters());
            StaticDepthAsFinishingPipingInMeters.setText(list.getStaticDepthAsFinishingPipingInMeters());
            DiggingStartMoment.setText(list.getDiggingStartMoment());
            DiggingFinishMoment.setText(list.getDiggingFinishMoment());
            DiggingCompanyName.setText(list.getDiggingCompanyName());
            DiggingEngineerName.setText(list.getDiggingEngineerName());
            UsageNumber.setText(list.getUsageNumber());
            UsageMoment.setText(list.getUsageMoment());
            UsageWellDepth.setText(list.getUsageWellDepth());

            UsageWaterFlowPipeDiametersInMeters.setText(list.getUsageWaterFlowPipeDiametersInMeters());
            MaxPowerOfMotorInKiloWats.setText(list.getMaxPowerOfMotorInKiloWats());
            MaxExtractionInLitersPerSecond.setText(list.getMaxExtractionInLitersPerSecond());
            MaxExtractionInCubicMetersPerDay.setText(list.getMaxExtractionInCubicMetersPerDay());
            MaxExtractionInCubicMetersPerYear.setText(list.getMaxExtractionInCubicMetersPerYear());
            UptimeHoursPerYear.setText(list.getUptimeHoursPerYear());
            DrivingForce.setText(list.getDrivingForce());
            ElectricitySubscriptionNumber.setText(list.getElectricitySubscriptionNumber());
            ElectricitySubscriptionIdentifier.setText(list.getElectricitySubscriptionIdentifier());
            PumpInstallationDepth.setText(list.getPumpInstallationDepth());
            PumpType.setText(list.getPumpType());
            LandArea.setText(list.getLandArea());
        }
    }

    private void getWellData(String wellId) {

        apiService.getWellDetails(wellId);

        apiService.DataWellDetailsExtractor(new IDataExtractor<AddWellDataModel>() {
            @Override
            public void ExtractData(AddWellDataModel addWellDataModels) {
                initial(addWellDataModels);
            }
        });
    }

    private void editWellData(String wellId){
        apiService.editWell(wellId,editWell());
    }

    public static void start(Context context, String wellId , String customerId) {
        Intent intent = new Intent(context, AddWellActivity.class);
        intent.putExtra(VariableKeys.WellId, wellId);
        intent.putExtra(VariableKeys.CustomerId, customerId);
        context.startActivity(intent);
    }

}
