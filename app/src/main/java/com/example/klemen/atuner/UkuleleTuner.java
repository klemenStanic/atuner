package com.example.klemen.atuner;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.Image;
import android.provider.ContactsContract;
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

public class UkuleleTuner extends Activity {

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

        setContentView(R.layout.activity_ukulele_tuner);


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

        if (pitchInHz >= 388 && pitchInHz <= 396) {
            noteText.setText("G4");
            if (pitchInHz >= 388 && pitchInHz < 390) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 396 && pitchInHz > 394) {
                rightArrow.setVisibility(View.INVISIBLE);
                leftArrow.setVisibility(View.VISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.ukulele_untunedg);
            if (pitchInHz >= 390 && pitchInHz <= 394) { // 392
                relativeLayout.setBackgroundResource(R.drawable.ukulele_tunedg);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 257 && pitchInHz <= 265) {
            noteText.setText("C4");
            if (pitchInHz >= 257 && pitchInHz < 259) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 265 && pitchInHz > 263) {
                rightArrow.setVisibility(View.INVISIBLE);
                leftArrow.setVisibility(View.VISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.ukulele_untunedc);
            if (pitchInHz >= 259 && pitchInHz <= 263) { //261
                relativeLayout.setBackgroundResource(R.drawable.ukulele_tunedc);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 325 && pitchInHz <= 333) {
            noteText.setText("E4");
            if (pitchInHz >= 325 && pitchInHz < 327) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 333 && pitchInHz > 331) {
                rightArrow.setVisibility(View.INVISIBLE);
                leftArrow.setVisibility(View.VISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.ukulele_untunede);
            if (pitchInHz >= 327 && pitchInHz <= 331) { //329
                relativeLayout.setBackgroundResource(R.drawable.ukulele_tunede);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 436 && pitchInHz <= 444) {
            noteText.setText("A4");
            if (pitchInHz >= 436 && pitchInHz < 438) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 444 && pitchInHz > 442) {
                rightArrow.setVisibility(View.INVISIBLE);
                leftArrow.setVisibility(View.VISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.ukulele_untuneda);
            if (pitchInHz >= 438 && pitchInHz <= 442) { //440
                relativeLayout.setBackgroundResource(R.drawable.ukulele_tuneda);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
//-----------------------------------------------------------------------------------
        } else {
            //noteText.setText("");
            relativeLayout.setBackgroundResource(R.drawable.ukulele_static);
        }
    }



    public void getValidSampleRates() {
        for (int rate : new int[]{44100, 22050, 11025, 16000, 8000}) {  // add the rates you wish to check against
            int bufferSize = AudioRecord.getMinBufferSize(rate, AudioFormat.CHANNEL_CONFIGURATION_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
            Log.e("MYLOG", "BUFFER SIZE: " + bufferSize + "");
        }
    }
}
