package io.firebase.contactlens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference, databaseReferenceName, databaseReferenceAddress,databaseReferenceNumber,databaseReferenceFacebook, databaseReferenceTwitter,databaseReferenceLinkedin, databaseReferenceGithub;
    private EditText editTextName, editTextAddress, editTextNumber, editTextFacebook, editTextTwitter, editTextLinkedIn, editTextGitHub;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();

        //check if user is logged in, if not, returns to login screen
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        //gets an instance of the user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //gets reference to firebase database associated with this app based on values in the google services file
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //links variables with their instances in the activity xml file
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        editTextFacebook = (EditText) findViewById(R.id.editTextFacebook);
        editTextTwitter = (EditText) findViewById(R.id.editTextTwitter);
        editTextLinkedIn = (EditText) findViewById(R.id.editTextLinkedIn);
        editTextGitHub = (EditText) findViewById(R.id.editTextGithub);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        //gets references for each of the child nodes under current users ID in database
        databaseReferenceName = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("name"));
        databaseReferenceAddress = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("email"));
        databaseReferenceNumber = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("number"));
        databaseReferenceFacebook = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("facebook"));
        databaseReferenceTwitter = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("twitter"));
        databaseReferenceLinkedin = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("linkedin"));
        databaseReferenceGithub = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("github"));


        //These place listeners on each of the children of the current users ID in the database
        databaseReferenceName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Takes a snapshot of the childs content, if it's not null then the contents are written to a string and displayed in the EditText.
                //This was intended to expedite the detail submission process as without it the user must fill in every EditText again
                // even if only changing one value
                if (dataSnapshot.exists()) {
                    String text = dataSnapshot.getValue().toString();
                    editTextName.setText(text);
                }
                //as this only fires if the given field exist in the database if the field were not to exist nothing happens and the EditText will
                //simply contain the hint from the xml file
            }
            @Override
            public void onCancelled(DatabaseError databaseError){
                }
        });
        databaseReferenceAddress.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String text = dataSnapshot.getValue().toString();
                    editTextAddress.setText(text);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReferenceNumber.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String text = dataSnapshot.getValue().toString();
                    editTextNumber.setText(text);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReferenceFacebook.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String text = dataSnapshot.getValue().toString();
                    editTextFacebook.setText(text);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReferenceTwitter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String text = dataSnapshot.getValue().toString();
                    editTextTwitter.setText(text);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReferenceLinkedin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String text = dataSnapshot.getValue().toString();
                    editTextLinkedIn.setText(text);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReferenceGithub.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String text = dataSnapshot.getValue().toString();
                    editTextGitHub.setText(text);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSave.setOnClickListener(this);
    }

    private void saveUserInformation() {
        String name;
        String add;
        String num;
        String face;
        String twit;
        String link;
        String git;
        //checks if user has input anything into the EditText
        if (editTextName.getText().toString().equals("")) {
            //if not, assign an empty string to name
            name = "";
        } else {
            //if not empty, assign the value to name
            name = editTextName.getText().toString().trim();
        }
        //similar to above
        if (editTextAddress.getText().toString().equals("")) {
            add = "";
        } else {
            add = editTextAddress.getText().toString().trim();
        }
        if (editTextNumber.getText().toString().equals("")) {
            num = "";
        } else {
            num = editTextNumber.getText().toString().trim();
        }
        if (editTextFacebook.getText().toString().equals("")) {
            face = "";
        } else {
            face = editTextFacebook.getText().toString().trim();
        }
        if (editTextTwitter.getText().toString().equals("")) {
            twit = "";
        } else {
            twit = editTextTwitter.getText().toString().trim();
        }
        if (editTextLinkedIn.getText().toString().equals("")) {
            link = "";
        } else {
            link = editTextLinkedIn.getText().toString().trim();
        }
        if (editTextGitHub.getText().toString().equals("")) {
            git = "";
        } else {
            git = editTextGitHub.getText().toString().trim();
        }

        //add the strings generated above to the UserInformation method
        UserInformation userInformation = new UserInformation(name, add, num, face, twit, link, git);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //set the values of the contact details rows in the user database entry to those of the EditTexts
        databaseReference.child(user.getUid()).setValue(userInformation);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSave) {
            saveUserInformation();
            finish();
        }
    }
}

