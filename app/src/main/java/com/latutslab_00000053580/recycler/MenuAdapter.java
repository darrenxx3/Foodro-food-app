package com.latutslab_00000053580.recycler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.latutslab_00000053580.foodro.APIHandler;
import com.latutslab_00000053580.foodro.Food;
import com.latutslab_00000053580.foodro_home.MerchantEditMenu;
import com.latutslab_00000053580.foodro_home.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private final ArrayList<Food> foodArrayList;
    private final Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView menuNama;
        private final TextView menuHarga;
        private final ImageView menuImage;
        private final Button btnEdit;
        private final Button btnDelete;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            menuNama = (TextView) view.findViewById(R.id.menuNama);
            menuHarga = (TextView) view.findViewById(R.id.menuHarga);
            menuImage = (ImageView) view.findViewById(R.id.menuImage);
            btnEdit = (Button) view.findViewById(R.id.btnEdit);
            btnDelete = (Button) view.findViewById(R.id.btnDelete);
        }
    }

    public MenuAdapter(Context context, ArrayList<Food> foodArrayList) {
        this.context = context;
        this.foodArrayList = foodArrayList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout_menu_merchant, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Food model = foodArrayList.get(position);
        Log.i("SQLite", model.getName());
        viewHolder.menuNama.setText(String.valueOf(model.getName()));
        viewHolder.menuHarga.setText(String.valueOf(model.getPrice()));

        Picasso.get().load(model.getImage()).into(viewHolder.menuImage);

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, MerchantEditMenu.class);
                intent.putExtra("image", model.getImage());
                intent.putExtra("name", model.getName());
                intent.putExtra("price", model.getPrice());
                intent.putExtra("id", model.getId());

                context.startActivity(intent);
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                APIHandler handler = new APIHandler();
                handler.deleteFood(context, model.getId());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }
}


