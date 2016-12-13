package io.firebase.contactlens;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QueryDatabase extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference, databaseReferenceName, databaseReferenceAddress, databaseReferenceNumber, databaseReferenceFacebook, databaseReferenceTwitter, databaseReferenceLinkedin, databaseReferenceGithub, databaseReferenceUsers, databaseReferenceContacts;
    private TextView textViewName, textViewAddress, textViewNumber, textViewFacebook, textViewTwitter, textViewLinkedin, textViewGithub;
    private Button buttonScan;
    public String newscanContent, users, contacts;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_database);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        newscanContent = getIntent().getStringExtra("scanContent").trim();
        firebaseAuth = FirebaseAuth.getInstance();
        buttonScan = (Button) findViewById(R.id.buttonRetrieve);
        buttonScan.setOnClickListener(this);

    }

    public void retrieveDetails() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReferenceName = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("name"));
        databaseReferenceAddress = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("email"));
        databaseReferenceNumber = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("number"));
        databaseReferenceFacebook = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("facebook"));
        databaseReferenceTwitter = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("twitter"));
        databaseReferenceLinkedin = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("linkedin"));
        databaseReferenceGithub = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("github"));
        databaseReferenceUsers = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("users"));
        databaseReferenceContacts = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("contacts"));


        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewNumber = (TextView) findViewById(R.id.textViewNumber);
        textViewFacebook = (TextView) findViewById(R.id.textViewFacebook);
        textViewTwitter = (TextView) findViewById(R.id.textViewTwitter);
        textViewLinkedin = (TextView) findViewById(R.id.textViewLinkedIn);
        textViewGithub = (TextView) findViewById(R.id.textViewGithub);

        textViewName.setOnClickListener(this);
        textViewAddress.setOnClickListener(this);
        textViewNumber.setOnClickListener(this);
        textViewFacebook.setOnClickListener(this);
        textViewTwitter.setOnClickListener(this);
        textViewLinkedin.setOnClickListener(this);
        textViewGithub.setOnClickListener(this);

        databaseReferenceName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue().toString();
                textViewName.setText(text);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        databaseReferenceAddress.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue().toString();
                textViewAddress.setText(text);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        databaseReferenceNumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue().toString();
                textViewNumber.setText(text);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        databaseReferenceFacebook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue().toString();
                textViewFacebook.setText(text);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        databaseReferenceTwitter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue().toString();
                textViewTwitter.setText(text);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        databaseReferenceLinkedin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue().toString();
                textViewLinkedin.setText(text);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        databaseReferenceGithub.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue().toString();
                textViewGithub.setText(text);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        databaseReferenceUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    users = dataSnapshot.getValue().toString();
                    databaseReferenceUsers.setValue(users + newscanContent + "/");
                }
                else{
                    databaseReferenceUsers.setValue(newscanContent + "/");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        databaseReferenceContacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            contacts = dataSnapshot.getValue().toString();
                            databaseReferenceContacts.setValue(contacts + (textViewName.getText().toString().trim()) + "/");
                        }
                    }, 100);
                }
                else{
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            databaseReferenceContacts.setValue((textViewName.getText().toString().trim()) + "/");
                        }
                    }, 100);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, MenuActivity.class));
    }

    @Override
    public void onClick(View view) {
        if (view == buttonScan && newscanContent.length() == 28) {
            retrieveDetails();
            buttonScan.setVisibility(View.GONE);

        }
        else if(newscanContent.length() != 28){
            Toast t = Toast.makeText(this, "Error: The QR you have scanned is not valid",Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER_VERTICAL,0,0);
            t.show();
        }

        if(view == textViewFacebook && textViewFacebook.getText().toString().trim() != ""){
            String url = textViewFacebook.getText().toString().trim();
            if(!url.startsWith("www.")&& !url.startsWith("http://") && !url.startsWith("https://")){
                url = "www."+url;
            }
            if(!url.startsWith("http://") && !url.startsWith("https://")){
                url = "http://"+url;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
        else if(view == textViewTwitter  && textViewTwitter.getText().toString().trim() != ""){
            String url = textViewTwitter.getText().toString().trim();
            if(!url.startsWith("www.")&& !url.startsWith("http://")){
                url = "www."+url;
            }
            if(!url.startsWith("http://")){
                url = "http://"+url;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
        else if(view == textViewLinkedin  && textViewLinkedin.getText().toString().trim() != ""){
            String url = textViewLinkedin.getText().toString().trim();
            if(!url.startsWith("www.")&& !url.startsWith("http://")){
                url = "www."+url;
            }
            if(!url.startsWith("http://")){
                url = "http://"+url;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
        else if(view == textViewGithub && textViewGithub.getText().toString().trim() != ""){
            String url = textViewGithub.getText().toString().trim();
            if(!url.startsWith("www.")&& !url.startsWith("http://")){
                url = "www."+url;
            }
            if(!url.startsWith("http://")){
                url = "http://"+url;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
    }
}


