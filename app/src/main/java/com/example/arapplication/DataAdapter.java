package com.example.arapplication;

import android.content.Context;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    Context context;
    ArrayList<ModelData> modelDataArrayList;
    public DataAdapter(Context context, ArrayList<ModelData> modelDataArrayList)
    {
        this.context = context;
        this.modelDataArrayList = modelDataArrayList;
    }
    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.dataitem,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        ModelData modeldata = modelDataArrayList.get(position);
        holder.txtdata.setText(modeldata.data);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtdata;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdata = itemView.findViewById(R.id.txtdata);
        }
    }
}
