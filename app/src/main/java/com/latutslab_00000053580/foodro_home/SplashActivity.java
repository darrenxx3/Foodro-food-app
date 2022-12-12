package com.latutslab_00000053580.foodro_home;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.latutslab_00000053580.foodro.User;
import com.latutslab_00000053580.sqlite.DbUser;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_splash);

        DbUser dbUser = new DbUser(getApplicationContext());
        dbUser.open();
        User user = dbUser.Authenticate();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if (user != null) {
                    startActivity(new Intent(SplashActivity.this ,MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    startActivity(new Intent(SplashActivity.this, Account_Setup.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

                SplashActivity.this.finish();
                dbUser.close();
            }
        }, SPLASH_DISPLAY_LENGTH);


    }
}