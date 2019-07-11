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
import com.customer.main.model.response.OrderDetailsResponse.OrderDetail;

import java.util.List;

/**
 *
 * @author Karan
 * @date 19/4/19
 */
public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {

    private Context context;
    private ConstraintLayout clOrder;
    private List<OrderDetail> orderDetails;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNumberOfPlants, tvPlantName, tvAmount;

        public MyViewHolder(View view) {
            super(view);
            tvNumberOfPlants = view.findViewById(R.id.tvNumberOfPlants);
            tvPlantName = view.findViewById(R.id.tvPlantName);
            tvAmount = view.findViewById(R.id.tvAmount);
            clOrder = view.findViewById(R.id.clOrder);

        }
    }


    public OrderDetailsAdapter(Context context, List<OrderDetail> orderDetails, String totalamount) {
        this.context = context;
        this.orderDetails = orderDetails;
    }

    @Override
    public OrderDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_details_list_row, parent, false);

        return new OrderDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderDetailsAdapter.MyViewHolder holder, int position) {
        holder.tvNumberOfPlants.setText(orderDetails.get(position).getOqty());
        holder.tvPlantName.setText(orderDetails.get(position).getPname());
        holder.tvAmount.setText(" ₹ " + orderDetails.get(position).getPprice());
        clOrder.setOnClickListener(view -> context.startActivity(new Intent(context, OrderManagementActivity.class).putExtra("orderid", orderDetails.get(position).getOid())));
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }
}