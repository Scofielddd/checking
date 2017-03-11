package com.cakir.checking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import checkingapi.Client;
import checkingapi.ClientMethods;
import checkingapi.model.Checkin;
import checkingapi.model.Place;
import checkingapi.model.Question;
import checkingapi.model.User;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class CheckinActivity extends AppCompatActivity {
    private View progressView;
    private ListView listView;
    private ArrayList<HashMap<String, String>> placeList;
    private ListAdapter adapter;
    ClientMethods clientMethods;
    SweetAlertDialog sweet;
    private long Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        progressView=findViewById(R.id.checkin_progress);
        listView=(ListView)findViewById(R.id.placelist);

        placeList = new ArrayList<HashMap<String, String>>();
        adapter = new SimpleAdapter(
                CheckinActivity.this, placeList,
                R.layout.place_item, new String[]{"Name", "Id"}, new int[]{R.id.plName, R.id.plId});
        listView.setAdapter(adapter);

        clientMethods = new ClientMethods() {
            @Override public void Checkins(ArrayList<Checkin> checkins) {}
            @Override public void Login(User user) {}
            @Override public void Register(boolean result) {}
            @Override public void Username(boolean result) {}
            @Override public void Email(boolean result) {}

            @Override
            public void Location(ArrayList<Place> places) {
                if (places!=null)
                {
                    for(int i=0; i<places.size();i++)
                    {
                        HashMap<String,String> checkin=new HashMap<String, String>();
                        checkin.put("Name",places.get(i).Name);
                        checkin.put("Id",String.valueOf(places.get(i).Id));
                        placeList.add(checkin);
                    }
                }
                ((BaseAdapter)adapter).notifyDataSetChanged();
            }

            @Override
            public void Do(final Question question) {
                // Dismiss the progress dialog
                sweet.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                sweet.showContentText(false);
                sweet.setTitleText("Checking Başarılı");
                sweet.setConfirmText("TAMAM");

                sweet.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweet.dismiss();
                        CheckinActivity.this.finish();
                        Intent intent=new Intent(CheckinActivity.this, QuestionActivity.class);
                        intent.putExtra("CheckinId",question.CheckinId);
                        intent.putExtra("Quest",question.Quest);
                        intent.putExtra("A",question.A);
                        intent.putExtra("B",question.B);
                        intent.putExtra("C",question.C);
                        intent.putExtra("D",question.D);
                        intent.putExtra("PlaceId",Id);
                        startActivity(intent);
                    }

                });
            }


            @Override
            public void Answer(boolean result) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {

                sweet = new SweetAlertDialog(CheckinActivity.this,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                sweet.setTitleText("Emin Misin ? ");
                sweet.setContentText("Onaylarsan Check-in Yapıcaksın");
                sweet.setConfirmText("Evet");
                sweet.setCancelText("Hayır");
                sweet.setCustomImage(R.drawable.check_logo);

                sweet.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });

                sweet.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sDialog) {
                        sDialog.setTitleText("");
                        sDialog.showCancelButton(false);
                        sDialog.setContentText("CHECKING YAPILIYOR").changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                        sDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));

                        Id=Long.parseLong(((TextView)view.findViewById(R.id.plId)).getText().toString());
                        Client.Do(Id,clientMethods);

                    }
                })
                        .show();
            }
        });

        Bundle extras = getIntent().getExtras();
        double x = extras.getDouble("Latitude");
        double y = extras.getDouble("Longitude");
        //Client.Location(x,y);
        Client.Location(0,0,clientMethods);

    }

}
