package io.firebase.contactlens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static io.firebase.contactlens.R.id.textViewHelp;
import static io.firebase.contactlens.R.id.textViewSignin;

public class Help extends AppCompatActivity {
    private TextView textViewHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        textViewHelp = (TextView) findViewById(R.id.textViewHelp);
        textViewHelp.setText("Welcome to ContactLens\nPress a button to navigate to your required activity\n\nMy Details\nAt the My Details screen you may submit your chosen contact details. Pressing the Save Information button will store the input information. When filling in the Facebook, Twitter, LinkedIn and GitHub text fields it is recommended you use the following structure - sitename.com/user.id e.g. facebook.com/john.smith, twitter.com/johnsmith123. Doing so will allow other users to link directly into your social media from the app.\n\nScan\nPressing the Scan button will open the QR Scanner. Direct this at another users QR code to retrieve their details.\n\nMy QR\nOn clicking My QR an image of a QR will appear. This is your unique QR code and allowing other users to scan it will give them your details\n\nMy Contacts\nThis will display the names of people whose ContactLens you have connected with. On clicking their names it will display their expanded contact details.");

    }
}
