package com.example.android.workingwithfirebase;

import static com.example.android.workingwithfirebase.MyAdapter.USER_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {

    private TextView detailTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailTxt = findViewById(R.id.detailTxt);
        if(getIntent().hasExtra(USER_ID)){
            String pushId = getIntent().getStringExtra(USER_ID);
            FirebaseDatabase.getInstance().getReference("users").child(pushId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Person person = snapshot.getValue(Person.class);
                    detailTxt.setText(person.getName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}