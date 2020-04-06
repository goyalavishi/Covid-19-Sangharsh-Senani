package com.example.svsucss.HomeFragments.leaderboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.svsucss.ContributionDataModel;
import com.example.svsucss.R;

import java.util.ArrayList;

public class ContributionAdapter extends RecyclerView.Adapter<ContributionAdapter.ContributionCardHolder>{

    ArrayList<ContributionDataModel> contributionList;
    Context context;

    public ContributionAdapter(Context context, ArrayList<ContributionDataModel> contributionList)
    {
        Log.d("Inside Adapter","Hello");
        this.context=context;
        this.contributionList=contributionList;
        Log.e("size",contributionList.size()+"");
    }

    @NonNull
    @Override
    public ContributionCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_contribution,parent,false);
        ContributionCardHolder cardHolder=new ContributionCardHolder(view);
        return cardHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContributionCardHolder holder, int position) {

        Log.d("position",position+"");
        holder.name.setText(contributionList.get(position).getName());
        holder.date.setText(contributionList.get(position).getDate());
        holder.dryration.setText((int) contributionList.get(position).getDry_ration()+" Kg");
        holder.packedfood.setText(contributionList.get(position).getPacked_food()+ " Packets");
        holder.sponsi.setText(contributionList.get(position).getSponsi());
        holder.place.setText(contributionList.get(position).getPlace());
    }

    @Override
    public int getItemCount() {

        return contributionList.size();

    }

    public static class ContributionCardHolder extends RecyclerView.ViewHolder
    {
        TextView name,packedfood,dryration,sponsi,place;
        TextView date;

        public ContributionCardHolder(View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.contributorName);
            date=itemView.findViewById(R.id.tv_date);
            place=itemView.findViewById(R.id.tv_place);
            sponsi=itemView.findViewById(R.id.tv_sponsi);
            packedfood=itemView.findViewById(R.id.tv_packed_food);
            dryration=itemView.findViewById(R.id.tv_dry_ration);

        }

    }
}
