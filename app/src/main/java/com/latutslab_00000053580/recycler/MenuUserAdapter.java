package com.latutslab_00000053580.recycler;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.latutslab_00000053580.foodro.Cart;
import com.latutslab_00000053580.foodro.Food;
import com.latutslab_00000053580.foodro_home.R;
import com.latutslab_00000053580.sqlite.DbCart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuUserAdapter extends RecyclerView.Adapter<MenuUserAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<Food> foodArrayList;
    private final int merchantID;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView menuAddHarga;
        private final TextView menuAddNama;
        private final ImageView menuAddImg;
        private final Button btnAdd;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            menuAddNama = (TextView) view.findViewById(R.id.menuAddName);
            menuAddHarga = (TextView) view.findViewById(R.id.menuAddPrice);
            menuAddImg = (ImageView) view.findViewById(R.id.menuAddImg);
            btnAdd = view.findViewById(R.id.btnAdd);
        }
    }

    public MenuUserAdapter(Context context, int merchantID, ArrayList<Food> foodArrayList) {
        this.context = context;
        this.merchantID = merchantID;
        this.foodArrayList = foodArrayList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MenuUserAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout_menu_add  , viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Food model = foodArrayList.get(position);
        viewHolder.menuAddNama.setText(model.getName());
        viewHolder.menuAddHarga.setText(Integer.toString(model.getPrice()));
        viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Update pilihan ke db
                Food food = foodArrayList.get(position);
                Cart cart = new Cart(food.getId(), 1, food.getName(), food.getPrice(), food.getImage(), merchantID);

                DbCart dbCart = new DbCart(context);
                dbCart.open();
                dbCart.addCart(cart);
            }
        });
        Picasso.get().load(model.getImage()).fit().centerCrop().into(viewHolder.menuAddImg);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }
}
