package com.latutslab_00000053580.recycler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.latutslab_00000053580.foodro.APIHandler;
import com.latutslab_00000053580.foodro.Order;
import com.latutslab_00000053580.foodro_home.R;
import com.latutslab_00000053580.foodro_user.DetailTransactionUser;

import java.io.Serializable;
import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements Serializable {

    private final ArrayList<Order> orderArrayList;
    private final Context context;
    APIHandler handler = new APIHandler();
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderBuyer;
        private final TextView orderItem;
        private final TextView orderID;
        private final TextView orderTotal;
        private final Button btnReady;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            orderBuyer = (TextView) view.findViewById(R.id.historyNama);
            orderItem = (TextView) view.findViewById(R.id.historyItem);
            orderID = (TextView) view.findViewById(R.id.historyId);
            orderTotal = (TextView) view.findViewById(R.id.historyTotal);
            btnReady = (Button) view.findViewById(R.id.btnEdit);
        }
    }

    public HistoryAdapter(Context context,ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout_history_order, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Order model = orderArrayList.get(viewHolder.getBindingAdapterPosition());
        viewHolder.orderBuyer.setText(model.getCustomer().getFullName());
        viewHolder.orderItem.setText(model.getOrderDetailStr());
        viewHolder.orderTotal.setText("Total: Rp." + model.getOrderDetailTotal());
        viewHolder.orderID.setText(Integer.toString(model.getId()));

        viewHolder.btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailTransactionUser.class);
                intent.putExtra("order", model);
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }
}


