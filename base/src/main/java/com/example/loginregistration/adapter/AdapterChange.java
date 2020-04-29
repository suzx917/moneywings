package com.example.loginregistration.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregistration.R;
import com.example.loginregistration.fbclass.Change;


import java.util.List;

public class AdapterChange extends RecyclerView.Adapter<AdapterChange.viewholderChange>{

    List<Change> changeList;


    public AdapterChange(List<Change> changeList)
    {
        this.changeList = changeList;
    }

    @NonNull
    @Override
    public viewholderChange onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_entry,parent,false);
        return new viewholderChange(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderChange holder, final int position) {
        holder.type.setText(changeList.get(position).getType());
        holder.name.setText(changeList.get(position).getCounterPartName());
        holder.amount.setText(""+changeList.get(position).getValue());

    }

    @Override
    public int getItemCount() {
        return changeList.size();
    }

    public class viewholderChange extends RecyclerView.ViewHolder{
        TextView type;
        TextView name;
        TextView amount;
        View mView;

        public viewholderChange(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.TType);
            name = itemView.findViewById(R.id.CName);
            amount = itemView.findViewById(R.id.Amount);
            mView = itemView;
        }
    }
}
