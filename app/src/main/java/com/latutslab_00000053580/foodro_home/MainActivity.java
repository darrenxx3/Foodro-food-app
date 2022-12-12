package com.latutslab_00000053580.foodro_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.latutslab_00000053580.foodro_home.databinding.HomeMainBinding;
import com.latutslab_00000053580.foodro_merchant.HomeMerchant;
import com.latutslab_00000053580.foodro_user.HomeUser;
import com.latutslab_00000053580.sqlite.DbUser;

public class MainActivity extends AppCompatActivity {

    HomeMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DbUser dbuser = new DbUser(getBaseContext());
        dbuser.open();
        int user_role = dbuser.getRole();
        dbuser.close();

        Log.i("SQLite", "ROLE: " + user_role);

        if(user_role == 1){
            replaceFragment(new HomeUser());
        } else if(user_role == 2 ){
            replaceFragment(new HomeMerchant());
        }
//        Intent intent = getIntent();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:

                    switch (user_role) {
                        case 1:
                            replaceFragment(new HomeUser());
                            break;
                        case 2:
                            replaceFragment(new HomeMerchant());
                            break;
                        default:
                            Log.e("DEFAULTSWITCH", "fail");
                            Log.e("DEFAULTSWITCH", Integer.toString(user_role));
                            dbuser.open();
                            dbuser.logout();
                            dbuser.close();
                    }
                    break;
                case R.id.order:
                    replaceFragment(new OrderNavigation());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileUser());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}