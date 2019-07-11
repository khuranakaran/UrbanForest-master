package com.customer.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.customer.R;
import com.customer.main.activities.ProductDetailsActivity;
import com.customer.main.model.request.get_all_products.Datum;

import java.util.List;

/**
 * Created by Karan on 21/6/19.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private Context context;
    private String url;
    private String categoryName;
    private LinearLayout llCategory;
    List<Datum> data;

    public MenuAdapter(Context context, List<Datum> data, String url, String categoryName) {
        this.context = context;
        this.data = data;
        this.url = url;
        this.categoryName = categoryName;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductName, tvProductPrice, tvProductDesc;
        public ImageView ivProductImage;

        public MyViewHolder(View view) {
            super(view);
            ivProductImage = view.findViewById(R.id.ivProductImage);
            tvProductName = view.findViewById(R.id.tvProductName);
            tvProductPrice = view.findViewById(R.id.tvProductPrice);

            llCategory = view.findViewById(R.id.llCategory);

        }
    }



    @Override
    public MenuAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);

        return new MenuAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MenuAdapter.MyViewHolder holder, int position) {

        Glide.
                with(context)
                .load(url + data.get(position).getPimage())
                .into(holder.ivProductImage);
        holder.tvProductName.setText(data.get(position).getPname());
        holder.tvProductPrice.setText("₹ " + data.get(position).getPrice());


        llCategory.setOnClickListener(view -> context.startActivity(new Intent(context, ProductDetailsActivity.class).putExtra("category_id", data.get(position).getPid()).putExtra("category_name", categoryName)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
