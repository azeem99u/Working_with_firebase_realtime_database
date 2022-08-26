package com.example.android.workingwithfirebase;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ReadAndWriteFromFirebase extends AppCompatActivity {

    private ValueEventListener mValueEventListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private EditText nameEt, ageEt, pushIdEt;
    private Button writeDataBtn, readDataBtn;
    private TextView nameTxt, ageTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_and_write_from_firebase);
        initViews();

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("users");

        writeDataBtn.setOnClickListener(this::writeData);
        readDataBtn.setOnClickListener(this::readData);
    }

    private void writeData(View view) {
        String name = "";
        int age = 0;

        if (!TextUtils.isEmpty(nameEt.getText().toString())) {
            name = nameEt.getText().toString();
        }

        if (!TextUtils.isEmpty(ageEt.getText().toString())) {
            age = Integer.parseInt(ageEt.getText().toString());
        }
        if (age == 0 && name == "") {
            return;
        }
        String userKey = mRef.push().getKey();
        if (userKey != null) {
            Map<String,Object> insertData = new HashMap<>();
            insertData.put("Name",name);
            insertData.put("Age",age);
            mRef.child(userKey).setValue(insertData);
 /*           mRef.child(userKey).child("Name").setValue(name);
            mRef.child(userKey).child("Age").setValue(age);*/
        }

    }

    private void readData(View view) {

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Map<String, Object> data = (Map<String, Object>) snapshot.getValue();
                nameTxt.append(data+"");
                Log.d("tag", "onDataChange:"+data);

          /*      Object nameObj = (String) data.get("Name");
                Object ageObj = (Object) data.get("Age");
                String name = String.valueOf(nameObj);
                nameTxt.append(data.toString()+"\n"); */


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
             /*   Map<String, Object> data = (Map<String, Object>) snapshot.getValue();
                Object ageObj = (Object) data.get("Age");
                String ageString = String.valueOf(ageObj);
                int age = Integer.parseInt(ageString);
                ageTxt.append(""+age);*/

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

   /*     mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();

                    Log.d("tag", "onDataChange:"+data);
                    Object nameObj = (String) data.get("Name");
                    Object ageObj = (Object) data.get("Age");
                    String name = String.valueOf(nameObj);
                    String ageString = String.valueOf(ageObj);
                    int age = Integer.parseInt(ageString);
                    nameTxt.append(name+"\n");
                    ageTxt.append(age+"\n");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });*/
    }


    private void initViews() {
        nameEt = findViewById(R.id.etName3);
        ageEt = findViewById(R.id.etAge3);
        writeDataBtn = findViewById(R.id.writeDataBtn3);
        nameTxt = findViewById(R.id.dataTxt1);
        nameTxt.setMovementMethod(new ScrollingMovementMethod());
        ageTxt = findViewById(R.id.ageTxt2);
        readDataBtn = findViewById(R.id.readDataBtn3);
    }
}