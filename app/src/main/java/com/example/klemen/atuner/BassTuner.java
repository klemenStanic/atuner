package com.example.klemen.atuner;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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

        if (pitchInHz >= 78 && pitchInHz <= 86) {
            noteText.setText("E2");
            if (pitchInHz >= 78 && pitchInHz < 80) {
                noteText.setText("  E2 >");
            } else if (pitchInHz <= 86 && pitchInHz > 84) {
                noteText.setText("< E2  ");
            }
            relativeLayout.setBackgroundResource(R.drawable.guitar_untunnede);
            if (pitchInHz >= 80 && pitchInHz <= 84) {
                relativeLayout.setBackgroundResource(R.drawable.guitar_tunnede);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 106 && pitchInHz <= 114) {
            noteText.setText("A2");
            if (pitchInHz >= 106 && pitchInHz < 108) {
                noteText.setText("  A2 >");
            } else if (pitchInHz <= 114 && pitchInHz > 112) {
                noteText.setText("< A2  ");
            }
            relativeLayout.setBackgroundResource(R.drawable.guitar_untunneda);
            if (pitchInHz >= 108 && pitchInHz <= 112) {
                relativeLayout.setBackgroundResource(R.drawable.guitar_tunneda);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 142 && pitchInHz <=150) {
            noteText.setText("D3");
            if (pitchInHz >= 142 && pitchInHz < 144) {
                noteText.setText("  D3 >");
            } else if (pitchInHz <= 150 && pitchInHz > 148) {
                noteText.setText("< D3  ");
            }
            relativeLayout.setBackgroundResource(R.drawable.guitar_untunnedd);
            if (pitchInHz >= 144 && pitchInHz <= 148) {
                relativeLayout.setBackgroundResource(R.drawable.guitar_tunnedd);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 192 && pitchInHz <= 200) {
            noteText.setText("G2");
            if (pitchInHz >= 192 && pitchInHz < 194) {
                noteText.setText("  G2 >");
            } else if (pitchInHz <= 200 && pitchInHz > 198) {
                noteText.setText("< G2  ");
            }
            relativeLayout.setBackgroundResource(R.drawable.guitar_untunnedg);
            if (pitchInHz >= 194 && pitchInHz <= 198) {
                relativeLayout.setBackgroundResource(R.drawable.guitar_tunnedg);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 242 && pitchInHz <= 251) {
            noteText.setText("B3");
            if (pitchInHz >= 242 && pitchInHz < 244) {
                noteText.setText("  B3 >");
            } else if (pitchInHz <= 251 && pitchInHz > 249) {
                noteText.setText("< B3  ");
            }
            relativeLayout.setBackgroundResource(R.drawable.guitar_untunnedb);
            if (pitchInHz >= 244 && pitchInHz <= 249) {
                relativeLayout.setBackgroundResource(R.drawable.guitar_tunnedb);
            }
//-----------------------------------------------------------------------------------
        } else if (pitchInHz >= 325 && pitchInHz <= 334) {
            noteText.setText("e4");
            if (pitchInHz >= 325 && pitchInHz < 327) {
                noteText.setText("  e4 >");
            } else if (pitchInHz <= 334 && pitchInHz > 331) {
                noteText.setText("< e4  ");
            }
            relativeLayout.setBackgroundResource(R.drawable.guitar_untunned_e);
            if (pitchInHz >= 327 && pitchInHz <= 331) {
                relativeLayout.setBackgroundResource(R.drawable.guitar_tunned_e);
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

