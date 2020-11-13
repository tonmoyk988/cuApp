package com.hfad.cuapp;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import android.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hfad.cuapp.Firebase.authentication.firebaseauthui;
import com.hfad.cuapp.model.dept_main_page;
import com.hfad.cuapp.viewModel.Global_variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;



    //RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<rept_home_page_activity_list> activity_lists;
    private ImageView imageView;

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthstatelistener;
    private DatabaseReference dUsers;
    private FirebaseAnalytics mFirebaseAnalytics;

    public static String batchForpost;

    public MainActivity(){
        dept_main_page lis= new dept_main_page();
        activity_lists = lis.getlist();
            setBatchForpost();


    }


    private ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setBatchForpost();
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            setBatchForpost();
            //dUsers= FirebaseDatabase.getInstance().getReference().child("Users");
            //dUsers.child("condition").setValue("hi");
            //dUsers.keepSynced(true);
        }

        //FirebaseAuthentication
        mAuth=FirebaseAuth.getInstance();
        mAuthstatelistener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){

                    startActivity(new Intent(MainActivity.this, firebaseauthui.class));
                    finish();
                    /*
                    Intent intent =new Intent(MainActivity.this,loginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/
                }
                if(firebaseAuth.getCurrentUser()!=null){
                    setBatchForpost();
                }
            }
        };





        toolbar = (Toolbar)findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dept. Of CSE");


        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle =new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.drawer_opem,R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        NavigationView navi=(NavigationView)findViewById(R.id.navmenu);
        navi.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);





        recyclerView = (RecyclerView) findViewById(R.id.homepage_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new myAdapter(activity_lists,this);
        recyclerView.setAdapter(adapter);


    }

    //For passing the batch number to the dept_update ativity
    public static void setBatchForpost() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            DatabaseReference batch_ref = FirebaseDatabase.getInstance().getReference("User_info").child(FirebaseAuth.getInstance().getUid());
            batch_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    batchForpost=(String) dataSnapshot.child("batch").getValue();
                    Global_variable.batchForpost=(String) dataSnapshot.child("batch").getValue();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthstatelistener);
        //setBatchForpost();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.dept_home_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.go_to_campus:
                Intent intent = new Intent(this, campus_home.class);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                mAuth.signOut();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void checkUsers(){
        final String uemail=mAuth.getCurrentUser().getUid();
        dUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(uemail)){
                    //Intent intent =new Intent(MainActivity.this,);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
