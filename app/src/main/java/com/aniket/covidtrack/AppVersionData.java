package com.aniket.covidtrack;

import android.content.Context;
import android.content.SharedPreferences;

public class AppVersionData {

    String appversion;
    String updatelink;
    String updatefeatures;

    Context context;
    SharedPreferences sharedPreferences;


    public void remove(){

        sharedPreferences.edit().clear().commit();

    }



    public String getAppVersion() {
        appversion = sharedPreferences.getString("appversion","");
        return appversion;

    }

    public void setAppVersion(String appversion) {
        this.appversion = appversion;
        sharedPreferences.edit().putString("appversion",appversion).commit();
    }



    public String getUpdateLink() {
        updatelink = sharedPreferences.getString("updatelink","");
        return updatelink;

    }

    public void setUpdateLink(String updateLink) {
        this.updatelink = updateLink;
        sharedPreferences.edit().putString("updatelink",updateLink).commit();
    }


    public String getUpdatefeatures() {
        updatefeatures = sharedPreferences.getString("uf","");
        return updatefeatures;

    }

    public void setUpdatefeatures(String updatefeatures) {
        this.updatefeatures = updatefeatures;
        sharedPreferences.edit().putString("uf",updatefeatures).commit();
    }







    public AppVersionData(Context context){

        this.context = context;
        sharedPreferences  = context.getSharedPreferences("appversiondata",Context.MODE_PRIVATE);

    }



}