package com.aniket.covidtrack.ShareAdapters;

public class CountryData {

    public String CountryName,TotalCases,TotalDeaths,NewCases,NewDeaths;
    public String ActiveCases,CriticalCases,CasesPerMillion,DeathPerMillion,Recovered;


    public CountryData(String countryName, String totalCases, String totalDeaths, String newCases, String newDeaths, String activeCases, String criticalCases, String casesPerMillion, String deathPerMillion, String recovered) {
        CountryName = countryName;
        TotalCases = totalCases;
        TotalDeaths = totalDeaths;
        NewCases = newCases;
        NewDeaths = newDeaths;
        ActiveCases = activeCases;
        CriticalCases = criticalCases;
        CasesPerMillion = casesPerMillion;
        DeathPerMillion = deathPerMillion;
        Recovered = recovered;
    }

    public CountryData() {

    }


    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getTotalCases() {
        return TotalCases;
    }

    public void setTotalCases(String totalCases) {
        TotalCases = totalCases;
    }

    public String getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public String getNewCases() {
        return NewCases;
    }

    public void setNewCases(String newCases) {
        NewCases = newCases;
    }

    public String getNewDeaths() {
        return NewDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        NewDeaths = newDeaths;
    }

    public String getActiveCases() {
        return ActiveCases;
    }

    public void setActiveCases(String activeCases) {
        ActiveCases = activeCases;
    }

    public String getCriticalCases() {
        return CriticalCases;
    }

    public void setCriticalCases(String criticalCases) {
        CriticalCases = criticalCases;
    }

    public String getCasesPerMillion() {
        return CasesPerMillion;
    }

    public void setCasesPerMillion(String casesPerMillion) {
        CasesPerMillion = casesPerMillion;
    }

    public String getDeathPerMillion() {
        return DeathPerMillion;
    }

    public void setDeathPerMillion(String deathPerMillion) {
        DeathPerMillion = deathPerMillion;
    }

    public String getRecovered() {
        return Recovered;
    }

    public void setRecovered(String recovered) {
        Recovered = recovered;
    }
}
