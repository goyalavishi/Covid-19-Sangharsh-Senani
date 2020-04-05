package com.example.svsucss.HomeFragments.addContribution;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.svsucss.MyPreferences;
import com.example.svsucss.R;

public class AddContriutionFragment extends Fragment {


    private LinearLayout layout_student, layout_staff, staff_details,student_details,details,layout_selection,layout_other_details;
    private ImageView iv_student, iv_staff;
    private TextView tv_student, tv_staff,tv_details;
    private int currentSelected = 0;
    MyPreferences myPreferences;
    Button submit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_contribution, container, false);

        int flag =1;
        myPreferences=new MyPreferences(getContext());
        details=root.findViewById(R.id.auth);
        tv_details=root.findViewById(R.id.detail_type);
        layout_selection=root.findViewById(R.id.selection);
        layout_other_details=root.findViewById(R.id.other_details);

        iv_staff=root.findViewById(R.id.iv_staff);
        tv_staff=root.findViewById(R.id.tv_staff);
        layout_staff=root.findViewById(R.id.staff);
        staff_details=root.findViewById(R.id.staff_details);

        iv_student=root.findViewById(R.id.iv_student);
        tv_student=root.findViewById(R.id.tv_student);
        layout_student=root.findViewById(R.id.student);
        student_details=root.findViewById(R.id.student_details);
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

            myPreferences.setTypeOfUser("Staff");
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

        myPreferences.setTypeOfUser("Student");

    }

    void onSubmit()
    {
        validatefields();
        myPreferences.setIsNotLogin(false);
        tv_details.setText("Contribution Details");
        layout_selection.setVisibility(View.GONE);
        details.setVisibility(View.GONE);
        layout_other_details.setVisibility(View.VISIBLE);
    }

    void validatefields()
    {

    }


}
