package com.aniket.covidtrack.ShareAdapters;

public class StateData {

    public String StateName,ConfirmedCases,Discharged,Deaths,ConfirmedForeignCases;


    public StateData(String stateName, String confirmedCases, String discharged, String deaths, String confirmedForeignCases) {
        StateName = stateName;
        ConfirmedCases = confirmedCases;
        Discharged = discharged;
        Deaths = deaths;
        ConfirmedForeignCases = confirmedForeignCases;
    }


    public StateData() {

    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getConfirmedCases() {
        return ConfirmedCases;
    }

    public void setConfirmedCases(String confirmedCases) {
        ConfirmedCases = confirmedCases;
    }

    public String getDischarged() {
        return Discharged;
    }

    public void setDischarged(String discharged) {
        Discharged = discharged;
    }

    public String getDeaths() {
        return Deaths;
    }

    public void setDeaths(String deaths) {
        Deaths = deaths;
    }

    public String getConfirmedForeignCases() {
        return ConfirmedForeignCases;
    }

    public void setConfirmedForeignCases(String confirmedForeignCases) {
        ConfirmedForeignCases = confirmedForeignCases;
    }
}


