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
import com.customer.main.activities.MenuActivity;
import com.customer.main.model.response.categories_response.Datum;

import java.util.List;

/**
 * Created by Karan on 27/4/19.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    private Context context;
    private String url;
    private LinearLayout llCategory;
    List<Datum> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCategoryName;
        public ImageView ivCategoryImage;

        public MyViewHolder(View view) {
            super(view);
            ivCategoryImage = view.findViewById(R.id.ivCategoryImage);
            tvCategoryName = view.findViewById(R.id.tvCategoryName);
            llCategory = view.findViewById(R.id.llCategory);

        }
    }


    public CategoriesAdapter(Context context, List<Datum> data, String url) {
        this.context = context;
        this.data = data;
        this.url = url;
    }

    @Override
    public CategoriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new CategoriesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoriesAdapter.MyViewHolder holder, int position) {

        String imageUrl =  url + data.get(position).getCatimage();

        Log.e("KKK", imageUrl);

        Glide.
                with(context)
                .load(imageUrl)
                .into(holder.ivCategoryImage);
        holder.tvCategoryName.setText(data.get(position).getCatname());

        llCategory.setOnClickListener(view -> context.startActivity(new Intent(context, MenuActivity.class).putExtra("category_id", data.get(position).getCid()).putExtra("category_name", data.get(position).getCatname())));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
