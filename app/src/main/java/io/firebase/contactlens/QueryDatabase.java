package io.firebase.contactlens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class QueryDatabase extends AppCompatActivity implements View.OnClickListener{


    Firebase mRef;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferenceName, databaseReferenceAddress,databaseReferenceNumber,databaseReferenceFacebook,databaseReferenceTwitter,databaseReferenceLinkedin,databaseReferenceGithub;
    private TextView textViewUserID,textViewName,textViewAddress,textViewNumber,textViewFacebook,textViewTwitter,textViewLinkedin,textViewGithub;
    private Button buttonRetrieve;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_database);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        buttonRetrieve = (Button) findViewById(R.id.buttonRetrieve);
        buttonRetrieve.setOnClickListener(this);

        textViewUserID = (TextView) findViewById(R.id.textViewUserID);
        textViewUserID.setText("Your UID is " + user.getUid());

    }

    public void retrieveDetails() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReferenceName = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("name"));
        databaseReferenceAddress = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("email"));
        databaseReferenceNumber = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("number"));
        databaseReferenceFacebook = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("facebook"));
        databaseReferenceTwitter = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("twitter"));
        databaseReferenceLinkedin = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("linkedin"));
        databaseReferenceGithub = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("github"));

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewNumber = (TextView) findViewById(R.id.textViewNumber);
        textViewFacebook = (TextView) findViewById(R.id.textViewFacebook);
        textViewTwitter = (TextView) findViewById(R.id.textViewTwitter);
        textViewLinkedin = (TextView) findViewById(R.id.textViewLinkedIn);
        textViewGithub = (TextView) findViewById(R.id.textViewGithub);

        //This listener below works although the data retrieved is in an unfinished
        // state, however IT DOES WORK. If the retrieval ability breaks at some point, return to this.
        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue().toString();
                textViewDB.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

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

    @Override
    public void onClick(View view) {
        if (view == buttonRetrieve) {
            retrieveDetails();
        }
    }
}
