package com.adityabansal.motivatr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.contentful.vault.Vault;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.LinkedHashSet;
import java.util.Set;

import io.paperdb.Paper;
import us.feras.mdv.MarkdownView;


/**
 * Created by adityabansal on 11/5/16.
 */


public class PostView extends AppCompatActivity {
    //To store the curr post being displayed
    private Post currPost;

    //boolean to store whether its a stored post on just clicked on
    private boolean savedPost;

    //Text View to hold the title
    private TextView titleTV;

    //Body view, of MD format.
    private MarkdownView bodyMV;

    //Image View to store header image
    private ImageView top_image_view;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Only create the options menu if we want to delete the post.
        if (savedPost == true) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.postview_menu, menu);
            return true;
        }
        return false;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.delete_post_menu:
                //Delete the post from our Stored Data
                SAVED_DATA.deletePost(currPost);

                //Notify the gridview that our data has changed
                NavBarActivity.adapterViewPager.notifyDataSetChanged();

                //go back to parent activity
                super.onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_view);

        //Assigning XML fields
        titleTV = (TextView) findViewById(R.id.post_title);
        top_image_view = (ImageView) findViewById(R.id.top_postview_image);
        bodyMV = (MarkdownView) findViewById(R.id.markdownViewBody);

        //Getting the data from the passed in Intent
        currPost = Parcels.unwrap(getIntent().getParcelableExtra(Intents.EXTRA_POST));
        savedPost = getIntent().getExtras().getBoolean(Intents.SAVED_POST);

        //Displaying article
        initImage();
        initText();


    }

    private void initImage() {
        Picasso.with(getApplicationContext()).load(currPost.featuredImage.url()).fit().centerCrop().into(top_image_view);
    }

    private void initText() {
        titleTV.setText(currPost.title);
        bodyMV.loadMarkdown(currPost.body);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
