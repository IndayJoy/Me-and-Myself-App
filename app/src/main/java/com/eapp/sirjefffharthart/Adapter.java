package com.eapp.sirjefffharthart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    private List<com.eapp.sirjefffharthart.ModelClass> userList;

    public Adapter (List<com.eapp.sirjefffharthart.ModelClass>userList){
        this .userList=userList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        int resource= userList.get(position).getImageview1();
        String name= userList.get(position).getTextview1();
        String msg= userList.get(position).getTextview3();
        String line= userList.get(position).getDivider();

        holder.setData(resource,name,msg,line);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;
        private TextView textView3;
        private TextView divider;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageview1);
            textView=itemView.findViewById(R.id.textview);
            textView3=itemView.findViewById(R.id.textview3);
            divider=itemView.findViewById(R.id.divider);

        }

        public void setData(int resource, String name, String msg, String line) {

            imageView.setImageResource(resource);
            textView.setText(name);
            textView3.setText(msg);
            divider.setText(line);
        }
    }
}
