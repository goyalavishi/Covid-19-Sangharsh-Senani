package com.example.svsucss.HomeFragments.aboutUs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.svsucss.Activities.ContactDeveloper;
import com.example.svsucss.Activities.ContactUs;
import com.example.svsucss.Activities.MainActivity;
import com.example.svsucss.R;


public class AboutUsFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button contactus, contactDeveloper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_about_us, container, false);
        Button websiteLink= root.findViewById(R.id.website_link);
        websiteLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://covidssharyana.in/");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        contactus = root.findViewById(R.id.contact_form_link);
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(getContext(), ContactUs.class);
                startActivity(i);
            }
        });

        contactDeveloper = root.findViewById(R.id.contact_dev_link);
        contactDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), ContactDeveloper.class);
                startActivity(i);
            }
        });

        return root;
    }

}
