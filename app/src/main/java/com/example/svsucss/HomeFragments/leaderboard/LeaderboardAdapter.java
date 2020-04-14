package com.example.svsucss.HomeFragments.leaderboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.svsucss.ContributionDataModel;
import com.example.svsucss.R;

import java.util.ArrayList;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardCard> {

    ArrayList<ContributionDataModel> contributionList;
    Context context;

    public LeaderboardAdapter(Context context, ArrayList<ContributionDataModel> contributionList)
    {
        Log.d("Inside Adapter","Hello");
        this.context=context;
        this.contributionList=contributionList;
        Log.e("size",contributionList.size()+"");
    }

    @NonNull
    @Override
    public LeaderboardCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_leaderboard_fragment,parent,false);
        LeaderboardCard cardHolder=new LeaderboardCard(view);
        return cardHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardCard holder, int position) {

        Log.d("position", position + "");
        holder.name.setText(contributionList.get(position).getName());
        holder.dryration.setText((int) contributionList.get(position).getDry_ration() + " Kg");
        holder.packedfood.setText(contributionList.get(position).getPacked_food() + " Packets");
        holder.cash.setText(contributionList.get(position).getCash() + " Rs");
        holder.timeDevoted.setText(contributionList.get(position).getHoursDevoted() + " Hours");
    }

    @Override
    public int getItemCount() {
        return contributionList.size();
    }


    public static class LeaderboardCard extends RecyclerView.ViewHolder
    {
        TextView name,packedfood,dryration,cash,timeDevoted;
        //TextView date;

        public LeaderboardCard(View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            cash=itemView.findViewById(R.id.tv_cash);
            timeDevoted=itemView.findViewById(R.id.tv_time);
            packedfood=itemView.findViewById(R.id.tv_packed_food);
            dryration=itemView.findViewById(R.id.tv_dry_ration);

        }

    }
}
