package com.example.myvalute4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterValute extends RecyclerView.Adapter<AdapterValute.ViewHolder> {
    private Value mValue;
    private Context mContext;

    public AdapterValute(Value list, Context context) {
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
        final String name = mValue.getMapValute().get(position).name;
        final String valute = mValue.getMapValute().get(position).value;

        holder.name.setText(name);
        holder.value.setText(valute);
    }

    @Override
    public int getItemCount() {
        return mValue.getMapValute().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name,value;

        public ViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            value = (TextView)view.findViewById(R.id.value);
        }
    }
}
