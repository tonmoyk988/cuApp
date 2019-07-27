package com.hfad.cuapp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
//import android.v7.widget.Toolbar;

public class dept_update extends AppCompatActivity {
    private Toolbar toolbar;


    String id,usernamvevalue;
    EditText uptop,up;
    ImageView add,like,dislike;
    TextView user_name;
    updatepost_into_database intodata;
    DatabaseReference reff;
    RecyclerView recyclerView;
    List<Posts> posts;
    Posts p;
    ArrayList<String> nudates;
    private FirebaseAuth mAuth;
    private DatabaseReference dReff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_update);

        toolbar = (Toolbar) findViewById(R.id.update_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Updates");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth=FirebaseAuth.getInstance();
        dReff=FirebaseDatabase.getInstance().getReference();

        user_name=(TextView)findViewById(R.id.poster_name);
        uptop = (EditText)findViewById(R.id.not_topic);
        up = (EditText)findViewById(R.id.update);
        add = (ImageView) findViewById(R.id.add);
        like=(ImageView) findViewById(R.id.like);
        dislike=(ImageView) findViewById(R.id.dislike);


        reff= FirebaseDatabase.getInstance().getReference().child("Post");
        intodata=new updatepost_into_database();



        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String uptop1 = uptop.getText().toString();
                String update = up.getText().toString();
                String s1=uptop.getText().toString().trim();
                String s2= up.getText().toString().trim();
                if(!TextUtils.isEmpty(s1)&&!TextUtils.isEmpty(s2)){
                    intodata.setUpdatetopic(uptop1);
                    intodata.setUpdate(update);
                    intodata.setU_name(usernamvevalue);
                    reff.push().setValue(intodata);
                    Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(dept_update.this,"Empty Field !",Toast.LENGTH_SHORT).show();
                }

                uptop.setText("");
                up.setText("");
            }
        });





        //Keep synced data locally
        reff.keepSynced(true);

        posts=new ArrayList<>();



        recyclerView =(RecyclerView)findViewById(R.id.postlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*UpdateAdapter adapter = new UpdateAdapter(posts,this);
        recyclerView.setAdapter(adapter);*/


    }



    private void likehandler() {
    }

    public void setUsername() {

        String userid=mAuth.getUid();
        dReff=dReff.child("Users").child(userid);

        dReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usernamvevalue=dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        setUsername();
        FirebaseRecyclerAdapter<Posts,postviewHolder> recyclerAdapter =new FirebaseRecyclerAdapter<Posts, postviewHolder>(
                Posts.class,
                R.layout.dept_update,
                postviewHolder.class,
                reff) {
            @Override
            protected void populateViewHolder(postviewHolder viewHolder, Posts model, int position) {

                viewHolder.setupdatetop(model.getupdatetopic());
                viewHolder.setupdate(model.getupdate());
                viewHolder.setusername(model.getU_name());
            }
        };
        recyclerView.setAdapter(recyclerAdapter);

    }




    public static class postviewHolder extends RecyclerView.ViewHolder{
        TextView t1;
        TextView t2;
        TextView t3;
        View mview;
        public postviewHolder(View itemView){
            super(itemView);
            mview=itemView;
        }

        public void setupdatetop(String s){
            t1 = (TextView) mview.findViewById(R.id.update_heading);
            t1.setText(s);
        }

        public void setupdate(String s2){
            t2 = (TextView) mview.findViewById(R.id.update_status);
            t2.setText(s2);
        }
        public void setusername(String s3){
            t3 = (TextView) mview.findViewById(R.id.poster_name);
            t3.setText(s3);
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle","onRestart invoked");
        Intent intent =new Intent(dept_update.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    //ALL OF this
    //To Prevent Crashesh
    //After Screen goes
    //Off

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause invoked");
        Intent intent =new Intent(dept_update.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle","onStop invoked");
        Intent intent =new Intent(dept_update.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle","onDestroy invoked");
        Intent intent =new Intent(dept_update.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
