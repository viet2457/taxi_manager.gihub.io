package com.example.login;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_gui);

        Button register_btn = (Button) findViewById(R.id.register_button);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Register.class);

                startActivity(intent);
            }
        });
        Button register_bt = findViewById(R.id.register_button);
        register_bt.setBackgroundColor(getResources().getColor(R.color.green));
        Button login_bt = findViewById(R.id.login_button);
        login_bt.setBackgroundColor(getResources().getColor(R.color.green));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


    }
}