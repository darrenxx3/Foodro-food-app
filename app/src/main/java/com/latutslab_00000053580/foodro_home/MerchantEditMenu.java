package com.latutslab_00000053580.foodro_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.latutslab_00000053580.foodro.APIHandler;
import com.latutslab_00000053580.sqlite.DbUser;

import java.io.IOException;

public class MerchantEditMenu extends AppCompatActivity {

    Bitmap photo;
    APIHandler handler = new APIHandler();
    ImageView image;
    Button btnEdit;
    private final int IMG_REQUEST = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_edit_menu);
        Intent i = getIntent();

        EditText addName = (EditText) findViewById(R.id.addName);
        EditText addPrice = (EditText) findViewById(R.id.addPrice);
        image = (ImageView) findViewById(R.id.image);
        Button btnChoose = findViewById(R.id.btnImage);
        btnEdit = (Button) findViewById(R.id.btnAddMenu2);
        btnEdit.setEnabled(false);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMG_REQUEST);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                APIHandler handler = new APIHandler();
                handler.updateFood(getApplicationContext(),
                        i.getIntExtra("id", 1),
                        addName.getText().toString(),
                        Integer.parseInt(addPrice.getText().toString()),
                        photo
                );

                Log.i("UPDATEFOOD", Integer.toString(i.getIntExtra("id", 1)));
                Toast.makeText(getApplicationContext(), "Item Successfuly added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try{
                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                image.setImageBitmap(photo);
                btnEdit.setEnabled(true);
            }catch(IOException e){
                Log.e("CHOOSEIMG",  e.toString());
            }
        }
    }
}