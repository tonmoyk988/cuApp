package com.hfad.cuapp.dept_options.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hfad.cuapp.Calender;
import com.hfad.cuapp.R;
import com.hfad.cuapp.model.new_asnmnt_ct;
import com.hfad.cuapp.viewModel.Global_variable;
import com.hfad.cuapp.viewModel.activity_adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class activities extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    ArrayList<new_asnmnt_ct> newAsnmntCts ;
    ArrayList<String> newAsnmntCts_database_key;
    activity_adapter  adapter;
    public static TextView calenderTxtview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        toolbar=(Toolbar) findViewById(R.id.activity_bar);
        newAsnmntCts  = new ArrayList<new_asnmnt_ct>();
        newAsnmntCts_database_key=new ArrayList<String>();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Activities");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(RecyclerView) findViewById(R.id.ativitylist_R_view);
        LinearLayoutManager linearLayout= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setHasFixedSize(true);

        fetch();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_add_new_asnmntdlg();
            }
        });


    }

    public void fetch() {
        DatabaseReference dataReference=FirebaseDatabase.getInstance().getReference();
        DatabaseReference databaseReference=dataReference.child("Users_post").child(Global_variable.batchForpost).child("new_asnmnt_ct");
        Query query=databaseReference.orderByChild("serial_key");
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                newAsnmntCts.clear();
                newAsnmntCts_database_key.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    new_asnmnt_ct n=data.getValue(new_asnmnt_ct.class);
                    newAsnmntCts.add(n);
                    newAsnmntCts_database_key.add(data.getKey().toString());
                    //newAsnmntCts.add(data.getValue(new_asnmnt_ct.class));
                }
                adapter = new activity_adapter(activities.this,newAsnmntCts,newAsnmntCts_database_key);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(valueEventListener);
        /*This Portion dooes work fine
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users_post").child(Global_variable.batchForpost).child("new_asnmnt_ct");
    databaseReference.keepSynced(true);
    databaseReference.orderByChild("serial_key").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            newAsnmntCts.clear();
            newAsnmntCts_database_key.clear();
            for(DataSnapshot data:dataSnapshot.getChildren()){
                new_asnmnt_ct n=data.getValue(new_asnmnt_ct.class);
                newAsnmntCts.add(n);
                newAsnmntCts_database_key.add(data.getKey().toString());
                //newAsnmntCts.add(data.getValue(new_asnmnt_ct.class));
            }
            Collections.reverse(newAsnmntCts);
            Collections.reverse(newAsnmntCts_database_key);
            adapter = new activity_adapter(activities.this,newAsnmntCts,newAsnmntCts_database_key);
            recyclerView.setAdapter(adapter);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });*/



        /*Query query = FirebaseDatabase.getInstance().getReference().child("Users_post").child(Global_variable.batchForpost).child("new_asnmnt_ct");//.child("MChFFrOIa-V4wh_b28n");

         */
        /*FirebaseRecyclerOptions<new_asnmnt_ct> options =
                new FirebaseRecyclerOptions.Builder<new_asnmnt_ct>()
                        .setQuery(query, new_asnmnt_ct.class)
                        .build();
        FirebaseRecyclerOptions<new_asnmnt_ct> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<new_asnmnt_ct>().
                setQuery(query, new SnapshotParser<new_asnmnt_ct>() {
                    @NonNull
                    @Override
                    public new_asnmnt_ct parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new new_asnmnt_ct(snapshot.child("subj").getValue().toString(),
                                snapshot.child("date").getValue().toString(),
                                snapshot.child("dtls").getValue().toString());
                    }
                }).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<new_asnmnt_ct,viewHolder>(firebaseRecyclerOptions) {
            @NonNull
            @Override
            public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.asnmnt_ct_list_view, parent, false);
                return new viewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull viewHolder viewHolder, int i, @NonNull new_asnmnt_ct newasnmntct) {
                viewHolder.setDate(newasnmntct.getDate());
                viewHolder.setSubj(newasnmntct.getSubj());
                viewHolder.setTaskName(newasnmntct.getTask_name());

            }

        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);*/

    }

    public void show_add_new_asnmntdlg(){
        AlertDialog.Builder alertbuilder=new AlertDialog.Builder(activities.this);
        LayoutInflater inflater =(LayoutInflater) this.getSystemService(activities.this.LAYOUT_INFLATER_SERVICE);
        View dialogeView = inflater.inflate( R.layout.add_new_asnmnt, null );
        alertbuilder.setView(dialogeView);

        calenderTxtview =(TextView) dialogeView.findViewById(R.id.caleder_date);
        final FloatingActionButton add_btn = dialogeView.findViewById(R.id.add_fab);
        final EditText as=(EditText)dialogeView.findViewById(R.id.asnmnt_subj) ;
        //final EditText sd=(EditText)dialogeView.findViewById(R.id.sub_date) ;
        final EditText dt=(EditText)dialogeView.findViewById(R.id.asnmnt_dtls) ;
        final Spinner sp= (Spinner)dialogeView.findViewById(R.id.task_spinner);
        calenderTxtview.setText("Click here to select date");
        //ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(activities.this,R.array.asnmt_ct_arr,android.R.)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.asnmt_ct_arr, R.layout.custom_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        alertbuilder.setTitle("");
        final AlertDialog ad =alertbuilder.create();
        ad.show();



        calenderTxtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activities.this, Calender.class));
            }
        });

       add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bacchader Algorithm
                //String serialK=calenderTxtview.getText().toString();
                String date=calenderTxtview.getText().toString();
                int sKey =0;
                String dateT="Click here to select date";
                if(calenderTxtview.getText().toString()!= dateT) {
                     sKey = algoForSerialKey(date);
                }

                String subj= as.getText().toString();

                String dtls =dt.getText().toString();
                String t=sp.getSelectedItem().toString();
                if(!TextUtils.isEmpty(subj.trim()) && !TextUtils.isEmpty(date.trim()) && !TextUtils.isEmpty(dtls.trim()) && calenderTxtview.getText().toString()!= dateT){
                    new_asnmnt_ct obj = new new_asnmnt_ct();
                    obj.setSubj(subj);
                    obj.setDate(date);
                    obj.setDtls(dtls);
                    obj.setTask_name(t);
                    obj.setSerial_key(sKey);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users_post").child(Global_variable.batchForpost).child("new_asnmnt_ct");
                    databaseReference.push().setValue(obj);
                    Toast.makeText(activities.this,"Added",Toast.LENGTH_SHORT).show();
                    ad.dismiss();
                }
                else {
                    Toast.makeText(activities.this,"Empty Field !",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private int algoForSerialKey(String s) {
        String[] month={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        Map<String, Integer> map=new HashMap<String, Integer>();
        map.put(month[0],new Integer(32));
        map.put(month[1],new Integer(64));
        map.put(month[2],new Integer(96));
        map.put(month[3],new Integer(128));
        map.put(month[4],new Integer(160));
        map.put(month[5],new Integer(192));
        map.put(month[6],new Integer(224));
        map.put(month[7],new Integer(256));
        map.put(month[8],new Integer(288));
        map.put(month[9],new Integer(320));
        map.put(month[10],new Integer(352));
        map.put(month[11],new Integer(384));
        String serialK=s;
        int n=serialK.length()-1;
        String mon= serialK.substring(n-2,n+1);
        String date=serialK.substring(0,n-3);

        int d=Integer.parseInt(date);

        int sKey = d+map.get(mon);
        return sKey;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //firebaseRecyclerAdapter.stopListening();
    }




    public class viewHolder extends RecyclerView.ViewHolder{
        public TextView subj,taskName,date;
        public RelativeLayout relativeLayout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.asnmnt_ct_ro);
            subj=(TextView)itemView.findViewById(R.id.subject_n);
            taskName=(TextView) itemView.findViewById(R.id.task_n);
            date=(TextView)itemView.findViewById(R.id.tak_date);
        }


        public void setSubj(String subj) {
            this.subj.setText(subj);

        }

        public void setTaskName(String taskName) {
            this.taskName.setText(taskName);
        }

        public void setDate(String date) {
            this.date.setText(date);
        }
    }



}
