package com.aniket.covidtrack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GetCountryAdapter  extends RecyclerView.Adapter<GetCountryAdapter.ViewHolder> {

    private Context context;
    private List<GetCountry> list;


    public GetCountryAdapter(Context context, List<GetCountry> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GetCountry country = list.get(position);

        holder.countryname.setText(country.getCountryName());
        Picasso.get().load(country.getFlagLink()).into(holder.flaglink);




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView countryname;
        public ImageView flaglink;

        public ViewHolder(View itemView) {
            super(itemView);

            countryname = itemView.findViewById(R.id.tv_countryname_list);
            flaglink = itemView.findViewById(R.id.flaglink);


            // Add click listener for root view

           itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    final GetCountry country = list.get(position);
                    //countryInstance.setCountryName(country.getCountryName());
                    Intent intent =  new Intent(context, MainActivity.class);
                    intent.putExtra("country", country.getCountryName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);


                }
            });


        }
    }

}