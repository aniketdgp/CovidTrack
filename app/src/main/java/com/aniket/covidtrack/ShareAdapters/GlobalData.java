package com.aniket.covidtrack.ShareAdapters;

public class GlobalData {

    public String  GlobalCases,GlobalDeaths,GlobalRecovery,GlobalActiveCases,AffectedCountry;


    public GlobalData(String globalCases, String globalDeaths, String globalRecovery, String globalActiveCases, String affectedCountry) {
        GlobalCases = globalCases;
        GlobalDeaths = globalDeaths;
        GlobalRecovery = globalRecovery;
        GlobalActiveCases = globalActiveCases;
        AffectedCountry = affectedCountry;
    }


    public GlobalData() {

    }


    public String getGlobalCases() {
        return GlobalCases;
    }

    public void setGlobalCases(String globalCases) {
        GlobalCases = globalCases;
    }

    public String getGlobalDeaths() {
        return GlobalDeaths;
    }

    public void setGlobalDeaths(String globalDeaths) {
        GlobalDeaths = globalDeaths;
    }

    public String getGlobalRecovery() {
        return GlobalRecovery;
    }

    public void setGlobalRecovery(String globalRecovery) {
        GlobalRecovery = globalRecovery;
    }

    public String getGlobalActiveCases() {
        return GlobalActiveCases;
    }

    public void setGlobalActiveCases(String globalActiveCases) {
        GlobalActiveCases = globalActiveCases;
    }

    public String getAffectedCountry() {
        return AffectedCountry;
    }

    public void setAffectedCountry(String affectedCountry) {
        AffectedCountry = affectedCountry;
    }
}
