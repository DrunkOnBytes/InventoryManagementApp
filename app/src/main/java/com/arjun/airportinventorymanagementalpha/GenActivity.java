package com.arjun.airportinventorymanagementalpha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;

public class GenActivity extends AppCompatActivity {

    TextView item, installedBy;
    ImageView qr;
    Button gen;
    DatabaseReference database;
    FirebaseAuth mAuth;
    String code1,code2;
    int flag;

    public String getAlphaNumericString()
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen);

        item=(TextView)findViewById(R.id.item);
        installedBy=(TextView)findViewById(R.id.instalBy);
        qr= (ImageView)findViewById(R.id.qr);
        gen=(Button)findViewById(R.id.genButton);
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t = item.getText().toString();

                if (t != null && !t.isEmpty()) {

                    code1=t.substring(0,2);

                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                HashMap<String, Object> existingCodes = (HashMap<String, Object>) dataSnapshot.getValue();
                                do {
                                    flag = 0;
                                    code2 = getAlphaNumericString();
                                    for (String key : existingCodes.keySet()) {

                                        if (key.equals(code1 + code2))
                                            flag = 1;
                                    }
                                } while (flag == 1);
                            }
                            else{
                                code2 = getAlphaNumericString();
                            }
                                Log.i("Mes", code2);

                                try {


                                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                    BitMatrix bitMatrix = multiFormatWriter.encode((code1 + code2), BarcodeFormat.QR_CODE, 500, 500);
                                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                    qr.setImageBitmap(bitmap);

                                } catch (WriterException e) {
                                    e.printStackTrace();
                                }

                                Data data = new Data(item.getText().toString(), installedBy.getText().toString());
                                database.child(code1 + code2).setValue(data, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                                        if (databaseError == null)
                                            Toast.makeText(GenActivity.this, "Data uploaded Successfully", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(GenActivity.this, "Data upload Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

        });

    }
}
