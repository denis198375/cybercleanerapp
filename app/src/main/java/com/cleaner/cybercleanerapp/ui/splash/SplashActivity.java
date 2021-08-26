package com.cleaner.cybercleanerapp.ui.splash;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.cleaner.cybercleanerapp.R;
import com.cleaner.cybercleanerapp.ui.MainActivity;
import com.cleaner.cybercleanerapp.util.DiskStat;
import com.cleaner.cybercleanerapp.util.MemStat;
import com.cleaner.cybercleanerapp.util.MemoryUtils;
import com.cleaner.cybercleanerapp.util.SingletonClassApp;

import javax.net.ssl.SNIHostName;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
    }

    private void initViews() {
        getMemory();
        ProgressBar progressView = findViewById(R.id.progress);
        ProgressBarAnimation anim = new ProgressBarAnimation(progressView, 0, 100);
        anim.setDuration(7000);
        progressView.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void getMemory() {
        final Handler handler = new Handler();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final DiskStat diskStat = new DiskStat();
                final MemStat memStat = new MemStat(getApplicationContext());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("mamory", memStat.getUsedMemory() + "/" + memStat.getTotalMemory());
                        SingletonClassApp.getInstance().UsedMemory = String.valueOf(memStat.getUsedMemory());
                        SingletonClassApp.getInstance().TotalMemory = String.valueOf(memStat.getTotalMemory());
                        SingletonClassApp.getInstance().procentMemory=memStat.getProcentMemory();

//                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                    intent.putExtra(MainActivity.PARAM_TOTAL_SPACE, diskStat.getTotalSpace());
//                    intent.putExtra(MainActivity.PARAM_USED_SPACE, diskStat.getUsedSpace());
//                    intent.putExtra(MainActivity.PARAM_TOTAL_MEMORY, memStat.getTotalMemory());
//                    intent.putExtra(MainActivity.PARAM_USED_MEMORY, memStat.getUsedMemory());
//                    startActivity(intent);
//                    finish();
                    }
                });
            }
        });

        thread.start();
    }

}