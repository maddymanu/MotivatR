package com.adityabansal.motivatr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.contentful.vault.Vault;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_savedposts, container, false);

        Log.d("SAVED POST FRAG" , "INIT");

        final Set<Post> allSavedPosts = Paper.book().read("savedPosts", new HashSet<Post>());

        for(Post p: allSavedPosts) {
            Log.d("SAVED POST FRAG" , p.title);
        }

//        Vault vault = Vault.with(getContext(), Space2.class);
//        List<Post> posts = vault.fetch(Post.class).all();



        return view;

    }
}
