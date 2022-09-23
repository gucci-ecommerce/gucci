package com.gucci;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gucci.Common.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    TextView tv_signIn;
    EditText et_name, et_phone, et_email, et_password;
    Button btn_sign_up;
    String val_name, val_phone, val_email, val_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        onclickFunc();
    }

    public void init(){
        tv_signIn = findViewById(R.id.tv_signIn);
        et_name = findViewById(R.id.et_full_name);
        et_phone = findViewById(R.id.et_phone_no);
        et_email  =findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_sign_up = findViewById(R.id.btn_sign_up);

    }

    public void onclickFunc(){
        tv_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSignInIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goToSignInIntent);
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
                progress.setTitle("Creating Account");
                progress.setMessage("Wait while registering user...");
                progress.setCancelable(false);
                progress.show();
                val_name = et_name.getText().toString();
                val_phone = et_phone.getText().toString();
                val_email = et_email.getText().toString();
                val_password = et_password.getText().toString();
                DatabaseReference userDBref = Utils.dbRef.child("Users");
                if (!val_name.isEmpty() && !val_phone.isEmpty() && !val_email.isEmpty() && !val_password.isEmpty()){
                    try {
                        userDBref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<String> users_list = new ArrayList<>();
                                if (snapshot.exists()){
                                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                        String registered_phone = dataSnapshot.getKey();
                                        users_list.add(registered_phone);
                                    }
                                }
                                if (users_list.contains(val_phone)){
                                    Toast.makeText(RegisterActivity.this, R.string.title_user_already_registered, Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    DatabaseReference userDbSetRef = userDBref.child(val_phone);
//                                    userDbSetRef.child("full_name").setValue(val_name);
//                                    userDbSetRef.child("phone").setValue(val_phone);
//                                    userDbSetRef.child("email").setValue(val_email);
//                                    userDbSetRef.child("password").setValue(val_password);
                                    Map<String,String> user_details = new HashMap<>();
                                    user_details.put("full_name", val_name);
                                    user_details.put("phone", val_phone);
                                    user_details.put("email", val_email);
                                    user_details.put("password", val_password);
                                    userDbSetRef.setValue(user_details);
                                    Toast.makeText(RegisterActivity.this, R.string.title_user_registered, Toast.LENGTH_SHORT).show();

                                    Intent goToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(goToLogin);
                                    finish();

                                }
                                progress.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(RegisterActivity.this, R.string.title_user_register_failed, Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            }
                        });
                    }
                    catch (Exception e){
                        Toast.makeText(RegisterActivity.this, R.string.title_user_register_failed, Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, R.string.title_fill_all_field, Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }

            }
        });
    }
}