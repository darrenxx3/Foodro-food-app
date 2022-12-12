package com.latutslab_00000053580.foodro_home;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.method.HideReturnsTransformationMethod;
        import android.text.method.PasswordTransformationMethod;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.EditText;

        import com.latutslab_00000053580.foodro.APIHandler;

public class Account_Setup extends AppCompatActivity {

    EditText inputPass, inputUsername;
    CheckBox showpassword;
    APIHandler handler = new APIHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_account_setup);



        inputPass = findViewById(R.id.inputPass);
        inputUsername = findViewById(R.id.txtFirst);
        showpassword = findViewById(R.id.showpassword);

        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if(b){
                    inputPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    inputPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        Button btnsignup = findViewById(R.id.signupBtn);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account_Setup.this, SignUpMenu.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        Button btnLogin = findViewById(R.id.loginBtn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handler.login(getBaseContext(),inputUsername.getText().toString(), inputPass.getText().toString());
//                Log.i("USER", user.getEmail());
//                Log.i("USER", user.getFirstname());
//                Log.i("USER", user.getLastname());
//                Log.i("USER", Integer.toString(user.getUser_id()));
//                Log.i("USER", Integer.toString(user.getActive()));
//                Log.i("USER", Integer.toString(user.getRole()));
//
//                Food[] foods = handler.getAllFood(getBaseContext());
//                Log.i("FOODS", foods[0].getName());
//                Log.i("FOODS", foods[1].getName());
//                Log.i("FOODS", foods[2].getName());


            }
        });
    }
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}