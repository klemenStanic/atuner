package com.example.klemen.atuner;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Menu extends ListActivity {

    ListView list;
    String[] itemname ={
            "Guitar",
            "Bass",
            "Ukulele",
            "Metronome"
    };

    Integer[] imgid={
        R.drawable.guitar_menu,
            R.drawable.bass_menu,
            R.drawable.ukulele_menu,
            R.drawable.metronome_menu
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        ImageListAdapter adapter=new ImageListAdapter(this, itemname, imgid);
        list= findViewById(android.R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intentOut = new Intent();
                switch(position) {
                    case 0 :
                        intentOut = new Intent(getApplicationContext(), GuitarTuner.class);
                        break;
                    case 1 :
                        intentOut = new Intent(getApplicationContext(), BassTuner.class);
                        break;
                    case 2:
                        intentOut = new Intent(getApplicationContext(), UkuleleTuner.class);
                        break;
                    case 3:
                        intentOut = new Intent(getApplicationContext(), Metronome.class);
                        break;
                    default :
                        Log.e("MYLOG", "wrong intent");
                }
                startActivity(intentOut);
            }
        });
    }
}
