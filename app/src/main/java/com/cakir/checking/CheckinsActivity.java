package com.cakir.checking;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;

import checkingapi.Client;
import checkingapi.ClientMethods;
import checkingapi.model.Checkin;
import checkingapi.model.Place;
import checkingapi.model.Question;
import checkingapi.model.User;

public class CheckinsActivity extends AppCompatActivity implements ClientMethods, AbsListView.OnScrollListener{
    private int Page=1;
    private int preLast;
    private View ListProgress;
    private ListAdapter adapter;
    private ArrayList<HashMap<String,String>> list;
    private ListView listView;
    private SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkins);

        ListProgress = findViewById(R.id.list_progress);
        listView = (ListView)findViewById(R.id.list);
        list = new ArrayList<HashMap<String, String>>();
        adapter = new SimpleAdapter(
                CheckinsActivity.this, list,
                R.layout.list_item, new String[]{"Placename","Dt","Point"}, new int[]{R.id.placename,
                R.id.dt, R.id.point});
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        ListProgress.setVisibility(View.VISIBLE);
        Client.Checkins(1,this);
        df.setTimeZone(TimeZone.getTimeZone("GMT+6"));
    }

    @Override
    public void Checkins(ArrayList<Checkin> checkins) {
        if (checkins!=null) {
            for (int i = 0; i < checkins.size(); i++) {
                HashMap<String, String> checkin = new HashMap<String, String>();
                checkin.put("Placename", checkins.get(i).Placename);

                checkin.put("Dt", df.format(checkins.get(i).Dt));
                checkin.put("Point", Long.toString(checkins.get(i).Point));
                list.add(checkin);
            }
            ((BaseAdapter) adapter).notifyDataSetChanged();
        }
        ListProgress.setVisibility(View.GONE);
    }


    @Override
    public void onScroll(AbsListView lw, final int firstVisibleItem,
                         final int visibleItemCount, final int totalItemCount)
    {

        switch(lw.getId())
        {
            case R.id.list:

                // Make your calculation stuff here. You have all your
                // needed info from the parameters of this function.

                // Sample calculation to determine if the last
                // item is fully visible.
                final int lastItem = firstVisibleItem + visibleItemCount;

                if(lastItem == totalItemCount)
                {
                    if(preLast!=lastItem)
                    {
                        //to avoid multiple calls for last item
                        Log.d("Last", "Last");
                        Page++;
                        ListProgress.setVisibility(View.VISIBLE);
                        Client.Checkins(Page, this);
                        preLast = lastItem;
                    }
                }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState){}

    @Override
    public void Login(User user) {

    }

    @Override
    public void Register(boolean result) {

    }

    @Override
    public void Username(boolean result) {

    }

    @Override
    public void Email(boolean result) {

    }

    @Override
    public void Location(ArrayList<Place> places) {

    }

    @Override
    public void Do(Question question) {

    }

    @Override
    public void Answer(boolean result) {

    }
}
