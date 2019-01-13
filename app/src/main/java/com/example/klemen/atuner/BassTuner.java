package com.example.klemen.atuner;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class BassTuner extends Activity {

    TextView pitchText;
    TextView noteText;
    RelativeLayout relativeLayout;
    ArrayList<Float> arrayList;
    Thread audioThread;
    Runnable runnable;
    PitchDetectionHandler pdh;
    AudioDispatcher dispatcher;

    ImageView leftArrow;
    ImageView rightArrow;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            dispatcher.stop();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bass_tuner);


        noteText = findViewById(R.id.noteTV);
        relativeLayout = findViewById(R.id.relativeLayout);
        arrayList = new ArrayList<Float>();
        leftArrow = findViewById(R.id.leftArrowIV);
        leftArrow.setVisibility(View.INVISIBLE);
        rightArrow = findViewById(R.id.rightArrowIV);
        rightArrow.setVisibility(View.INVISIBLE);

        getValidSampleRates();

        dispatcher =
                AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult res, AudioEvent e) {
                final float pitchInHz = res.getPitch();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        //Log.e("MYLOG", pitchInHz + "");
                        handlePitches(pitchInHz);
                        processPitch();
                    }
                };
                runOnUiThread(runnable);
            }
        };



        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);

        audioThread = new Thread(dispatcher, "Audio Thread");

        audioThread.start();
    }

    public void handlePitches(float pitchInHz){
        if (pitchInHz < 40 || pitchInHz > 500) {
            return;
        }
        arrayList.add(pitchInHz);
        if (arrayList.size() > 7){
            arrayList.remove(0);
        }
//        for (int i = 0; i < arrayList.size(); i++){
//            Log.e("ARRAYLIST", arrayList.get(i) + "");
//            Log.e("ARRAYLIST", "----------------");
//
//        }
        Log.e("SIZE", arrayList.size() + "");
    }

    public float getMean() {
        float out = 0;
        for (int i = 0; i < arrayList.size(); i++){
            out += arrayList.get(i);
        }
        out = out / arrayList.size();
        return out;
    }

    public void processPitch() {

        float pitchInHz = getMean();
        //Log.e("MYLOG", pitchInHz + "");

        if (pitchInHz >= 37 && pitchInHz <= 45) {
            noteText.setText("E1");
            if (pitchInHz >= 37 && pitchInHz < 39) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 45 && pitchInHz > 43) {
                rightArrow.setVisibility(View.INVISIBLE);
                leftArrow.setVisibility(View.VISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.bass_untunede);
            if (pitchInHz >= 39 && pitchInHz <= 43) { //41
                relativeLayout.setBackgroundResource(R.drawable.bass_tunede);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 51 && pitchInHz <= 59) {
            noteText.setText("A1");
            if (pitchInHz >= 51 && pitchInHz < 53) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 59 && pitchInHz > 57) {
                rightArrow.setVisibility(View.INVISIBLE);
                leftArrow.setVisibility(View.VISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.bass_untuneda);
            if (pitchInHz >= 53 && pitchInHz <= 57) { //55
                relativeLayout.setBackgroundResource(R.drawable.bass_tuneda);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 69 && pitchInHz <= 77) {
            noteText.setText("D2");
            if (pitchInHz >= 69 && pitchInHz < 71) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 77 && pitchInHz > 75) {
                rightArrow.setVisibility(View.INVISIBLE);
                leftArrow.setVisibility(View.VISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.bass_untunedd);
            if (pitchInHz >= 71 && pitchInHz <= 75) { //73
                relativeLayout.setBackgroundResource(R.drawable.bass_tunedd);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 94 && pitchInHz <= 102) {
            noteText.setText("G2");
            if (pitchInHz >= 94 && pitchInHz < 96) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 102 && pitchInHz > 100) {
                rightArrow.setVisibility(View.INVISIBLE);
                leftArrow.setVisibility(View.VISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.bass_untunedg);
            if (pitchInHz >= 96 && pitchInHz <= 100) { //98
                relativeLayout.setBackgroundResource(R.drawable.bass_tunedg);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
//-----------------------------------------------------------------------------------
        } else {
            //noteText.setText("");
            relativeLayout.setBackgroundResource(R.drawable.bass_static);
        }
    }



    public void getValidSampleRates() {
        for (int rate : new int[]{44100, 22050, 11025, 16000, 8000}) {  // add the rates you wish to check against
            int bufferSize = AudioRecord.getMinBufferSize(rate, AudioFormat.CHANNEL_CONFIGURATION_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
            Log.e("MYLOG", "BUFFER SIZE: " + bufferSize + "");
        }
    }
}
