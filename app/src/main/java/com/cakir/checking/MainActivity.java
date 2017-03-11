package com.cakir.checking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.w3c.dom.Text;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import checkingapi.Client;
import checkingapi.ClientMethods;
import checkingapi.model.Checkin;
import checkingapi.model.Place;
import checkingapi.model.Question;
import checkingapi.model.User;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    ProfileFragment profileFragment = new ProfileFragment();
    CheckingFragment checkingFragment = new CheckingFragment();
    CampaignFragment campaignFragment = new CampaignFragment();

    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Need login?
        if (Client.user == null) {
            MainActivity.this.finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            return;
        }

        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setActiveTabColor(getResources().getColor(R.color.colorAccent));
        bottomBar.setDefaultTabPosition(1);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLocation();
                if(mLastLocation!=null) {
                    Intent intent = new Intent(MainActivity.this, CheckinActivity.class);
                    intent.putExtra("Latitude",mLastLocation.getLatitude());
                    intent.putExtra("Longitude",mLastLocation.getLongitude());
                    startActivity(intent);
                } else
                {
                    Snackbar.make(view,"Konum alınamadı",Snackbar.LENGTH_LONG).show();
                }
            }
        });
*/

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("BIRINCI","SCROLLED");
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:bottomBar.selectTabWithId(R.id.tab_favorites);break;
                    case 1:bottomBar.selectTabWithId(R.id.tab_nearby);break;
                    case 2:bottomBar.selectTabWithId(R.id.tab_friends);break;
                }
                Log.e("IKINCI","SELECTED");

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("UCUNCU","SCROLL STATE");
            }
        });

        //////////////////////////////////////////////////////////////////////

        /*
        * Burada alt bar ile ekran fragmentları arasındaki baglantıyı sağladık
        *
        * */
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_favorites) {
                    mViewPager.setCurrentItem(0);
                }
                else if (tabId == R.id.tab_nearby){
                    mViewPager.setCurrentItem(1);
                }
                else{
                    mViewPager.setCurrentItem(2);
                }
            }
        });

        //////////////////////////////////////////////////////


        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {

            }
        });


        BottomBarTab nearby = bottomBar.getTabWithId(R.id.tab_nearby);


        //burada bulunan mekan sayısı belirtebiliyoruz
        nearby.setBadgeCount(5);
        nearby.removeBadge();

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return campaignFragment;
                case 1:
                    return checkingFragment;
                case 2:
                    return profileFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
