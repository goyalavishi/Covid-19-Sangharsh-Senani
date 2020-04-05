package com.example.svsucss.HomeFragments.addContribution;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.svsucss.R;

public class AddContriutionFragment extends Fragment {


    private LinearLayout layout_student, layout_staff;
    private ImageView iv_student, iv_staff;
    private TextView tv_student, tv_staff;
    private int currentSelected = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_contribution, container, false);

        int flag =1;

        iv_staff=root.findViewById(R.id.iv_staff);
        tv_staff=root.findViewById(R.id.tv_staff);
        layout_staff=root.findViewById(R.id.staff);

        iv_student=root.findViewById(R.id.iv_student);
        tv_student=root.findViewById(R.id.tv_student);
        layout_student=root.findViewById(R.id.student);

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


        return root;
    }

    void selected_staff()
    {

            layout_staff.setSelected(true);
            layout_student.setSelected(false);

            layout_staff.setBackground(getResources().getDrawable(R.drawable.background_cards));
            layout_student.setBackground(getResources().getDrawable(R.drawable.background_cards));
            iv_staff.setColorFilter(getResources().getColor(R.color.colorPrimary));
            tv_staff.setTextColor(getResources().getColor(R.color.colorPrimary));
            unselectElse(2);
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
        layout_staff.setSelected(false);
        layout_student.setSelected(true);

        layout_staff.setBackground(getResources().getDrawable(R.drawable.background_cards));
        layout_student.setBackground(getResources().getDrawable(R.drawable.background_cards));
        iv_student.setColorFilter(getResources().getColor(R.color.colorPrimary));
        tv_student.setTextColor(getResources().getColor(R.color.colorPrimary));
        unselectElse(1);
    }


}
