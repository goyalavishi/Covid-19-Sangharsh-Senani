package com.example.svsucss.HomeFragments.myContribution;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.svsucss.ContributionDataModel;
import com.example.svsucss.HomeFragments.leaderboard.ContributionAdapter;
import com.example.svsucss.MyPreferences;
import com.example.svsucss.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Date;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MyContribution extends Fragment {

    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    ContributionAdapter contributionAdapter;
    ContributionDataModel contributionDataModel;
    ArrayList<ContributionDataModel> contributionDataModelArrayList, dateFilteredModel;
    Long totalpackets,totalhours;
    Double total_dry_ration,totalcash;
    TextView dryRation,packedFood,timeDevoted,cashDonated;
    MyPreferences myPreferences;
    String date;

  int mYear,mMonth,mDay;


    public MyContribution() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FloatingActionButton dateFilter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.content_my_fragment, container, false);

        dateFilter = root.findViewById(R.id.floating_action_button);
        myPreferences = new MyPreferences(getContext());
        recyclerView = root.findViewById(R.id.recyclerView);
        contributionDataModel = new ContributionDataModel();
        Log.e("Hello ", "hello");
        contributionDataModelArrayList = new ArrayList<>();
        dateFilteredModel = new ArrayList<>();
        totalpackets= Long.valueOf(0);
        totalcash=0.0;
        totalhours=Long.valueOf(0);
        total_dry_ration=0.0;

        dryRation=root.findViewById(R.id.tv_dry_ration);
        packedFood=root.findViewById(R.id.tv_packed_food);
        cashDonated = root.findViewById(R.id.tv_cash);
        timeDevoted= root.findViewById(R.id.tv_time);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Contribution")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.e("Hello ", " no response");
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.e("Hello ", "response");

                                if(document.getString("addersid").equals(myPreferences.getUserId())) {
                                    contributionDataModel = new ContributionDataModel();
                                    contributionDataModel.setName(document.getString("name"));
                                    contributionDataModel.setDate(document.getString("date"));
                                    contributionDataModel.setSponsi(document.getString("sponsi"));
                                    contributionDataModel.setDry_ration((document.getDouble("dry_ration")));
                                    contributionDataModel.setPacked_food(document.getLong("packed_food"));
                                    contributionDataModel.setCash(document.getDouble("cash"));
                                    contributionDataModel.setHoursDevoted(document.getLong("hoursDevoted"));
                                    contributionDataModel.setPlace(document.getString("place"));

                                    total_dry_ration += contributionDataModel.getDry_ration();
                                    totalpackets += contributionDataModel.getPacked_food();
                                    totalcash +=contributionDataModel.getCash();
                                    totalhours += contributionDataModel.getHoursDevoted();

                                    contributionDataModelArrayList.add(contributionDataModel);
                                }
                            }
                            contributionAdapter=new ContributionAdapter(getContext(),contributionDataModelArrayList);
                            recyclerView.setAdapter(contributionAdapter);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            packedFood.setText(totalpackets.toString());
                            dryRation.setText(total_dry_ration.toString());
                            cashDonated.setText(totalcash.toString());
                            timeDevoted.setText(totalhours.toString());

                        }

                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        dateFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datepicker();
            }
        });

        return root;
    }

    void datepicker()
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        filterDate(1);

                        // date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {


                filterDate(0);

            }
        });
        datePickerDialog.show();

    }

    void filterDate(int x)
    {
        total_dry_ration = 0.0;
        totalpackets = Long.valueOf(0);
        totalcash = 0.0;
        totalhours = Long.valueOf(0);

        dateFilteredModel = new ArrayList<>();

        if(x == 1)
        {
            for (ContributionDataModel contributionDataModel : contributionDataModelArrayList) {

                if (contributionDataModel.getDate().equals(date)) {

                    total_dry_ration += contributionDataModel.getDry_ration();
                    totalpackets += contributionDataModel.getPacked_food();
                    totalhours += contributionDataModel.getHoursDevoted();
                    totalcash += contributionDataModel.getCash();

                    dateFilteredModel.add(contributionDataModel);
                }
            }
        }

        else
        {
            for (ContributionDataModel contributionDataModel : contributionDataModelArrayList) {

                    total_dry_ration += contributionDataModel.getDry_ration();
                    totalpackets += contributionDataModel.getPacked_food();
                    totalhours += contributionDataModel.getHoursDevoted();
                    totalcash += contributionDataModel.getCash();

                    dateFilteredModel.add(contributionDataModel);
            }
        }


        contributionAdapter=new ContributionAdapter(getContext(),dateFilteredModel);
        recyclerView.setAdapter(contributionAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        packedFood.setText(totalpackets.toString());
        dryRation.setText(total_dry_ration.toString());
        cashDonated.setText(totalcash.toString());
        timeDevoted.setText(totalhours.toString());
    }
}