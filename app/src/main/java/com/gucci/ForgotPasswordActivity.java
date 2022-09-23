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
import com.google.firebase.database.ValueEventListener;
import com.gucci.Common.Utils;

import java.util.ArrayList;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText et_phone, et_new_pw, et_confirm_pw;
    Button btn_reset_pw;
    TextView tv_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
        onclickFunc();
    }

    public void init(){
        et_phone = findViewById(R.id.et_phone);
        et_new_pw = findViewById(R.id.et_new_password);
        et_confirm_pw = findViewById(R.id.et_confirm_password);
        btn_reset_pw = findViewById(R.id.btn_reset_password);
        tv_sign = findViewById(R.id.tv_signIn);
    }

    public void onclickFunc(){
        btn_reset_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progress = new ProgressDialog(ForgotPasswordActivity.this);
                progress.setTitle("Password Reset");
                progress.setMessage("Wait while resetting password...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();
                String phone, new_pw, confirm_pw;
                phone = et_phone.getText().toString();
                new_pw = et_new_pw.getText().toString();
                confirm_pw = et_confirm_pw.getText().toString();
                if (!phone.isEmpty() && !new_pw.isEmpty() && !confirm_pw.isEmpty()){
                    if (new_pw.equals(confirm_pw)){
                        DatabaseReference dbref = Utils.dbRef.child("Users");
                        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<String> users_list = new ArrayList<>();
                                if (snapshot.exists()){
                                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                        users_list.add(dataSnapshot.getKey());
                                    }
                                }
                                if (users_list.contains(phone)){
                                    DatabaseReference dbref = Utils.dbRef.child("Users").child(phone);
                                    dbref.child("password").setValue(confirm_pw);
                                    Toast.makeText(ForgotPasswordActivity.this, R.string.title_pw_reset, Toast.LENGTH_SHORT).show();
                                    Intent goToLoginIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                    startActivity(goToLoginIntent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(ForgotPasswordActivity.this, R.string.title_user_no_exist, Toast.LENGTH_SHORT).show();
                                }
                                progress.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                progress.dismiss();
                            }
                        });
                    }
                    else{
                        Toast.makeText(ForgotPasswordActivity.this, R.string.title_pw_mismatch, Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }
                else{
                    Toast.makeText(ForgotPasswordActivity.this, R.string.title_fill_all_field, Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        });

        tv_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToLoginIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(goToLoginIntent);
                finish();
            }
        });
    }
}