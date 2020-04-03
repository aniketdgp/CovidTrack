package com.aniket.covidtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aniket.covidtrack.ShareAdapters.GlobalData;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class GlobalCasesActivity extends AppCompatActivity {

    // Url to make request
    private static String url ;
    // JSON Node names
    private static final String TAG_GLOBALCASES = "cases";
    private static final String TAG_GLOBALDEATHS = "deaths";
    private static final String TAG_GLOBALRECOVERY = "recovered";
    private static final String TAG_GLOBALACTIVECASES = "active";
    private static final String TAG_GLOBALAFFECTEDCOUNTRY = "affectedCountries";

    Runnable refresh;
    Handler handler;

    private RequestQueue mQueue;
    ImageButton ShareData,About;
    GlobalData gd;
    private FirebaseAnalytics mFirebaseAnalytics;

    private ShimmerFrameLayout mshimmer0,mshimmer1;

    TextView GlobalCases,GlobalDeaths,GlobalRecovery,GlobalActiveCases,GlobalAffectedCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_cases);

        //Setting Up Connections To Variable to code
        mshimmer1 = findViewById(R.id.ag_shimmer1);
        mshimmer0 = findViewById(R.id.ag_shimmer0);

        GlobalCases = (TextView)findViewById(R.id.tv_globalcases);
        GlobalDeaths = (TextView)findViewById(R.id.tv_globaldeaths);
        GlobalRecovery = (TextView)findViewById(R.id.tv_globalrecovery);
        GlobalActiveCases = (TextView)findViewById(R.id.tv_globalactivecases);
        GlobalAffectedCountries = (TextView)findViewById(R.id.tv_globalaffectedcountry);
        About = (ImageButton)findViewById(R.id.btn_about);
        ShareData = (ImageButton) findViewById(R.id.btn_share);
        mQueue = Volley.newRequestQueue(this);
        gd = new GlobalData();
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);




        About.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(GlobalCasesActivity.this,AboutActivity.class);
                startActivity(intent);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "About Button");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Intent Activity GC");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

            }
        });



        //Setting Up BlankData For Start

        GlobalCases.setText("");
        GlobalDeaths.setText("");
        GlobalRecovery.setText("");
        GlobalActiveCases.setText("");
        GlobalAffectedCountries.setText("");


        url = "https://corona.lmao.ninja/all";
        jsonParse();

//        //Auto Refresh Function in 1s or 1000ms
//        handler = new Handler();
//        refresh = new Runnable() {
//            public void run() {
//                // Do something
//                jsonParse();
//
//                handler.postDelayed(refresh, 60000);
//            }
//        };
//        handler.post(refresh);





        //Button To Go to Share App
        ShareData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Share Button");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Share Intent GCA");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                final AppVersionData avd = new AppVersionData(GlobalCasesActivity.this);
                String dl = avd.getUpdateLink();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Present COVID-19 Global Cases"+"\n"+
                                "-------------------------------------------------------------" +" \n\n" +
                                "Cases Globally : "+gd.getGlobalCases() +"\n"+
                                "Deaths Globally : "+gd.getGlobalDeaths()+"\n"+
                                "Recovered Globally : "+gd.getGlobalRecovery()+"\n"+
                                "Active Cases Globally : "+gd.getGlobalActiveCases()+"\n"+
                                "Affected Country Globally : " +gd.getAffectedCountry()+"\n\n\n"+

                                "Via CovidTrack App "+"\n"+
                                "Download Now \n" + dl

                );
                sendIntent.setType("text/plain");
                startActivity(sendIntent);


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


                            String globalcases = response.getString(TAG_GLOBALCASES);
                            String globaldeaths = response.getString(TAG_GLOBALDEATHS);
                            String globalrecovery = response.getString(TAG_GLOBALRECOVERY);
                            String globalactivecases = response.getString(TAG_GLOBALACTIVECASES);
                            String globalaffectedcountry = response.getString(TAG_GLOBALAFFECTEDCOUNTRY);


                            gd.setGlobalCases(globalcases);
                            gd.setGlobalDeaths(globaldeaths);
                            gd.setGlobalRecovery(globalrecovery);
                            gd.setGlobalActiveCases(globalactivecases);
                            gd.setAffectedCountry(globalaffectedcountry);



                            GlobalCases.setText(globalcases);
                            GlobalDeaths.setText(globaldeaths);
                            GlobalRecovery.setText(globalrecovery);
                            GlobalActiveCases.setText(globalactivecases);
                            GlobalAffectedCountries.setText(globalaffectedcountry);

                            mshimmer0.hideShimmer();
                            mshimmer1.hideShimmer();

                        } catch (JSONException e) {
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
}
