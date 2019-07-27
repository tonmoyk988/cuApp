package com.hfad.cuapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.viewHolder>{
    private List<rept_home_page_activity_list> activity_lists;
    private Context context;


    public myAdapter(List<rept_home_page_activity_list> activity_lists, Context context) {
        this.activity_lists = activity_lists;
        this.context = context;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= null;

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.department_home_layout, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final rept_home_page_activity_list activity = activity_lists.get(position);
        holder.textView.setText(activity.getContent_name());
        holder.imageView.setImageResource(activity.getIv());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,activity.getContent_name(),Toast.LENGTH_SHORT).show();
                if (activity.getContent_name()=="Class Updates"){
                    Intent intent = new Intent(context,dept_update.class);
                    context.startActivity(intent);

                }
                else if(activity.getContent_name()=="Class Schedule"){
                    Intent intent = new Intent(context,routinr_Class.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return activity_lists.size();
    }




    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        RelativeLayout relativeLayout;
        public viewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            textView = (TextView) itemView.findViewById(R.id.content);
            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.dept_page_relative_layout);
        }
    }
}
