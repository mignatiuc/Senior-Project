package com.example.maxim_ignatiuc.battlescreen;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.animation.TranslateAnimation;


public class MainActivity extends Activity {

    boolean keepRun = true;
    int totalProgressTime = 100;
    private Handler progressBarbHandler = new Handler();
    private Thread t;
    private ProgressBar progress;

    TextView tv;
    RelativeLayout rl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progress = (ProgressBar) findViewById(R.id.progressBar1);
        progress.setProgress(totalProgressTime);






        rl = (RelativeLayout) findViewById(R.id.rl);
        tv = (TextView) findViewById(R.id.myTextView);




       final TranslateAnimation moveLefttoRight = new TranslateAnimation(100, 420, 0, 0);
        moveLefttoRight.setDuration(30);
        moveLefttoRight.setRepeatCount(1);
        moveLefttoRight.setRepeatMode(2);
        moveLefttoRight.setFillAfter(false);





        progress.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (totalProgressTime >= 100) {
                    tv.startAnimation(moveLefttoRight);
                    progress.setProgress(0);
                    totalProgressTime = 0;
                }
            }
        });



        t = new Thread(new Runnable(){

            @Override
            public void run() {

                while (keepRun) {

                    if (totalProgressTime >= 100) {
                        progress.setProgress(100);
                    } else {
                        try {
                            totalProgressTime += 5;
                            Thread.sleep(80);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        progressBarbHandler.post(new Runnable() {
                            public void run() {
                                progress.setProgress(totalProgressTime);
                            }
                        });

                    }
                }


            }

        });

t.start();

}

    @Override
    protected void onDestroy() {
        keepRun = false;
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
