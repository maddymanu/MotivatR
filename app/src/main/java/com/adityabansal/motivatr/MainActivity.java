package com.adityabansal.motivatr;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.paperdb.Paper;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends Fragment {
    SyncCallback callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(getActivity());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_main, container, false);
//        setContentView(R.layout.activity_main);

        Vault vault = Vault.with(getContext(), Space2.class);
        List<Post> posts = vault.fetch(Post.class).all();



//        SQLiteDatabase db = vault.getReadableDatabase();


        doStuff();

        return view;

    }

    private void doStuff() {

        CDAClient client = CDAClient.builder()
                .setSpace("o8ueiznwl6xg")
                .setToken("999b58e557abc1bf4292817e83b822b3eb7034e68e01799c0b6afa6a88c03f82")
                .build();

//        vault.requestSync(SyncConfig.builder().setClient(client).build());



        Vault.with(getActivity(), Space2.class).requestSync(SyncConfig.builder().setClient(client).build(), callback = new SyncCallback() {
            @Override public void onResult(SyncResult result) {
                if (result.isSuccessful()) {
                    Vault vault = Vault.with(getContext(), Space2.class);
                    final List<Post> all = vault.fetch(Post.class).all();

                    final SharedPreferences ss = getActivity().getSharedPreferences("db", 0);
          /*          SharedPreferences.Editor editor = ss.edit();
                    editor.clear();
                    editor.apply();
*/

                    Set<String> hs = ss.getStringSet("discardedSlugs", new HashSet<String>());
                    final Set<String> in = new HashSet<String>(hs);


                    final Set<Post> allSavedPosts = Paper.book().read("savedPosts", new HashSet<Post>());


                    for(Post p: allSavedPosts){
                        Log.d("SAVED POST" , p.featuredImage.url());
                    }

                    for(String s: in) {
//                        Log.d("SHARED PREFEES DISCARED" , s);

                    }
                    ss.edit().putStringSet("discardedSlugs", in).apply();

                    //TODO
                    //Remove the saved and discarded from all.
                    SwipeDeck cardStack = (SwipeDeck) getActivity().findViewById(R.id.swipe_deck);

                    final SwipeDeckAdapter adapter = new SwipeDeckAdapter(all, getActivity());
                    cardStack.setAdapter(adapter);

                    cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
                        @Override
                        public void cardSwipedLeft(int position) {
                            Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
                            //all.get(position).status = "LEFT SWIPED";
                            in.add(all.get(position).slug);
                            ss.edit().putStringSet("discardedSlugs", in).apply();
                        }

                        @Override
                        public void cardSwipedRight(int position) {
                            Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
                            allSavedPosts.add(all.get(position));
                            Paper.book().write("savedPosts", allSavedPosts);
                        }

                        @Override
                        public void cardsDepleted() {
                            Log.i("MainActivity", "no more cards");
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
}
