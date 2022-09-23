package com.gucci.Dashboard.DashboardFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gucci.Common.Utils;
import com.gucci.Dashboard.HomeActivity;
import com.gucci.LoginActivity;
import com.gucci.R;

import java.util.Objects;

public class SettingsFragment extends Fragment {
    CardView cv_logout, cv_send_feedback, cv_rate, cv_about;
    TextView tv_fullName, tv_email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        init(view);
        onClickFunctions();
        return view;
    }

    public void init(View view){
        cv_logout = view.findViewById(R.id.cv_logout);
        cv_send_feedback = view.findViewById(R.id.cv_send_feedback);
        cv_rate = view.findViewById(R.id.cv_rating);
        cv_about = view.findViewById(R.id.cv_about);
        tv_fullName = view.findViewById(R.id.tv_fullName);
        tv_email = view.findViewById(R.id.tv_email);

        String phone = Utils.getprefs(getContext(), "phone", "");
        DatabaseReference dataRef = Utils.dbRef.child("Users").child(phone);
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String full_name = snapshot.child("full_name").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    tv_fullName.setText(full_name);
                    tv_email.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    
    public void onClickFunctions(){
        cv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Log Out")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.editprefBool(requireActivity(), "login", false);
                                Utils.editprefs(requireActivity(), "phone", "");
                                Toast.makeText(requireActivity(), R.string.title_logout_success, Toast.LENGTH_SHORT).show();
                                Intent goToSignUpIntent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(goToSignUpIntent);
                                requireActivity().finish();
                            }
                        })
                        .setNegativeButton("No",null);
                AlertDialog  alert = builder.create();
                alert.show();
            }
        });

        cv_send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Under Development", Toast.LENGTH_SHORT).show();
            }
        });

        cv_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Under Development", Toast.LENGTH_SHORT).show();
            }
        });

        cv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Under Development", Toast.LENGTH_SHORT).show();
            }
        });

    }
}