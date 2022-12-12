package com.latutslab_00000053580.recycler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.latutslab_00000053580.foodro.Cart;
import com.latutslab_00000053580.foodro.Food;
import com.latutslab_00000053580.foodro.OrderDetail;
import com.latutslab_00000053580.foodro_home.BasketUser;
import com.latutslab_00000053580.foodro_home.R;
import com.latutslab_00000053580.sqlite.DbCart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<Cart> cartArrayList;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView basketNama;
        private final TextView basketHarga;
        private final ImageView basketImage;
        private final TextView basketTotal;
        private final TextView basketQty;
        private final Button btnMinus;
        private final Button btnPlus;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            basketNama = (TextView) view.findViewById(R.id.basketName);
            basketHarga = (TextView) view.findViewById(R.id.basketPrice);
            basketImage = (ImageView) view.findViewById(R.id.basketImage);
            basketTotal = (TextView) view.findViewById(R.id.basketTotal);
            basketQty = (TextView) view.findViewById(R.id.basketQty);
            btnMinus = (Button) view.findViewById(R.id.btnMinus);
            btnPlus = (Button) view.findViewById(R.id.btnPlus);

        }
    }

    public CartAdapter(ArrayList<Cart> cartArrayList, Context context) {
        this.cartArrayList = cartArrayList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout_menu_in_basket, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Cart model = cartArrayList.get(position);
        viewHolder.basketHarga.setText(String.valueOf(model.getPrice()));
        viewHolder.basketNama.setText(String.valueOf(model.getName()));
        viewHolder.basketQty.setText(String.valueOf(model.getQuantity()));
        viewHolder.basketTotal.setText(String.valueOf(model.getTotal()));
        Picasso.get().load("https://nos.jkt-1.neo.id/mcdonalds/assets/img/bg/img_visi.jpg").into(viewHolder.basketImage);

        DbCart dbCart = new DbCart(context);
        dbCart.open();

        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(viewHolder.basketQty.getText().toString()) > 1 ){
                    dbCart.minusCartQty(model.getItemID());

                } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Confirmation");
                builder.setMessage("remove item from cart?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dbCart.deleteCart(model.getItemID());
                        Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alert.show();
                }

                int updatedQty = dbCart.getQty(model.getItemID());

                viewHolder.basketQty.setText(String.valueOf(updatedQty));
                viewHolder.basketTotal.setText(String.valueOf(updatedQty * model.getPrice()));
            }
        });

        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbCart.addCartQty(model.getItemID());
                int updatedQty = dbCart.getQty(model.getItemID());

                viewHolder.basketQty.setText(String.valueOf(updatedQty));
                viewHolder.basketTotal.setText(String.valueOf(updatedQty * model.getPrice()));
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }
}
