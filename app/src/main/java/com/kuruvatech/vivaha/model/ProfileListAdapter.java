package com.kuruvatech.vivaha.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.kuruvatech.vivaha.R;
import com.kuruvatech.vivaha.utils.ImageLoader;

/**
 * Created by dganeshappa on 11/11/2015.
 */
public class ProfileListAdapter extends ArrayAdapter<Profile> {

    ArrayList<Profile> profileList;
    LayoutInflater vi;
    int Resource;
    ViewHolder itemHolder;
    public ImageLoader imageLoader;
    public ProfileListAdapter(Context context, int resource, ArrayList<Profile> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        profileList = objects;
        imageLoader = new ImageLoader(context.getApplicationContext());
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            itemHolder = new ViewHolder();
            v = vi.inflate(Resource, null);
            itemHolder.profilename= (TextView) v.findViewById(R.id.vendor_name);
            itemHolder.profilephone= (TextView) v.findViewById(R.id.vendor_delivery_time);
            itemHolder.profileage= (TextView) v.findViewById(R.id.vendor_rating);
            itemHolder.profilecommunity= (TextView) v.findViewById(R.id.vendor_speciality);
            itemHolder.profilelogo = (ImageView)v.findViewById(R.id.vendor_image_view);
            itemHolder.profileoccupation = (TextView) v.findViewById(R.id.textViewIsopen);
            v.setTag(itemHolder);
        } else {
            itemHolder = (ViewHolder) v.getTag();
        }
        itemHolder.profilename.setText(profileList.get(position).getName());
        itemHolder.profilephone.setText(profileList.get(position).getPhone());
        itemHolder.profileage.setText(String.valueOf(profileList.get(position).getAge()));
       // itemHolder.profileid.setText(profileList.get(position).getId());
        itemHolder.profilecommunity.setText(profileList.get(position).getCommunity());
        itemHolder.profileoccupation.setText(profileList.get(position).getOccupation());
        String image_url = profileList.get(position).getLogo();
        imageLoader.DisplayImage(image_url,  itemHolder.profilelogo);
      //  imageLoader.DowloadImage(profileList.get(position).getProfileLogo());
        return v;

    }

    static class ViewHolder {
        //  public ImageView imageview;
        public TextView profilename;
        public TextView profilephone;
        public TextView profileid;
        public TextView profilecommunity;
        public TextView profileage;
        public TextView profileoccupation;
        ImageView profilelogo;
    }

}
