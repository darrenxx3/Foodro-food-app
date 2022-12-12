package com.latutslab_00000053580.foodro_home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.latutslab_00000053580.foodro.APIHandler;

public class SignUpMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_sign_up_menu);

        Button btnSignup = (Button) findViewById(R.id.btnSignup);
        EditText txtFirst = (EditText) findViewById(R.id.txtFirst);
        EditText txtLast = (EditText) findViewById(R.id.txtLast);
        EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        CheckBox isMerchant = findViewById(R.id.checkMerchant);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIHandler handler = new APIHandler();
                if(isMerchant.isChecked()){
                    handler.register(getBaseContext(), 2,
                            txtFirst.getText().toString(),
                            txtLast.getText().toString(),
                            txtPassword.getText().toString(),
                            txtEmail.getText().toString());
                }else{
                    handler.register(getBaseContext(), 1,
                            txtFirst.getText().toString(),
                            txtLast.getText().toString(),
                            txtPassword.getText().toString(),
                            txtEmail.getText().toString());
                }

            }
        });    }
}