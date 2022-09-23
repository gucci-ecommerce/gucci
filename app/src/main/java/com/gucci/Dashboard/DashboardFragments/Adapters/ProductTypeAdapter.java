package com.gucci.Dashboard.DashboardFragments.Adapters;

import static com.gucci.Dashboard.DashboardFragments.HomeFragment.productsAdapter;
import static com.gucci.Dashboard.DashboardFragments.HomeFragment.productsViewModelArrayList;
import static com.gucci.Dashboard.DashboardFragments.HomeFragment.rvProducts;
import static com.gucci.Dashboard.DashboardFragments.HomeFragment.temp_productsViewModelArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.gucci.Dashboard.DashboardFragments.HomeFragment;
import com.gucci.Dashboard.DashboardFragments.Models.ProductTypeViewModel;
import com.gucci.Dashboard.DashboardFragments.Models.ProductsViewModel;
import com.gucci.R;

import java.util.ArrayList;

public class ProductTypeAdapter extends RecyclerView.Adapter<ProductTypeAdapter.ViewHolder> {
    Context context;
    HomeFragment homeFragment;
    ArrayList<ProductTypeViewModel> homeFragmentViewModelArrayList=new ArrayList<ProductTypeViewModel>();

    public ProductTypeAdapter(Context context, HomeFragment homeFragment, ArrayList<ProductTypeViewModel> homeFragmentViewModelArrayList) {
        this.context = context;
        this.homeFragment = homeFragment;
        this.homeFragmentViewModelArrayList = homeFragmentViewModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_types_rv_layout, parent, false);
        return new ProductTypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductTypeViewModel productTypeViewModel = homeFragmentViewModelArrayList.get(position);
        holder.tv_itemName.setText(productTypeViewModel.getType_name());
        int paddingDp_1 = 20;
        int paddingDp_2 = 10;
        float density = context.getResources().getDisplayMetrics().density;
        int paddingPixel_1 = (int)(paddingDp_1 * density);
        int paddingPixel_2 = (int)(paddingDp_2 * density);
        if (position==0){
            holder.ll_main.setPadding(paddingPixel_1, 0, paddingPixel_2, 0);
        }
        else if (position == homeFragmentViewModelArrayList.size() - 1){
            holder.ll_main.setPadding(0, 0, paddingPixel_1, 0);
        }

        if (productTypeViewModel.getSelected()){
            holder.ll_main_layout.setBackgroundResource(R.drawable.circular_rectangle_design_black);
            holder.tv_itemName.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
        else{
            holder.ll_main_layout.setBackgroundResource(R.drawable.circular_rectangle_design_white);
            holder.tv_itemName.setTextColor(ContextCompat.getColor(context, R.color.unselect));
        }

        holder.ll_main_layout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if (productTypeViewModel.getSelected()){
                    productTypeViewModel.setSelected(false);
                }
                else{
                    productTypeViewModel.setSelected(true);
                    for (int i=0; i<homeFragmentViewModelArrayList.size(); i++){
                        if (i!=position){
                            homeFragmentViewModelArrayList.set(i, new ProductTypeViewModel(homeFragmentViewModelArrayList.get(i).getType_name(), false));
                        }
                    }
                }
                updateList(homeFragmentViewModelArrayList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeFragmentViewModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_itemName;
        LinearLayout ll_main_layout;
        LinearLayoutCompat ll_main;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_itemName = itemView.findViewById(R.id.tv_rv_type_itemName);
            ll_main_layout = itemView.findViewById(R.id.ll_type);
            ll_main = itemView.findViewById(R.id.ll_main_layout);
        }
    }


    public void updateList(ArrayList<ProductTypeViewModel> list){
        ((Activity)context).runOnUiThread(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                homeFragmentViewModelArrayList = list;
                notifyDataSetChanged();
            }
        });
    }

}
