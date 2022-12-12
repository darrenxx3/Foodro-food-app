package com.latutslab_00000053580.foodro_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.latutslab_00000053580.foodro.Order;
import com.latutslab_00000053580.foodro_home.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailTransactionUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction_user);

        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra("order");

        ImageView detailImage = (ImageView) findViewById(R.id.detailImage);
        Picasso.get().load(order.getCustomer().getImage()).fit().centerCrop().into(detailImage);

        TextView detailMerchant = (TextView) findViewById(R.id.detailMerchant);
        TextView detailStatus = (TextView) findViewById(R.id.detailStatus);
        TextView detailQty = (TextView) findViewById(R.id.detailQty);
        TextView detailName = (TextView) findViewById(R.id.detailName);
        TextView detailPrice = (TextView) findViewById(R.id.detailPrice);
        TextView detailTotal = (TextView) findViewById(R.id.detailTotal);
        TextView detailDate = findViewById(R.id.txtDate);

        detailMerchant.setText(order.getCustomer().getFullName());
        detailStatus.setText(order.getStatusString());
        detailQty.setText(order.getOrderQty());
        detailName.setText(order.getOrderName());
        detailPrice.setText(order.getOrderPrice());
        detailTotal.setText(String.valueOf(order.getOrderDetailTotal()));
        detailDate.setText(order.getDateTime());
    }
}