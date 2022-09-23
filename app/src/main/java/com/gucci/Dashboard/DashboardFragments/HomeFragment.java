package com.gucci.Dashboard.DashboardFragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gucci.Common.Utils;
import com.gucci.Dashboard.DashboardFragments.Adapters.ProductTypeAdapter;
import com.gucci.Dashboard.DashboardFragments.Adapters.ProductsAdapter;
import com.gucci.Dashboard.DashboardFragments.Models.ProductTypeViewModel;
import com.gucci.Dashboard.DashboardFragments.Models.ProductsViewModel;
import com.gucci.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment {
    public static RecyclerView rvType, rvProducts;
    ProductTypeAdapter productTypeAdapter;
    public static ProductsAdapter productsAdapter;
    public static ArrayList<ProductsViewModel> temp_productsViewModelArrayList = new ArrayList<>();
    public static ArrayList<ProductsViewModel> productsViewModelArrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initWidgets(view);
//        initTypeRV();
        loadDataFromDB();
//        initProductsRV();
        return view;
    }

    public void initWidgets(View view){
        rvType = view.findViewById(R.id.rv_types);
        rvProducts = view.findViewById(R.id.rv_products);
    }

    public void loadDataFromDB(){
        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Loading data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        DatabaseReference dbref = Utils.dbRef.child("Products");
        DatabaseReference categoriesRef = dbref.child("categories");
        DatabaseReference dataRef = dbref.child("data");

        initTypeRV(categoriesRef);
        initProductsRV(dataRef);
        progress.dismiss();
    }

    public void initTypeRV(DatabaseReference categoriesRef){
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ProductTypeViewModel> types_list = new ArrayList<>();
                types_list.add(new ProductTypeViewModel("All", true));
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        String categories = dataSnapshot.getValue().toString();
                        types_list.add(new ProductTypeViewModel(categories, false));
                    }
                    productTypeAdapter = new ProductTypeAdapter(getContext(), HomeFragment.this, types_list);
                    rvType.setNestedScrollingEnabled(false);
                    rvType.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    rvType.setAdapter(productTypeAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void initProductsRV(DatabaseReference dataRef){
        String phone = Utils.getprefs(getContext(), "phone", "");
        DatabaseReference favDbRef = Utils.dbRef.child("Users").child(phone).child("Favourites");
//        favDbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    List<String> productIdList = new ArrayList<>();
//                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                        String productId = dataSnapshot.getKey();
//                        productIdList.add(productId);
//                    }
//                    Log.e("check__", String.valueOf(productIdList));
//                    Utils.editprefsarr(getContext(), productIdList, "fav_id");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productsViewModelArrayList.clear();
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        Map product_dict = (Map)dataSnapshot.getValue();
                        String category = product_dict.get("category").toString();
                        String description = product_dict.get("description").toString();
                        String id = product_dict.get("id").toString();
                        String imageUrl = product_dict.get("imageUrl").toString();
                        String newPrice = product_dict.get("newPrice").toString();
                        String oldPrice = product_dict.get("oldPrice").toString();
                        String productName = product_dict.get("productName").toString();
                        String tag = product_dict.get("tag").toString();
                        List<String> fav_id = (ArrayList<String>) Utils.getprefsarr("fav_id");
                        boolean favourite = false;
//                        if (fav_id.contains(id)){
//                            favourite = true;
//                        }
//                        else{
//                            favourite = false;
//                        }
                        productsViewModelArrayList.add(new ProductsViewModel(id, productName, tag, imageUrl, newPrice,  oldPrice, description, category, favourite));
                        temp_productsViewModelArrayList = productsViewModelArrayList;
                        productsAdapter = new ProductsAdapter(getContext(),  productsViewModelArrayList);
                        rvProducts.setNestedScrollingEnabled(false);
                        rvProducts.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        rvProducts.setAdapter(productsAdapter);
                    }
                    List<String> temp_arr = new ArrayList<>();
                    temp_arr.add("dummy");
                    Utils.editprefsarr(getContext(), temp_arr, "fav_id");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}