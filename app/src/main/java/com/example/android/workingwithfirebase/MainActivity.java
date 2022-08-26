
package com.example.android.workingwithfirebase;

import android.accessibilityservice.GestureDescription;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private StorageReference mSRef;
    private ValueEventListener mValueEventListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private EditText nameEt,ageEt,pushIdEt;
    private Button writeDataBtn, readDataBtn;
    private TextView nameTxt,ageTxt;
    private String pushId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        mSRef = firebaseStorage.getReference("docs/");




        mDatabase = FirebaseDatabase.getInstance();

        mRef = mDatabase.getReference("users");

        writeDataBtn.setOnClickListener(this::writeData);

        readDataBtn.setOnClickListener(this::readData);



       /* mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = snapshot.getValue(String.class);
                outputText.setText(data);
                Toast.makeText(MainActivity.this, "data read successfully", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        mRef.child("user1").addValueEventListener(mValueEventListener);*/
    }

    private void readData(View view) {
       /* //realTime Update
        mRef.child("user1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = snapshot.getValue(String.class);
                outputText.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        pushId = pushIdEt.getText().toString().replaceAll(" ","");
        //single time Update using when btn clicked
        mRef.child(pushId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*String data = snapshot.getValue(String.class);
                nameTxt.setText(data);*/
                Map<String,Object> data =(Map<String, Object>) snapshot.getValue();

                nameTxt.setText(data.get("Name").toString());
                ageTxt.setText(data.get("Age").toString());

                Toast.makeText(MainActivity.this, "data read successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            //    Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void writeData(View view) {
        String name = nameEt.getText().toString();
        int age = Integer.parseInt(ageEt.getText().toString());
        String userKey = mRef.push().getKey();

        mRef.child(userKey).child("Name").setValue(name);
        mRef.child(userKey).child("Age").setValue(age);

/*        mRef.child("user1").child("Name").setValue(name);
        mRef.child("user1").child("Age").setValue(age);
        mRef.setValue(text);*/

/*
        mRef.child("user1").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //Toast.makeText(MainActivity.this, "data written successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              //  Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
*/

    }

    public void updateData(View view) {
        String name = nameEt.getText().toString();
        int age = Integer.parseInt(ageEt.getText().toString());
        pushId = pushIdEt.getText().toString().replaceAll(" ","");

        Map<String,Object> updateData = new HashMap<>();
        updateData.put("/"+pushId+"/Name",name);
        updateData.put("/"+pushId+"/Age",age);

        mRef.updateChildren(updateData);

    }

    private void initViews() {
        nameEt = findViewById(R.id.etName);
        ageEt = findViewById(R.id.etAge);
        pushIdEt = findViewById(R.id.etPushID);
        writeDataBtn = findViewById(R.id.writeDataBtn);
        nameTxt = findViewById(R.id.nameTxt);
        ageTxt = findViewById(R.id.ageTxt);
        readDataBtn = findViewById(R.id.readDataBtn);
    }

    public void deleteData(View view) {
        String pushId = pushIdEt.getText().toString().replaceAll(" ","");
        Task<Void> task = mRef.child(pushId).removeValue();
      //  Task<Void> task = mRef.child("user1").child("Name").removeValue();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




/*    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRef.child("user1").removeEventListener(mValueEventListener);
    }*/
}