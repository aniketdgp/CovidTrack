package com.aniket.covidtrack;

public class GetState {

    public String StateName,StateConfirmedCases,StateDischarged,StateDeaths,StateConfirmedForeignCases;

    public GetState(String stateName, String stateConfirmedCases, String stateDischarged, String stateDeaths, String stateConfirmedForeignCases) {
        StateName = stateName;
        StateConfirmedCases = stateConfirmedCases;
        StateDischarged = stateDischarged;
        StateDeaths = stateDeaths;
        StateConfirmedForeignCases = stateConfirmedForeignCases;
    }


    public GetState() {
    }


    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getStateConfirmedCases() {
        return StateConfirmedCases;
    }

    public void setStateConfirmedCases(String stateConfirmedCases) {
        StateConfirmedCases = stateConfirmedCases;
    }

    public String getStateDischarged() {
        return StateDischarged;
    }

    public void setStateDischarged(String stateDischarged) {
        StateDischarged = stateDischarged;
    }

    public String getStateDeaths() {
        return StateDeaths;
    }

    public void setStateDeaths(String stateDeaths) {
        StateDeaths = stateDeaths;
    }

    public String getStateConfirmedForeignCases() {
        return StateConfirmedForeignCases;
    }

    public void setStateConfirmedForeignCases(String stateConfirmedForeignCases) {
        StateConfirmedForeignCases = stateConfirmedForeignCases;
    }
}
