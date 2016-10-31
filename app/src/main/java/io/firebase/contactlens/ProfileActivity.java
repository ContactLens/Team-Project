package io.firebase.contactlens;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textViewInstructions;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextName, editTextAddress, editTextNumber, editTextFacebook, editTextTwitter, editTextLinkedIn, editTextGitHub;
    private Button buttonSave;
    private Button buttonQuery;
    private Button buttonMyQR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        editTextFacebook = (EditText) findViewById(R.id.editTextFacebook);
        editTextTwitter = (EditText) findViewById(R.id.editTextTwitter);
        editTextLinkedIn = (EditText) findViewById(R.id.editTextLinkedIn);
        editTextGitHub = (EditText) findViewById(R.id.editTextGithub);
        buttonSave = (Button) findViewById(R.id.buttonSave);


        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewInstructions = (TextView) findViewById(R.id.textViewInstructions);

        textViewInstructions.setText("Enter your chosen contact details below");

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonQuery = (Button) findViewById(R.id.buttonQuery);
        buttonMyQR = (Button) findViewById(R.id.buttonMyQR);

        buttonLogout.setOnClickListener(this);
        buttonQuery.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonMyQR.setOnClickListener(this);


    }

    private void saveUserInformation() {
        //+++++WARNING CHANGES MADE HERE, ROLLBACK IF NECESSARY++++++++

        String name;
        String add;
        String num;
        String face;
        String twit;
        String link;
        String git;

        if (editTextName.getText().toString().equals("")) {
            name = null;
        } else {
            name = editTextName.getText().toString().trim();
        }

        if (editTextAddress.getText().toString().equals("")) {
            add = null;
        } else {
            add = editTextAddress.getText().toString().trim();
        }

        if (editTextNumber.getText().toString().equals("")) {
            num = null;
        } else {
            num = editTextNumber.getText().toString().trim();
        }

        if (editTextFacebook.getText().toString().equals("")) {
            face = null;
        } else {
            face = editTextFacebook.getText().toString().trim();
        }

        if (editTextTwitter.getText().toString().equals("")) {
            twit = null;
        } else {
            twit = editTextTwitter.getText().toString().trim();
        }

        if (editTextLinkedIn.getText().toString().equals("")) {
            link = null;
        } else {
            link = editTextLinkedIn.getText().toString().trim();
        }

        if (editTextGitHub.getText().toString().equals("")) {
            git = null;
        } else {
            git = editTextGitHub.getText().toString().trim();
        }

        UserInformation userInformation = new UserInformation(name, add, num, face, twit, link, git);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "Contact Details Saved...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if (view == buttonLogout) {
            //logging out user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login
            startActivity(new Intent(this, LoginActivity.class));
        }
        else if (view == buttonSave) {
            saveUserInformation();
        }
        else if (view == buttonQuery) {
            startActivity(new Intent(this, QueryDatabase.class));
        }
        else if (view == buttonMyQR) {
            startActivity(new Intent(this, QRGeneratorActivity.class));
        }

    }
}

