package com.aniket.covidtrack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aniket.covidtrack.ShareAdapters.CountryData;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    // Url to make request
    private static String url ;
    // JSON Node names
    private static final String TAG_COUNTRYNAME = "country";
    private static final String TAG_COUNTRYINFO = "countryInfo";
    private static final String TAG_FLAGURL = "flag";
    private static final String TAG_CASES = "cases";
    private static final String TAG_TODAYCASES = "todayCases";
    private static final String TAG_DEATH   = "deaths";
    private static final String TAG_TODAYDEATHS = "todayDeaths";
    private static final String TAG_RECOVERED = "recovered";
    private static final String TAG_ACTIVE = "active";
    private static final String TAG_CRITICAL = "critical";
    private static final String TAG_CASESPERONEMILLION = "casesPerOneMillion";
    private static final String TAG_DEATHSPERONEMILLION = "deathsPerOneMillion";
    private RequestQueue mQueue;
    private ShimmerFrameLayout mshimmer0,mshimmer1,mshimmer2,mshimmer3;
    private FirebaseAnalytics mFirebaseAnalytics;

    Runnable refresh;
    Handler handler;

    TextView TotalCases,TotalDeaths,NewCases,NewDeaths;
    TextView ActiveCases,CriticalCases,CasesPerMillion,DeathPerMillion,Recovered,CountryName;
    ImageView Flag;
    String countryname;
    ImageButton ChooseLocation,GlobalCase,About,ShareData;
    Button StateWise,AppUpdate;
    ScrollView Scv;
    CountryData cd ;

    String appversion,downloadlink,updatefeatures;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Setting Connections To Layout
        mshimmer1 = findViewById(R.id.ma_shimmer1);
        mshimmer2 = findViewById(R.id.ma_shimmer2);
        mshimmer3 = findViewById(R.id.ma_shimmer3);
        //mshimmer0 = findViewById(R.id.ma_shimmer0);


        CountryName = (TextView)findViewById(R.id.tv_countryname);
        TotalCases = (TextView)findViewById(R.id.tv_totalcases);
        TotalDeaths = (TextView)findViewById(R.id.tv_totaldeath);
        NewDeaths = (TextView)findViewById(R.id.tv_newdeath);
        NewCases = (TextView)findViewById(R.id.tv_newcases);
        ActiveCases = (TextView)findViewById(R.id.tv_activecases);
        CriticalCases = (TextView)findViewById(R.id.tv_criticalcases);
        CasesPerMillion = (TextView)findViewById(R.id.tv_casespermillion);
        DeathPerMillion = (TextView)findViewById(R.id.tv_deathpermillion);
        Recovered = (TextView)findViewById(R.id.tv_recoveredcases);
        Flag = (ImageView)findViewById(R.id.iv_flag);
        ChooseLocation = (ImageButton)findViewById(R.id.btn_location);
        GlobalCase = (ImageButton)findViewById(R.id.btn_globaldata);
        About = (ImageButton)findViewById(R.id.btn_about);
        StateWise = (Button)findViewById(R.id.btn_statewise);
        ShareData = (ImageButton)findViewById(R.id.btn_share);
        AppUpdate = (Button)findViewById(R.id.updatebutton);
        Scv = (ScrollView)findViewById(R.id.scv);
        mQueue = Volley.newRequestQueue(this);
        cd = new CountryData();
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Setting DefaultText


        CountryName.setText("");
        TotalCases.setText("");
        NewCases.setText("");
        TotalDeaths.setText("");
        NewDeaths.setText("");
        Recovered.setText("");
        ActiveCases.setText("");
        CriticalCases.setText("");
        CasesPerMillion.setText("");
        DeathPerMillion.setText("");
        Flag.setImageBitmap(null);





        ChooseLocation.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,ChooseLocationActivity.class);
                startActivity(intent);

            }
        });


        GlobalCase.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,GlobalCasesActivity.class);
                startActivity(intent);

            }
        });


        About.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "About Button");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Intent Activity MA");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

            }
        });



        StateWise.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,IndianStateActivity.class);
                startActivity(intent);

            }
        });


        //Get Country Name
        countryname = getIntent().getStringExtra("country");
        //System.out.println(countryname);


        if(countryname == null){
            countryname = "India";
        }


        if(countryname.equals("India")){
            StateWise.setVisibility(View.VISIBLE);
        }
        else{
            StateWise.setVisibility(View.GONE);
            Scv.setPadding(0,0,0,30);
        }

        url = "https://corona.lmao.ninja/countries/"+countryname;
        jsonParse();
        checkUpdate();


        //Auto Refresh Function in 1s or 1000ms
        handler = new Handler();
        refresh = new Runnable() {
            public void run() {

                //Start UpdateService App
                Intent restartServiceIntentApp = new Intent(MainActivity.this, AppUpdateService.class);
                restartServiceIntentApp.setPackage(getPackageName());
                startService(restartServiceIntentApp);

                handler.postDelayed(refresh, 1000);
            }
        };
        handler.post(refresh);


        //Button To Go to Share App
        ShareData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Share Button");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Share Intent MA");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                final AppVersionData avd = new AppVersionData(MainActivity.this);
                String dl = avd.getUpdateLink();


                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Present COVID-19 Cases in "+cd.getCountryName()+"\n"+
                                "-------------------------------------------------------------" +" \n\n" +
                                "Total Cases : "+ cd.getTotalCases()+"\n"+
                                "Total Deaths "  + cd.getTotalDeaths()+"\n"+
                                "New Cases : " + cd.getNewCases() + "\n"+
                                "New Deaths : " + cd.getNewDeaths() + "\n"+
                                "Active Cases : " + cd.getActiveCases() + "\n"+
                                "Critical Cases : " + cd.getCriticalCases()+"\n"+
                                "Recovered : " + cd.getRecovered() + "\n\n\n"+
                                "Via CovidTrack App : "+"\n"+
                                "Download Now : \n" + dl

                        );
                sendIntent.setType("text/plain");
                startActivity(sendIntent);


            }
        });


        //App Update Button


        AppUpdate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                final AppVersionData avd = new AppVersionData(MainActivity.this);
                downloadlink = avd.getUpdateLink();

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(downloadlink));
                startActivity(i);


            }
        });










    }

    private void jsonParse() {

       // System.out.println("run");
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject CountryInfo = response.getJSONObject(TAG_COUNTRYINFO);
                            String flag = CountryInfo.getString(TAG_FLAGURL);
                            String country = response.getString(TAG_COUNTRYNAME);
                            String cases = response.getString(TAG_CASES);
                            String todaycases = response.getString(TAG_TODAYCASES);
                            String deaths = response.getString(TAG_DEATH);
                            String todaydeath = response.getString(TAG_TODAYDEATHS);
                            String recovered = response.getString(TAG_RECOVERED);
                            String active = response.getString(TAG_ACTIVE);
                            String critical = response.getString(TAG_CRITICAL);
                            String cpm = response.getString(TAG_CASESPERONEMILLION);
                            String dpm = response.getString(TAG_DEATHSPERONEMILLION);



                            cd.setTotalCases(cases);
                            cd.setCountryName(country);
                            cd.setNewCases(todaycases);
                            cd.setTotalDeaths(deaths);
                            cd.setNewDeaths(todaydeath);
                            cd.setRecovered(recovered);
                            cd.setActiveCases(active);
                            cd.setCriticalCases(critical);



                            CountryName.setText(country);
                            TotalCases.setText(cases);
                            NewCases.setText("+"+todaycases);
                            TotalDeaths.setText(deaths);
                            NewDeaths.setText("+"+todaydeath);
                            Recovered.setText(recovered);
                            ActiveCases.setText(active);
                            CriticalCases.setText(critical);
                            CasesPerMillion.setText(cpm);
                            DeathPerMillion.setText(dpm);
                            Picasso.get().load(flag).into(Flag);

                            //System.out.println(flag);

                            mshimmer1.hideShimmer();
                            mshimmer2.hideShimmer();
                            mshimmer3.hideShimmer();
                           // mshimmer0.hideShimmer();



                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }




    void checkUpdate(){


        // int va =100; //  for testing
        final AppVersionData avd = new AppVersionData(MainActivity.this);
        appversion = avd.getAppVersion();
        downloadlink = avd.getUpdateLink();
        updatefeatures = avd.getUpdatefeatures();


        if(appversion==null){
            appversion = getString(R.string.appversion);
        }

        int va = Integer.parseInt(appversion);


        PackageInfo packageInfo = null;
        try {      packageInfo=getPackageManager().getPackageInfo(MainActivity.this.getPackageName(), 0);
            int curVersionCode = packageInfo.versionCode;
            if (curVersionCode < va) {  // instead of one use value get from server for the new update.

                AppUpdate.setVisibility(View.VISIBLE);
                Toast.makeText(this,
                        "New Version Found Download Now \n\n"+"New Version Includes : \n"+updatefeatures,
                        Toast.LENGTH_LONG).show();

            }
            else {
                AppUpdate.setVisibility(View.INVISIBLE);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



    }






    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exit");
        builder.setMessage("Do you want to Exit ? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //saveResult();
                MainActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
              //  MainActivity.super.onBackPressed();
            }
        });
        builder.show();
    }



}