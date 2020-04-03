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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IndianStateActivity extends AppCompatActivity {

    private String url = "https://api.rootnet.in/covid19-in/stats/latest";
    private static final String TAG_REGIONAL = "regional";
    private ShimmerFrameLayout mShimmerFrameLayout;


    private RecyclerView mList;

    private List<GetState> StateList;
    private RecyclerView.Adapter adapter;
    private RequestQueue mQueue;
    ImageButton About;
    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indian_state);



        mList = findViewById(R.id.main_lister);

        StateList = new ArrayList<>();
        adapter = new GetStateAdapter(getApplicationContext(), StateList);

        //Creating connection Between Variable and Activity
        About = (ImageButton)findViewById(R.id.btn_about);
        mShimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);






        About.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(IndianStateActivity.this,AboutActivity.class);
                startActivity(intent);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "About Button");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Intent Activity ISA");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

            }
        });

        mList.setAdapter(adapter);

        getData();


    }

    private void getData() {




        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                         // System.out.println(response.getJSONObject("data"));

                            JSONObject jab = response.getJSONObject("data");

                           // System.out.println(jab.toString());

                            JSONArray jArray = jab.getJSONArray("regional");


                            for (int i = 0; i < jArray.length() ; i++) {
                                try {


                                    JSONObject jsonObject = jArray.getJSONObject(i);

                                    String loc = jsonObject.getString("loc");
                                    String discharged = jsonObject.getString("discharged");
                                    String confirmedcases = jsonObject.getString("confirmedCasesIndian");
                                    String deaths = jsonObject.getString("deaths");
                                    String confirmedforeigncases = jsonObject.getString("confirmedCasesForeign");



                                    GetState state = new GetState();
                                    state.setStateName(loc);
                                    state.setStateDischarged(discharged);
                                    state.setStateConfirmedCases(confirmedcases);
                                    state.setStateDeaths(deaths);
                                    state.setStateConfirmedForeignCases(confirmedforeigncases);

                                    StateList.add(state);
                                    mShimmerFrameLayout.hideShimmer();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


}

















