package io.firebase.contactlens;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRScanner extends AppCompatActivity {

    public String scanContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        //sets up QR scanner attributes
        final Activity activity = this;
        IntentIntegrator integrator= new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setOrientationLocked(false);
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        //on scanning successfully
        if(result != null)
        {
            //check scans contents, if there are none, implying the user activated scan successfully but pressed back before scanning a code, return
            //to the menu
            if(result.getContents()==null){
                Toast scanCancel = Toast.makeText(this, "Scan Cancelled",Toast.LENGTH_SHORT);
                scanCancel.setGravity(Gravity.CENTER_VERTICAL,0,0);
                scanCancel.show();

                finish();
            }
            //if a QR was scanned place the scanned data into a string variable and make this available for use in the QueryDatabase activity
            else{
                Toast scan = Toast.makeText(this, "Scan Complete",Toast.LENGTH_SHORT);
                scan.setGravity(Gravity.CENTER_VERTICAL,0,0);
                scan.show();

                scanContent = result.getContents();
                Intent secondActivity = new Intent (getApplicationContext(),QueryDatabase.class);
                secondActivity.putExtra("scanContent",scanContent);
                startActivity(secondActivity);
            }
        }
        else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}

