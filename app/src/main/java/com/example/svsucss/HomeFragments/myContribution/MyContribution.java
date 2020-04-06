package com.example.svsucss.HomeFragments.myContribution;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.svsucss.ContributionDataModel;
import com.example.svsucss.HomeFragments.leaderboard.ContributionAdapter;
import com.example.svsucss.MyPreferences;
import com.example.svsucss.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MyContribution extends Fragment {

    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    ContributionAdapter contributionAdapter;
    ContributionDataModel contributionDataModel;
    ArrayList<ContributionDataModel> contributionDataModelArrayList;
    Long totalpackets;
    Double total_dry_ration;
    TextView dryRation,packedFood;
    MyPreferences myPreferences;
    public MyContribution() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_my_contribution, container, false);

        myPreferences= new MyPreferences(getContext());
        recyclerView=root.findViewById(R.id.recyclerView);
        contributionDataModel=new ContributionDataModel();
        contributionDataModelArrayList= new ArrayList<>();
        firebaseFirestore= FirebaseFirestore.getInstance();
        totalpackets= Long.valueOf(0);
        total_dry_ration=0.0;

        dryRation=root.findViewById(R.id.tv_dry_ration);
        packedFood=root.findViewById(R.id.tv_packed_food);

        firebaseFirestore.collection("Contribution")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                if(document.getString("addersid").equals(myPreferences.getUserId())) {
                                    contributionDataModel = new ContributionDataModel();
                                    contributionDataModel.setName(document.getString("name"));
                                    contributionDataModel.setDate(document.getString("date"));
                                    contributionDataModel.setSponsi(document.getString("sponsi"));
                                    contributionDataModel.setDry_ration((document.getDouble("dry_ration")));
                                    contributionDataModel.setPacked_food(document.getLong("packed_food"));
                                    contributionDataModel.setPlace(document.getString("place"));

                                    total_dry_ration += contributionDataModel.getDry_ration();
                                    totalpackets += contributionDataModel.getPacked_food();

                                    contributionDataModelArrayList.add(contributionDataModel);
                                }
                            }
                            contributionAdapter=new ContributionAdapter(getContext(),contributionDataModelArrayList);
                            recyclerView.setAdapter(contributionAdapter);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            packedFood.setText(totalpackets.toString());
                            dryRation.setText(total_dry_ration.toString());


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return root;
    }
}
