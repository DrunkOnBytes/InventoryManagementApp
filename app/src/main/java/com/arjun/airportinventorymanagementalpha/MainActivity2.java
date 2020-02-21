package com.arjun.airportinventorymanagementalpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity {

    Intent intent2;
    public void gen(View view){

        Intent intentScan=new Intent(getApplicationContext(), GenActivity.class);
        startActivity(intentScan);
    }
    public void scan(View view){

        Intent intentScan=new Intent(getApplicationContext(), ScanActivity.class);
        startActivity(intentScan);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        FirebaseAuth.getInstance().signOut();
        intent2= new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent2);
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent= getIntent();

        getSupportActionBar().setTitle(intent.getStringExtra("username"));

    }
}
