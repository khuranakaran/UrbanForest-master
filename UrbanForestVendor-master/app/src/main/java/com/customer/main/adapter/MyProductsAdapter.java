package com.customer.main.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.customer.R;
import com.customer.main.activities.MyProductDetailsActivity;
import com.customer.main.model.response.my_products_response.Datum;

import java.util.List;

/**
 *
 * @author Karan
 * @date 3/5/19
 */
public class MyProductsAdapter extends RecyclerView.Adapter<MyProductsAdapter.MyViewHolder> {

    private Context context;
    private String url;
    private LinearLayout llMyProduct;
    List<Datum> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMyProductName, tvMyProductPrice;
        public ImageView ivMyProductImage;

        public MyViewHolder(View view) {
            super(view);
            ivMyProductImage = view.findViewById(R.id.ivMyProductImage);
            tvMyProductName = view.findViewById(R.id.tvMyProductName);
            tvMyProductPrice = view.findViewById(R.id.tvMyProductPrice);
            llMyProduct = view.findViewById(R.id.llMyProduct);

        }
    }


    public MyProductsAdapter(Context context, List<Datum> data, String url) {
        this.context = context;
        this.data = data;
        this.url = url;
    }

    @Override
    public MyProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_product, parent, false);

        return new MyProductsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyProductsAdapter.MyViewHolder holder, int position) {
        Glide.
                with(context)
                .load(url + data.get(position).getPimage())
                .into(holder.ivMyProductImage);
        holder.tvMyProductName.setText(data.get(position).getPname());
        holder.tvMyProductPrice.setText(" ₹ " + data.get(position).getPrice());

        Log.e("KKKK", "SIZE : " + data.get(position).getSize());

        llMyProduct.setOnClickListener(view -> context.startActivity(new Intent(context, MyProductDetailsActivity.class).putExtra("data", data.get(position)).putExtra("url", url)));


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

