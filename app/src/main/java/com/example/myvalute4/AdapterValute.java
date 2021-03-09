package com.example.myvalute4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myvalute4.model.Valute;

import java.util.ArrayList;

public class AdapterValute extends RecyclerView.Adapter<AdapterValute.ViewHolder> {
    private ArrayList<Valute> mValue;
    private Context mContext;

    public AdapterValute(ArrayList<Valute> list, Context context) {
        this.mContext=context;
        this.mValue =list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = mValue.get(position).name;
        String value = mValue.get(position).value;

        holder.name.setText(name);
        holder.value.setText(value);
    }

    @Override
    public int getItemCount() {
        return mValue.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView value;

        public ViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            value = (TextView)view.findViewById(R.id.value);
        }
    }
}
