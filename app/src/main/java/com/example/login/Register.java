package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText user, mk, email;

    private Button login,register;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_gui);
        Button dnhap = (Button) findViewById(R.id.login_bt);
        dnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Register.this, MainActivity.class);

                startActivity(intent);
            }
        });
        Button register_btn = findViewById(R.id.register_bt);
        register_btn.setBackgroundColor(getResources().getColor(R.color.green));
        Button login_btn = findViewById(R.id.login_bt);
        login_btn.setBackgroundColor(getResources().getColor(R.color.green));
        mk = (EditText) findViewById(R.id.pass_tx);
        user = (EditText) findViewById(R.id.user_tx);
        email = (EditText) findViewById(R.id.gmail_tx);
        login = (Button) findViewById(R.id.login_bt);
        register = (Button) findViewById(R.id.register_bt);
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Taxi manager");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = user.getText().toString();
                String password = mk.getText().toString();
                String mail = email.getText(). toString();
                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                    createUser(username, password, mail);
                } else {
                    updateUser(username, password, mail);
                }
            }
        });

        toggleButton();
    }

    // Changing button text
    private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            login.setText("Đăng nhập");
        } else {
            login.setText("Đăng nhập");
        }
    }

    /**
     * Creating new user node under 'users'
     */
    private void createUser(String username, String password, String email ) {

        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }


        User object = new User(username, password , email);

        mFirebaseDatabase.child(userId).setValue(object);

        addUserChangeListener();
    }

    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User object = dataSnapshot.getValue(User.class);

                // Check for null
                if (object == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + object.username+ ", " + object.password + ", " + object.email);

                // Display newly updated name and email


                // clear edit text
                user.setText("");
                mk.setText("");
                email.setText("");

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String username, String password, String email) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(username))
            mFirebaseDatabase.child(userId).child("username").setValue(username);

        if (!TextUtils.isEmpty(password))
            mFirebaseDatabase.child(userId).child("password").setValue(password);

        if(!TextUtils.isEmpty(email))
            mFirebaseDatabase.child(userId).child("email").setValue(email);
}


}