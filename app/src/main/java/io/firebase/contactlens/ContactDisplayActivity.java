
package io.firebase.contactlens;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactDisplayActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Button buttonRetrieve;
    private DatabaseReference databaseReference, databaseReferenceName, databaseReferenceAddress, databaseReferenceNumber, databaseReferenceFacebook, databaseReferenceTwitter, databaseReferenceLinkedin, databaseReferenceGithub;
    private TextView textViewName, textViewAddress, textViewNumber, textViewFacebook, textViewTwitter, textViewLinkedin, textViewGithub;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_display);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        buttonRetrieve = (Button) findViewById(R.id.buttonRetrieve);
        //buttonRetrieve.setOnClickListener(this);
        retrieveDetails();
    }

    public void retrieveDetails() {
        String UID = "11QPZgse5SgTwwX1LgqbvqqUgVz1";
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReferenceName = (FirebaseDatabase.getInstance().getReference().child(UID).child("name"));
        databaseReferenceAddress = (FirebaseDatabase.getInstance().getReference().child(UID).child("email"));
        databaseReferenceNumber = (FirebaseDatabase.getInstance().getReference().child(UID).child("number"));
        databaseReferenceFacebook = (FirebaseDatabase.getInstance().getReference().child(UID).child("facebook"));
        databaseReferenceTwitter = (FirebaseDatabase.getInstance().getReference().child(UID).child("twitter"));
        databaseReferenceLinkedin = (FirebaseDatabase.getInstance().getReference().child(UID).child("linkedin"));
        databaseReferenceGithub = (FirebaseDatabase.getInstance().getReference().child(UID).child("github"));

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
    }

    //@Override
    public void onClick(View view) {
        /*if (view == buttonRetrieve) {
            retrieveDetails();
        }*/

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