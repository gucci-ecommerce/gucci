package com.gucci.Dashboard.DashboardFragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gucci.Dashboard.DashboardFragments.Adapters.CartAdapter;
import com.gucci.Dashboard.DashboardFragments.Adapters.CategoriesAdapter;
import com.gucci.Dashboard.DashboardFragments.Models.CartViewModel;
import com.gucci.Dashboard.DashboardFragments.Models.CategoriesViewModel;
import com.gucci.R;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {
    RecyclerView rv_categories;
    CategoriesAdapter categoriesAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_categories, container, false);
        rv_categories = view.findViewById(R.id.rv_categories);
        initCartRv();
        return view;
    }

    public void initCartRv(){
        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Loading data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        ArrayList<CategoriesViewModel> categoriesViewModelArrayList = new ArrayList<>();
        categoriesViewModelArrayList.add(new CategoriesViewModel("BIG FASHION FESTIVAL 2022", "Coming Soon", R.drawable.bg_categories_1,R.drawable.img_dress_1));
        categoriesViewModelArrayList.add(new CategoriesViewModel("AUTUMN WINTER 2022", "New Styles Getting Added Daily", R.drawable.bg_categories_2, R.drawable.img_dress_2));
        categoriesViewModelArrayList.add(new CategoriesViewModel("WOMEN", "Kurtas & Suits, Tops & teens, Dress", R.drawable.bg_categories_3,R.drawable.img_dress_3));
        categoriesViewModelArrayList.add(new CategoriesViewModel("MEN", "T-Shirts, Shirts, Jeans, Shoes", R.drawable.bg_categories_4,R.drawable.img_dress_4));
        categoriesViewModelArrayList.add(new CategoriesViewModel("KIDS", "Brands, Clothing, Footwear, Accessories", R.drawable.bg_categories_5,R.drawable.img_dress_5));
        categoriesViewModelArrayList.add(new CategoriesViewModel("BEAUTY & GROOMING", "Skincare, Makeup, Grooming & ", R.drawable.bg_categories_6,R.drawable.img_dress_6));
        categoriesViewModelArrayList.add(new CategoriesViewModel("HOME & LIVING", "Bedsheets, Blankets, Towels", R.drawable.bg_categories_1,R.drawable.img_dress_7));
        categoriesViewModelArrayList.add(new CategoriesViewModel("ACCESSORIES", "One Shop Stop to Amp Up", R.drawable.bg_categories_2,R.drawable.img_dress_8));

        categoriesAdapter = new CategoriesAdapter(getContext(), CategoriesFragment.this, categoriesViewModelArrayList);
        rv_categories.setNestedScrollingEnabled(false);
        rv_categories.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_categories.setAdapter(categoriesAdapter);
        progress.dismiss();
    }
}