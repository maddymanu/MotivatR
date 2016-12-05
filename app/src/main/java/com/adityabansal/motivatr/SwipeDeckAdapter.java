package com.adityabansal.motivatr;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * Created by adityabansal on 11/4/16.
 */

public class SwipeDeckAdapter extends BaseAdapter {

    private List<Post> data;
    private Context context;
    private FragmentManager fragmentManager;

    public SwipeDeckAdapter(List<Post> data, Context context, FragmentManager fm) {
        this.data = data;
        this.context = context;
        this.fragmentManager = fm;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // normally use a viewholder
            v = inflater.inflate(R.layout.single_card_view, parent, false);
        }
        final ImageView mainImg = (ImageView) v.findViewById(R.id.offer_image);
        final TextView title_textview = (TextView) v.findViewById(R.id.title_post);


        String url = null;
        try {
            url = data.get(position).featuredImage.url();
        } catch (NullPointerException e) {

        }

        try {

            Picasso.with(this.context).load(url).fit().centerCrop().into(mainImg);
            title_textview.setText(data.get(position).title);

        } catch (Exception e2) {
        }


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post item = (Post) getItem(position);


                context.startActivity(new Intent(context, PostView.class)
                        .putExtra(Intents.EXTRA_POST, Parcels.wrap(item)));

            }
        });

        return v;
    }


}