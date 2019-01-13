package com.example.klemen.atuner;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;

public class Metronome extends Activity {

    AudioGenerator audio;
    ImageView playBtn;
    ImageView plusBtn;
    ImageView minusBtn;
    SeekBar seekBar;
    TextView bpmTV;

    boolean playing = false;
    int currentBPM = 60;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            if(playing) {
                audio.destroyAudioTrack();
            }

            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);

        audio = new AudioGenerator(10000);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bpmTV.setText(progress + "");
                currentBPM = progress;
                playing = false;
                playBtn.setImageResource(R.drawable.play);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        playBtn = findViewById(R.id.playBtn);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playing = !playing;
                if (playing) {
                    playBtn.setImageResource(R.drawable.pause);
                    startPlaying();


                } else {
                    playBtn.setImageResource(R.drawable.play);
                    playing = false;

                }
            }
        });


        plusBtn = findViewById(R.id.plusIV);

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentBPM == 260){
                    return;
                }
                seekBar.setProgress(Integer.parseInt((String) bpmTV.getText()) + 1);
                bpmTV.setText((Integer.parseInt((String) bpmTV.getText()) + 1) + "");
            }
        });
        minusBtn = findViewById(R.id.minusIV);
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentBPM == 20){
                    return;
                }
                seekBar.setProgress(Integer.parseInt((String) bpmTV.getText()) - 1);
                bpmTV.setText((Integer.parseInt((String) bpmTV.getText()) - 1) + "");
            }
        });




        bpmTV = findViewById(R.id.currentBPM);


//
//        double[] secondOfPlay = createBeat(30);
//
//        while (true){
//            audio.writeSound(secondOfPlay);
//        }

        //audio.destroyAudioTrack();
    }


    public void startPlaying() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                audio.createPlayer();


                float lenTemp = (10000.0f * 60.0f/((float) currentBPM));
                int len = (int) lenTemp;


                double[] beat = new double[len];
                for (int i = 0; i < len; i++){
                    if (i >= len-10){
                        beat[i] = 1;
                    } else {
                        beat[i] = 0;
                    }

                }

                while (playing) {
                    audio.writeSound(beat);
                }
                audio.destroyAudioTrack();
            }
        });

    }

}
