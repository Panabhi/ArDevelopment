package com.example.arapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {

    Context context;
    ArrayList<Model> modelArrayList;

    public ModelAdapter(Context context,ArrayList<Model> modelArrayList)
    {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }
    @NonNull
    @Override
    public ModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelAdapter.ViewHolder holder, int position) {
        Model model = modelArrayList.get(position);
        holder.modeldesc.setText(model.name);
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView modeldesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modeldesc = itemView.findViewById(R.id.txtdesc);
        }

    }
}
