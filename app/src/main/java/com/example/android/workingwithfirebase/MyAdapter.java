package com.example.android.workingwithfirebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public static final String USER_ID = "USER_ID";
    private Context context;
    private ArrayList<Person> personArrayList;

    public MyAdapter(Context context, ArrayList<Person> personArrayList) {
        this.context = context;
        this.personArrayList = personArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Person person = personArrayList.get(position);
        holder.textView.setText(person.getName() +" || "+ person.getAge());

        holder.textView.setOnClickListener(view -> {
            String uId = person.getUId();
            context.startActivity(new Intent(context,DetailActivity.class).putExtra(USER_ID,uId));
        });

        holder.textView.setOnLongClickListener(view -> {

            String uId = person.getUId();
            Task<Void> voidTask = Utils.removeUser(uId);
            voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "removed successfully", Toast.LENGTH_SHORT).show();
                }
            });

            return true;
        });
    }


    @Override
    public int getItemCount() {
        return personArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
