package com.adityabansal.motivatr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.contentful.vault.Vault;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.paperdb.Paper;

/**
 * Created by adityabansal on 11/5/16.
 */

public class SavedPostsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_savedposts, container, false);

        final Set<Post> allSavedPosts = SAVED_DATA.allSavedPosts;
        final List<Post> savedReversedListPosts = new ArrayList<Post>(allSavedPosts);
        Collections.reverse(savedReversedListPosts);


        //change to send already reverse arrayList
        GridView gridView = (GridView) view.findViewById(R.id.grid_view_saved_feed);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(getContext(), savedReversedListPosts));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Sending image id to PostView
                Intent i = new Intent(getActivity(), PostView.class);
                i.putExtra(Intents.SAVED_POST, true);
                i.putExtra(Intents.EXTRA_POST, Parcels.wrap(savedReversedListPosts.get(position)));
                startActivity(i);
            }
        });

        return view;

    }
}

class ImageAdapter extends BaseAdapter {
    private Context mContext;
    List<Post> savedPostsList;

    // Constructor
    public ImageAdapter(Context c, List<Post> savedPosts) {
        mContext = c;
        savedPostsList = savedPosts;

    }

    @Override
    public int getCount() {
        return savedPostsList.size();
    }

    @Override
    public Object getItem(int position) {
        return savedPostsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
/*        ImageView imageView = new ImageView(mContext);
//        imageView.setImageResource(mThumbIds[position]);
        if(savedPostsList.get(position).featuredImage != null) {
            Picasso.with(mContext).load(savedPostsList.get(position).featuredImage.url()).fit().centerCrop().into(imageView);
        }
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(800, 800));
        return imageView;*/


        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.gridview_single_otem, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) row.findViewById(R.id.grid_view_title);
            holder.imageView = (ImageView) row.findViewById(R.id.grid_view_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Post item = savedPostsList.get(position);
        holder.titleTextView.setText(item.title);

        Picasso.with(mContext).load(item.featuredImage.url()).into(holder.imageView);
//        holder.imageView.setLayoutParams(new GridView.LayoutParams(800, 800));
        return row;

    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
    }

}
