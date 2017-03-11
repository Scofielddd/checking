package com.cakir.checking;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.roughike.bottombar.BottomBar;

import java.util.ArrayList;

/**
 * Created by ahmet on 3.11.2016.
 */

public class CheckingFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    RippleBackground rippleBackground;
    View.OnClickListener btnOnClick;
    ImageView button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.check_x, container, false);
        rippleBackground = (RippleBackground) rootView.findViewById(R.id.content);

        final Handler handler = new Handler();


        button = (ImageView)rootView.findViewById(R.id.centerImage);


        //CHECK BUTONUNA TIKLAMA ICIN LISTENER EKLİYORUZ

        /*async yapı altta kullanılabilir ??*/
        btnOnClick =new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setOnClickListener(null); //BUTONA 1KEZ TIKLAYINCA BIR DAHA TIKLANMAMASI ICIN LISTENERI SILIYORUM
                rippleBackground.startRippleAnimation(); //DALGA EFEKTINI CAGIRIYORUZ
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        foundDevice();
                    }
                }, 1000);  //BURADA YAZAN INTEGER DEGER DALGA EFEKTINDEN SONRA CIHAZ EFEKTI TETIKLENME SURESI
            }
        };
        button.setOnClickListener(btnOnClick);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        return rootView;
    }



    public void StopRippleEfekt() {
        rippleBackground.stopRippleAnimation();
    }


    public void foundDevice(){
        StopRippleEfekt();
        button.setOnClickListener(btnOnClick);
        updateLocation();
        if(mLastLocation!=null) {
            ((BottomBar) getActivity().findViewById(R.id.bottomBar)).selectTabWithId(R.id.tab_favorites);
            Intent intent = new Intent(CheckingFragment.this.getContext(), CheckinActivity.class);
            intent.putExtra("Latitude",mLastLocation.getLatitude());
            intent.putExtra("Longitude",mLastLocation.getLongitude());
            startActivity(intent);
        } else
        {
            Snackbar.make(CheckingFragment.this.getView(),"Konum alınamadı",Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void updateLocation()
    {
        if (ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Location","Permission Denied!");
            return;}
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation==null)
            Log.d("Location","null");
        else
            Log.d("Location","lat:"+mLastLocation.getLatitude()+"   long:"+mLastLocation.getLongitude());
    }
}

