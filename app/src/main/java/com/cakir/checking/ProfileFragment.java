package com.cakir.checking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import checkingapi.Client;

/**
 * Created by ahmet on 3.11.2016.
 */

public class ProfileFragment extends Fragment {

    Button btnCheckins;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profil_x, container, false);
        return rootView;
    }

    @Override
    public void onStart() {

        if (Client.user!=null) {
            ((TextView) getActivity().findViewById(R.id.profileTextName)).setText(Client.user.Name + " " + Client.user.Surname);
            ((TextView) getActivity().findViewById(R.id.profileTextUsername)).setText(Client.user.Username);
            ((TextView) getActivity().findViewById(R.id.profileTextPoint)).setText(Long.toString(Client.user.Point));
        }

        btnCheckins = (Button) this.getActivity().findViewById(R.id.btnCheckins);
        btnCheckins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileFragment.this.getContext(), CheckinsActivity.class));
            }
        });

        super.onStart();
    }
}
