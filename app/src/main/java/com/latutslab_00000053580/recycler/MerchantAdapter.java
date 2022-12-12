package com.latutslab_00000053580.recycler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.latutslab_00000053580.foodro.User;
import com.latutslab_00000053580.foodro_home.R;
import com.latutslab_00000053580.foodro_home.MenuUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MerchantAdapter extends RecyclerView.Adapter<MerchantAdapter.ViewHolder>{

    private final ArrayList<User> userArrList;
//    private static ClickListener clickListener;
    private final Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView merchantName;
        private final ImageView merchantImg;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            merchantName = (TextView) view.findViewById(R.id.merchantName);
            merchantImg = (ImageView) view.findViewById(R.id.merchantImage);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
//            clickListener.onItemClick(getAbsoluteAdapterPosition(), v);

            int position = getAbsoluteAdapterPosition();

            Log.i("TESTON", "position: " + position);
            User user = userArrList.get(position);
            Intent intent = new Intent(context, MenuUser.class);
            intent.putExtra("id", user.getId());
            intent.putExtra("name", user.getFullName());
            intent.putExtra("image", user.getImage());
            context.startActivity(intent);
        }
    }

//    public void setOnItemClickListener(ClickListener clickListener){
//        MerchantAdapter.clickListener = clickListener;
//    }
//
//    public interface ClickListener {
//        void onItemClick(int position, View v);
//    }

    public MerchantAdapter(ArrayList<User> userArrList, Context context) {
        this.userArrList = userArrList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout_merchant, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        User model = userArrList.get(position);
        viewHolder.merchantName.setText(model.getFullName());

//        String path = "data:image/png;base64,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";

        Picasso.get().load(model.getImage()).into(viewHolder.merchantImg);
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Recycle Click" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return userArrList.size();
    }
}


