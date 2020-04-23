 package com.example.svsucss.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.svsucss.R;

 public class ContactDeveloper extends AppCompatActivity {

     EditText name,email,subject,description;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_contact_developer);

         name=findViewById(R.id.name);
        subject=findViewById(R.id.subject);
        description=findViewById(R.id.description);

        Button send= findViewById(R.id.sendmail);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendEmail();
            }
        });
    }

     protected void sendEmail() {
         Log.i("Send email", "");
         String[] TO = {"thedeadlocksolutions@gmail.com"};
         String[] CC = {"avishi@theparkinghero.com"};
         Intent emailIntent = new Intent(Intent.ACTION_SEND);

         emailIntent.setData(Uri.parse("mailto:"));
         emailIntent.setType("text/plain");
         emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
         emailIntent.putExtra(Intent.EXTRA_CC, CC);
         emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
         emailIntent.putExtra(Intent.EXTRA_TEXT, description.getText().toString());

         try {
             startActivity(Intent.createChooser(emailIntent, "Send mail..."));
             finish();
             Log.i("Finished sending email", "");
         } catch (android.content.ActivityNotFoundException ex) {
             Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
         }
     }
}
