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

        //gets base reference for the database
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //retrieves the contents of the QR scanned in the scanner activity
        newscanContent = getIntent().getStringExtra("scanContent").trim();
        firebaseAuth = FirebaseAuth.getInstance();
        buttonScan = (Button) findViewById(R.id.buttonRetrieve);
        buttonScan.setOnClickListener(this);

    }

    public void retrieveDetails() {
        //gets current users ID
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //gets references to each of the rows in the scanned users entry in the database
        databaseReferenceName = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("name"));
        databaseReferenceAddress = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("email"));
        databaseReferenceNumber = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("number"));
        databaseReferenceFacebook = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("facebook"));
        databaseReferenceTwitter = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("twitter"));
        databaseReferenceLinkedin = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("linkedin"));
        databaseReferenceGithub = (FirebaseDatabase.getInstance().getReference().child(newscanContent).child("github"));
        databaseReferenceUsers = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("users"));
        databaseReferenceContacts = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("contacts"));

        //initialises the textviews
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewNumber = (TextView) findViewById(R.id.textViewNumber);
        textViewFacebook = (TextView) findViewById(R.id.textViewFacebook);
        textViewTwitter = (TextView) findViewById(R.id.textViewTwitter);
        textViewLinkedin = (TextView) findViewById(R.id.textViewLinkedIn);
        textViewGithub = (TextView) findViewById(R.id.textViewGithub);

        //sets click listeners to the textfields
        textViewName.setOnClickListener(this);
        textViewAddress.setOnClickListener(this);
        textViewNumber.setOnClickListener(this);
        textViewFacebook.setOnClickListener(this);
        textViewTwitter.setOnClickListener(this);
        textViewLinkedin.setOnClickListener(this);
        textViewGithub.setOnClickListener(this);

        //adds a ValueEventListener to each of the rows in the database, this retrieves the rows contents and returns them to the app
        //also responsible for allowing live updates as it fires on any changes made in the database ensuring the most up to date info is displayed
        databaseReferenceName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //writes row contents to a local variable
                String text = dataSnapshot.getValue().toString();
                //local variable is written into the textView and displayed
                textViewName.setText(text);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //same as above
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

        //this is what creates the "users" row in the database, i.e the UIDs needed to create and display contacts in the My Contacts tab
        //first we add a listener as above for the "users" row
        databaseReferenceUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //we now check if the row exists (which it won't for first time users and those that have not added anyone)
                if(dataSnapshot.exists()) {
                    //if it does we retrieve its current contents and write them to a string
                    users = dataSnapshot.getValue().toString();
                    //we then concatenate this string with the value of the scanned QR and add a / to the end, this our delimiter for splitting the
                    // string in the ContactList activity
                    databaseReferenceUsers.setValue(users + newscanContent + "/");
                }
                else{
                    //if it doesn't exist we create it by setting it the value retrieved from our scanned QR and add a / for the same reason as above
                    databaseReferenceUsers.setValue(newscanContent + "/");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //we now use a reference for the 'names' row in the database which contains the names of the contacts added in order
        databaseReferenceContacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //we follow the same formula as above but with a slight change, the firing of the DataSnapshot is intentionally delayed using the
                    //handler below
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            contacts = dataSnapshot.getValue().toString();
                            /*The reason for the delay is because we do not get the scanned users name from the previous activity whereas with their
                            UID we do. Instead we use the value displayed in the "Name" textfield when we retrieve their details. If this delay was
                            not present, the DataSnapshot would fire before the contacts name was retrieved and set in the textfield resulting in a
                            Null Pointer exception*/
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
        //checks the length of the QRs contents, a simple error check is performed. All firebase UIDs are 28 characters in length. If the scanned
        //QR is not 28 characters it cannot refer to another user.
        if (view == buttonScan && newscanContent.length() == 28) {
            retrieveDetails();
            //removes the "Save and Retrieve" button so the user can't accidentally hit the button more than once per scan
            buttonScan.setVisibility(View.GONE);

        }
        else if(newscanContent.length() != 28){
            Toast t = Toast.makeText(this, "Error: The QR you have scanned is not valid",Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER_VERTICAL,0,0);
            t.show();
        }
        //allows user to open a browser activity navigating to the address in the chosen textfield
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


