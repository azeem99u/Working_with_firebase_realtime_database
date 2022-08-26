package com.example.android.workingwithfirebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class Utils {

    public static Task<Void> removeUser(String key) {
        Task<Void> users = FirebaseDatabase.getInstance().getReference("users")
                .child(key).
                removeValue();
        return users;
    }
}
