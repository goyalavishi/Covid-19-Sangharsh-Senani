package com.example.svsucss.HomeFragments.addContribution;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.svsucss.ContributionDataModel;
import com.example.svsucss.MyPreferences;
import com.example.svsucss.R;
import com.example.svsucss.UserDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;


import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddContriutionFragment extends Fragment {


    private LinearLayout layout_student, layout_staff, staff_details,student_details,details,layout_selection,layout_other_details;
    private LinearLayout layout_thanks;
    private ImageView iv_student, iv_staff;
    private TextView tv_student, tv_staff,tv_details;
    private EditText studentName,studentEnrollmentNumber,staffName,staffDesignation,date,place,sponsi,packedfood,dryration;
    private int mYear, mMonth, mDay;
    private int currentSelected = 0,flag=0;
    MyPreferences myPreferences;
    Button submit,contribution_submit,add_another;
    FirebaseAuth firebaseAuth;
    String userType;
    FirebaseFirestore db;
    UserDataModel userDataModel;
    ContributionDataModel contributionDataModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_add_contribution, container, false);

        firebaseAuth= FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        userDataModel=new UserDataModel();

        tv_details=root.findViewById(R.id.detail_type);
        studentName=root.findViewById(R.id.student_name);
        staffName=root.findViewById(R.id.staff_name);
        staffDesignation=root.findViewById(R.id.staff_designation);
        studentEnrollmentNumber=root.findViewById(R.id.student_enrollment);

        details=root.findViewById(R.id.auth);
        layout_selection=root.findViewById(R.id.selection);
        iv_staff=root.findViewById(R.id.iv_staff);
        tv_staff=root.findViewById(R.id.tv_staff);
        layout_staff=root.findViewById(R.id.staff);
        staff_details=root.findViewById(R.id.staff_details);

        iv_student=root.findViewById(R.id.iv_student);
        tv_student=root.findViewById(R.id.tv_student);
        layout_student=root.findViewById(R.id.student);
        student_details=root.findViewById(R.id.student_details);

        layout_other_details=root.findViewById(R.id.other_details);
        layout_thanks=root.findViewById(R.id.layout_Thanks);

        int flag =1;
        myPreferences=new MyPreferences(getContext());

        if(myPreferences.isNotLogin()==true)
        {
            uiNotLogin(root);
        }
        else
        {
            uiLogin(root);
        }

        date=root.findViewById(R.id.contri_date);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            date.setShowSoftInputOnFocus(false);
        }
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextFocusSettings(place);
                datepicker();
            }
        });
        place=root.findViewById(R.id.contri_place);
        sponsi=root.findViewById(R.id.sponsi);
        packedfood=root.findViewById(R.id.packed_food);
        dryration=root.findViewById(R.id.dry_ration);
        contribution_submit=root.findViewById(R.id.other_details_submit);

        contributionDataModel= new ContributionDataModel();
        contribution_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contributionSubmitButtonOnClick(root);
            }
        });

        add_another=root.findViewById(R.id.add_another);
        add_another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_thanks.setVisibility(View.GONE);
                tv_details.setText("Contribution Details");
                layout_other_details.setVisibility(View.VISIBLE);

            }
        });

        onEditTextClick(studentName,studentEnrollmentNumber);
        onEditTextClickButton(studentEnrollmentNumber,submit);
        onEditTextClick(place,packedfood);
        onEditTextClick(packedfood,dryration);
        onEditTextClick(dryration,sponsi);
        onEditTextClickButton(sponsi,contribution_submit);



        return root;
    }

    void selected_staff()
    {
            details.setVisibility(View.VISIBLE);
            staff_details.setVisibility(View.VISIBLE);
            student_details.setVisibility(View.GONE);
            layout_staff.setSelected(true);
            layout_student.setSelected(false);

            layout_staff.setBackground(getResources().getDrawable(R.drawable.background_cards));
            layout_student.setBackground(getResources().getDrawable(R.drawable.background_cards));
            iv_staff.setColorFilter(getResources().getColor(R.color.colorPrimary));
            tv_staff.setTextColor(getResources().getColor(R.color.colorPrimary));
            unselectElse(2);

            myPreferences.setTypeOfUser("staff");
            userType="staff";
    }

    private void unselectElse(int current){
        switch (currentSelected){
            case 1:

                iv_student.setColorFilter(getResources().getColor(R.color.gray));
                tv_student.setTextColor(getResources().getColor(R.color.gray));
                currentSelected = current;
                break;
            case 2:
                iv_staff.setColorFilter(getResources().getColor(R.color.gray));
                tv_staff.setTextColor(getResources().getColor(R.color.gray));
                currentSelected = current;
                break;
        }

        if(currentSelected == 0){
            currentSelected = current;
        }
    }

    void selected_student()
    {
        details.setVisibility(View.VISIBLE);
        staff_details.setVisibility(View.GONE);
        student_details.setVisibility(View.VISIBLE);
        layout_staff.setSelected(false);
        layout_student.setSelected(true);

        layout_staff.setBackground(getResources().getDrawable(R.drawable.background_cards));
        layout_student.setBackground(getResources().getDrawable(R.drawable.background_cards));
        iv_student.setColorFilter(getResources().getColor(R.color.colorPrimary));
        tv_student.setTextColor(getResources().getColor(R.color.colorPrimary));
        unselectElse(1);

        myPreferences.setTypeOfUser("student");
        userType="student";
    }

    void onSubmit()
    {
        validatefields();
        if(userType.equals("student"))
        {
            userDataModel.setUserType(userType);
            userDataModel.setName(studentName.getText().toString());
            userDataModel.setEnrollmentNumber(studentEnrollmentNumber.getText().toString());

            flag = 0;
            db.collection("User").whereEqualTo("userType","student")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(document.getString("enrollment").equals(userDataModel.getEnrollmentNumber()))
                                    {
                                        myPreferences.setIsNotLogin(false);
                                        tv_details.setText("Contribution Details");
                                        layout_selection.setVisibility(View.GONE);
                                        details.setVisibility(View.GONE);
                                        layout_other_details.setVisibility(View.VISIBLE);
                                        userDataModel.setId(document.getString("id"));
                                        myPreferences.setUserId(document.getString("id"));
                                        myPreferences.setUserName(document.getString("name"));
                                        flag =1;

                                    }
                                }

                                if(flag==0)
                                {
                                    Toast.makeText(getContext(),"Enter Correct Enrollment Number",Toast.LENGTH_LONG).show();
                                    studentEnrollmentNumber.setError("Enter Correct Enrollment Number");
                                }
                            }
                            else {
                                Log.e(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

        if(userType.equals("staff"))
        {
            userDataModel.setUserType(userType);
            userDataModel.setName(staffName.getText().toString());
            userDataModel.setDesignation(staffDesignation.getText().toString());

            flag = 0;
            db.collection("User").whereEqualTo("userType","staff")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if((document.getString("designation").equals(userDataModel.getDesignation().toString().toLowerCase()))&&(document.getString("name").equals(userDataModel.getName().toString().toLowerCase())))
                                    {
                                        myPreferences.setIsNotLogin(false);
                                        tv_details.setText("Contribution Details");
                                        layout_selection.setVisibility(View.GONE);
                                        details.setVisibility(View.GONE);
                                        layout_other_details.setVisibility(View.VISIBLE);
                                        userDataModel.setId(document.getString("id"));
                                        myPreferences.setUserId(document.getString("id"));
                                        myPreferences.setUserName(document.getString("name"));
                                        flag =1;
                                    }
                                }

                                if(flag==0)
                                {
                                    Toast.makeText(getContext(),"Enter Correct Details",Toast.LENGTH_LONG).show();
                                    staffDesignation.setError("Enter Correct Details");
                                    staffName.setError("Enter Correct Details");
                                }
                            }
                            else {
                                Log.e(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }

    void validatefields()
    {

    }

    void uiNotLogin(View root)
    {
        layout_other_details.setVisibility(View.GONE);

        layout_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelected == 1) {

                } else {
                    selected_student();
                }
            }
        });

        layout_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelected == 2) {

                } else {
                    selected_staff();

                }
            }
        });

        submit=root.findViewById(R.id.auth_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
    }

    void uiLogin(View root)
    {
        userType=myPreferences.getTypeOfUser();
        tv_details.setText("Contribution Details");
        layout_selection.setVisibility(View.GONE);
        details.setVisibility(View.GONE);
        layout_other_details.setVisibility(View.VISIBLE);
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

                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    void contributionSubmitButtonOnClick(View root)
    {
        int indexs=contributionValidation();
        if(indexs==0) {
            contributionDataModel.setName(myPreferences.getUserName());
            contributionDataModel.setAddersid(myPreferences.getUserId());
            contributionDataModel.setDate(date.getText().toString());
            contributionDataModel.setPlace(place.getText().toString());
            contributionDataModel.setDry_ration(Double.valueOf(dryration.getText().toString()));
            contributionDataModel.setPacked_food(Long.valueOf((packedfood.getText().toString())));
            contributionDataModel.setSponsi(sponsi.getText().toString());


            db.collection("Contribution")
                    .add(contributionDataModel)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
//                        layout_other_details.setVisibility(View.GONE);
//                        layout_thanks.setVisibility(View.VISIBLE);
//                        tv_details.setText("Thank You For Your Contribustion,\nTogether We Can Make A Difference!");

                            new MaterialAlertDialogBuilder(getContext())
                                    .setTitle("Good Job!")
                                    .setMessage("Thank You For Your Contribustion,\nTogether We Can Make A Difference!")
                                    .setPositiveButton("Add Another", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            date.setText("");
                                            place.setText("");
                                            sponsi.setText("");
                                            dryration.setText("");
                                            packedfood.setText("");
                                        }
                                    })
                                    .show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                            Toast.makeText(getContext(), "There are some issues in adding your contribution, " +
                                    "Please try again later", Toast.LENGTH_LONG).show();
                        }
                    });


        }

    }

    int contributionValidation()
    {
        int index=0;
        if(date.getText().toString().equals(""))
        {
            index=1;
            date.setError("Enter Date");
        }

        if(place.getText().toString().equals(""))
        {
            index=1;

            place.setError("Enter Place");
        }

        if(packedfood.getText().toString().equals(""))
        {
            index=1;

            packedfood.setError("Enter No. of Packed Food");
        }

        if(dryration.getText().toString().equals(""))
        {
            index=1;

            dryration.setError("Enter Kgs of Dry Ration");
        }

        if(sponsi.getText().toString().equals(""))
        {
            index=1;

            sponsi.setError("Enter Sponser Name");
        }

        return index;
    }

    void editTextFocusSettings(EditText next)
    {
        next.setFocusable(true);
    }


    void onEditTextClick(EditText editText, final EditText next)
    {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextFocusSettings(next);
            }
        });
    }

    void onEditTextClickButton(EditText editText, final Button next)
    {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setFocusable(true);
            }
        });
    }
}
