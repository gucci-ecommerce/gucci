package com.gucci.Common;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    static SharedPreferences sharedpreferences;
    public static final String MY_PREFERENCES = "MyPrefs";
    public static String firebase_db_url = "https://gucci-ecommerce-default-rtdb.firebaseio.com/";
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(firebase_db_url);
    public static DatabaseReference dbRef = firebaseDatabase.getReference();
    public static DecimalFormat df = new DecimalFormat("#.##");

    public static String getprefs(Context context, String name, String defaultv){
        String s = "";
        sharedpreferences = context.getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        s = context.getSharedPreferences(MY_PREFERENCES, 0).getString(name, defaultv);
        return s;
    }

    public static void editprefs(Context context,String name,String value){
        sharedpreferences = context.getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static Boolean getprefBool(Context context, String name, Boolean defaultv){
        boolean s = false;
        sharedpreferences = context.getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        s = context.getSharedPreferences(MY_PREFERENCES, 0).getBoolean(name, defaultv);
        return s;
    }

    public static void editprefBool(Context context,String name,Boolean value){
        sharedpreferences = context.getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    public static void editprefsarr(Context context, List<String> list, String arrayname){
        sharedpreferences = context.getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(arrayname, json);
        editor.apply();
    }


    public static List<String> getprefsarr(String arrayname) {
        Gson gson = new Gson();
        String json = sharedpreferences.getString(arrayname,null);
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> returnarray ;
        returnarray = gson.fromJson(json, type);
        return returnarray;
    }

    public static void dialogBox(final Context context, String title, String message, String pos_btn_msg, String neg_btn_msg){
        AlertDialog.Builder  builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(pos_btn_msg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton(neg_btn_msg,null);
        AlertDialog  alert = builder.create();
        alert.show();
    }
}
