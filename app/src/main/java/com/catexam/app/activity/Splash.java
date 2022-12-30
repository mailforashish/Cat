package com.catexam.app.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.catexam.app.R;
import com.catexam.app.databinding.ActivitySplashBinding;
import com.catexam.app.util.HideStatus;

public class Splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    ActivitySplashBinding binding;
    HideStatus hideStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        hideStatus = new HideStatus(getWindow(), true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}