package com.aniket.covidtrack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aniket.covidtrack.ShareAdapters.StateData;

import java.util.List;

public class GetStateAdapter extends RecyclerView.Adapter<GetStateAdapter.ViewHolder> {
    private List<GetState> list;
    private Context context;


    public GetStateAdapter(Context context, List<GetState> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_state, parent, false);
        return new GetStateAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GetStateAdapter.ViewHolder holder, int position) {
        GetState state = list.get(position);

        holder.statename.setText(state.getStateName());
        holder.confirmedcases.setText(state.getStateConfirmedCases());
        holder.death.setText(state.getStateDeaths());
        holder.discharged.setText(state.getStateDischarged());
        holder.foreigncases.setText(state.getStateConfirmedForeignCases());





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView statename,confirmedcases,discharged,death,foreigncases;


        public ViewHolder(View itemView) {
            super(itemView);

            statename = itemView.findViewById(R.id.tv_statename);
            confirmedcases = itemView.findViewById(R.id.tv_state_confirmedcases);
            discharged = itemView.findViewById(R.id.tv_state_discharged);
            death = itemView.findViewById(R.id.tv_state_deaths);
            foreigncases = itemView.findViewById(R.id.tv_state_confirmedforeign);



            // Add click listener for root view

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    GetState state = list.get(position);
                    StateData sd = new StateData();
                    sd.setStateName(state.getStateName());
                    sd.setConfirmedCases(state.getStateConfirmedCases());
                    sd.setDischarged(state.getStateDischarged());
                    sd.setDeaths(state.getStateDeaths());
                    sd.setConfirmedForeignCases(state.getStateConfirmedForeignCases());


                    final AppVersionData avd = new AppVersionData(context);
                    String dl = avd.getUpdateLink();

                    Intent sendIntent = new Intent();
                    sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Present COVID-19 Cases in "+sd.getStateName()+"\n"+
                                    "-------------------------------------------------------------" +" \n\n" +
                                    "Confirmed Cases : "+sd.getConfirmedCases()+"\n"+
                                    "Discharged : " + sd.getDischarged()+"\n"+
                                    "Deaths : " + sd.getDeaths()+"\n"+
                                    "Confirmed Foreign Cases : "+sd.getConfirmedForeignCases()+"\n\n\n"+

                                    "Via CovidTrack App : "+"\n"+
                                    "Download Now \n: " + dl

                    );
                    sendIntent.setType("text/plain");
                    context.startActivity(sendIntent);



                }
            });


        }
    }




}
