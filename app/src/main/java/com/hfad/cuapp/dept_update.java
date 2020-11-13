package com.hfad.cuapp;

import android.content.Intent;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.cuapp.InterFaces.RecycleViewOnItemClick;
import com.hfad.cuapp.model.Posts;
import com.hfad.cuapp.viewModel.Global_variable;
import com.hfad.cuapp.viewModel.class_update_adapter;
//import android.v7.widget.Toolbar;

public class dept_update extends AppCompatActivity {
    private Toolbar toolbar;


    String id,usernamvevalue;
    EditText uptop,up;
    ImageView add,like,dislike,f_menu;
    TextView user_name;
    updatepost_into_database intodata;
    DatabaseReference reff;
    ArrayList<Posts> posts;
    ArrayList<String> post_database_key_list;
    class_update_adapter adapter;
    RecyclerView rv;
    public  String b ;
    DatabaseReference batch_ref = FirebaseDatabase.getInstance().getReference("User_info").child(FirebaseAuth.getInstance().getUid());





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_update);



        toolbar = (Toolbar) findViewById(R.id.update_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Updates");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        user_name=(TextView)findViewById(R.id.poster_name);
        uptop = (EditText)findViewById(R.id.not_topic);
        up = (EditText)findViewById(R.id.update);
        add = (ImageView) findViewById(R.id.add);
        like=(ImageView) findViewById(R.id.like);
        dislike=(ImageView) findViewById(R.id.dislike);




        intodata=new updatepost_into_database();


        //Get the bactch of the current User
        batch_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //final String s;
                b=dataSnapshot.child("batch").getValue().toString();
                usernamvevalue=dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                reff= FirebaseDatabase.getInstance().getReference("Users_post");

                String uptop1 = uptop.getText().toString();
                String update = up.getText().toString();
                String s1=uptop.getText().toString().trim();
                String s2= up.getText().toString().trim();
                //String time = Calendar.getInstance().getTime().toString();
                final SimpleDateFormat simpleDateFormat= new SimpleDateFormat();
                String time=simpleDateFormat.format(new Date());
                char[] c=time.toCharArray();
                int i=0;
                for(char ch:c){
                    i++;
                    if((int)ch==32){break;}
                }
                String timepart=time.substring(i,time.length());
                Calendar calendar = Calendar.getInstance();
                String temp = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
                char[] d =temp.toCharArray();
                i=0;
                for(char ch:d){
                    i++;
                    if((int)ch==44){break;}
                }
                String postdate = temp.substring(0,i-1);//time.substring(0,i-1);
                timepart =timepart+","+postdate;
                if(!TextUtils.isEmpty(s1)&&!TextUtils.isEmpty(s2)){
                    intodata.setUpdatetopic(uptop1);
                    intodata.setUpdate(update);
                    intodata.setU_name(usernamvevalue);
                    intodata.setPost_time(timepart);
                    intodata.setUid(FirebaseAuth.getInstance().getUid());
                    //intodata.setLikes("");
                    //intodata.setDisLikes("");
                    reff.child(b).child("posts").push().setValue(intodata);
                    Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(dept_update.this,"Empty Field !",Toast.LENGTH_SHORT).show();
                }

                uptop.setText("");
                up.setText("");
            }
        });


        rv =(RecyclerView) findViewById(R.id.postlist);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        posts= new ArrayList<Posts>();
        post_database_key_list=new ArrayList<String>();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users_post").child(Global_variable.batchForpost).child("posts");

        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                post_database_key_list.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Posts p = data.getValue(Posts.class);
                    posts.add(p);

                    String pdkl = data.getKey().toString();
                    post_database_key_list.add(pdkl);

                }
                Collections.reverse(posts);
                Collections.reverse(post_database_key_list);

                adapter = new class_update_adapter(dept_update.this,posts,post_database_key_list);
                rv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

/*
    //PopUPmenu For editing or deleting post
    public void popUpMenu(View v){
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.dept_update_popup_menu, popup.getMenu());
        popup.show();

        //PopupMenu popupMenu  = new PopupMenu(this,v);
        //popupMenu.setOnMenuItemClickListener(this);
        //popupMenu.inflate(R.menu.dept_update_popup_menu);
        //popupMenu.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.edit:
                Toast.makeText(this,"Edit was clicked in pos "+getitempos,Toast.LENGTH_SHORT).show();

                return true;
            case R.id.delete:
                Toast.makeText(this,"Delete was clicked in pos "+getitempos,Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

*/

    @Override
    protected void onStart() {
        super.onStart();

/*
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
*/
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
        Intent intent =new Intent(dept_update.this, MainActivity.class);
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
