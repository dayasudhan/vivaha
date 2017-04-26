package com.kuruvatech.vivaha;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.splunk.mint.Mint;

import com.kuruvatech.vivaha.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout layout;
    private DrawerLayout dLayout;
    SessionManager session;
    private boolean ishotelFragmentOpen;
    //private String notification;
    private void finishscreen() {
        this.finish();
    }


//    public String getNotification() {
//        return notification;
//    }
    private boolean isTodayMenuselected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Mint.initAndStartSession(this, "49d903c2");
        session = new SessionManager(getApplicationContext());
//        notification=getIntent().getStringExtra("notificationFragment");
        session.checkLogin();
        setContentView(R.layout.activity_main_nav);
        ishotelFragmentOpen = true;


        layout = (RelativeLayout) findViewById(R.id.layout);
        setNavigationDrawer();
        setToolBar();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(session.isKill)
        {
            session.isKill = false;
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public void setActionBarTitle(String title) {
    getSupportActionBar().setTitle(title);
    }
    private void setToolBar() {
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setTitleTextColor(getResources().getColor(R.color.parrotgreen6));
        tb.setSubtitleTextColor(getResources().getColor(R.color.parrotgreen6));
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("SHUBHA VIVAHA");
        }
    }

    private void setNavigationDrawer() {
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        isTodayMenuselected = true;
        transaction.replace(R.id.frame, new ProfileListFragment());
        transaction.commit();
        ishotelFragmentOpen = true;
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                Fragment frag = null;
                int itemId = menuItem.getItemId();

                if (itemId == R.id.navigation_order_today_list) {
                    isTodayMenuselected = true;
                    ishotelFragmentOpen = true;
                    //  isOtherFragmentOpen=false;
                    frag = new ProfileListFragment();
                }
                else if (itemId == R.id.navigation_settings) {
                    isTodayMenuselected = false;
                    ishotelFragmentOpen = true;
                    //isOtherFragmentOpen=false;
                    frag = new SearchConfigFragment();
                }
//                else if (itemId == R.id.navigation_menu) {
//                    //isOtherFragmentOpen=false;
//                    isTodayMenuselected = false;
//                    ishotelFragmentOpen = false;
//                    frag = new MenuFragment();
//                }
//                else if (itemId == R.id.navigation_about_me) {
//                    //isOtherFragmentOpen=true;
//                    ishotelFragmentOpen = false;
//                    frag = new AboutMeFragment();
//                }
//                else if (itemId == R.id.navigation_about_Khaanavali) {
//                    //isOtherFragmentOpen=true;
//                    ishotelFragmentOpen = false;
//                    frag = new AboutKhaanavali();
//                }
//                else if (itemId == R.id.navigation_settings) {
//                    ishotelFragmentOpen = false;
//                    //isOtherFragmentOpen=true;
//                    frag = new SettingsFragment();
//                }
                else if (itemId == R.id.navigation_logout) {
                    //isOtherFragmentOpen=true;
                    frag = new LogoutFragment();
                    ishotelFragmentOpen = false;
                }

                if (frag != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.frame, frag);
                    transaction.commit();
                    dLayout.closeDrawers();
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        String btnName = null;

        switch(itemId) {

            case android.R.id.home: {
                dLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return false;
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }



        if (dLayout.isDrawerOpen(GravityCompat.START)) {
            dLayout.closeDrawer(GravityCompat.START);
        }
        else
        if (ishotelFragmentOpen == false) {
            dLayout.openDrawer(GravityCompat.START);
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                   // onBack=true;
                }
            }, 2000);

        }
    }
}
