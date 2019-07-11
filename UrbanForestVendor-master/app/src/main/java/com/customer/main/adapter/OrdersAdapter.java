package com.customer.main.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.customer.R;
import com.customer.main.activities.OrderManagementActivity;
import com.customer.main.model.response.VendorOrdersResponse.VendorOrdersResponse;
import com.customer.main.model.response.general_response.GeneralResponse;

/**
 *
 * @author Karan
 * @date 19/4/19
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {

    private Context context;
    private VendorOrdersResponse ordersResponse;
    private GeneralResponse body;
    private ConstraintLayout clOrder;

    public OrdersAdapter(Context context, GeneralResponse body) {
        this.context = context;
        this.body = body;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOrderName, tvTotalOrder, tvOrderAmount;

        public MyViewHolder(View view) {
            super(view);
            tvOrderName = view.findViewById(R.id.tvOrderName);
            tvTotalOrder = view.findViewById(R.id.tvTotalOrder);
            tvOrderAmount = view.findViewById(R.id.tvOrderAmount);
            clOrder = view.findViewById(R.id.clOrder);
        }
    }


    public OrdersAdapter(Context context, VendorOrdersResponse ordersResponse) {
        this.context = context;
        this.ordersResponse = ordersResponse;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (ordersResponse.getData() != null){
            holder.tvOrderName.setText(ordersResponse.getData().get(position).getName());
            holder.tvTotalOrder.setText(ordersResponse.getData().get(position).getOmobile());
            holder.tvOrderAmount.setText(" ₹ " + ordersResponse.getData().get(position).getTotalamount());
            clOrder.setOnClickListener(view -> context.startActivity(new Intent(context, OrderManagementActivity.class).putExtra("orderid", ordersResponse.getData().get(position).getOid())));
        } else if (body != null) {
//            holder.tvOrderName.setText(body.getData().get(position).getName());
//            holder.tvTotalOrder.setText(body.getData().get(position).getOmobile());
//            holder.tvOrderAmount.setText(" ₹ " + body.getData().get(position).getTotalamount());
//            clOrder.setOnClickListener(view -> context.startActivity(new Intent(context, OrderManagementActivity.class).putExtra("orderid", body.getData().get(position).getOid())));
        }
    }

    @Override
    public int getItemCount() {
        if (ordersResponse != null){
            return ordersResponse.getData().size();
        } else if (body != null) {
            return ordersResponse.getData().size();
        }

        return 0;
    }
}