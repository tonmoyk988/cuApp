package com.hfad.cuapp.Firebase.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.cuapp.MainActivity;
import com.hfad.cuapp.R;
import com.hfad.cuapp.new_user_setup;
import com.hfad.cuapp.viewModel.Global_variable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class firebaseauthui extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebaseauthui);
        startlogin();
    }


    public void startlogin(){

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAlwaysShowSignInMethodScreen(true)
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in



                FirebaseUserMetadata metadata = FirebaseAuth.getInstance().getCurrentUser().getMetadata();
                if(metadata.getCreationTimestamp()==metadata.getLastSignInTimestamp()){
                    startActivity(new Intent(firebaseauthui.this, new_user_setup.class));
                    finish();
                }
                else{

                    startActivity(new Intent(this,MainActivity.class));
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    finish();
                    // ...
                }
                finish();
            } else {
                if(response == null){
                    finish();
                }
                else if(resultCode == ErrorCodes.NO_NETWORK||resultCode==ErrorCodes.UNKNOWN_ERROR){
                    Toast.makeText(firebaseauthui.this,"Error LogIn!",Toast.LENGTH_SHORT).show();
                    //finish();
                    }
            }
        }
    }


}
