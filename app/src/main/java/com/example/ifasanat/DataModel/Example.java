package com.example.ifasanat.DataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Example{

  @SerializedName("province")
  @Expose
  private String province;

  @SerializedName("projectManagerName")
  @Expose
  private String projectManagerName;

  @SerializedName("city")
  @Expose
  private String city;

  @SerializedName("installationMoment")
  @Expose
  private String installationMoment;

  @SerializedName("initMoment")
  @Expose
  private String initMoment;

  @SerializedName("id")
  @Expose
  private Integer id;

  @SerializedName("sn")
  @Expose
  private Integer sn;

  @SerializedName("state")
  @Expose
  private Integer state;

  @SerializedName("simcardPhoneNumber")
  @Expose
  private Integer simcardPhoneNumber;



  public void setProvince(String province){
   this.province=province;
  }

  public String getProvince(){
   return province;
  }

  public void setProjectManagerName(String projectManagerName){
   this.projectManagerName=projectManagerName;
  }

  public String getProjectManagerName(){
   return projectManagerName;
  }

  public void setCity(String city){
   this.city=city;
  }

  public String getCity(){
   return city;
  }

  public void setInstallationMoment(String installationMoment){
   this.installationMoment=installationMoment;
  }

  public String getInstallationMoment(){
   return installationMoment;
  }

  public void setInitMoment(String initMoment){
   this.initMoment=initMoment;
  }

  public String getInitMoment(){
   return initMoment;
  }

  public void setId(Integer id){
   this.id=id;
  }

  public Integer getId(){
   return id;
  }

  public void setSn(Integer sn){
   this.sn=sn;
  }

  public Integer getSn(){
   return sn;
  }

  public void setState(Integer state){
   this.state=state;
  }

  public Integer getState(){
   return state;
  }

  public void setSimcardPhoneNumber(Integer simcardPhoneNumber){
   this.simcardPhoneNumber=simcardPhoneNumber;
  }

  public Integer getSimcardPhoneNumber(){
   return simcardPhoneNumber;
  }
}