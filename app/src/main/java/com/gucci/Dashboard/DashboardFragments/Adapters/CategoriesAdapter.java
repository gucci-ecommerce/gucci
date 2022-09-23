package com.gucci.Dashboard.DashboardFragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gucci.Dashboard.DashboardFragments.CategoriesFragment;
import com.gucci.Dashboard.DashboardFragments.Models.CategoriesViewModel;
import com.gucci.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    Context context;
    CategoriesFragment categoriesFragment;
    ArrayList<CategoriesViewModel> categoriesViewModelArrayList = new ArrayList<>();

    public CategoriesAdapter(Context context, CategoriesFragment categoriesFragment, ArrayList<CategoriesViewModel> categoriesViewModelArrayList) {
        this.context = context;
        this.categoriesFragment = categoriesFragment;
        this.categoriesViewModelArrayList = categoriesViewModelArrayList;
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catgories_rv_layout, parent, false);
        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
        CategoriesViewModel categoriesViewModel = categoriesViewModelArrayList.get(position);
        holder.tv_title.setText(categoriesViewModel.getTitle());
        holder.tv_sub_title.setText(categoriesViewModel.getSub_title());
        holder.ll_main_layout.setBackgroundResource(categoriesViewModel.getBg());
        holder.iv_image.setBackgroundResource(categoriesViewModel.getImage());
    }

    @Override
    public int getItemCount() {
        return categoriesViewModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayoutCompat ll_main_layout;
        TextView tv_title, tv_sub_title;
        ImageView iv_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_main_layout = itemView.findViewById(R.id.ll_main_layout);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_sub_title = itemView.findViewById(R.id.tv_sub_title);
            iv_image = itemView.findViewById(R.id.iv_image);
        }
    }
}
