package com.example.svsucss.HomeFragments.addContribution;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
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

import com.example.svsucss.MyPreferences;
import com.example.svsucss.R;
import com.example.svsucss.UserDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.concurrent.Executor;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddContriutionFragment extends Fragment {


    private LinearLayout layout_student, layout_staff, staff_details,student_details,details,layout_selection,layout_other_details;
    private ImageView iv_student, iv_staff;
    private TextView tv_student, tv_staff,tv_details;
    private EditText studentName,studentEnrollmentNumber,staffName,staffDesignation,date,place,sponsi,packedfood,dryration;
    private int mYear, mMonth, mDay;
    private int currentSelected = 0;
    MyPreferences myPreferences;
    Button submit;
    FirebaseAuth firebaseAuth;
    String userType;
    FirebaseFirestore db;
    UserDataModel userDataModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_contribution, container, false);

        firebaseAuth= FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        userDataModel=new UserDataModel();

        tv_details=root.findViewById(R.id.detail_type);
        studentName=root.findViewById(R.id.student_name);
        staffName=root.findViewById(R.id.staff_name);
        staffDesignation= root.findViewById(R.id.staff_designation);
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
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });

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
        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signIn:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signIn:failure", task.getException());
                        }

                        userDataModel.setUserType(userType);
                        if(userDataModel.getUserType().equals("student"))
                        {
                            userDataModel.setName(studentName.getText().toString());
                            userDataModel.setEnrollmentNumber(studentEnrollmentNumber.getText().toString());
                        }
                    }
                });

        myPreferences.setIsNotLogin(false);
        tv_details.setText("Contribution Details");
        layout_selection.setVisibility(View.GONE);
        details.setVisibility(View.GONE);
        layout_other_details.setVisibility(View.VISIBLE);
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


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
