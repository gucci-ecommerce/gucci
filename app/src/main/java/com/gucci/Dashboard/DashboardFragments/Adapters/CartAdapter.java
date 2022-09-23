package com.gucci.Dashboard.DashboardFragments.Adapters;

import static com.gucci.Common.Utils.df;
import static com.gucci.Dashboard.DashboardFragments.CartFragment.ll_checkout;
import static com.gucci.Dashboard.DashboardFragments.CartFragment.phone;
import static com.gucci.Dashboard.DashboardFragments.CartFragment.tv_cartCount;
import static com.gucci.Dashboard.DashboardFragments.CartFragment.tv_no_items;
import static com.gucci.Dashboard.DashboardFragments.CartFragment.tv_shipping;
import static com.gucci.Dashboard.DashboardFragments.CartFragment.tv_sub_total;
import static com.gucci.Dashboard.DashboardFragments.CartFragment.tv_total;
import static com.gucci.Dashboard.DashboardFragments.CartFragment.val_sub_shipping;
import static com.gucci.Dashboard.DashboardFragments.CartFragment.val_sub_total;
import static com.gucci.Dashboard.DashboardFragments.CartFragment.val_total;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gucci.Common.Utils;
import com.gucci.Dashboard.DashboardFragments.CartFragment;
import com.gucci.Dashboard.DashboardFragments.Models.CartViewModel;
import com.gucci.Dashboard.DashboardFragments.Models.ProductTypeViewModel;
import com.gucci.Dashboard.DashboardFragments.Models.ProductsViewModel;
import com.gucci.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    CartFragment cartFragment;
    ArrayList<CartViewModel> cartViewModelArrayList = new ArrayList<>();

    public CartAdapter(Context context, CartFragment cartFragment, ArrayList<CartViewModel> cartViewModelArrayList) {
        this.context = context;
        this.cartFragment = cartFragment;
        this.cartViewModelArrayList = cartViewModelArrayList;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carts_rv_layout, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartViewModel cartViewModel = cartViewModelArrayList.get(position);
        Picasso.get()
                .load(cartViewModel.getProductImage())
                .resize(1024, 1024)
                .centerCrop()
                .into(holder.iv_cartImage);
        holder.tv_cartTitle.setText(cartViewModel.getProductTitle());
        holder.tv_cartNewPrice.setText("$"+cartViewModel.getProductNewPrice());
        holder.tv_cartOriginalPrice.setText("$"+cartViewModel.getProductOriginalPrice());
        holder.tv_cartOriginalPrice.setPaintFlags(holder.tv_cartOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_quantity.setText(cartViewModel.getQuantity());



        String phone = Utils.getprefs(context, "phone", "");
        holder.rl_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progress = new ProgressDialog(context);
                progress.setMessage("Adding quntity...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();
                int qty = Integer.parseInt(cartViewModel.getQuantity());
                double price = Double.parseDouble(cartViewModel.getProductNewPrice());
                qty += 1;
                val_sub_total += price;
                val_total = val_sub_total + val_sub_shipping;
                tv_sub_total.setText("$"+df.format(val_sub_total));
                tv_shipping.setText("$"+df.format(val_sub_shipping));
                tv_total.setText("$"+df.format(val_total));
                cartViewModelArrayList.set(position, new CartViewModel(cartViewModel.getId(),
                        cartViewModel.getProductImage(),
                        cartViewModel.getProductTitle(),
                        cartViewModel.getProductOriginalPrice(),
                        cartViewModel.getProductNewPrice(),
                        String.valueOf(qty)));
                updateList(cartViewModelArrayList);

                DatabaseReference dataRef = Utils.dbRef.child("Users").child(phone).child("Carts").child(cartViewModel.getId());
                dataRef.child("quantity").setValue(qty);
                progress.dismiss();
            }
        });

        holder.rl_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progress = new ProgressDialog(context);
                progress.setMessage("Removing quantity..");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();
                int qty = Integer.parseInt(cartViewModel.getQuantity());
                double price = Double.parseDouble(cartViewModel.getProductNewPrice());
                if (qty-1 == 0){
                    DatabaseReference dataRef = Utils.dbRef.child("Users").child(phone).child("Carts").child(cartViewModel.getId());
                    dataRef.removeValue();
                    cartViewModelArrayList.remove(position);
                    updateList(cartViewModelArrayList);
                    if (cartViewModelArrayList.size()==0){
                        tv_cartCount.setText("");
                        ll_checkout.setVisibility(View.GONE);
                        tv_no_items.setVisibility(View.VISIBLE);
                    }
                    else {
                        tv_cartCount.setText(cartViewModelArrayList.size() + " Items");
                        ll_checkout.setVisibility(View.VISIBLE);
                        tv_no_items.setVisibility(View.GONE);
                    }
                }
                else{
                    qty -= 1;
                    val_sub_total -= price;
                    val_total = val_sub_total + val_sub_shipping;
                    tv_sub_total.setText("$"+df.format(val_sub_total));
                    tv_shipping.setText("$"+df.format(val_sub_shipping));
                    tv_total.setText("$"+df.format(val_total));
                    cartViewModelArrayList.set(position, new CartViewModel(cartViewModel.getId(),
                            cartViewModel.getProductImage(),
                            cartViewModel.getProductTitle(),
                            cartViewModel.getProductOriginalPrice(),
                            cartViewModel.getProductNewPrice(),
                            String.valueOf(qty)));
                    updateList(cartViewModelArrayList);

                    DatabaseReference dataRef = Utils.dbRef.child("Users").child(phone).child("Carts").child(cartViewModel.getId());
                    dataRef.child("quantity").setValue(qty);
                }
                progress.dismiss();
            }
        });

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dataRef = Utils.dbRef.child("Users").child(phone).child("Carts").child(cartViewModel.getId());
                dataRef.removeValue();
                cartViewModelArrayList.remove(position);
                updateList(cartViewModelArrayList);
                if (cartViewModelArrayList.size()==0){
                    tv_cartCount.setText("");
                    ll_checkout.setVisibility(View.GONE);
                    tv_no_items.setVisibility(View.VISIBLE);
                }
                else {
                    tv_cartCount.setText(cartViewModelArrayList.size() + " Items");
                    ll_checkout.setVisibility(View.VISIBLE);
                    tv_no_items.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartViewModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_cartImage;
        TextView tv_cartTitle, tv_cartNewPrice, tv_cartOriginalPrice, tv_quantity;
        ImageView iv_delete;
        RelativeLayout rl_add, rl_remove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_cartImage = itemView.findViewById(R.id.iv_cartImage);
            tv_cartTitle = itemView.findViewById(R.id.tv_cartTitle);
            tv_cartNewPrice = itemView.findViewById(R.id.tv_cartNewPrice);
            tv_cartOriginalPrice = itemView.findViewById(R.id.tv_cartOriginalPrice);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            rl_add = itemView.findViewById(R.id.rl_add);
            rl_remove = itemView.findViewById(R.id.rl_remove);
        }
    }

    public void updateList(ArrayList<CartViewModel> list){
        ((Activity)context).runOnUiThread(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                cartViewModelArrayList = list;
                notifyDataSetChanged();
            }
        });
    }
}
