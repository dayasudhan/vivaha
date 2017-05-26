package com.kuruvatech.vivaha;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kuruvatech.vivaha.model.Address;
import com.kuruvatech.vivaha.model.Parent;
import com.kuruvatech.vivaha.model.Profile;
import com.kuruvatech.vivaha.utils.Constants;
import com.kuruvatech.vivaha.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

import static com.kuruvatech.vivaha.R.*;

/**
 * Created by gagan on 3/30/2017.
 */
public class SearchConfigFragment extends Fragment {

    View rootview;
    SessionManager session;
    String selectedAge ,selectedGender, selectedCast, selectedMothertongue;
    private Spinner mt_spinner,cm_spinner,age_spinner,gender_spinner;
    List<String> mtList;
    List<String> communitylist;
    ArrayList<Profile> profileList;
    ArrayList<Profile> profileListorgnl;
    Button searchButton;
    int minage = 0,maxage = 100;
    private static final String TAG_MOTHERTONGUE = "mothertongue";
    private static final String TAG_COMUNITY = "community";
    private static final String TAG_PROFILES = "profiles";
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(layout.settings, container, false);
        session = new SessionManager(getActivity().getApplicationContext());
//        TextView lblName = (TextView) rootview.findViewById(R.id.settingname);

        ((MainActivity) getActivity())
                .setActionBarTitle("Profiles Search");
//        HashMap<String, String> user = session.getUserDetails();
//        email = user.get(SessionManager.KEY_EMAIL);
        mtList = new ArrayList<String>();
        communitylist = new ArrayList<String>();
        profileList = new ArrayList<Profile>();
        profileListorgnl = new ArrayList<Profile>();

        selectedAge =  new String("all");
        selectedCast =  new String("all");
        selectedGender =  new String("all");
        selectedMothertongue =  new String("all");

        searchButton = (Button) rootview.findViewById(id.searchbutton);
        searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ProfileListActivity.class);

                    if(selectedAge.equals("all"))
                    {
                        minage = 0;
                        maxage = 100;
                    }
                    else if(selectedAge.equals("18-22"))
                    {
                        minage = 18;
                        maxage = 22;
                    }
                    else if(selectedAge.equals("22-25"))
                    {
                        minage = 22;
                        maxage = 25;
                    }
                    else if(selectedAge.equals("25-28"))
                    {
                        minage = 25;
                        maxage = 28;
                    }
                    else if(selectedAge.equals("28-30"))
                    {
                        minage = 28;
                        maxage = 30;
                    }
                    else if(selectedAge.equals("30-32"))
                    {
                        minage = 30;
                        maxage = 32;
                    }
                    else if(selectedAge.equals("33-35"))
                    {
                        minage = 33;
                        maxage = 35;
                    }
                    else if(selectedAge.equals("35 and above"))
                    {
                        minage = 35;
                        maxage = 100;
                    }
                    String casttype = new String("all");
                    if(selectedGender.equals("Bride"))
                    {
                        casttype = "Female";
                    }
                    else if(selectedGender.equals("Bridegroom"))
                    {
                        casttype = "Male";
                    }
//                    String newt = new String("");
//                    newt = newt.concat(selectedAge).concat("-").concat(selectedCast).concat("-").concat(casttype).concat("-").concat(selectedMothertongue);
//                    Toast.makeText(getContext(), newt, Toast.LENGTH_SHORT).show();
                    profileList.clear();
                    for (int i = 0; i < profileListorgnl.size(); i++) {
                        Profile profile = profileListorgnl.get(i);
                        if((selectedCast.equals(profile.getCommunity()) || selectedCast.equals("all")) &&
                                (selectedMothertongue.equals(profile.getMothertongue()) || selectedMothertongue.equals("all")) &&
                                (casttype.equals(profile.getGender()) || casttype.equals("all"))&&
                                (profile.getAge() >= minage && profile.getAge() <= maxage))
                        {
                            profileList.add(profile);
                        }
                    }
                    Gson gson = new Gson();
                    String strProfileList = gson.toJson(profileList);
                    intent.putExtra("profileList", strProfileList);
                    startActivity(intent);
                }
            });
        addItemsOnGender();
        addItemsOnAge();
        initcommunityandmothertongue();

        return rootview;
    }

    // add items into spinner dynamically
    public void addItemsOnMothertongue() {

        mt_spinner = (Spinner) rootview.findViewById(id.mothertonguespinner);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),

                android.R.layout.simple_spinner_item, mtList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mt_spinner.setAdapter(dataAdapter);
        mt_spinner.setPrompt(mtList.get(0).toString());
        mt_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                   selectedMothertongue =  new String(item.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {} ;

        });
    }
    public void addItemsOnCommunity() {

        cm_spinner = (Spinner) rootview.findViewById(id.castspinner);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),

                android.R.layout.simple_spinner_item, communitylist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cm_spinner.setAdapter(dataAdapter);
        cm_spinner.setPrompt(communitylist.get(0).toString());
        cm_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    selectedCast =  new String(item.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    public void addItemsOnAge() {

        age_spinner = (Spinner) rootview.findViewById(id.agespinner);

        List<String> agelist = new ArrayList<String>();
        agelist.add("all");
        agelist.add("18-22");
        agelist.add("22-25");
        agelist.add("25-28");
        agelist.add("28-30");
        agelist.add("30-32");
        agelist.add("33-35");
        agelist.add("35 and above");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, agelist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age_spinner.setAdapter(dataAdapter);
        age_spinner.setPrompt(agelist.get(0).toString());
        age_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    selectedAge =  new String(item.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });;
    }

    public void addItemsOnGender() {

        gender_spinner = (Spinner) rootview.findViewById(id.genderspinner);

        List<String> genderlist = new ArrayList<String>();
        genderlist.add("all");
        genderlist.add("Bride");
        genderlist.add("Bridegroom");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),

                android.R.layout.simple_spinner_item, genderlist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(dataAdapter);
        gender_spinner.setPrompt(genderlist.get(0).toString());
        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    selectedGender =  new String(item.toString());
                }
        }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {} ;
        });
    }


    public void initcommunityandmothertongue() {
        String url = Constants.GET_PROFILE2_INFO;
        url = url + session.getEmail();
        new JSONAsyncTask().execute(url);
    }


    public class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;


        public JSONAsyncTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
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
                    JSONArray jmainarray = new JSONArray(data);
                    for (int i = jmainarray.length() - 1; i >= 0; i--) {
                        JSONObject mainobject = jmainarray.getJSONObject(i);
                        if (mainobject.has(TAG_MOTHERTONGUE)) {
                            String mtstrarray = mainobject.getString(TAG_MOTHERTONGUE);
                            JSONArray mtarray = new JSONArray(mtstrarray);
                            mtList.clear();
                            mtList.add("all");
                            for (int j = mtarray.length() - 1; j >= 0; j--) {
                                JSONObject mtobject = mtarray.getJSONObject(j);
                                if (mtobject.has("name")) {
                                    mtList.add(mtobject.getString("name"));
                                }
                            }
                        }
                        if (mainobject.has(TAG_COMUNITY)) {
                            String cmstrarray = mainobject.getString(TAG_COMUNITY);
                            JSONArray cmarray = new JSONArray(cmstrarray);
                            communitylist.clear();
                            communitylist.add("all");
                            for (int j = cmarray.length() - 1; j >= 0; j--) {
                                JSONObject mtobject = cmarray.getJSONObject(j);
                                if (mtobject.has("name")) {
                                    communitylist.add(mtobject.getString("name"));
                                }
                            }
                        }
                        if (mainobject.has(TAG_PROFILES)) {
                            String cmstrarray = mainobject.getString(TAG_PROFILES);
                            JSONArray jarray = new JSONArray(cmstrarray);
                            for (int j = jarray.length() - 1; j >= 0; j--) {
                                JSONObject object = jarray.getJSONObject(j);

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
                                }
                                if (object.has(TAG_OCCUPATION)) {
                                    profile.setOccupation(object.getString(TAG_OCCUPATION));
                                }
                                if (object.has(TAG_EDUCATION)) {
                                    profile.setEducation(object.getString(TAG_EDUCATION));
                                }
                                if (object.has(TAG_SUMMARY)) {
                                    profile.setSummary(object.getString(TAG_SUMMARY));
                                }
                                if (object.has(TAG_COMMUNNITY)) {
                                    profile.setCommunity(object.getString(TAG_COMMUNNITY));
                                }
                                if (object.has(TAG_MOTHER_TONGUE)) {
                                    profile.setMothertongue(object.getString(TAG_MOTHER_TONGUE));
                                }
                                if (object.has(TAG_INCOME)) {
                                    profile.setIncome(object.getString(TAG_INCOME));
                                }
                                if (object.has(TAG_GOTHRA)) {
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
                                }
                                if (object.has(TAG_WEIGHT)) {
                                    profile.setWeight(Float.parseFloat(object.getString(TAG_WEIGHT)));
                                }
                                if (object.has(TAG_ORIGIN)) {
                                    profile.setOrigin(object.getString(TAG_ORIGIN));
                                }
                                if (object.has(TAG_DOB)) {
                                    profile.setDob(object.getString(TAG_DOB));
                                }
                                if (object.has(TAG_AGE)) {
                                    try {
                                        int intValue = object.getInt(TAG_AGE);
                                        profile.setAge(intValue);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                if (object.has(TAG_LOGO)) {
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
                                //profileList.add(profile);
                            }
                        }
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
                addItemsOnMothertongue();
                addItemsOnCommunity();
            }
            else if (result == false) {
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
            }
        }
    }
}