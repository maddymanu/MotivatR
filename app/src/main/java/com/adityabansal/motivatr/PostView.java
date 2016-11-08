package com.adityabansal.motivatr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.contentful.vault.Vault;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import io.paperdb.Paper;
import us.feras.mdv.MarkdownView;


/**
 * Created by adityabansal on 11/5/16.
 */

public class PostView extends AppCompatActivity {
    private Post currPost;

//    ImageView main;

    TextView titleTV, bodyTV;
    MarkdownView bodyMV;
    ImageView top_image_view;



    void PostView(Post p) {
        currPost = p;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Bundle bundle = this.getArguments();
//        currPost = Parcels.unwrap(bundle.getParcelable("post"));
//        Paper.init(getActivity());



                setContentView(R.layout.activity_post_view);






        titleTV = (TextView) findViewById(R.id.post_title);
        top_image_view = (ImageView) findViewById(R.id.top_postview_image);
//        bodyTV = (TextView) findViewById(R.id.body_post);
        bodyMV = (MarkdownView) findViewById(R.id.markdownViewBody);
        currPost = Parcels.unwrap(getIntent().getParcelableExtra(Intents.EXTRA_POST));

        Log.d("DATA FROM POSTVIEW" , currPost.title+currPost.body);


        initImage();
        initText();



    }

    private void initImage() {
        Picasso.with(getApplicationContext()).load(currPost.featuredImage.url()).fit().centerCrop().into(top_image_view);
    }


/*
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_post_view, container, false);
//        setContentView(R.layout.activity_post_view);






        titleTV = (TextView) view.findViewById(R.id.post_title);
         bodyTV = (TextView) view.findViewById(R.id.body_post);
        bodyMV = (MarkdownView) view.findViewById(R.id.markdownViewBody);
//        currPost = Parcels.unwrap(getActivity().getIntent().getParcelableExtra(Intents.EXTRA_POST));

        Log.d("DATA FROM POSTVIEW" , currPost.title+currPost.body);


        initText();

        return view;

    }*/

    private void initText() {

        titleTV.setText(currPost.title);
        bodyMV.loadMarkdown(currPost.body);

    }


    @Override public void onDestroy() {
        super.onDestroy();
    }
}
