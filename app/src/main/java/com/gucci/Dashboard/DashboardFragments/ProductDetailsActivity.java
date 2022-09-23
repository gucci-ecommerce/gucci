package com.gucci.Dashboard.DashboardFragments;

import static com.gucci.Dashboard.HomeActivity.navController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gucci.Common.Utils;
import com.gucci.LoginActivity;
import com.gucci.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {
    RelativeLayout rl_back;
    ImageView iv_prod_image;
    TextView tv_title, tv_tag, tv_price,  tv_desc;
    RelativeLayout rl_move_to_cart;
    Button btn_add_to_cart;
    String productTitle ="";
    String productTag = "";
    String productPrice = "";
    String productDesc = "";
    String image = "";
    String prod_id = "";
    String productOriginalPrice = "";
    boolean added_to_cart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        init();
        setIntentData();
        onClick();
    }

    public void init(){
        rl_back = findViewById(R.id.rl_back_arrow);
        iv_prod_image = findViewById(R.id.iv_productImage);
        tv_title = findViewById(R.id.tv_productName);
        tv_tag = findViewById(R.id.tv_productTag);
        tv_price = findViewById(R.id.tv_productPrice);
        tv_desc = findViewById(R.id.tv_product_desc);
        rl_move_to_cart = findViewById(R.id.rl_move_to_cart);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
    }

    public void setIntentData(){
        ProgressDialog progress = new ProgressDialog(ProductDetailsActivity.this);
        progress.setMessage("Loading data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productTitle = extras.getString("productTitle");
            productTag = extras.getString("productTag");
            productPrice = extras.getString("productPrice");
            productDesc = extras.getString("productDesc");
            image = extras.getString("productImage");
            productOriginalPrice = extras.getString("productOldPrice");
            prod_id = extras.getString("productId");

//            int image = extras.getInt("productImage");
//            iv_prod_image.setImageResource(image);
            Picasso.get()
                    .load(image)
                    .resize(1024, 1024)
                    .centerCrop()
                    .into(iv_prod_image);
            tv_title.setText(productTitle);
            tv_tag.setText(productTag);
            tv_price.setText("$"+productPrice);
            tv_desc.setText(productDesc);
        }

        String phone = Utils.getprefs(this, "phone", "");
        DatabaseReference dbRef = Utils.dbRef.child("Users").child(phone).child("Carts");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> cart_items = new ArrayList<>();
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String cart_prod_id = dataSnapshot.getKey();
                        cart_items.add(cart_prod_id);
                    }
                    if (cart_items.contains(prod_id)){
                        added_to_cart = true;
                        btn_add_to_cart.setText(R.string.title_remove_from_cart);
                        btn_add_to_cart.setBackgroundResource(R.drawable.circular_rectangle_design_unselect_color);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        progress.dismiss();
    }

    public void onClick(){
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = Utils.getprefs(getApplicationContext(), "phone", "");
                DatabaseReference dbRef = Utils.dbRef.child("Users").child(phone).child("Carts");
                if (added_to_cart){
                    ProgressDialog progress = new ProgressDialog(ProductDetailsActivity.this);
                    progress.setMessage("Adding to cart...");
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();
                    dbRef = dbRef.child(prod_id);
                    dbRef.removeValue();
                    added_to_cart = false;
                    btn_add_to_cart.setBackgroundResource(R.drawable.circular_rectangle_design_primary_color);
                    btn_add_to_cart.setText(R.string.title_add_to_cart);
                    progress.dismiss();
                }
                else{
                    if (!prod_id.isEmpty()){
                        Log.e("description : ", productDesc);
                        Log.e("id : ", prod_id);
                        Log.e("imageUrl : ", image);
                        Log.e("newPrice : ", productPrice);
                        Log.e("oldPrice : ", productOriginalPrice);
                        Log.e("productName : ", productTitle);
                        Log.e("tag : ", productTag);

                        ProgressDialog progress = new ProgressDialog(ProductDetailsActivity.this);
                        progress.setMessage("Removing from cart...");
                        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                        progress.show();
                        dbRef = dbRef.child(prod_id);
                        Map<String, String> cart_data = new HashMap<String, String>();
                        cart_data.put("description", productDesc);
                        cart_data.put("id", prod_id);
                        cart_data.put("imageUrl", image);
                        cart_data.put("newPrice", productPrice);
                        cart_data.put("oldPrice", productOriginalPrice);
                        cart_data.put("productName", productTitle);
                        cart_data.put("quantity", "1");
                        cart_data.put("tag", productTag);
                        dbRef.setValue(cart_data);
                        added_to_cart = true;
                        btn_add_to_cart.setBackgroundResource(R.drawable.circular_rectangle_design_unselect_color);
                        btn_add_to_cart.setText(R.string.title_remove_from_cart);
                        progress.dismiss();
                    }
                }
            }
        });

        rl_move_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            super.onBackPressed(); //replaced
        }
    }
}