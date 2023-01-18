package com.example.pruebatecnica_blaspiris;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;




public class MainActivity extends AppCompatActivity {
    EditText searchUser;
    RecyclerView recyclerView;
    AsyncTaskUsers asyncTaskUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //CHECK WRITE_CONTACTS PERMISSION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, 100);
        }

        searchUser=findViewById(R.id.searchUser);
        recyclerView=findViewById(R.id.recyclerView);
        asyncTaskUsers= (AsyncTaskUsers) new AsyncTaskUsers(MainActivity.this,recyclerView).execute();


        //SEARCH USER
        searchUser.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable search) {
                asyncTaskUsers.searchUser(search.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }




















}