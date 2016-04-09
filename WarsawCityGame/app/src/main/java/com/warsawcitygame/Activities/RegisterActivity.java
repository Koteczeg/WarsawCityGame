package com.warsawcitygame.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.warsawcitygame.CustomControls.KenBurnsView;
import com.warsawcitygame.R;


public class RegisterActivity extends AppCompatActivity {
    private KenBurnsView mKenBurns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mKenBurns = (KenBurnsView) findViewById(com.warsawcitygame.R.id.ken_burns_images);
        mKenBurns.setImageResource(com.warsawcitygame.R.drawable.splash_background);
    }
}
