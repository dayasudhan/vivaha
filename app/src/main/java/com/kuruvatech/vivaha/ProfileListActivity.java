package com.kuruvatech.vivaha;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
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

    private static final String TAG_ID = "id";
    private static final String TAG_ID2 = "_id";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_OCCUPATION = "occupation";
    private static final String TAG_EDUCATION = "education";
    private static final String TAG_SUMMARY = "summary";
    private static final String TAG_COMMUNNITY = "community";
    private static final String TAG_MOTHER_TONGUE = "mothertongue";
    private static final String TAG_INCOME = "income";
    private static final String TAG_GOTHRA = "gothra";
    private static final String TAG_STAR = "star";
    private static final String TAG_RASHI = "rashi";
    private static final String TAG_HEIGHT = "height";
    private static final String TAG_WEIGHT = "weight";
    private static final String TAG_ORIGIN = "origin";
    private static final String TAG_DOB = "dob";
    private static final String TAG_AGE = "age";
    private static final String TAG_LOGO = "logo";
    private static final String TAG_PROFILE_LOGO = "profileLogo";
    private static final String TAG_MOTHER = "mother";
    private static final String TAG_FATHER = "father";
    private static final String TAG_ADDRESSLINE1 = "addressLine1";
    private static final String TAG_ADDRESSLINE2 = "addressLine2";
    private static final String TAG_STREET = "street";
    private static final String TAG_LANDMARK = "LandMark";
    private static final String TAG_CITY = "city";

    String selectedAge ,selectedGender, selectedCast, selectedMothertongue;
    SharedPreferences pref;
    ArrayList<Profile> profileList;
    ArrayList<Profile> profileListorgnl;
    String vendor_email;
    ProfileListAdapter adapter;
    JSONArray orderJarray;
    View rootview;
    ListView listView;
    int minage = 0,maxage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilelist);
        Mint.initAndStartSession(this, "49d903c2");
        listView = (ListView) findViewById(R.id.listView_profilelist);
        Intent i = getIntent();
        selectedAge = new String(i.getStringExtra("age"));
        selectedGender = new String(i.getStringExtra("gender"));
        selectedCast = new String(i.getStringExtra("community"));
        selectedMothertongue = new String(i.getStringExtra("mothertongue"));

//        if(selectedAge == "all")
//        {
//            minage = 0;
//            maxage = 100;
//        }
//        else if(selectedAge == "18-22")
//        {
//            minage = 0;
//            maxage = 100;
//        }
//        else if(selectedAge == "22-25")
//        {
//            minage = 22;
//            maxage = 25;
//        }
//        else if(selectedAge == "25-28")
//        {
//            minage = 25;
//            maxage = 28;
//        }
//        else if(selectedAge == "28-30")
//        {
//            minage = 28;
//            maxage = 30;
//        }
//        else if(selectedAge == "30-32")
//        {
//            minage = 30;
//            maxage = 32;
//        }
//        else if(selectedAge == "33-35")
//        {
//            minage = 33;
//            maxage = 35;
//        }
//        else if(selectedAge == "35 and above")
//        {
//            minage = 35;
//            maxage = 100;
//        }
        pref = getSharedPreferences("Khaanavali", 0);
        vendor_email = pref.getString("email", "name");
        Gson gson = new Gson();

        profileList = new ArrayList<Profile>();
        profileListorgnl = new ArrayList<Profile>();
        setActionBarTitle("Profile List");
        pref = getSharedPreferences("Khaanavali", 0);
        vendor_email = pref.getString("email", "name");

        bindView();

        adapter = new ProfileListAdapter(getApplicationContext(), R.layout.profilelist_item, profileList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub
                //          Toast.makeText(getActivity().getApplicationContext(), orderList.get(position).getCustomer().getName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ProfileListActivity.this, ProfileDetail.class);
                Gson gson = new Gson();
                String order = gson.toJson(profileList);
                intent.putExtra("profile", order);
                intent.putExtra("position", String.valueOf(position));
                startActivity(intent);
            }
        });


    }
    public void setActionBarTitle(String title) {
      //  getSupportActionBar().setTitle(title);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void bindView() {
        profileList.clear();
        String order_url = Constants.GET_PROFILE_INFO;
        order_url= order_url.concat(vendor_email);
        new JSONAsyncTask(listView).execute(order_url);
    }
    public  class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        ListView mListView;

        public  JSONAsyncTask(ListView gview)
        {
            this.mListView=gview;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ProfileListActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();

                    String data = EntityUtils.toString(entity);
                    JSONArray jarray = new JSONArray(data);
                    boolean isTodayOrder = false;
                    for (int i = jarray.length() - 1 ; i >= 0; i--) {
                        JSONObject object = jarray.getJSONObject(i);
                        isTodayOrder = false;

                        Profile profile = new Profile();
                        if (object.has(TAG_ID)) {
                            profile.setId(object.getString(TAG_ID));
                        }
                        if (object.has(TAG_NAME)) {
                            profile.setName(object.getString(TAG_NAME));
                        }
                        if (object.has(TAG_PHONE)) {
                            profile.setPhone(object.getString(TAG_PHONE));
                        }
                        if (object.has(TAG_EMAIL)) {
                            profile.setEmail(object.getString(TAG_EMAIL));
                        }
                        if (object.has(TAG_GENDER)) {
                            profile.setGender(object.getString(TAG_GENDER));
                        }if (object.has(TAG_OCCUPATION)) {
                            profile.setOccupation(object.getString(TAG_OCCUPATION));
                        }if (object.has(TAG_EDUCATION)) {
                            profile.setEducation(object.getString(TAG_EDUCATION));
                        }if (object.has(TAG_SUMMARY)) {
                            profile.setSummary(object.getString(TAG_SUMMARY));
                        }if (object.has(TAG_COMMUNNITY)) {
                            profile.setCommunity(object.getString(TAG_COMMUNNITY));
                        }if (object.has(TAG_MOTHER_TONGUE)) {
                            profile.setMothertongue(object.getString(TAG_MOTHER_TONGUE));
                        }if (object.has(TAG_INCOME)) {
                            profile.setIncome(object.getString(TAG_INCOME));
                        }if (object.has(TAG_GOTHRA)) {
                            profile.setGothra(object.getString(TAG_GOTHRA));
                        }
                        if (object.has(TAG_STAR)) {
                            profile.setStar(object.getString(TAG_STAR));
                        }
                        if (object.has(TAG_RASHI)) {
                            profile.setRashi(object.getString(TAG_RASHI));
                        }
                        if (object.has(TAG_HEIGHT)) {
                            profile.setHeight(Float.parseFloat(object.getString(TAG_HEIGHT)));
                        }if (object.has(TAG_WEIGHT)) {
                            profile.setWeight(Float.parseFloat(object.getString(TAG_WEIGHT)));
                        }
                        if (object.has(TAG_ORIGIN)) {
                            profile.setOrigin(object.getString(TAG_ORIGIN));
                        }if (object.has(TAG_DOB)) {
                            profile.setDob(object.getString(TAG_DOB));
                        }if (object.has(TAG_AGE)) {
                            try {
                                int intValue = object.getInt(TAG_AGE);
                                profile.setAge(intValue);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }if (object.has(TAG_LOGO)) {
                            profile.setLogo(object.getString(TAG_LOGO));
                        }
                        if (object.has(TAG_PROFILE_LOGO)) {
                            profile.setProfileLogo(object.getString(TAG_PROFILE_LOGO));
                        }
                        if (object.has(TAG_FATHER)) {
                            JSONObject obj = object.getJSONObject(TAG_FATHER);
                            Parent parent = new Parent();
                            if (obj.has(TAG_NAME))
                                parent.setName(obj.getString(TAG_NAME));
                            if (obj.has(TAG_OCCUPATION))
                                parent.setOccupation(obj.getString(TAG_OCCUPATION));
                            if (obj.has(TAG_PHONE))
                                parent.setPhone(obj.getString(TAG_PHONE));
                            profile.setFather(parent);
                        }
                        if (object.has(TAG_MOTHER)) {
                            JSONObject obj = object.getJSONObject(TAG_MOTHER);
                            Parent parent = new Parent();
                            if (obj.has(TAG_NAME))
                                parent.setName(obj.getString(TAG_NAME));
                            if (obj.has(TAG_OCCUPATION))
                                parent.setOccupation(obj.getString(TAG_OCCUPATION));
                            if (obj.has(TAG_PHONE))
                                parent.setPhone(obj.getString(TAG_PHONE));
                            profile.setMother(parent);
                        }

                        if (object.has(TAG_ADDRESS)) {
                            JSONObject addrObj = object.getJSONObject(TAG_ADDRESS);
                            Address address = new Address();
                            if (addrObj.has(TAG_ADDRESSLINE1))
                                address.setAddressLine1(addrObj.getString(TAG_ADDRESSLINE1));
                            if (addrObj.has(TAG_ADDRESSLINE1))
                                address.setAddressLine2(addrObj.getString(TAG_ADDRESSLINE2));

                            if (addrObj.has(TAG_CITY))
                                address.setCity(addrObj.getString(TAG_CITY));
                            if (addrObj.has(TAG_LANDMARK))
                                address.setLandMark(addrObj.getString(TAG_LANDMARK));
                            if (addrObj.has(TAG_STREET))
                                address.setStreet(addrObj.getString(TAG_STREET));
                            profile.setAddress(address);
                        }
                        profileListorgnl.add(profile);
                        profileList.add(profile);
//                        if((selectedCast == profile.getCommunity() || selectedCast == "all") &&
//                                (selectedMothertongue == profile.getMothertongue() || selectedMothertongue == "all") &&
//                                (selectedGender == profile.getGender() || selectedGender == "all")&&
//                                (profile.getAge() >= minage && profile.getAge() <= maxage))
//                        {
//                            profileList.add(profile);
//                        }

                    }
                    return true;
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            if(result == true)
            {
                adapter.notifyDataSetChanged();
            }

            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }


    }
}
