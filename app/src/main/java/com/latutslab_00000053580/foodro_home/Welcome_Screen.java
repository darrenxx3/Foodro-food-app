package com.latutslab_00000053580.foodro_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.latutslab_00000053580.foodro.User;
import com.latutslab_00000053580.sqlite.DbUser;

public class Welcome_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_welcome_screen);

        DbUser dbUser = new DbUser(getApplicationContext());
        dbUser.open();
        // TODO: TESTING PURPOSE ONLY, buat nge log out
        dbUser.logout();

        User user = dbUser.Authenticate();
        dbUser.close();

        if (user != null) {
            int role = user.getRole();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
//            intent.putExtra("ROLE", role);
            startActivity(intent);

//            if (user.getRole() == 1) {
//                startActivity(new Intent(Welcome_Screen.this, MainActivity.class));
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            } else if (user.getRole() == 2) {
//                startActivity(new Intent(Welcome_Screen.this, Home_Merchant.class));
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
        }

        Button ButtonGET = findViewById(R.id.buttonGTS);
        ButtonGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Welcome_Screen.this, Account_Setup.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}