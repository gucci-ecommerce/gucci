package com.gucci.Dashboard.DashboardFragments.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.gucci.Common.Utils;
import com.gucci.Dashboard.DashboardFragments.HomeFragment;
import com.gucci.Dashboard.DashboardFragments.Models.ProductTypeViewModel;
import com.gucci.Dashboard.DashboardFragments.Models.ProductsViewModel;
import com.gucci.Dashboard.DashboardFragments.ProductDetailsActivity;
import com.gucci.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    Context context;
    HomeFragment homeFragment;
    ArrayList<ProductsViewModel> productsViewModelArrayList = new ArrayList<>();

    public ProductsAdapter(Context context, ArrayList<ProductsViewModel> productsViewModelArrayList) {
        this.context = context;
        this.productsViewModelArrayList = productsViewModelArrayList;
    }



    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_rv_layout, parent, false);
        return new ProductsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {
        ProductsViewModel productsViewModel = productsViewModelArrayList.get(position);
//        holder.iv_productImage.setBackgroundResource(productsViewModel.getProductImage());
        Picasso.get()
                .load(productsViewModel.getProductImage())
                .resize(1024, 1024)
                .centerCrop()
                .into(holder.iv_productImage);
        holder.tv_new_price.setText("$"+productsViewModel.getNewPrice());
        holder.tv_original_price.setText("$"+productsViewModel.getOriginalPrice());
        holder.tv_productName.setText(productsViewModel.getProductTitle());
        holder.tv_original_price.setPaintFlags(holder.tv_original_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if (productsViewModel.getProductFavourite()){
            holder.iv_productFav.setBackgroundResource(R.drawable.ic_favourite);
        }
        holder.ll_main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productDetailsIntent = new Intent(context, ProductDetailsActivity.class);
                productDetailsIntent.putExtra("productId", productsViewModel.getId());
                productDetailsIntent.putExtra("productImage", productsViewModel.getProductImage());
                productDetailsIntent.putExtra("productTitle", productsViewModel.getProductTitle());
                productDetailsIntent.putExtra("productTag", productsViewModel.getTag());
                productDetailsIntent.putExtra("productPrice", productsViewModel.getNewPrice());
                productDetailsIntent.putExtra("productOldPrice", productsViewModel.getOriginalPrice());
                productDetailsIntent.putExtra("productDesc", productsViewModel.getDescription());
                context.startActivity(productDetailsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsViewModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_productImage, iv_productFav;
        TextView tv_original_price, tv_new_price, tv_productName;
        LinearLayout ll_main_layout;
        RelativeLayout rl_fav;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_productImage = itemView.findViewById(R.id.iv_productImage);
            iv_productFav = itemView.findViewById(R.id.iv_productFav);
            tv_original_price = itemView.findViewById(R.id.tv_original_price);
            tv_new_price = itemView.findViewById(R.id.tv_new_price);
            tv_productName = itemView.findViewById(R.id.tv_productName);
            ll_main_layout = itemView.findViewById(R.id.ll_main_layout);
            rl_fav = itemView.findViewById(R.id.rl_fav);
        }
    }
}
