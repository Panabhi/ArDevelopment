package com.example.arapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelViewHolder> {
    Context context;
    ArrayList<ModelClass> modelClassArrayList;
    ItemClickListener itemClickListner;
    public ModelAdapter(Context context, ArrayList<ModelClass> modelClassArrayList)
    {
        this.context = context;
        this.modelClassArrayList = modelClassArrayList;
       // this.itemClickListner = itemClickListener;
    }
    @NonNull
    @Override
    public ModelAdapter.ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ModelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelAdapter.ModelViewHolder holder, int position) {
        ModelClass m = modelClassArrayList.get(position);
        holder.modelname.setText(m.name);

    }

    @Override
    public int getItemCount() {
        return modelClassArrayList.size();
    }
    public interface ItemClickListener
    {
        void OnItemClick(ModelClass modelClass);
    }

    public class ModelViewHolder extends RecyclerView.ViewHolder {
        GridLayout gridLayout;
        CardView cardView;
        RelativeLayout relativeLayout;
        ImageView modelimage;
        TextView modelname;
        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.modelcardview);
            relativeLayout = itemView.findViewById(R.id.relative1);
            modelimage = itemView.findViewById(R.id.modelimage);
            modelname = itemView.findViewById(R.id.txtmodelname);
        }
    }
}
