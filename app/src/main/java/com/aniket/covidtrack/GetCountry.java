package com.aniket.covidtrack;

public class GetCountry {



    public String CountryName;
    public String FlagLink;

    public GetCountry() {

    }

    public GetCountry(String CountryName, String FlagLink) {
        this.CountryName = CountryName;
        this.FlagLink = FlagLink;
    }


    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getFlagLink() {
        return FlagLink;
    }

    public void setFlagLink(String flagLink) {
        FlagLink = flagLink;
    }
}
