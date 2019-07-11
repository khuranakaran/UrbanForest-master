package com.customer.main.adapter;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.customer.R;
import com.customer.main.model.response.customer_my_orders.MyOrdersResponse;
import com.customer.main.model.response.general_response.GeneralResponse;

/**
 * Created by Karan on 3/5/19.
 */
public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyViewHolder> {

    private Context context;
    private MyOrdersResponse ordersResponse;
    private GeneralResponse body;
    private ConstraintLayout clOrder;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOrderName, tvTotalOrder, tvOrderAmount, tvOrderPlacedOn, tvOrderDeliveryDate, tvOrderDeliveryAddress;

        public MyViewHolder(View view) {
            super(view);
            tvOrderName = view.findViewById(R.id.tvOrderName);
            tvTotalOrder = view.findViewById(R.id.tvTotalOrder);
            tvOrderAmount = view.findViewById(R.id.tvOrderAmount);
//            tvOrderPlacedOn = view.findViewById(R.id.tvOrderPlacedOn);
//            tvOrderDeliveryDate = view.findViewById(R.id.tvOrderDeliveryDate);
            tvOrderDeliveryAddress = view.findViewById(R.id.tvOrderDeliveryAddress);
            clOrder = view.findViewById(R.id.clOrder);
        }
    }


    public MyOrdersAdapter(Context context, MyOrdersResponse ordersResponse) {
        this.context = context;
        this.ordersResponse = ordersResponse;
    }

    @Override
    public MyOrdersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_row, parent, false);

        return new MyOrdersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyOrdersAdapter.MyViewHolder holder, int position) {

        if (ordersResponse.getData() != null) {
            holder.tvOrderName.setText(ordersResponse.getData().get(position).getName());
            holder.tvTotalOrder.setText(ordersResponse.getData().get(position).getOmobile());
            holder.tvOrderAmount.setText(" ₹ " + ordersResponse.getData().get(position).getTotalamount());
//            holder.tvOrderPlacedOn.setText("Order Placed on : " + ordersResponse.getData().get(position).getOdate());
//            holder.tvOrderPlacedOn.setText("Delivery date : " + ordersResponse.getData().get(position).getDate());
            holder.tvOrderDeliveryAddress.setText(ordersResponse.getData().get(position).getOaddress());
//            clOrder.setOnClickListener(view -> context.startActivity(new Intent(context, OrderDetailsActivity.class).putExtra("orderid", ordersResponse.getData().get(position).getOid())));
        }
    }

    @Override
    public int getItemCount() {
        return ordersResponse.getData().size();
    }
}
