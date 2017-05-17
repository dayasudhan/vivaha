package com.kuruvatech.vivaha;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kuruvatech.vivaha.model.Profile;
import com.kuruvatech.vivaha.utils.ImageLoader;
import java.util.ArrayList;

/**
 * Created by gagan on 3/22/2017.
 */
public class ProfileDetail extends AppCompatActivity {
    String vendor_email;
    SharedPreferences pref;
    Profile profile;
    ArrayList<Profile> profileList;
    Button btnnext,btnprevious,btnshareit,btnphonecall;
    int position;

    TextView txtViewName ;
    TextView txtViewPhone ;
    TextView txtViewEmail;
    TextView txtViewaddress ;
    TextView txtViewstar ;
    TextView txtViewrashi ;
    TextView txtViewgothra ;
    TextView txtViewage ;
    TextView txtViewdob ;
    TextView txtViewoccupation ;
    TextView txtViewEducation ;
    TextView txtViewheight ;
    TextView txtViewweight ;
    TextView txtViewsummary ;
    TextView txtVieworigin ;
    TextView txtViewmothertongue ;
    TextView txtViewid ;
    TextView txtViewincome;
    TextView txtViewfather ;
    TextView txtViewmother ;
    TextView txtViewcommunity ;
    TextView txtViewdetailsummary ;
    ImageView background,background2;
    ImageLoader imageLoader;
    String bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profiledetail);
        Intent i = getIntent();
        pref = getSharedPreferences("Khaanavali", 0);
        vendor_email = pref.getString("email", "name");
        Gson gson = new Gson();
        TypeToken<ArrayList<Profile>> token = new TypeToken<ArrayList<Profile>>() {};
        profileList = gson.fromJson(i.getStringExtra("profile"), token.getType());
        position = Integer.parseInt(i.getStringExtra("position"));
        profile = profileList.get(position);

        txtViewName = (TextView) findViewById(R.id.name);
        txtViewPhone = (TextView) findViewById(R.id.phone);
        txtViewEmail = (TextView) findViewById(R.id.emailid);
        txtViewaddress = (TextView) findViewById(R.id.address);
        txtViewstar = (TextView) findViewById(R.id.star);
        txtViewrashi = (TextView) findViewById(R.id.rashi);
        txtViewgothra = (TextView) findViewById(R.id.gothra);
        txtViewage = (TextView) findViewById(R.id.age);
        txtViewdob = (TextView) findViewById(R.id.dob);
        txtViewoccupation = (TextView) findViewById(R.id.occupation);
        txtViewEducation = (TextView) findViewById(R.id.education);
        txtViewheight = (TextView) findViewById(R.id.height);
        txtViewweight = (TextView) findViewById(R.id.weight);
        txtViewsummary = (TextView) findViewById(R.id.summary);
        txtVieworigin = (TextView) findViewById(R.id.origin);
        txtViewmothertongue = (TextView) findViewById(R.id.mothertongue);
        txtViewid = (TextView) findViewById(R.id.profileid);
        txtViewincome = (TextView) findViewById(R.id.income);
        txtViewfather = (TextView) findViewById(R.id.fatherdetail);
        txtViewmother = (TextView) findViewById(R.id.motherdetail);
        txtViewcommunity = (TextView) findViewById(R.id.community);
        txtViewdetailsummary = (TextView) findViewById(R.id.detailsummary);

        background = (ImageView) findViewById(R.id.background);
        background2 = (ImageView) findViewById(R.id.background2);
        imageLoader = new ImageLoader(getApplicationContext().getApplicationContext());

        btnnext= (Button) findViewById(R.id.next_button);
        btnprevious= (Button) findViewById(R.id.previous_button);
        btnshareit= (Button) findViewById(R.id.shareit_button);
        btnphonecall = (Button) findViewById(R.id.btnphonecall);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(profileList.size()>(position + 1))
                    position = position + 1;
                    profile = profileList.get(position);
                displayInfo();
            };
        });

        btnprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(position > 0)
                {
                    position = position -1;
                    profile = profileList.get(position);
                    displayInfo();
                }
            };
        });

        btnshareit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
//                    Intent shareIntent = new Intent();
//                    shareIntent.setAction(Intent.ACTION_SEND);
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, profile.getProfileLogo());
//                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Matrimony Profile");
//                    shareIntent.setType("text/plain");
//                    startActivity(Intent.createChooser(shareIntent, "Share Image"));




//                String pathofBmp=
//                        MediaStore.Images.Media.insertImage(getContentResolver(),
//                                bitmap,"Matrimony Profile", null);
                Uri uri = Uri.parse(bitmap);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Matrimony Profile");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Share Matrimony Profile"));
            };
        });
        btnphonecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                String calNumber = new String("tel:").concat(profile.getPhone());
                callIntent.setData(Uri.parse(calNumber));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileDetail.this,
                            Manifest.permission.CALL_PHONE)) {

                        new AlertDialog.Builder(ProfileDetail.this)
                                .setTitle("Permission Required")
                                .setMessage("This permission was denied earlier by you. This permission is required to call from app .So, in order to use this feature please allow this permission by clicking ok.")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        ActivityCompat.requestPermissions(ProfileDetail.this,
                                                new String[]{Manifest.permission.CALL_PHONE},
                                                1);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    } else {

                        ActivityCompat.requestPermissions(ProfileDetail.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);

                    }
                    return;
                }
                startActivity(callIntent);
            }
        });

        displayInfo();
        setToolBar();
    }

    void displayInfo()
    {
        txtViewName.setText(profile.getName().concat(" ( ").concat(profile.getId()).concat(" )"));
        txtViewPhone.setText(profile.getPhone());
        txtViewEmail.setText(profile.getEmail());
        txtViewstar.setText(profile.getStar());
        txtViewEducation.setText(profile.getEducation());
        txtViewcommunity.setText(profile.getCommunity());
        txtViewoccupation.setText(profile.getOccupation());
        txtViewrashi.setText(profile.getRashi());
        txtViewgothra.setText(profile.getGothra());
        txtViewage.setText(String.valueOf(profile.getAge()));
        txtViewdob.setText(profile.getDob());
        txtViewheight.setText(String.valueOf(profile.getHeight()));
        txtViewweight.setText(String.valueOf(profile.getWeight()));
        txtVieworigin.setText(profile.getOrigin());
        txtViewmothertongue.setText(profile.getMothertongue());
        txtViewid.setText(profile.getId());
        txtViewincome.setText(profile.getIncome());
        txtViewsummary.setText(profile.getSummary());

        String father =  new String(profile.getFather().getName());
        father.concat(" | ");
        father.concat(profile.getFather().getOccupation());
        father.concat(" | ");
        father.concat(profile.getFather().getPhone());
        txtViewfather.setText(father);

        String mother =  new String(profile.getMother().getName());
        mother.concat(" | ");
        mother.concat(profile.getMother().getOccupation());
        mother.concat(" | ");
        mother.concat(profile.getMother().getPhone());
        txtViewmother.setText(mother);

        String address = new String(profile.getAddress().getAddressLine1());
        address.concat("\n");
        address.concat(profile.getAddress().getAddressLine2());
        address.concat("\n");
        address.concat(profile.getAddress().getStreet());
        address.concat("\n");
        address.concat(profile.getAddress().getLandMark());
        address.concat("\n");

        address.concat(profile.getAddress().getCity());
        txtViewaddress.setText(address);


        String detailsummary =  new String(String.valueOf(profile.getAge()));
        detailsummary.concat(" | ");
        detailsummary.concat(profile.getCommunity());
        detailsummary.concat(" | ");
        detailsummary.concat(profile.getOccupation());
        txtViewdetailsummary.setText(detailsummary);

        imageLoader.DisplayImage(profile.getLogo(),  background);
        imageLoader.DisplayImage(profile.getProfileLogo(),  background2);
      //  imageLoader.DowloadImage(profile.getProfileLogo());
        bitmap =imageLoader.getImagePath(profile.getProfileLogo());
        Toast.makeText(getApplicationContext(), bitmap, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_order_detail, menu);
        return true;
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
            ab.setTitle("Profile Detail");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent i=new Intent(this,MainActivity.class);
//        startActivity(i);
    }
};
