package com.example.wardenfront;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private ArrayList<String> input;

    public recyclerAdapter(ArrayList<String> arr){
        input = arr;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView jobD;

        public MyViewHolder(final View view){
            super(view);
            jobD = view.findViewById(R.id.Job1);
        }
    }
    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.seeker_recycler, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String desc = input.get(position);
        holder.jobD.setText(desc);

    }

    @Override
    public int getItemCount() {
        return input.size();
    }
}
