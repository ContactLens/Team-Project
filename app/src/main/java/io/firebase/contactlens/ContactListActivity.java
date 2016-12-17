/*package io.firebase.contactlens;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.Toast;

public class ContactListActivity extends AppCompatActivity {

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        String[] values = new String[] {
                "Bruce Wayne"

        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent appInfo = new Intent(ContactListActivity.this, ContactDisplayActivity.class);
                startActivity(appInfo);

            }

        });
    }

}*/

package io.firebase.contactlens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import static io.firebase.contactlens.R.id.list;


public class ContactListActivity extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReferenceUsers;
    DatabaseReference databaseReferenceContacts;

    //constructs lists for names and UIDs
    List<String> arrNames = new ArrayList<String>();
    public List<String> dedupeNames;
    List<String> arrUIDs = new ArrayList<String>();
    public List<String> dedupeUIDs;

    //define the listview itself as well as a string that contains the entirety of the names column in the database and likewise for the users column
    private ListView listView;
    public String contactNamesString;
    public String contactUIDString;
    public String[] contactUIDArray;
    public String[] contactNamesArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        listView = (ListView) findViewById(list);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //gets references to the positions of the users and contacts rows in the database
        databaseReferenceUsers = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("users"));
        databaseReferenceContacts = (FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("contacts"));

        //listener for changes to the users column in database, only fires on opening the contact list rather than every few milliseconds
        databaseReferenceUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //if the users row exists (for a user that has yet to scan anothers QR it won't) take the value stored in the row and cast it
                    //to a string and assign it to contactUIDString
                    contactUIDString = dataSnapshot.getValue().toString();
                    //split the string of UID's on the /'s that separate individuals and add this to the string array
                    contactUIDArray = contactUIDString.split("/");
                    //cast this to an ArrayList as it must be of this type for the next step
                    arrUIDs = Arrays.asList(contactUIDArray);
                    //copy arrUIDs contents into a new ArrayList as a LinkedHaskSet, this ensures if a user accidentally scans the same person twice
                    //the duplicated entry is removed and the UIDs are in the same order as the names displayed
                    dedupeUIDs = new ArrayList<>(new LinkedHashSet<String>(arrUIDs));

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        databaseReferenceContacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //follows same structure as above
                    contactNamesString = dataSnapshot.getValue().toString();

                    contactNamesArray = contactNamesString.split("/");

                    arrNames = Arrays.asList(contactNamesArray);

                    dedupeNames = new ArrayList<>(new LinkedHashSet<String>(arrNames));

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, dedupeNames);
                    listView.setAdapter(arrayAdapter);
                }
                else
                {
                    Toast.makeText(getBaseContext(), "You have not added any contacts yet",Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //assigns the position of the name clicked to an int
                int posit = position;
                //assigns the value in the UID array with the same position as that of the name clicked on
                String UID = dedupeUIDs.get(posit).trim();
                //makes this variable able to be retrieved in the ContactDisplayActivity
                Intent secondActivity = new Intent (getApplicationContext(),ContactDisplayActivity.class);
                secondActivity.putExtra("UID",UID);
                startActivity(secondActivity);
            }
        });
    }

    @Override
    public void onClick(View view) {}
}