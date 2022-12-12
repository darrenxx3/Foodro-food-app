package com.latutslab_00000053580.foodro_home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.latutslab_00000053580.foodro.APIHandler;
import com.latutslab_00000053580.foodro.Cart;
import com.latutslab_00000053580.foodro_home.R;
import com.latutslab_00000053580.foodro_user.HomeUser;
import com.latutslab_00000053580.sqlite.DbCart;
import com.latutslab_00000053580.sqlite.DbUser;

import java.io.IOException;
import java.util.ArrayList;

public class UploadPaymentUser extends AppCompatActivity {

    private final int GALLERY_REQ_CODE = 1000;

    ImageView uploadImage;
    Bitmap photo;
    Button btnConfirm;
    APIHandler handler = new APIHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_payment_user);

        DbCart dbCart = new DbCart(getApplicationContext());
        dbCart.open();


        uploadImage = (ImageView)findViewById(R.id.uploadImage);
        Button btnUpload = (Button)findViewById(R.id.btnOpenGallery);
        TextView txtTotalBayar = (TextView) findViewById(R.id.txtTotalBayar);
        txtTotalBayar.setText(String.valueOf(dbCart.getTotal()));

        btnConfirm = findViewById(R.id.btnConfirm);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, GALLERY_REQ_CODE);
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Cart> cartArrayList = dbCart.getCartList();

                DbUser dbUser = new DbUser(getApplicationContext());
                dbUser.open();
                int userID = dbUser.getID();
                dbUser.close();
                dbCart.clearCart();
                dbCart.close();

                handler.createOrder(getApplicationContext(), userID, cartArrayList,  photo);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try{
                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                uploadImage.setImageBitmap(photo);
                btnConfirm.setEnabled(true);
            }catch(IOException e){
                Log.e("CHOOSEIMG",  e.toString());
            }
        }
    }
}