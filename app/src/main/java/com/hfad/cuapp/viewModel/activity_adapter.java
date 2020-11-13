package com.hfad.cuapp.viewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.cuapp.MainActivity;
import com.hfad.cuapp.R;
import com.hfad.cuapp.dept_options.activity.activities;
import com.hfad.cuapp.model.new_asnmnt_ct;

import java.util.ArrayList;

public class activity_adapter extends RecyclerView.Adapter<activity_adapter.viewHolder>{

    static Context context;
    static ArrayList<new_asnmnt_ct> asnmntCt;
    static ArrayList<String> key_list;

    public activity_adapter(Context context, ArrayList<new_asnmnt_ct> s,ArrayList<String> p){
        this.context=context;
        asnmntCt=s;
        key_list=p;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.asnmnt_ct_list_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        holder.date.setText(asnmntCt.get(position).getDate());
        holder.subj.setText(asnmntCt.get(position).getSubj());
        holder.taskName.setText(asnmntCt.get(position).getTask_name());
        holder.imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbuilder=new AlertDialog.Builder(context);
                LayoutInflater inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                View dialogeView = inflater.inflate( R.layout.show_asnmnt_ct_dtls, null );
                alertbuilder.setView(dialogeView);

                alertbuilder.setTitle("");
                final AlertDialog ad =alertbuilder.create();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users_post").child(Global_variable.batchForpost).child("new_asnmnt_ct").child(activity_adapter.key_list.get(position));
                final TextView textView = (TextView) dialogeView.findViewById(R.id.show_asnmnt_txtview);
                final Button dlt_btn =(Button) dialogeView.findViewById(R.id.show_asnmnt_delete_btn);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("dtls").exists()){
                            String s = dataSnapshot.child("dtls").getValue().toString();
                            textView.setText(s);
                        }
                        else {
                            ad.dismiss();
                        }


                        dlt_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //delete(position);
                                DialogInterface.OnClickListener dialogClkListener=new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        switch (which){
                                            case DialogInterface.BUTTON_POSITIVE:

                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users_post").child(MainActivity.batchForpost).child("new_asnmnt_ct").child(activity_adapter.key_list.get(position));
                                                databaseReference.removeValue();
                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                break;
                                        }
                                    }
                                };
                                AlertDialog.Builder ab = new AlertDialog.Builder(activity_adapter.context);
                                ab.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClkListener)
                                        .setNegativeButton("No", dialogClkListener).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                ad.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return asnmntCt.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public TextView subj,taskName,date;
        ImageButton imgbtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            subj=(TextView)itemView.findViewById(R.id.subject_n);
            taskName=(TextView) itemView.findViewById(R.id.task_n);
            date=(TextView)itemView.findViewById(R.id.tak_date);
            imgbtn=(ImageButton)itemView.findViewById(R.id.task_dtls_btn);
        }
    }
}
