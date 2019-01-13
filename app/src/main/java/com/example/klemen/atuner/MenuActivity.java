package com.example.klemen.atuner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MenuActivity extends Activity {

    ImageView guitar;
    ImageView bass;
    ImageView uku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        checkRecordPermission();


        guitar = findViewById(R.id.guitarIV);
        bass = findViewById(R.id.bassIV);
        uku = findViewById(R.id.ukuIV);


        guitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOut = new Intent(getApplicationContext(), GuitarTuner.class);
                startActivity(intentOut);
            }
        });

        bass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOut = new Intent(getApplicationContext(), BassTuner.class);
                startActivity(intentOut);
            }
        });

        uku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MYLOG", "CLICKED uku");
            }
        });
    }


    private void checkRecordPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    123);
        }
    }
}
