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
import com.gucci.Dashboard.HomeActivity;

import java.util.ArrayList;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView tv_signUp, tv_password;
    EditText et_phone, et_password;
    Button btn_signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_signUp = findViewById(R.id.tv_signUp);
        btn_signIn = findViewById(R.id.btn_sign_in);
        tv_password = findViewById(R.id.tv_password);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        onClickFunctions();

    }

    public void onClickFunctions(){
        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSignUpIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goToSignUpIntent);
            }
        });

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progress = new ProgressDialog(LoginActivity.this);
                progress.setTitle("Signing in");
                progress.setMessage("Wait while loading...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();
                String phone = et_phone.getText().toString();
                String password = et_password.getText().toString();
                if (!phone.isEmpty() && !password.isEmpty()){
                    DatabaseReference dbref = Utils.dbRef.child("Users");
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<String> users_list = new ArrayList<>();
                            if (snapshot.exists()){
                                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                    String registered_phone = dataSnapshot.getKey();
                                    if (registered_phone.equals(phone)){
                                        Map map = (Map)dataSnapshot.getValue();
                                        String reg_password = map.get("password").toString();
                                        String fullName = map.get("full_name").toString();
                                        String email = map.get("email").toString();
                                        if (reg_password.equals(password)){
                                            Toast.makeText(LoginActivity.this, R.string.title_sign_in_success, Toast.LENGTH_SHORT).show();
                                            Intent goToSignUpIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                            goToSignUpIntent.putExtra("phone", phone);
                                            goToSignUpIntent.putExtra("full_name", fullName);
                                            goToSignUpIntent.putExtra("email", email);
                                            Utils.editprefBool(LoginActivity.this, "login", true);
                                            Utils.editprefs(LoginActivity.this, "phone", phone);
                                            startActivity(goToSignUpIntent);
                                            finish();
                                            break;
                                        }
                                        else{
                                            Toast.makeText(LoginActivity.this, R.string.title_incorrect, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, R.string.title_user_no_exist, Toast.LENGTH_SHORT).show();
                                    }
                                }
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
                    Toast.makeText(LoginActivity.this, R.string.title_fill_all_field, Toast.LENGTH_SHORT);
                    progress.dismiss();
                }

            }
        });

        tv_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSignUpIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(goToSignUpIntent);
            }
        });
    }
}