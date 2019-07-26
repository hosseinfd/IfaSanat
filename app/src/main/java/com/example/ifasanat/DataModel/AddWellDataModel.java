package com.example.ifasanat.DataModel;

public class AddWellDataModel {

    private String title;

    private int id;
    private String utmX;
    private String utmY;
    private String province;
    private String city;
    private String village;
    private String twelveDigitsCode;

    private String globalAuthenticationCode;
    private String stateOfUse;
    private String fileClass;
    private String state;
    private String regionCode;
    private String plainName;
    private String regionName;
    private String diggingNumber;
    private String diggingMoment;
    private String diggingCrookiNumber;
    private String diggingCrookiMoment;
    private String diggingType;
    private String diggingWellDepth;
    private String diggingDiameterInMeters;
    private String diggingPipeWorkDiameterInMeters;
    private String pipeWorkMaterial;
    private String latticePipeLongInMeters;
    private String latticePipeFromInMeters;
    private String latticePipeToInMeters;
    private boolean hasPartner;
    private String waterTouchedInDepthInMeters;
    private String staticDepthAsFinishingPipingInMeters;

    private String diggingStartMoment;
    private String diggingFinishMoment;
    private String diggingCompanyName;
    private String diggingEngineerName;
    private String usageNumber;
    private String usageMoment;
    private String usageWellDepth;
    private String UsageWorkingPipeDiameterInMeters ;
    private String UsageWaterFlowPipeDiametersInMeters ;
    private String MaxPowerOfMotorInKiloWats ;
    private String MaxExtractionInLitersPerSecond ;
    private String MaxExtractionInCubicMetersPerDay ;
    private String MaxExtractionInCubicMetersPerYear ;
    private String UptimeHoursPerYear ;
    private String DrivingForce ;
    private String ElectricitySubscriptionNumber ;
    private String ElectricitySubscriptionIdentifier ;
    private String PumpInstallationDepth ;
    private String PumpType ;
    private String UsageType ;
    private String LandArea ;

    private String Rank ;
    private String RepositoryState ;
    private double YearlyExtractVolumeUptime ;
    private String Description ;
    private String UsualUsageType ;
    private String UsageUpToDedication ;
    private String CorrectiveRepositoryState ;
    private String LastExtractionMoment ;
    private double AdditionalExtraction ;
    private double AdditionalExtractionTill5000 ;
    private String LastRenewMoment ;

    //MeterInfo

    private String MeterSerialNumber ;

    private int MeterSize ;

    private String MeterType ;

    private boolean HasBreaker ;

    private boolean HasValve ;

    private boolean HasFahamMeter ;

    private boolean HasExternalPower ;

    private String MeterInstallMoment ;

    private String SIMCardPhoneNumber ;

    private String SoftwareVersion ;

    private String HardwareVersion ;



    private String CardSerialNumber ;

    public String getGlobalAuthenticationCode() {
        return globalAuthenticationCode;
    }

    public void setGlobalAuthenticationCode(String globalAuthenticationCode) {
        this.globalAuthenticationCode = globalAuthenticationCode;
    }

    public void setTwelveDigitsCode(String twelveDigitsCode) {
        this.twelveDigitsCode = twelveDigitsCode;
    }

    public String getStateOfUse() {
        return stateOfUse;
    }

    public void setStateOfUse(String stateOfUse) {
        this.stateOfUse = stateOfUse;
    }

    public String getFileClass() {
        return fileClass;
    }

    public void setFileClass(String fileClass) {
        this.fileClass = fileClass;
    }

    public boolean isHasPartner() {
        return hasPartner;
    }

    public void setHasPartner(boolean hasPartner) {
        this.hasPartner = hasPartner;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDiggingNumber() {
        return diggingNumber;
    }

    public void setDiggingNumber(String diggingNumber) {
        this.diggingNumber = diggingNumber;
    }

    public String getDiggingMoment() {
        return diggingMoment;
    }

    public void setDiggingMoment(String diggingMoment) {
        this.diggingMoment = diggingMoment;
    }

    public String getDiggingCrookiNumber() {
        return diggingCrookiNumber;
    }

    public void setDiggingCrookiNumber(String diggingCrookiNumber) {
        this.diggingCrookiNumber = diggingCrookiNumber;
    }

    public String getDiggingCrookiMoment() {
        return diggingCrookiMoment;
    }

    public void setDiggingCrookiMoment(String diggingCrookiMoment) {
        this.diggingCrookiMoment = diggingCrookiMoment;
    }

    public String getDiggingWellDepth() {
        return diggingWellDepth;
    }

    public void setDiggingWellDepth(String diggingWellDepth) {
        this.diggingWellDepth = diggingWellDepth;
    }

    public String getLatticePipeLongInMeters() {
        return latticePipeLongInMeters;
    }

    public void setLatticePipeLongInMeters(String latticePipeLongInMeters) {
        this.latticePipeLongInMeters = latticePipeLongInMeters;
    }

    public String getLatticePipeFromInMeters() {
        return latticePipeFromInMeters;
    }

    public void setLatticePipeFromInMeters(String latticePipeFromInMeters) {
        this.latticePipeFromInMeters = latticePipeFromInMeters;
    }

    public String getWaterTouchedInDepthInMeters() {
        return waterTouchedInDepthInMeters;
    }

    public void setWaterTouchedInDepthInMeters(String waterTouchedInDepthInMeters) {
        this.waterTouchedInDepthInMeters = waterTouchedInDepthInMeters;
    }

    public String getDiggingStartMoment() {
        return diggingStartMoment;
    }

    public void setDiggingStartMoment(String diggingStartMoment) {
        this.diggingStartMoment = diggingStartMoment;
    }

    public String getDiggingFinishMoment() {
        return diggingFinishMoment;
    }

    public void setDiggingFinishMoment(String diggingFinishMoment) {
        this.diggingFinishMoment = diggingFinishMoment;
    }

    public String getDiggingCompanyName() {
        return diggingCompanyName;
    }

    public void setDiggingCompanyName(String diggingCompanyName) {
        this.diggingCompanyName = diggingCompanyName;
    }

    public String getDiggingEngineerName() {
        return diggingEngineerName;
    }

    public void setDiggingEngineerName(String diggingEngineerName) {
        this.diggingEngineerName = diggingEngineerName;
    }

    public String getUsageNumber() {
        return usageNumber;
    }

    public void setUsageNumber(String usageNumber) {
        this.usageNumber = usageNumber;
    }

    public String getUsageMoment() {
        return usageMoment;
    }

    public void setUsageMoment(String usageMoment) {
        this.usageMoment = usageMoment;
    }

    public String getUsageWellDepth() {
        return usageWellDepth;
    }

    public void setUsageWellDepth(String usageWellDepth) {
        this.usageWellDepth = usageWellDepth;
    }

    public String getUsageWorkingPipeDiameterInMeters() {
        return UsageWorkingPipeDiameterInMeters;
    }

    public void setUsageWorkingPipeDiameterInMeters(String usageWorkingPipeDiameterInMeters) {
        UsageWorkingPipeDiameterInMeters = usageWorkingPipeDiameterInMeters;
    }

    public String getUsageWaterFlowPipeDiametersInMeters() {
        return UsageWaterFlowPipeDiametersInMeters;
    }

    public void setUsageWaterFlowPipeDiametersInMeters(String usageWaterFlowPipeDiametersInMeters) {
        UsageWaterFlowPipeDiametersInMeters = usageWaterFlowPipeDiametersInMeters;
    }

    public String getMaxPowerOfMotorInKiloWats() {
        return MaxPowerOfMotorInKiloWats;
    }

    public void setMaxPowerOfMotorInKiloWats(String maxPowerOfMotorInKiloWats) {
        MaxPowerOfMotorInKiloWats = maxPowerOfMotorInKiloWats;
    }

    public String getMaxExtractionInLitersPerSecond() {
        return MaxExtractionInLitersPerSecond;
    }

    public void setMaxExtractionInLitersPerSecond(String maxExtractionInLitersPerSecond) {
        MaxExtractionInLitersPerSecond = maxExtractionInLitersPerSecond;
    }

    public String getMaxExtractionInCubicMetersPerDay() {
        return MaxExtractionInCubicMetersPerDay;
    }

    public void setMaxExtractionInCubicMetersPerDay(String maxExtractionInCubicMetersPerDay) {
        MaxExtractionInCubicMetersPerDay = maxExtractionInCubicMetersPerDay;
    }

    public String getMaxExtractionInCubicMetersPerYear() {
        return MaxExtractionInCubicMetersPerYear;
    }

    public void setMaxExtractionInCubicMetersPerYear(String maxExtractionInCubicMetersPerYear) {
        MaxExtractionInCubicMetersPerYear = maxExtractionInCubicMetersPerYear;
    }

    public String getUptimeHoursPerYear() {
        return UptimeHoursPerYear;
    }

    public void setUptimeHoursPerYear(String uptimeHoursPerYear) {
        UptimeHoursPerYear = uptimeHoursPerYear;
    }

    public String getDrivingForce() {
        return DrivingForce;
    }

    public void setDrivingForce(String drivingForce) {
        DrivingForce = drivingForce;
    }

    public String getElectricitySubscriptionNumber() {
        return ElectricitySubscriptionNumber;
    }

    public void setElectricitySubscriptionNumber(String electricitySubscriptionNumber) {
        ElectricitySubscriptionNumber = electricitySubscriptionNumber;
    }

    public String getElectricitySubscriptionIdentifier() {
        return ElectricitySubscriptionIdentifier;
    }

    public void setElectricitySubscriptionIdentifier(String electricitySubscriptionIdentifier) {
        ElectricitySubscriptionIdentifier = electricitySubscriptionIdentifier;
    }

    public String getPumpInstallationDepth() {
        return PumpInstallationDepth;
    }

    public void setPumpInstallationDepth(String pumpInstallationDepth) {
        PumpInstallationDepth = pumpInstallationDepth;
    }

    public String getPumpType() {
        return PumpType;
    }

    public void setPumpType(String pumpType) {
        PumpType = pumpType;
    }

    public String getUsageType() {
        return UsageType;
    }

    public void setUsageType(String usageType) {
        UsageType = usageType;
    }

    public String getLandArea() {
        return LandArea;
    }

    public void setLandArea(String landArea) {
        LandArea = landArea;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getRepositoryState() {
        return RepositoryState;
    }

    public void setRepositoryState(String repositoryState) {
        RepositoryState = repositoryState;
    }

    public double getYearlyExtractVolumeUptime() {
        return YearlyExtractVolumeUptime;
    }

    public void setYearlyExtractVolumeUptime(double yearlyExtractVolumeUptime) {
        YearlyExtractVolumeUptime = yearlyExtractVolumeUptime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUsualUsageType() {
        return UsualUsageType;
    }

    public void setUsualUsageType(String usualUsageType) {
        UsualUsageType = usualUsageType;
    }

    public String getUsageUpToDedication() {
        return UsageUpToDedication;
    }

    public void setUsageUpToDedication(String usageUpToDedication) {
        UsageUpToDedication = usageUpToDedication;
    }

    public String getCorrectiveRepositoryState() {
        return CorrectiveRepositoryState;
    }

    public void setCorrectiveRepositoryState(String correctiveRepositoryState) {
        CorrectiveRepositoryState = correctiveRepositoryState;
    }

    public String getLastExtractionMoment() {
        return LastExtractionMoment;
    }

    public void setLastExtractionMoment(String lastExtractionMoment) {
        LastExtractionMoment = lastExtractionMoment;
    }

    public double getAdditionalExtraction() {
        return AdditionalExtraction;
    }

    public void setAdditionalExtraction(double additionalExtraction) {
        AdditionalExtraction = additionalExtraction;
    }

    public double getAdditionalExtractionTill5000() {
        return AdditionalExtractionTill5000;
    }

    public void setAdditionalExtractionTill5000(double additionalExtractionTill5000) {
        AdditionalExtractionTill5000 = additionalExtractionTill5000;
    }

    public String getLastRenewMoment() {
        return LastRenewMoment;
    }

    public void setLastRenewMoment(String lastRenewMoment) {
        LastRenewMoment = lastRenewMoment;
    }

    public String getMeterSerialNumber() {
        return MeterSerialNumber;
    }

    public void setMeterSerialNumber(String meterSerialNumber) {
        MeterSerialNumber = meterSerialNumber;
    }

    public int getMeterSize() {
        return MeterSize;
    }

    public void setMeterSize(int meterSize) {
        MeterSize = meterSize;
    }

    public String getMeterType() {
        return MeterType;
    }

    public void setMeterType(String meterType) {
        MeterType = meterType;
    }

    public boolean isHasBreaker() {
        return HasBreaker;
    }

    public void setHasBreaker(boolean hasBreaker) {
        HasBreaker = hasBreaker;
    }

    public boolean isHasValve() {
        return HasValve;
    }

    public void setHasValve(boolean hasValve) {
        HasValve = hasValve;
    }

    public boolean isHasFahamMeter() {
        return HasFahamMeter;
    }

    public void setHasFahamMeter(boolean hasFahamMeter) {
        HasFahamMeter = hasFahamMeter;
    }

    public boolean isHasExternalPower() {
        return HasExternalPower;
    }

    public void setHasExternalPower(boolean hasExternalPower) {
        HasExternalPower = hasExternalPower;
    }

    public String getMeterInstallMoment() {
        return MeterInstallMoment;
    }

    public void setMeterInstallMoment(String meterInstallMoment) {
        MeterInstallMoment = meterInstallMoment;
    }

    public String getSIMCardPhoneNumber() {
        return SIMCardPhoneNumber;
    }

    public void setSIMCardPhoneNumber(String SIMCardPhoneNumber) {
        this.SIMCardPhoneNumber = SIMCardPhoneNumber;
    }

    public String getSoftwareVersion() {
        return SoftwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        SoftwareVersion = softwareVersion;
    }

    public String getHardwareVersion() {
        return HardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        HardwareVersion = hardwareVersion;
    }

    public String getCardSerialNumber() {
        return CardSerialNumber;
    }

    public void setCardSerialNumber(String cardSerialNumber) {
        CardSerialNumber = cardSerialNumber;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    private String CardType ;





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUtmX() {
        return utmX;
    }

    public void setUtmX(String utmX) {
        this.utmX = utmX;
    }

    public String getUtmY() {
        return utmY;
    }

    public void setUtmY(String utmY) {
        this.utmY = utmY;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getRegionName() {
        return state;
    }

    public void setRegionName(String regionName) {
        this.state = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }


    public String getTwelveDigitsCode() {
        return twelveDigitsCode;
    }

    public String getPlainName() {
        return plainName;
    }

    public void setPlainName(String plainName) {
        this.plainName = plainName;
    }

    public String getDiggingType() {
        return diggingType;
    }

    public void setDiggingType(String diggingType) {
        this.diggingType = diggingType;
    }

    public String getDiggingDiameterInMeters() {
        return diggingDiameterInMeters;
    }

    public void setDiggingDiameterInMeters(String diggingDiameterInMeters) {
        this.diggingDiameterInMeters = diggingDiameterInMeters;
    }

    public String getDiggingPipeWorkDiameterInMeters() {
        return diggingPipeWorkDiameterInMeters;
    }

    public void setDiggingPipeWorkDiameterInMeters(String diggingPipeWorkDiameterInMeters) {
        this.diggingPipeWorkDiameterInMeters = diggingPipeWorkDiameterInMeters;
    }

    public String getPipeWorkMaterial() {
        return pipeWorkMaterial;
    }

    public void setPipeWorkMaterial(String pipeWorkMaterial) {
        this.pipeWorkMaterial = pipeWorkMaterial;
    }

    public String getLatticePipeToInMeters() {
        return latticePipeToInMeters;
    }

    public void setLatticePipeToInMeters(String latticePipeToInMeters) {
        this.latticePipeToInMeters = latticePipeToInMeters;
    }

    public String getStaticDepthAsFinishingPipingInMeters() {
        return staticDepthAsFinishingPipingInMeters;
    }

    public void setStaticDepthAsFinishingPipingInMeters(String staticDepthAsFinishingPipingInMeters) {
        this.staticDepthAsFinishingPipingInMeters = staticDepthAsFinishingPipingInMeters;
    }
}
