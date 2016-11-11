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

import com.daprlabs.cardstack.SwipeDeck;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDACallback;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.contentful.vault.SyncCallback;
import com.contentful.vault.SyncConfig;
import com.contentful.vault.SyncResult;
import com.contentful.vault.Vault;

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

public class MainActivity extends Fragment {
    SyncCallback callback;
    View view;
    LayoutInflater inflater;
    ViewGroup container;

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


        if(view != null) {
//            savedInstanceState.
            return view;
        }
        view = inflater.inflate(R.layout.activity_main, container, false);
//        setContentView(R.layout.activity_main);


        setView();

        return view;

    }

    private void setView() {

        CDAClient client = CDAClient.builder()
                .setSpace("o8ueiznwl6xg")
                .setToken("999b58e557abc1bf4292817e83b822b3eb7034e68e01799c0b6afa6a88c03f82")
                .build();

        Vault.with(getActivity(), Space2.class).requestSync(SyncConfig.builder().setClient(client).build(), callback = new SyncCallback() {
            @Override public void onResult(SyncResult result) {
                if (result.isSuccessful()) {
                    Vault vault = Vault.with(getActivity(), Space2.class);
                    final List<Post> all = vault.fetch(Post.class).limit(Integer.parseInt("25")).order("updated_at DESC").all();

//                    final SharedPreferences ss = getActivity().getSharedPreferences("db", 0);
          /*          SharedPreferences.Editor editor = ss.edit();
                    editor.clear();
                    editor.apply();
*/

//                    Set<String> hs = ss.getStringSet("discardedSlugs", new HashSet<String>());
//                    final Set<String> in = new HashSet<String>(hs);



                    final Set<Post> allSavedPosts = SAVED_DATA.allSavedPosts;
//                    final Set<Post> allSavedPosts = Paper.book().read("savedPosts", new LinkedHashSet<Post>());
                    final Set<Post> allDiscaredPosts = Paper.book().read("discaredPosts", new HashSet<Post>());

                  /*  for(String discardeeSlug: in) {
                        if (all.contains(discardeeSlug)) {

                        }
                    }*/
           /*         for(Post p: all) {
                        if(allSavedPosts.contains(p) || allDiscaredPosts.contains(p)) {
                            all.remove(p);
                        }
                    }*/

                    for (Iterator<Post> it = all.iterator(); it.hasNext(); ) {
                        Post p = it.next();
                        if (allSavedPosts.contains(p) || allDiscaredPosts.contains(p)) {
                            it.remove();
                        }
                    }


                    SwipeDeck cardStack = (SwipeDeck) getActivity().findViewById(R.id.swipe_deck);
                    final SwipeDeckAdapter adapter = new SwipeDeckAdapter(all, getActivity(), getFragmentManager());

                    final TextView emptyListTV = (TextView) getActivity().findViewById(R.id.empty_list_tv);
                    cardStack.setLeftImage(R.id.left_image);
                    cardStack.setRightImage(R.id.right_image);

                    cardStack.setAdapter(adapter);

                    cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
                        @Override
                        public void cardSwipedLeft(int position) {
                            Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
                            //all.get(position).status = "LEFT SWIPED";
//                            in.add(all.get(position).slug);
//                            ss.edit().putStringSet("discardedSlugs", in).apply();
                            allDiscaredPosts.add(all.get(position));
                            Paper.book().write("discaredPosts", allDiscaredPosts);
                        }

                        @Override
                        public void cardSwipedRight(int position) {
                            Log.i("MainActivity", "card was swiped right, position in adapter: " + position);



                            //TODO
                            //change to hold only 50 saved & remove delete function bug
//                            allSavedPosts.add(all.get(position));
//                            Paper.book().write("savedPosts", allSavedPosts);
                            SAVED_DATA.writeNewPost(all.get(position));


                            NavBarActivity.adapterViewPager.notifyDataSetChanged();

                        }

                        @Override
                        public void cardsDepleted() {
                            Log.i("MainActivity", "no more cards");
                            /*final int index = container.indexOfChild(view);
                            container.removeView(view);
                            view = inflater.inflate(R.layout.empty_feed , container, false);
                            container.addView(view,index);*/
                            emptyListTV.setVisibility(View.VISIBLE);
                            NavBarActivity.adapterViewPager.notifyDataSetChanged();
//                            NavBarActivity.viewPager.setCurrentItem(1);

                        }

                        @Override
                        public void cardActionDown() {

                        }

                        @Override
                        public void cardActionUp() {

                        }
                    });



                } else {
                    // Failure
                }
            }
        });


    }

    @Override public void onDestroy() {
        Vault.cancel(callback);
        super.onDestroy();
    }


    @Override public void onResume() {
        super.onResume();
        Log.d("ON RESUME" , "MIAN ACT");
    }



}
