package io.firebase.contactlens;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class QRGeneratorActivity extends AppCompatActivity {

    private ImageView imageView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        final Context context = this;

        //creates a string containing the user ID of the currently logged in user
        String text2QR = user.getUid().toString();
        //ZXing method for initialising an undefined encoder
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            //creates a 200x200 matrix of bits representing the string presented in the structure of a QR code (as opposed to a barcode)
            BitMatrix bitMatrix = multiFormatWriter.encode(text2QR, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            //encodes the bit matrix into a bitmap image
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            imageView = (ImageView) this.findViewById(R.id.imageView);
            //sets the imageview to display the bitmap
            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }


    }
}
