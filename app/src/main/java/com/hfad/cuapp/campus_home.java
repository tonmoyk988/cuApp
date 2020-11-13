package com.hfad.cuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class campus_home extends AppCompatActivity implements OnMapReadyCallback{

    private static final String TAG = "campus_home";
    private static final int ERROR_DIALOG_ReQUEST =9002;
    private boolean mpermissiong=false;
    private static final int LOCATioN_PERMISSION_GRANT=12345;
    private GoogleMap gMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final float DeFAuLT_Zoom =15f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_home);

        if(isServiceOk()){

            SupportMapFragment mapFragment =SupportMapFragment.newInstance();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.mapLayout,mapFragment);
            fragmentTransaction.commit();
            mapFragment.getMapAsync(campus_home.this);
            geLoccationPermission();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this,"map is ready",Toast.LENGTH_SHORT).show();
        gMap=googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if(mpermissiong){
            getDeviceLocation();
        }

    }



    //Get the device location
    private void getDeviceLocation(){
        Log.d(TAG,"Getting the device current location");
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        try {
            if(mpermissiong){
                final Task<Location> location=fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"onComplete:found location");
                            Location currentLocation =(Location) task.getResult();
                            LatLng latLng = new LatLng(22.4701,91.7939);
                            moveCamera(latLng,DeFAuLT_Zoom);
                            //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,DeFAuLT_Zoom));
                            //gMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentLocation,0));
                        }
                        else{
                            Log.d(TAG,"nClomplete: current location is null");
                            Toast.makeText(campus_home.this,"Unable to get curent location",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }catch (SecurityException e){

        }
    }


    //moveCamera method

    public void moveCamera(LatLng latLng,float zoom){
        Log.d(TAG,"moving camera to lat"+ latLng.latitude+"lng"+latLng.longitude);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

    }



//Get the result of permission of the location
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mpermissiong=false;
        switch (requestCode){
            case (LOCATioN_PERMISSION_GRANT):
                if(grantResults.length>0){
                    for(int i=0;i<grantResults.length;i++){
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                        mpermissiong=false;
                        return;
                    }
                    mpermissiong=true;
                }
        }
    }




    private void geLoccationPermission(){
        String  perissions[] ={Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),perissions[1])== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),perissions[0])== PackageManager.PERMISSION_GRANTED){
                mpermissiong =true;
            }
            else {
                ActivityCompat.requestPermissions(this,perissions,LOCATioN_PERMISSION_GRANT);

            }
        }
        else {
            ActivityCompat.requestPermissions(this,perissions,LOCATioN_PERMISSION_GRANT);
        }


    }




    public boolean isServiceOk(){
        Log.d(TAG,"isServiceOk:chcking google service version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(campus_home.this);
        if(available== ConnectionResult.SUCCESS){
            Log.d(TAG,"Google Play Service is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG,"Error occured but we can solve it");
            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(campus_home.this,available,ERROR_DIALOG_ReQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this,"You cant make map reuest",Toast.LENGTH_SHORT).show();

        }
        return false;
    }

}
