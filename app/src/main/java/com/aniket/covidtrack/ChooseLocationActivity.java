package com.aniket.covidtrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChooseLocationActivity extends AppCompatActivity {


    private String url = "https://corona.lmao.ninja/countries?sort=country";

    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<GetCountry> CountryList;
    private RecyclerView.Adapter adapter;
    private FirebaseAnalytics mFirebaseAnalytics;

    ImageButton About;

    private ShimmerFrameLayout clshimmer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);

        mList = findViewById(R.id.main_list);

        CountryList = new ArrayList<>();
        adapter = new GetCountryAdapter(getApplicationContext(),CountryList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        //Creating connection Between Variable and Activity
        About = (ImageButton)findViewById(R.id.btn_about);
        clshimmer = findViewById(R.id.cl_shimmer);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);



        About.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(ChooseLocationActivity.this,AboutActivity.class);
                startActivity(intent);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "About Button");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Intent Activity CLA");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

            }
        });


        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        getData();



    }

    private void getData() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = response.length()-1; i >=0 ; i--) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONObject CountryInfo = jsonObject.getJSONObject("countryInfo");
                        String flag = CountryInfo.getString("flag");

                        GetCountry country = new GetCountry();
                        country.setCountryName(jsonObject.getString("country"));
                        country.setFlagLink(flag);

                        CountryList.add(country);
                    } catch (JSONException e) {
                        e.printStackTrace();
                      clshimmer.hideShimmer();
                    }
                }
                adapter.notifyDataSetChanged();
                clshimmer.hideShimmer();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                clshimmer.hideShimmer();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}