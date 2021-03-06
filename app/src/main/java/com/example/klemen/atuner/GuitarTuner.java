package com.example.klemen.atuner;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.nio.Buffer;
import java.util.ArrayList;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class GuitarTuner extends Activity {

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

        setContentView(R.layout.activity_guitar_tuner);


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


        if (pitchInHz >= 78 && pitchInHz <= 86) {
            noteText.setText("E2");
            if (pitchInHz >= 78 && pitchInHz < 80) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 86 && pitchInHz > 84) {
                leftArrow.setVisibility(View.VISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.guitar_untunnede);
            if (pitchInHz >= 80 && pitchInHz <= 84) {
                relativeLayout.setBackgroundResource(R.drawable.guitar_tunnede);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 106 && pitchInHz <= 114) {
            noteText.setText("A2");
            if (pitchInHz >= 106 && pitchInHz < 108) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 114 && pitchInHz > 112) {
                leftArrow.setVisibility(View.VISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.guitar_untunneda);
            if (pitchInHz >= 108 && pitchInHz <= 112) {
                relativeLayout.setBackgroundResource(R.drawable.guitar_tunneda);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 142 && pitchInHz <=150) {
            noteText.setText("D3");
            if (pitchInHz >= 142 && pitchInHz < 144) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 150 && pitchInHz > 148) {
                leftArrow.setVisibility(View.VISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.guitar_untunnedd);
            if (pitchInHz >= 144 && pitchInHz <= 148) {
                relativeLayout.setBackgroundResource(R.drawable.guitar_tunnedd);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 192 && pitchInHz <= 200) {
            noteText.setText("G2");
            if (pitchInHz >= 192 && pitchInHz < 194) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 200 && pitchInHz > 198) {
                leftArrow.setVisibility(View.VISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.guitar_untunnedg);
            if (pitchInHz >= 194 && pitchInHz <= 198) {
                relativeLayout.setBackgroundResource(R.drawable.guitar_tunnedg);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);

            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 242 && pitchInHz <= 251) {
            noteText.setText("B3");
            if (pitchInHz >= 242 && pitchInHz < 244) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 251 && pitchInHz > 249) {
                leftArrow.setVisibility(View.VISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.guitar_untunnedb);
            if (pitchInHz >= 244 && pitchInHz <= 249) {
                relativeLayout.setBackgroundResource(R.drawable.guitar_tunnedb);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 325 && pitchInHz <= 334) {
            noteText.setText("e4");
            if (pitchInHz >= 325 && pitchInHz < 327) {
                rightArrow.setVisibility(View.VISIBLE);
                leftArrow.setVisibility(View.INVISIBLE);
            } else if (pitchInHz <= 334 && pitchInHz > 331) {
                leftArrow.setVisibility(View.VISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
            relativeLayout.setBackgroundResource(R.drawable.guitar_untunned_e);
            if (pitchInHz >= 327 && pitchInHz <= 331) {
                relativeLayout.setBackgroundResource(R.drawable.guitar_tunned_e);
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
            }
//-----------------------------------------------------------------------------------
        } else {
            //noteText.setText("");
            relativeLayout.setBackgroundResource(R.drawable.guitar_static);
        }
    }



    public void getValidSampleRates() {
        for (int rate : new int[]{44100, 22050, 11025, 16000, 8000}) {  // add the rates you wish to check against
            int bufferSize = AudioRecord.getMinBufferSize(rate, AudioFormat.CHANNEL_CONFIGURATION_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
            Log.e("MYLOG", "BUFFER SIZE: " + bufferSize + "");
        }
    }
}
