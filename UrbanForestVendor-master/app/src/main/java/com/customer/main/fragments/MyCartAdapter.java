package com.customer.main.fragments;

import android.content.Context;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.customer.R;
import com.customer.main.model.response.general_response.GeneralResponse;
import com.customer.main.model.response.my_cart_response.MyCartResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karan on 22/6/19.
 */
class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyViewHolder> {

    private Context context;
    private MyCartResponse ordersResponse;
    private GeneralResponse body;
//    private ConstraintLayout clOrder;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOrderName, tvOrderAmount, tvOrderPlacedOn, tvOrderDeliveryDate, tvOrderDeliveryAddress;
        public ImageView ivProductImage;
        private AppCompatSpinner sizeSpinner, quantitySpinner;

        public MyViewHolder(View view) {
            super(view);
            ivProductImage = view.findViewById(R.id.ivProductImage);
            tvOrderName = view.findViewById(R.id.tvOrderName);
            tvOrderAmount = view.findViewById(R.id.tvOrderAmount);
//            tvOrderPlacedOn = view.findViewById(R.id.tvOrderPlacedOn);
//            tvOrderDeliveryDate = view.findViewById(R.id.tvOrderDeliveryDate);
            tvOrderDeliveryAddress = view.findViewById(R.id.tvOrderDeliveryAddress);
            sizeSpinner = view.findViewById(R.id.sizeSpinner);
            quantitySpinner = view.findViewById(R.id.quantitySpinner);
//            clOrder = view.findViewById(R.id.clOrder);
        }
    }


    public MyCartAdapter(Context context, MyCartResponse ordersResponse) {
        this.context = context;
        this.ordersResponse = ordersResponse;
    }

    @Override
    public MyCartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_row, parent, false);

        return new MyCartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyCartAdapter.MyViewHolder holder, int position) {

        if (ordersResponse.getData() != null) {
            Glide.with(context).load("http://ved201-001-site1.ftempurl.com/images/" + ordersResponse.getData().get(position).getPimage()).into(holder.ivProductImage);
            holder.tvOrderName.setText(ordersResponse.getData().get(position).getPname());
            holder.tvOrderAmount.setText(" ₹ " + ordersResponse.getData().get(position).getPrice());

            holder.sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//                    String companyId = companies_list.get(i).getCompanyId();
//                    String companyName = companies_list.get(i).getCompanyName();
//                    Toast.makeText(context, "Company Name: " + companyName, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
//            holder.tvOrderAmount.setText(" ₹ " + ordersResponse.getData().get(position).getTotalamount());
//            holder.tvOrderPlacedOn.setText("Order Placed on : " + ordersResponse.getData().get(position).getOdate());
//            holder.tvOrderPlacedOn.setText("Delivery date : " + ordersResponse.getData().get(position).getDate());
//            holder.tvOrderDeliveryAddress.setText(ordersResponse.getData().get(position).getOaddress());
          }
    }

    @Override
    public int getItemCount() {
        return ordersResponse.getData().size();
    }
}
