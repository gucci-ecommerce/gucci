package com.gucci.Dashboard.DashboardFragments;

import static com.gucci.Common.Utils.df;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gucci.Common.Utils;
import com.gucci.Dashboard.DashboardFragments.Adapters.CartAdapter;
import com.gucci.Dashboard.DashboardFragments.Adapters.ProductsAdapter;
import com.gucci.Dashboard.DashboardFragments.Models.CartViewModel;
import com.gucci.Dashboard.DashboardFragments.Models.ProductsViewModel;
import com.gucci.R;

import java.util.ArrayList;
import java.util.Map;


public class CartFragment extends Fragment {
    CartAdapter cartAdapter;
    RecyclerView rv_Cart;
    public static TextView tv_cartCount, tv_sub_total, tv_shipping, tv_total;
    public static String phone = "";
    public static LinearLayoutCompat ll_checkout;
    public static TextView tv_no_items;
    public static double val_sub_total = 0;
    public static double val_sub_shipping = 0;
    public static double val_total = 0;
    Button btn_checkout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);
        rv_Cart = view.findViewById(R.id.rv_cart_items);
        tv_cartCount = view.findViewById(R.id.tv_cartCount);
        tv_sub_total = view.findViewById(R.id.tv_sub_total);
        tv_shipping = view.findViewById(R.id.tv_shipping);
        tv_total = view.findViewById(R.id.tv_total);
        ll_checkout = view.findViewById(R.id.ll_checkout);
        tv_no_items = view.findViewById(R.id.tv_empty_cart);
        btn_checkout = view.findViewById(R.id.btn_checkout);
        initCartRv();
        onclick();
        return view;
    }

    @SuppressLint("SetTextI18n")
    public void initCartRv(){
        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Loading cart...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        String phone = Utils.getprefs(getContext(), "phone", "");
        DatabaseReference dataRef = Utils.dbRef.child("Users").child(phone).child("Carts");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<CartViewModel> cartViewModelArrayList = new ArrayList<>();
                val_sub_total = 0;
                val_sub_shipping = 0;
                val_total = 0;
                if (snapshot.exists()){
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Map product_dict = (Map)dataSnapshot.getValue();
                        String description = product_dict.get("description").toString();
                        String id = product_dict.get("id").toString();
                        String imageUrl = product_dict.get("imageUrl").toString();
                        String newPrice = product_dict.get("newPrice").toString();
                        String oldPrice = product_dict.get("oldPrice").toString();
                        String productName = product_dict.get("productName").toString();
                        String tag = product_dict.get("tag").toString();
                        String quantity = product_dict.get("quantity").toString();
                        val_sub_total += Double.parseDouble(quantity) * Double.parseDouble(newPrice);
                        val_sub_shipping = 5;
                        cartViewModelArrayList.add(new CartViewModel(id, imageUrl, productName, oldPrice, newPrice, quantity));
                    }
                    tv_cartCount.setText(cartViewModelArrayList.size() + " Items");
                    cartAdapter = new CartAdapter(getContext(), CartFragment.this, cartViewModelArrayList);
                    rv_Cart.setNestedScrollingEnabled(false);
                    rv_Cart.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_Cart.setAdapter(cartAdapter);
                }
                if (cartViewModelArrayList.size()==0){
                    val_sub_total = 0;
                    val_sub_shipping = 0;
                    val_total = 0;
                    tv_cartCount.setText("");
                    ll_checkout.setVisibility(View.GONE);
                    tv_no_items.setVisibility(View.VISIBLE);
                }
                else {
                    val_total = val_sub_total + val_sub_shipping;
                    tv_sub_total.setText("$"+df.format(val_sub_total));
                    tv_shipping.setText("$"+df.format(val_sub_shipping));
                    tv_total.setText("$"+df.format(val_total));
                    tv_cartCount.setText(cartViewModelArrayList.size() + " Items");
                    ll_checkout.setVisibility(View.VISIBLE);
                    tv_no_items.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        progress.dismiss();
    }

    public void onclick(){
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), R.string.title_under_dev, Toast.LENGTH_SHORT).show();
            }
        });
    }

}