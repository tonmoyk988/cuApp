package com.hfad.cuapp.viewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.cuapp.InterFaces.RecycleViewOnItemClick;
import com.hfad.cuapp.MainActivity;
import com.hfad.cuapp.R;
import com.hfad.cuapp.model.Posts;
import com.hfad.cuapp.dept_update;
import com.hfad.cuapp.updatepost_into_database;

import java.util.ArrayList;

public class class_update_adapter extends RecyclerView.Adapter<class_update_adapter.MyAdapter> {

    static Context context;
    public static ArrayList<Posts> posts;
    public static ArrayList<String> postKeys;

    static String mAuth =FirebaseAuth.getInstance().getCurrentUser().getUid();
    static DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users_post").child(MainActivity.batchForpost);
    static DatabaseReference databaseReferenceLike= FirebaseDatabase.getInstance().getReference("Users_post").child(MainActivity.batchForpost).child("likes");
    static boolean isLiked;



    public class_update_adapter(Context context,ArrayList<Posts> posts,ArrayList<String> postKeys){
        this.context =context;
        this.posts=posts;
        this.postKeys=postKeys;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyAdapter(LayoutInflater.from(context).inflate(R.layout.dept_update,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter holder, final int position) {
        holder.poster.setText(posts.get(position).getU_name());
        holder.update_time.setText(posts.get(position).getPost_time());
        holder.update_topic.setText(posts.get(position).getupdatetopic());
        holder.update.setText(posts.get(position).getupdate());

/*        holder.thumbsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //likebuttonAcitvity(holder.adapterPos);
                //isLiked=true;
                //if(isLiked){holder.thumbsup.setImageResource(R.drawable.ic_thumb_up_blue_24px);isLiked=false;}
                //else {holder.thumbsup.setImageResource(R.drawable.ic_thumb_up_24px);isLiked=true;}
                isLiked =true;

                databaseReferenceLike.child(postKeys.get(position)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(isLiked){
                            if(dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid())){
                                databaseReferenceLike.child(postKeys.get(position)).child(mAuth).removeValue();
                                isLiked=false;
                            }
                            else {
                                databaseReferenceLike.child(postKeys.get(position)).child(mAuth).setValue("liked");
                                isLiked=false;
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });*/


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }



    public static class MyAdapter extends RecyclerView.ViewHolder{
        public TextView poster;
        public TextView update_time;
        public TextView update_topic;
        public TextView update;
        public ImageView float_menu;
        public ImageView thumbsup;
        int adapterPos;

        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            poster = (TextView) itemView.findViewById(R.id.poster_name);
            update_time= (TextView) itemView.findViewById(R.id.update_time);
            update_topic= (TextView) itemView.findViewById(R.id.update_heading);
            update= (TextView) itemView.findViewById(R.id.update_status);
            float_menu=(ImageView)itemView.findViewById(R.id.float_menu);
            thumbsup=(ImageView)itemView.findViewById(R.id.like);
            adapterPos=getAdapterPosition();

            //get the position of the item in Arrayist when an item is clicked in recycerview
            //set the poUpmenu
            float_menu.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int pos =getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        //Setting up the popUp menu
                        PopupMenu popup = new PopupMenu(view.getContext(), view);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.dept_update_popup_menu, popup.getMenu());
                        popup.setOnMenuItemClickListener(new MyOnMenuItemClickListener(pos));
                        popup.show();

                    }
                }
            });

            thumbsup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    thumbsup.setImageResource(R.drawable.ic_thumb_up_blue_24px);
                    isLiked =true;
                    final int position =getAdapterPosition();
                    databaseReferenceLike.child(postKeys.get(position)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(isLiked){
                                if(dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid())){
                                    databaseReferenceLike.child(postKeys.get(position)).child(mAuth).removeValue();
                                    isLiked=false;
                                }
                                else {
                                    databaseReferenceLike.child(postKeys.get(position)).child(mAuth).setValue("liked");
                                    isLiked=false;
                                }
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });




        }
    }



    //class for menu-item click on OptionMenu
    private static class MyOnMenuItemClickListener implements PopupMenu.OnMenuItemClickListener{
        static int pos;
        static Posts post;
        static String postkeys;
        public MyOnMenuItemClickListener(int pos) {
            this.pos=pos;
            this.post=posts.get(pos);
            this.postkeys=class_update_adapter.postKeys.get(pos);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.edit:
                    showUpdateDialogue(post);
                    //Toast.makeText(context,"Edit was clicked in pos "+pos,Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.delete:
                    delete(pos);
                    Toast.makeText(context,"Delete was clicked in pos "+pos,Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        }

        //Creating dialogue for update or delete data through this method
        public void showUpdateDialogue(Posts p){

            AlertDialog.Builder alertbuilder=new AlertDialog.Builder(context);
            LayoutInflater inflater =(LayoutInflater) class_update_adapter.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View dialogeView = inflater.inflate( R.layout.post_delete_update, null );
            alertbuilder.setView(dialogeView);

            EditText updatetopic = (EditText) dialogeView.findViewById(R.id.updating_post_heading);
            final EditText updatedetails =(EditText) dialogeView.findViewById(R.id.updating_post_status);
            Button updatebtn =(Button)dialogeView.findViewById(R.id.updateButton);
            updatetopic.setText(p.getupdatetopic());
            updatedetails.setText(p.getupdate());

            //Show the atert box
            alertbuilder.setTitle("Update Dialogue");
            AlertDialog ad =alertbuilder.create();
            ad.show();

            updatebtn.setOnClickListener(new myButtonClickListener(post,updatetopic,updatedetails,ad));
        }


        //inner class for passing the Posts p into onClick method and to compare if the
        //updated topic details is same as the old one
        static class myButtonClickListener implements View.OnClickListener {
            Posts p;
            int position= MyOnMenuItemClickListener.pos;
            EditText updatetopic,updatedetails;
            AlertDialog ad;
            static String postKeys =MyOnMenuItemClickListener.postkeys;
            myButtonClickListener(Posts p,EditText updatetopic,EditText updatedetails,AlertDialog ad){
                this.p=p;
                this.updatetopic=updatetopic;
                this.updatedetails=updatedetails;
                this.ad=ad;
            }

            @Override
            public void onClick(View view) {
                String newUpdate=updatedetails.getText().toString();
                String newTopic=updatetopic.getText().toString();
                if((newUpdate.trim()!=p.getupdate().trim() && newUpdate.trim()!="") || (newTopic!=p.getupdatetopic().trim()&&newTopic.trim()!="")){
                    //String s=MyOnMenuItemClickListener.po
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users_post").child(MainActivity.batchForpost).child("posts").child(class_update_adapter.postKeys.get(position));
                    databaseReference.child("update").setValue(newUpdate);
                    databaseReference.child("updatetopic").setValue(newTopic);
                    Toast.makeText(class_update_adapter.context,"Updated Successfully",Toast.LENGTH_SHORT).show();
                    ad.dismiss();
                }
                else {
                    Toast.makeText(class_update_adapter.context,"Enter updated Topic or Post",Toast.LENGTH_SHORT).show();
                }
            }
        }

        //Deleting Post method
        public void delete(int pos){
            final int position=pos;
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users_post").child(MainActivity.batchForpost).child("posts").child(class_update_adapter.postKeys.get(position));
                            databaseReference.removeValue();
                            DatabaseReference likedatabaseReference  = FirebaseDatabase.getInstance().getReference("Users_post").child(MainActivity.batchForpost).child("likes").child(class_update_adapter.postKeys.get(position));
                            likedatabaseReference.removeValue();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder ab = new AlertDialog.Builder(class_update_adapter.context);
            ab.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }
}
