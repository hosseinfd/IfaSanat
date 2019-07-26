package com.example.ifasanat.ApiService;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.ServerError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.example.ifasanat.DataModel.AddInstallWellActivityDataModel;
import com.example.ifasanat.DataModel.AddWellDataModel;
import com.example.ifasanat.DataModel.CapturesDataModel;
import com.example.ifasanat.DataModel.CustomerDateModel;
import com.example.ifasanat.DataModel.CustomerDetails;
import com.example.ifasanat.DataModel.CustomerDetailsDataModel;
import com.example.ifasanat.DataModel.ItemInstallDataModel;
import com.example.ifasanat.Interface.IDataExtractor;
import com.example.ifasanat.R;
import com.example.ifasanat.UserSharedPrefManager.UserSharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ApiService {

    Context context;
    IDataExtractor iDataExtractor;
    ImageData imageData;
    IaddNewInstallation newInstallation;
    UserSharedPrefManager sharedPrefManager;

    public ApiService() {

    }

    public ApiService(Context context) {
        this.context = context;
        sharedPrefManager = new UserSharedPrefManager(context);
    }

    private String BaseUrl = "http://192.168.1.5:9090/api/user/";

    private String LoginUrl = "app/login";
    private String AppVersionUrl = "app_version";
    private String InstallationListUrl = "installation/list";
    private String CustomerListUrl = "customers/list";
    private String CustomerWellsListUrl = "customers/wells/list";
    private String FilterUrl = "filter_black";
    private String WellDetailUrl = "wells/details";
    private String AddCustomerUrl = "customer/add";
    private String CustomerDetails = "customers/details";
    private String EditCustomerUrl = "customer/edit";
    private String InstallationDetailsUrl = "Details?id=%s";
    private String MeterName = "meter/name";
    private String AddWell = "wells/add";
    private String UpLoadImageUrl = "http://192.168.1.5:9090/file/Gallery/upload";
    private String EditInstallationDetailsUrl = "installation/edit";
    private String EditWellUrl = "wells/edit";
    private String AddInstallationUrl = "installation/add";


    public void Login(final String username, final String password, final IDataExtractor<String> iDataExtractor) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BaseUrl + LoginUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (!response.getString("token").equals("null")) {
                        iDataExtractor.ExtractData(response.toString());
                    } else {
                        Toast.makeText(context, "نام کاربری یا رمز عبور اشتباه است", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, error.networkResponse.toString() , Toast.LENGTH_SHORT).show();
                Toast.makeText(context, error.networkResponse.statusCode + "" , Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("password", password);
                return map;
            }
        };


        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);


    }

    public void GetInstallationList(String count, String searchValue) {

        JSONObject json = new JSONObject();
        try {
            json.put("SearchValue", searchValue);
            json.put("Count", count);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseUrl + InstallationListUrl, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<ItemInstallDataModel> lists = new ArrayList<>();
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ItemInstallDataModel list = new ItemInstallDataModel();
                        JSONObject json = jsonArray.getJSONObject(i);

                        list.setId(json.getInt("id"));
                        list.setKonturNumber(json.getString("sn"));
                        list.setPhoneNumber(json.getString("simcardPhoneNumber"));
                        list.setState(String.valueOf(json.getInt("state")));
                        list.setInstallTime(json.getString("installationMoment"));
                        list.setCostumerName(json.getString("costumerFirstName") + " " + json.getString("costumerLastName"));
                        list.setAddress(json.getString("province") + " " + json.getString("city"));

                        lists.add(list);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

                iDataExtractor.ExtractData(lists);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sharedPrefManager.getToken());
                map.put("content_type", "application/json; charset=utf-8");
                return map;
            }
        };

        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);


    }

    public void GetCustomerList(final String count, final String searchValue) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Count", count);
            jsonObject.put("SearchValue", searchValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseUrl + CustomerListUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<CustomerDateModel> list = new ArrayList<>();
                    JSONArray jsonArray = response.getJSONArray("customers");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        CustomerDateModel model = new CustomerDateModel();
                        model.setId(json.getInt("id"));
                        model.setCustomerName(json.getString("firstName") + " " + json.getString("lastName"));
                        model.setCustomerAddress(json.getString("addresses"));
                        model.setCompanyName(json.getString("companyName"));
                        list.add(model);
                    }
                    iDataExtractor.ExtractData(list);
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(context, R.string.try_again_later, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sharedPrefManager.getToken());
                map.put("content_type", "application/json");
                return map;
            }
        };
        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);

    }

    public void SetFilter(final String count, JSONObject jsonObject) {


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseUrl + FilterUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    List<ItemInstallDataModel> lists = new ArrayList<>();
                    jsonArray = response.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        ItemInstallDataModel list = new ItemInstallDataModel();
                        list.setInstallNumber(String.valueOf(json.getInt("id")));
                        list.setId(json.getInt("id"));
                        list.setKonturNumber(String.valueOf(json.getString("sn")));
                        list.setPhoneNumber(String.valueOf(json.getLong("simcardPhoneNumber")));
                        list.setState(String.valueOf(json.getInt("state")));
                        list.setInstallTime(json.getString("installationMoment"));
                        list.setCostumerName(json.getString("firstName") + " " + json.getString("lastName"));
                        list.setAddress(json.getString("province") + " " + json.getString("city"));

                        lists.add(list);
                    }

                    iDataExtractor.ExtractData(lists);

                } catch (JSONException e) {
                    e.printStackTrace();

                    assert jsonArray != null;
                    if (jsonArray.length() == 0) {

                        Toast.makeText(context, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> map = new HashMap<>();
                map.put("Count", count);
                map.put("token", sharedPrefManager.getToken());
                return map;
            }
        };

        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);

    }

    public void getCustomerWellsList(final String id) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BaseUrl + CustomerWellsListUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        List<CustomerDetailsDataModel> model = new ArrayList<>();
                        try {
                            jsonArray = response.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                CustomerDetailsDataModel list = new CustomerDetailsDataModel();
                                JSONObject json = jsonArray.getJSONObject(i);
                                list.setId(json.getInt("wellId"));
                                list.setUtmx(json.getString("utmx"));
                                list.setUtmy(json.getString("utmy"));
                                list.setTwelveCode(json.getString("twelveDigitsCode"));
                                list.setProvince(json.getString("province"));
                                list.setCity(json.getString("city"));
                                list.setVillage(json.getString("village"));
                                list.setState(json.getString("state"));
                                model.add(list);
                            }

                            iDataExtractor.ExtractData(model);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (jsonArray.length() == 0) {

                                Toast.makeText(context, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sharedPrefManager.getToken());
                map.put("id", id);
                return map;
            }
        };
        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);

    }

    public void getWellDetails(final String id) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BaseUrl + WellDetailUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject json) {
                        try {


                            AddWellDataModel list = new AddWellDataModel();

//                                    list.setId(json.getInt("id"));
                            list.setUtmX(json.getString("utmX"));
                            list.setUtmY(json.getString("utmY"));
                            list.setProvince(json.getString("province"));
                            list.setCity(json.getString("city"));
                            list.setVillage(json.getString("village"));
                            list.setTwelveDigitsCode((json.getString("twelveDigitsCode")));
                            list.setPlainName(json.getString("plainName"));
                            list.setGlobalAuthenticationCode(json.getString("globalAuthenticationCode"));
                            list.setStateOfUse(json.getString("stateOfUse"));
                            list.setFileClass(json.getString("fileClass"));
                            list.setState(json.getString("state"));
                            list.setRegionCode(json.getString("regionCode"));
                            list.setRegionName(json.getString("regionName"));
                            list.setDiggingNumber(json.getString("diggingNumber"));
                            list.setDiggingMoment(json.getString("diggingMoment"));
                            list.setDiggingCrookiNumber(json.getString("diggingCrookiNumber"));
                            list.setDiggingCrookiMoment(json.getString("diggingCrookiMoment"));
                            list.setDiggingType(json.getString("diggingType"));
                            list.setDiggingWellDepth(json.getString("diggingWellDepth"));
                            list.setDiggingDiameterInMeters(json.getString("diggingDiameterInMeters"));
                            list.setDiggingPipeWorkDiameterInMeters(json.getString("diggingPipeWorkDiameterInMeters"));
                            list.setPipeWorkMaterial(json.getString("pipeWorkMaterial"));
                            list.setDiggingStartMoment(json.getString("diggingStartMoment"));
                            list.setDiggingFinishMoment(json.getString("diggingFinishMoment"));
                            list.setDiggingCompanyName(json.getString("diggingCompanyName"));
                            list.setDiggingEngineerName(json.getString("diggingEngineerName"));
                            list.setUsageNumber(json.getString("usageNumber"));
                            list.setUsageMoment(json.getString("usageMoment"));
                            list.setUsageWellDepth(json.getString("usageWellDepth"));
                            list.setElectricitySubscriptionNumber(json.getString("electricitySubscriptionNumber"));
                            list.setElectricitySubscriptionIdentifier(json.getString("electricitySubscriptionIdentifier"));
                            list.setDrivingForce(json.getString("drivingForce"));
                            list.setPumpType(json.getString("pumpType"));
                            list.setUsageType(json.getString("usageType"));
                            list.setLatticePipeLongInMeters(String.valueOf(json.getString("latticePipeLongInMeters")));
                            list.setLatticePipeFromInMeters(String.valueOf(json.getString("latticePipeFromInMeters")));
                            list.setLatticePipeToInMeters(String.valueOf(json.getString("latticePipeToInMeters")));
                            list.setWaterTouchedInDepthInMeters(String.valueOf(json.getString("waterTouchedInDepthInMeters")));
                            list.setStaticDepthAsFinishingPipingInMeters(String.valueOf(json.getString("staticDepthAsFinishingPipingInMeters")));
                            list.setLandArea(String.valueOf(json.getString("landArea")));
                            list.setMaxExtractionInCubicMetersPerDay(String.valueOf(json.getString("maxExtractionInCubicMetersPerDay")));
                            list.setMaxExtractionInCubicMetersPerYear(String.valueOf(json.getString("maxExtractionInCubicMetersPerYear")));
                            list.setUsageWorkingPipeDiameterInMeters(String.valueOf(json.getString("usageWorkingPipeDiameterInMeters")));
                            list.setUsageWaterFlowPipeDiametersInMeters(String.valueOf(json.getString("usageWaterFlowPipeDiametersInMeters")));
                            list.setMaxPowerOfMotorInKiloWats(String.valueOf(json.getString("maxPowerOfMotorInKiloWats")));
                            list.setMaxExtractionInLitersPerSecond(String.valueOf(json.getString("maxExtractionInLitersPerSecond")));
                            list.setPumpInstallationDepth(String.valueOf(json.getString("pumpInstallationDepth")));
                            list.setUptimeHoursPerYear(String.valueOf(json.getString("uptimeHoursPerYea")));

                            iDataExtractor.ExtractData(list);


                        } catch (JSONException e) {
                            if (json.length() == 0) {
                                Toast.makeText(context, R.string.try_again_later, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sharedPrefManager.getToken());
                map.put("id", id);
                return map;
            }
        };
        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);

    }

    public void addCustomer(final JSONObject jsonObject) {


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseUrl + AddCustomerUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    iDataExtractor.ExtractData(response.getString("add"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sharedPrefManager.getToken());
                return map;
            }
        };
        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);

    }

    public void getCustomerDetails(final String customerId, final IgetCustomerDetails igetCustomerDetails) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BaseUrl + CustomerDetails, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("result");
                    JSONObject json = jsonArray.getJSONObject(0);
                    CustomerDetails model = new CustomerDetails();
                    model.setFirstName(json.getString("firstName"));
                    model.setLastName(json.getString("lastName"));
                    model.setCompanyName(json.getString("companyName"));
                    model.setFathersName(json.getString("fathersName"));
                    model.setBirthday(json.getString("birthdayMoment"));
                    model.setCompanyNationalNumber(json.getString("companyNationalNumber"));
                    model.setCompanyRegisterNumber(json.getString("companyRegisterNumber"));
                    model.setCompanyEconomicalNumber(json.getString("companyEconomicalNumber"));
                    model.setNationalNumber(json.getString("nationalNumber"));
                    model.setShenasnameNumber(json.getString("shenasnameNumber"));
                    model.setRegisteredAt(json.getString("registeredAt"));
                    model.setAddress(json.getString("addresses"));

                    igetCustomerDetails.getCustomer(model);
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(context, R.string.try_again_later, Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, R.string.try_again_later , Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sharedPrefManager.getToken());
                map.put("customerId", customerId);
                map.put("Content-Type", "application/json; charset=utf-8");
                return map;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(1800, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);
    }

    public void editCustomer(final String id, final JSONObject jsonObject) {


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseUrl + EditCustomerUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    iDataExtractor.ExtractData(response.getString("add"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, R.string.try_again_later , Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sharedPrefManager.getToken());
                map.put("customerId", id);
                map.put("Content-Type", "application/json; charset=utf-8");
                return map;
            }
        };
        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);

    }

    public void getInstallationDetails(String id) {

        String link = String.format(InstallationDetailsUrl, id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BaseUrl + link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    AddInstallWellActivityDataModel list = new AddInstallWellActivityDataModel();
                    JSONArray jsonArray = response.getJSONArray("result");
                    JSONObject json = jsonArray.getJSONObject(0);
                    list.setId(String.valueOf(json.getString("id")));
                    list.setWellOwner(json.getString("firstName")+" "+json.getString("lastName"));
                    list.setSnNumber(String.valueOf(json.getString("snNumber")));
                    list.setSnName(json.getString("name"));
                    list.setSimcardPhoneNumber(String.valueOf(json.getString("simcardPhoneNumber")));
                    list.setWellId(String.valueOf(json.getString("wellId")));
                    list.setWellAddress(
                            json.getString("province") +" " +
                            json.getString("city")+" " +
                            json.getString("regionName") +" " +
                            json.getString("village"));
                    list.setNetworkState(json.getString("networkState"));
                    list.setInstallerName(String.valueOf(json.getString("installerName")));
                    list.setPlumbsSerialNumber(String.valueOf(json.getString("plumbsSerialNumber")));
                    list.setProjectManagerName(String.valueOf(json.getString("projectManagerName")));
                    list.setDeliveryMoment(String.valueOf(json.getString("deliveryMoment")));
                    list.setInstallationMoment(String.valueOf(json.getString("installationMoment")));
                    list.setProject(String.valueOf(json.getString("project")));
                    list.setBreakerInfo(String.valueOf(json.getString("breakerInfo")));
                    list.setAmount(String.valueOf(json.getString("amount")));
                    list.setUsesItems(String.valueOf(json.getBoolean("usesItems")));
                    list.setDescription(String.valueOf(json.getString("description")));

                    JSONArray array = response.getJSONArray("files");
                    List<CapturesDataModel> capturesDataModel = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        CapturesDataModel model = new CapturesDataModel();
                        model.setImagePath("http://192.168.1.5:9090/file/Gallery/download/"+String.valueOf(array.getString(i)));
                        capturesDataModel.add(model);

                    }

                    imageData.getData(capturesDataModel);
                    iDataExtractor.ExtractData(list);

                } catch (JSONException e) {
                    try {
                        if (response.getInt("statusCode") == 400) {

                            Toast.makeText(context, "برای این چاه نصبی وجود ندارد", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sharedPrefManager.getToken());
                return map;
            }
        };
        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);

    }

    public void editInstallationDetails(final String id, JSONObject jsonObject) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                BaseUrl + EditInstallationDetailsUrl, jsonObject , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    iDataExtractor.ExtractData(response.getString("add"));

                } catch (JSONException e) {

                    iDataExtractor.ExtractData(null);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // As of f605da3 the following should work
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        if (response.statusCode == 400 ){
                            Toast.makeText(context,  "مشکل در سمت سرور" , Toast.LENGTH_SHORT).show();
                        }
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }

                Toast.makeText(context,  "مشکل در سمت سرور" , Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sharedPrefManager.getToken());
                map.put("installationId", id);
                map.put("Content-Type", "application/json; charset=utf-8");
                return map;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(1800,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);

    }

    public void addInstallation(JSONObject jsonObject){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseUrl + AddInstallationUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            newInstallation.addNewInstallation(response.getString("add"),String.valueOf(response.getInt("installationId")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, R.string.try_again_later , Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sharedPrefManager.getToken());
                map.put("Content-Type", "application/json; charset=utf-8");
                return map;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(1800,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);
    }

    public void getMeterName(final String snNumebr) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseUrl + MeterName, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    iDataExtractor.ExtractData(String.valueOf(response.getString("name")));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sharedPrefManager.getToken());
                return map;
            }

            @Override
            public byte[] getBody() {
                return snNumebr.getBytes();

            }
        };
        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);

    }

    public void addWell(final String id, JSONObject jsonObject) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseUrl + AddWell, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    iDataExtractor.ExtractData(response.get("status"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sharedPrefManager.getToken());
                map.put("customerId", id);
                map.put("Content-Type", "application/json; charset=utf-8");
                return map;
            }

        };
        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);

    }

    public void editWell(final String id, JSONObject jsonObject) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseUrl + EditWellUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("done")){

                        Toast.makeText(context, "عملیات با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // As of f605da3 the following should work
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }

                Toast.makeText(context,R.string.try_again_later , Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sharedPrefManager.getToken());
                map.put("wellId", id);
                map.put("Content-Type", "application/json; charset=utf-8");
                return map;
            }

        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(1800, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(jsonObjectRequest);

    }

    public void uploadImage(final String installationId , String imagePath) {

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, UpLoadImageUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iDataExtractor.ExtractData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error + "", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");
                map.put("token", sharedPrefManager.getToken());
                map.put("installationId",installationId);
                return map;
            }
        };

        smr.addStringParam("file", " data text");
        smr.addFile("file", imagePath);

        smr.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        mRequestQueue.add(smr);

    }

    public void DataExtractor(IDataExtractor<List<ItemInstallDataModel>> iDataExtractor) {
        this.iDataExtractor = iDataExtractor;
    }

    public void DataCustomerExtractor(IDataExtractor<List<CustomerDateModel>> iDataExtractor) {
        this.iDataExtractor = iDataExtractor;
    }

    public void DataCustomerWellExtractor(IDataExtractor<List<CustomerDetailsDataModel>> iDataExtractor) {
        this.iDataExtractor = iDataExtractor;
    }

    public void DataWellDetailsExtractor(IDataExtractor<AddWellDataModel> iDataExtractor) {
        this.iDataExtractor = iDataExtractor;
    }

    public void getMessage(IDataExtractor<String> message) {
        this.iDataExtractor = message;
    }

    public void getLoginMessage(IDataExtractor<String> token,IDataExtractor<String> name) {
        this.iDataExtractor = token;
        this.iDataExtractor = name;
    }

    public void DataAddInstallWell(IDataExtractor<AddInstallWellActivityDataModel> iDataExtractor) {
        this.iDataExtractor = iDataExtractor;
    }

    public void DataImage(ImageData imageData) {
        this.imageData = imageData;
    }

    public void AddNewInstallation(IaddNewInstallation newInstallation){
        this.newInstallation = newInstallation;
    }

    public interface ImageData {
        void getData(List<CapturesDataModel> lists);
    }

    public interface IgetCustomerDetails {
        void getCustomer(CustomerDetails customerDetails);
    }

    public interface IaddNewInstallation{
        void addNewInstallation(String addMessage,String installationId);
    }
}
