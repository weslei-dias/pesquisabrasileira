package com.domain.androidcrud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.domain.androidcrud.R;

public class SplashActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarLogin();
            }
        }, 3000);
    }

    private void mostrarLogin() {
        Intent intent = new Intent(SplashActivity.this,
                LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
