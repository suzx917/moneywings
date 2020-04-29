package com.example.loginregistration.adapter;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregistration.ProfileSearch;
import com.example.loginregistration.R;
import com.example.loginregistration.businessObj.business;



import java.util.List;

public class AdapterBusiness extends RecyclerView.Adapter<AdapterBusiness.viewholderbusiness>{

    List<business> businessList;


    public AdapterBusiness(List<business> businessList)
    {
        this.businessList = businessList;
    }

    @NonNull
    @Override
    public viewholderbusiness onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_search,parent,false);
        return new viewholderbusiness(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderbusiness holder, final int position) {
        holder.description.setText(businessList.get(position).getDescription());
        holder.BusName.setText(businessList.get(position).getName());
        holder.country.setText(businessList.get(position).getCountry());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent=new Intent(v.getContext(), ProfileSearch.class);
                intent.putExtra("bid", businessList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    public class viewholderbusiness extends RecyclerView.ViewHolder{
        TextView BusName;
        TextView country;
        TextView description;
        View mView;

        public viewholderbusiness(@NonNull View itemView) {
            super(itemView);
            BusName = itemView.findViewById(R.id.BusName);
            country = itemView.findViewById(R.id.country);
            description = itemView.findViewById(R.id.description);
            mView = itemView;
        }
    }
}
