package com.example.project.userweight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<UserWeight> list;

    public MyAdapter(Context context, ArrayList<UserWeight> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserWeight u = list.get(position);
        holder.userId.setText(u.getUserId()+"");
        holder.weight.setText(u.getWeight()+"");
        holder.dateTime.setText(u.getDateTimeAdded());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView userId, weight, dateTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userId = itemView.findViewById(R.id.tvUserId);
            weight = itemView.findViewById(R.id.tvWeight);
            dateTime = itemView.findViewById(R.id.tvDateTime);
        }
    }


}
