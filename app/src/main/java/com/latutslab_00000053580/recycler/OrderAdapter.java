package com.latutslab_00000053580.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.latutslab_00000053580.foodro.APIHandler;
import com.latutslab_00000053580.foodro.Order;
import com.latutslab_00000053580.foodro_home.R;

import java.util.ArrayList;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final ArrayList<Order> orderArrayList;
    private final Context context;
    APIHandler handler = new APIHandler();
    private final int role;

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
        private final TextView txtUserStatus;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            orderBuyer = (TextView) view.findViewById(R.id.orderBuyer);
            orderItem = (TextView) view.findViewById(R.id.orderItem);
            orderID = (TextView) view.findViewById(R.id.historyId);
            orderTotal = (TextView) view.findViewById(R.id.orderTotal);
            btnReady = (Button) view.findViewById(R.id.btnDelete);
            txtUserStatus = (TextView) view.findViewById(R.id.txtUserStatus);

        }
    }

    public OrderAdapter(Context context, ArrayList<Order> orderArrayList, int role) {
        this.context = context;
        this.orderArrayList = orderArrayList;
        this.role = role;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout_incoming_order, viewGroup, false);

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

        //todo rubah nama button sesuai order status
        Log.i("ISUSER", Integer.toString(role));
        if(role == 1){
            Log.i("ISUSER", Integer.toString(role));
            viewHolder.btnReady.setVisibility(View.GONE);
            viewHolder.txtUserStatus.setVisibility(View.VISIBLE);
            viewHolder.txtUserStatus.setText(model.getStatusString());
        }else{
            viewHolder.txtUserStatus.setVisibility(View.GONE );
            viewHolder.btnReady.setText(model.getStatusStringMerchant());
        }


        viewHolder.btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.updateStatus(context, model.getId(), model.getOrderDetails().get(0).getFood().getId(), model.getStatus()+1);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }
}


