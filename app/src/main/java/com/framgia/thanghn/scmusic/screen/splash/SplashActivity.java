package com.framgia.thanghn.scmusic.screen.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.framgia.thanghn.scmusic.R;
import com.framgia.thanghn.scmusic.screen.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
    private static boolean ACTIVE = true;
    private static final int SPLASH_TIME = 2500;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (ACTIVE && (waited < SPLASH_TIME)) {
                        sleep(100);
                        if (ACTIVE) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                } finally {
                    finish();
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }
        };
        splashTread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ACTIVE = false;
        }
        return true;
    }
}