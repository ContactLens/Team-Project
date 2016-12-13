package io.firebase.contactlens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout, buttonScan, buttonMyQR, buttonMyDetails, buttonList, buttonHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

//couples buttons with their equivalent in the activity layout file
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonScan = (Button) findViewById(R.id.buttonScan);
        buttonMyQR = (Button) findViewById(R.id.buttonMyQR);
        buttonMyDetails = (Button) findViewById(R.id.buttonMyDetails);
        buttonList = (Button) findViewById(R.id.buttonList);
        buttonHelp = (Button) findViewById(R.id.buttonHelp);

//adds listeners to each of the menu buttons
        buttonLogout.setOnClickListener(this);
        buttonScan.setOnClickListener(this);
        buttonMyQR.setOnClickListener(this);
        buttonMyDetails.setOnClickListener(this);
        buttonList.setOnClickListener(this);
        buttonHelp.setOnClickListener(this);
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
        //navigate to QR code of user
        else if (view == buttonMyQR) {
            startActivity(new Intent(this, QRGeneratorActivity.class));
        }
        //navigate to QR scanner
        else if (view == buttonScan) {
            startActivity(new Intent(this, QRScanner.class));
        }
        //Navigate to submission form for users info
        else if (view == buttonMyDetails) {
            startActivity(new Intent(this, ProfileActivity.class));
        }
        //Navigate to contact list
        else if (view == buttonList) {
            startActivity(new Intent(this, ContactListActivity.class));
        }
        //Nav to help
        else if (view == buttonHelp) {
            startActivity(new Intent(this, Help.class));
        }
    }
}
