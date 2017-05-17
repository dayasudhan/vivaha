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
    Button searchButton;

    private static final String TAG_MOTHERTONGUE = "mothertongue";
    private static final String TAG_COMUNITY = "community";

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
        selectedAge =  new String("all");
        selectedCast =  new String("all");
        selectedGender =  new String("all");
        selectedMothertongue =  new String("all");

        searchButton = (Button) rootview.findViewById(id.searchbutton);
        searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ProfileListActivity.class);
                    intent.putExtra("age", selectedAge);
                    intent.putExtra("community", selectedCast);
                    intent.putExtra("gender", selectedGender);
                    intent.putExtra("mothertongue", selectedMothertongue);
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
        genderlist.add("BrideGroom");
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
                    JSONArray jarray = new JSONArray(data);
                    for (int i = jarray.length() - 1; i >= 0; i--) {
                        JSONObject object = jarray.getJSONObject(i);
                        if (object.has(TAG_MOTHERTONGUE)) {
                            String mtstrarray = object.getString(TAG_MOTHERTONGUE);
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
                        if (object.has(TAG_COMUNITY)) {
                            String cmstrarray = object.getString(TAG_COMUNITY);
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