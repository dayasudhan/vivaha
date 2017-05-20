package com.kuruvatech.vivaha;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kuruvatech.vivaha.model.Address;
import com.kuruvatech.vivaha.model.Parent;
import com.kuruvatech.vivaha.model.Profile;
import com.kuruvatech.vivaha.model.ProfileListAdapter;
import com.kuruvatech.vivaha.utils.Constants;
import com.splunk.mint.Mint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by gagan on 4/13/2017.
 */
public class ProfileListActivity extends AppCompatActivity {


    SharedPreferences pref;
    ArrayList<Profile> profileList;
    String vendor_email;
    ProfileListAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilelist);
        Mint.initAndStartSession(this, "49d903c2");
        listView = (ListView) findViewById(R.id.listView_profilelist);
        profileList = new ArrayList<Profile>();
        Intent i = getIntent();
        Gson gson = new Gson();
        TypeToken<ArrayList<Profile>> token = new TypeToken<ArrayList<Profile>>() {};
        profileList = gson.fromJson(i.getStringExtra("profileList"), token.getType());

        pref = getSharedPreferences("Khaanavali", 0);
        vendor_email = pref.getString("email", "name");

        setToolBar();
        pref = getSharedPreferences("Khaanavali", 0);
        vendor_email = pref.getString("email", "name");


        adapter = new ProfileListAdapter(getApplicationContext(), R.layout.profilelist_item, profileList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                Intent intent = new Intent(ProfileListActivity.this, ProfileDetail.class);
                Gson gson = new Gson();
                String order = gson.toJson(profileList);
                intent.putExtra("profile", order);
                intent.putExtra("position", String.valueOf(position));
                startActivity(intent);
            }
        });


    }
    private void setToolBar() {
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(tb);
        tb.setTitleTextColor(getResources().getColor(R.color.titleTextColor));
        tb.setSubtitleTextColor(getResources().getColor(R.color.parrotgreen6));
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_action_back);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Profiles List");
        }
    }
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
