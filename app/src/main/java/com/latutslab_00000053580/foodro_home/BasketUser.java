package com.latutslab_00000053580.foodro_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.latutslab_00000053580.foodro.Cart;
import com.latutslab_00000053580.recycler.CartAdapter;
import com.latutslab_00000053580.sqlite.DbCart;

import java.util.ArrayList;

public class BasketUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_user);

        RecyclerView cartRV = (RecyclerView) findViewById(R.id.cartRV);
        TextView txtNoCart  = (TextView) findViewById(R.id.txtNoCart);
        TextView txtTotal = (TextView) findViewById(R.id.txtTotal);
        LinearLayout linearBasket = (LinearLayout) findViewById(R.id.linearBasket);
        Button btnCO = (Button) findViewById(R.id.btnCO);

        DbCart dbCart = new DbCart(getApplicationContext());

        dbCart.open();
        ArrayList<Cart> cartArrayList = dbCart.getCartList();

        if(cartArrayList == null){
            cartRV.setVisibility(View.GONE);
            linearBasket.setVisibility(View.GONE);
            btnCO.setVisibility(View.GONE);
            txtNoCart.setVisibility(View.VISIBLE);
            return;
        }

        txtNoCart.setVisibility(View.GONE);
        cartRV.setVisibility(View.VISIBLE);

        CartAdapter cartAdapter = new CartAdapter(cartArrayList, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        cartRV.setLayoutManager(linearLayoutManager);
        cartRV.setAdapter(cartAdapter);

        txtTotal.setText(String.valueOf(dbCart.getTotal()));

        btnCO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UploadPaymentUser.class);
                intent.putExtra("total", txtTotal.getText());
                startActivity(intent);
            }
        });
    }
}