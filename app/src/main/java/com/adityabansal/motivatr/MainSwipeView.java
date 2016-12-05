package com.adityabansal.motivatr;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDACallback;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.contentful.vault.SyncCallback;
import com.contentful.vault.SyncConfig;
import com.contentful.vault.SyncResult;
import com.contentful.vault.Vault;
import com.daprlabs.aaron.swipedeck.SwipeDeck;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.paperdb.Paper;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainSwipeView extends Fragment {
    private SyncCallback callback;
    private View view;
    private LayoutInflater inflater;
    private ViewGroup container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(getActivity());
        Paper.book().destroy();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        this.inflater = inflater;
        this.container = container;


        if (view != null) {
            return view;
        }
        view = inflater.inflate(R.layout.activity_main, container, false);
        setView();

        return view;

    }

    private void setView() {

         final CDAClient client = CDAClient.builder()
                .setSpace("o8ueiznwl6xg")
                .setToken("999b58e557abc1bf4292817e83b822b3eb7034e68e01799c0b6afa6a88c03f82")
                .build();

        //Synchronised method to get data from the CMS
        Vault.with(getActivity(), Space2.class).requestSync(SyncConfig.builder().setClient(client).build(), callback = new SyncCallback() {
            @Override
            public void onResult(SyncResult result) {
                if (result.isSuccessful()) {

                    //Fetch the new posts ordered by date.
                    Vault vault = Vault.with(getActivity(), Space2.class);
                    final List<Post> all = vault.fetch(Post.class).limit(Integer.parseInt("25")).order("updated_at DESC").all();

                    //Fet the discarded/saved posts
                    final Set<Post> allSavedPosts = SAVED_DATA.allSavedPosts;
                    final Set<Post> allDiscaredPosts = Paper.book().read("discaredPosts", new HashSet<Post>());


                    //From the list of posts we get, remove the saved/discarded. Suboptimal, change this.
                    for (Iterator<Post> it = all.iterator(); it.hasNext(); ) {
                        Post p = it.next();
                        if (allSavedPosts.contains(p) || allDiscaredPosts.contains(p)) {
                            it.remove();
                        }
                    }

                    //Creating the main Swipe Deck & its adapater
                    SwipeDeck cardStack = (SwipeDeck) getActivity().findViewById(R.id.swipe_deck);
                    final SwipeDeckAdapter adapter = new SwipeDeckAdapter(all, getActivity(), getFragmentManager());

                    //Text view to show the empty List. TODO change
                    final TextView emptyListTV = (TextView) getActivity().findViewById(R.id.empty_list_tv);

                    cardStack.setAdapter(adapter);
                    cardStack.setLeftImage(R.id.left_image);
                    cardStack.setRightImage(R.id.right_image);

                    //Callback methods for the deck
                    cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {
                        @Override
                        public void cardSwipedLeft(long stableId) {

                            Log.i("MainSwipeView", "card was swiped left, position in adapter: " + stableId);
                            //Put this post in the discared posts //TODO change to SAVED DATA
                            if (stableId == all.size()-1){
                                Log.d("DEBUG" , "last item");
                                emptyListTV.setVisibility(View.VISIBLE);
                            }
                            allDiscaredPosts.add(all.get((int)stableId));
                            Paper.book().write("discaredPosts", allDiscaredPosts);

                        }

                        @Override
                        public void cardSwipedRight(long stableId) {

                            Log.i("MainSwipeView", "card was swiped right, position in adapter: " + stableId);
                            //TODO
                            //change to hold only 50 saved & remove delete function bug
                            if (stableId == all.size()-1){
                                emptyListTV.setVisibility(View.VISIBLE);
                                Log.d("DEBUG" , "last item");
                            }
                            SAVED_DATA.writeNewPost(all.get((int)stableId));
                            NavBarActivity.adapterViewPager.notifyDataSetChanged();

                        }



                    });


                } else {
                    // Failure
                }
            }
        });


    }

    @Override
    public void onDestroy() {
        Vault.cancel(callback);
        super.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


}
