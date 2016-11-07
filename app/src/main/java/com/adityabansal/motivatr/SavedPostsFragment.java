package com.adityabansal.motivatr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.contentful.vault.Vault;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashSet;
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

//TODO add GridView to this screen

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_savedposts, container, false);

        Log.d("SAVED POST FRAG" , "INIT");

        final Set<Post> allSavedPosts = Paper.book().read("savedPosts", new HashSet<Post>());

        for(Post p: allSavedPosts) {
            Log.d("SAVED POST FRAG" , p.title);
        }


        GridView gridView = (GridView) view.findViewById(R.id.grid_view_saved_feed);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(getContext(), allSavedPosts));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Sending image id to FullScreenActivity

                List<Post> savedPostsList =  new ArrayList<Post>(allSavedPosts);
                Intent i = new Intent(getActivity(), PostView.class);
                i.putExtra(Intents.EXTRA_POST, Parcels.wrap(savedPostsList.get(position)));
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
    public ImageAdapter(Context c, Set<Post> savedPosts){
        mContext = c;
//        this.savedPosts = savedPosts;
        savedPostsList = new ArrayList<Post>(savedPosts);

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
        ImageView imageView = new ImageView(mContext);
//        imageView.setImageResource(mThumbIds[position]);
        if(savedPostsList.get(position).featuredImage != null) {
            Picasso.with(mContext).load(savedPostsList.get(position).featuredImage.url()).fit().centerCrop().into(imageView);
        }
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(800, 800));
        return imageView;
    }

}
