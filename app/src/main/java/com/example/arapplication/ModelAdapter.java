package com.example.arapplication;

import android.content.Context;
import android.media.Image;
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
    ItemClickListener itemClickListener;

    public ModelAdapter(Context context,ArrayList<Model> modelArrayList,ItemClickListener itemClickListener)
    {
        this.context = context;
        this.modelArrayList = modelArrayList;
        this.itemClickListener = itemClickListener;
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
        holder.modelname.setText(model.model_name);
        holder.itemView.setOnClickListener(view -> {
            itemClickListener.OnItemClick(modelArrayList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public interface ItemClickListener
    {
        void OnItemClick(Model model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView modelname;
        ImageView imgmodel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modelname = itemView.findViewById(R.id.txtmodelname);
            imgmodel  = itemView.findViewById(R.id.imgmodel);
        }

    }
}
