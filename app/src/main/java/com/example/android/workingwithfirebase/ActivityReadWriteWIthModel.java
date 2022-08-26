package com.example.android.workingwithfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.hardware.lights.LightState;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityReadWriteWIthModel extends AppCompatActivity {

    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Person> personArrayList;
    ValueEventListener valueEventListener;
    private ChildEventListener mChildEventListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private EditText nameEt,ageEt;
    private Button writeDataBtn,readDataBtn;
    /*private TextView dataTxt2;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_model);

        initViews();
        personArrayList = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance();

        mRef = mDatabase.getReference("users");
        myAdapter = new MyAdapter(this,personArrayList);
        recyclerView.setAdapter(myAdapter);

      /*  mChildEventListener = new ChildEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
              *//*  Person personData = snapshot.getValue(Person.class);
                String name = personData.getName();
                int age = personData.getAge();
                dataTxt2.append("Name: "+name+"Age: "+age+"\n");*//*

                Person person =  snapshot.getValue(Person.class);
                personArrayList.add(person);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Person person = snapshot.getValue(Person.class);
                person.setUId(snapshot.getKey());
                personArrayList.remove(person);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mRef.addChildEventListener(mChildEventListener);
        */


      //  writeDataBtn.setOnClickListener(this::writeData);
        writeDataBtn.setOnClickListener(this::readData);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Person person = dataSnapshot.getValue(Person.class);
                    personArrayList.add(person);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

    }

    private void readData(View view) {
       // select * from table name
       // mRef.addValueEventListener(valueEventListener);

        //select * from table name where name = "ali"

        /*Query query1 = mRef.orderByChild("name").equalTo("ali");
        query1.addValueEventListener(valueEventListener);*/

        //select * from table name order by name;
    /*    Query query1 = mRef.orderByChild("name");
        query1.addValueEventListener(valueEventListener);*/

        //select * from table name where age > 30;
        /*Query query1 = mRef.orderByChild("age").startAt(30);
        query1.addValueEventListener(valueEventListener);*/



        //select * from table name where age < 30;
/*           Query query1 = mRef.orderByChild("age").endAt(30);
        query1.addValueEventListener(valueEventListener);*/

        // select * from table name limit by 3 showing top three

//        Query query = mRef.limitToFirst(3);
//        query.addValueEventListener(valueEventListener);

        // select * from table name limit by 3 showing last 3

        mRef.limitToLast(3).addValueEventListener(valueEventListener);


    }

    private void writeData(View view) {
        String key = mRef.push().getKey();
        String name = null;
        int age = 0;

        if (!TextUtils.isEmpty(nameEt.getText().toString())){
            name = nameEt.getText().toString();
        }

        if (!TextUtils.isEmpty(ageEt.getText().toString())){
            age = Integer.parseInt(ageEt.getText().toString());
        }

        if (name != null && age != 0) {
            Person person  = new Person(name,age);
            person.setUId(key);
            mRef.child(key).setValue(person);
        }


    }


    private void initViews() {
        recyclerView = findViewById(R.id.recycleView);
        nameEt = findViewById(R.id.etName3);
        ageEt = findViewById(R.id.etAge3);
        writeDataBtn = findViewById(R.id.writeDataBtn3);
/*        dataTxt2 = findViewById(R.id.dataTxt2);
        dataTxt2.setMovementMethod(new ScrollingMovementMethod());*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRef.removeEventListener(mChildEventListener);
        mRef.removeEventListener(valueEventListener);
    }
}