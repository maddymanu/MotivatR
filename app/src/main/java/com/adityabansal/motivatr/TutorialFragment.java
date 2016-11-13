package com.adityabansal.motivatr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.contentful.vault.Resource;
import com.squareup.picasso.Picasso;

/**
 * Created by adityabansal on 11/13/16.
 */

public class TutorialFragment extends Fragment {

    ImageView img;
    TextView titleTV;

    int imageId;
    String title;

    public static TutorialFragment newInstance(String title, CharSequence desc, int image, int bgColor ) {


        Bundle bundle = new Bundle();
        bundle.putString("title", title.toString());
        bundle.putString("desc", desc.toString());
        bundle.putInt("image", image);
        bundle.putInt("bgColor", bgColor);

        TutorialFragment fragment = new TutorialFragment();
        fragment.setArguments(bundle);

        return fragment;

    }

    public TutorialFragment() {
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            imageId = bundle.getInt("image");
            title = bundle.getString("title");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.fragment_tutorial, container, false);

        img = (ImageView) v.findViewById(R.id.image_tut);
        titleTV = (TextView) v.findViewById(R.id.title);

        readBundle(getArguments());


        titleTV.setText(title);
        Picasso.with(getActivity()).load(imageId).fit().centerCrop().into(img);
        return v;
    }
}
