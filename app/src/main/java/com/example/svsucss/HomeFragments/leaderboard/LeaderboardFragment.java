package com.example.svsucss.HomeFragments.leaderboard;

import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.svsucss.ContributionDataModel;
import com.example.svsucss.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LeaderboardFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    LeaderboardAdapter contributionAdapter;
    ContributionDataModel contributionDataModel,temp;
    ArrayList<ContributionDataModel> contributionDataModelArrayList,combined;
    ArrayList<String> users;
    Long totalpackets ,totalhours;
    Double total_dry_ration,totalcash;
    TextView dryRation,packedFood,timeDevoted,cashDonated;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        combined =new ArrayList<>();
        users=new ArrayList<>();
        recyclerView=root.findViewById(R.id.recyclerView);
        contributionDataModel=new ContributionDataModel();
        contributionDataModelArrayList= new ArrayList<>();
        firebaseFirestore= FirebaseFirestore.getInstance();
        totalpackets= Long.valueOf(0);
        totalcash=0.0;
        totalhours=Long.valueOf(0);
        total_dry_ration=0.0;

        dryRation=root.findViewById(R.id.tv_dry_ration);
        packedFood=root.findViewById(R.id.tv_packed_food);
        cashDonated = root.findViewById(R.id.tv_cash);
        timeDevoted= root.findViewById(R.id.tv_time);

        firebaseFirestore.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                              //  Log.e(TAG, document.getId() + " => " + document.getData());
                                users.add(document.getString("name"));
                            }

                            firebaseFirestore.collection("Contribution")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Log.d(TAG, document.getId() + " => " + document.getData());


                                                   // Log.d("hours",document.getString()+"");

                                                    contributionDataModel=new ContributionDataModel();
                                                    contributionDataModel.setName(document.getString("name"));
                                                    contributionDataModel.setDry_ration((document.getDouble("dry_ration")));
                                                    contributionDataModel.setCash(document.getDouble("cash"));
                                                    contributionDataModel.setHoursDevoted(document.getLong("hoursDevoted"));
                                                    contributionDataModel.setPacked_food(document.getLong("packed_food"));
                                                    total_dry_ration += contributionDataModel.getDry_ration();
                                                    totalpackets += contributionDataModel.getPacked_food();
                                                    totalcash +=contributionDataModel.getCash();
                                                    totalhours += contributionDataModel.getHoursDevoted();
                                                    contributionDataModelArrayList.add(contributionDataModel);
                                                }


                                                for(String user:users) {

                                                    Double ration =0.0, cashContri = 0.0;
                                                    Long packets  = Long.valueOf(0), hours= Long.valueOf(0);

                                                    temp= new ContributionDataModel();
                                                    int flag=0;


                                                    //Log.d("userBefore",user);


                                                    for (ContributionDataModel contributionData : contributionDataModelArrayList) {

                                                        if (user.toLowerCase().equals(contributionData.getName().toLowerCase())) {
                                                           // Log.d("Contribution Name",contributionData.getName());
                                                            //Log.d("hours",contributionData.getDry_ration()+", "+contributionData.getHoursDevoted()+"");
                                                            ration+= contributionData.getDry_ration();
                                                            packets+= contributionData.getPacked_food();
                                                            hours+= contributionData.getHoursDevoted();
                                                            cashContri+= contributionData.getCash();
                                                        }
                                                        flag=1;
                                                    }

                                                    if(flag==1)
                                                    {
                                                        temp.setPacked_food(packets);
                                                        temp.setDry_ration(ration);
                                                        temp.setCash(cashContri);
                                                        temp.setHoursDevoted(hours);
                                                        temp.setName(user);

                                                        if(temp.getPacked_food()!=0 || temp.getDry_ration()!=0.0 || temp.getHoursDevoted()!=0 || temp.getCash()!=0.0) {
                                                            Log.e("Added", user);
                                                            combined.add(temp);
                                                        }
                                                    }
                                                }
                                            }
                                            else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }

                                            Log.e("packedFood",totalpackets.toString());
                                            Log.e("dryRation",total_dry_ration.toString());

                                            packedFood.setText(totalpackets.toString());
                                            dryRation.setText(total_dry_ration.toString());
                                            timeDevoted.setText(totalhours.toString());
                                            cashDonated.setText(totalcash.toString());

                                            Log.d("combined size",combined.size()+"");
                                            contributionAdapter=new LeaderboardAdapter(getContext(),combined);
                                            recyclerView.setAdapter(contributionAdapter);
                                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                            recyclerView.setLayoutManager(layoutManager);
                                        }
                                    });
                        }

                        else
                        {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });


        return root;
    }
}
