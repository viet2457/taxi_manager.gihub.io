package com.example.login;

import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class User {
        public String username;
        public String password;
        public String email;
        // Default constructor required for calls to
        // DataSnapshot.getValue(User.class)
        public User() {
        }

        public User(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;

        }
    }

