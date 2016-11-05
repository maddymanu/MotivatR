package com.adityabansal.motivatr;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adityabansal on 11/4/16.
 */

public class SwipeDeckAdapter extends BaseAdapter {

    private List<Post> data;
    private Context context;

    public SwipeDeckAdapter(List<Post> data, Context context) {
        this.data = data;
        this.context = context;
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
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // normally use a viewholder
            v = inflater.inflate(R.layout.single_card_view, parent, false);
        }
       // ((TextView) v.findViewById(R.id.title_post)).setText(data.get(position).title);
            final ImageView mainImg = (ImageView) v.findViewById(R.id.img);


            String url = null;
           try {
               url =data.get(position).featuredImage.url();
           } catch (NullPointerException e){

           }

        try {

            Picasso.with(this.context).load(url).fit().centerCrop().into(mainImg);

        }catch (Exception e2) {
            Log.d("EXCCC" , e2.toString());
        }









        /*AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e("AsyncTask", "onPreExecute");
            }
            @Override
            protected String doInBackground(Void... params) {
                Log.v("AsyncTask", "doInBackground");

                String msg = "";

                String url =data.get(position).featuredImage.url();
                Log.d("IN BG" , url);
                try {

                    Picasso.with(context).load(url).fit().centerCrop().into(mainImg);

                }catch (Exception e2) {
                    Log.d("EXCCC" , e2.toString());
                }


                return msg;
            }
            @Override
            protected void onPostExecute(String msg) {
                Log.v("AsyncTask", "onPostExecute");

            }
        };*/

   /*     if(Build.VERSION.SDK_INT >= 11)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else*/
//        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

//        AsyncTask myAsyncTask = new LongOperation(mainImg, context , url);
//        new LongOperation(mainImg, context, url).execute("");
//        myAsyncTask.execute("Something");



      /*      if(url != null && mainImg != null && context != null) {
                try {
                    //Picasso.with(context).load(url).fit().centerCrop().into(mainImg);
                }catch (Exception e2) {

                }
            }*/

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String item = (String)   getItem(position).;
//                Log.i("MainActivity", item);
            }
        });

        return v;
    }


    /*private class LongOperation extends AsyncTask<String, Void, String> {

        public ImageView im;
        public Context c;
        public String url;

        public LongOperation(ImageView a, Context c, String u){
            this.im = a;
            this.c = c;
            this.url = u;
        }

        @Override
        protected String doInBackground(String... params) {
           for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            try {
                Picasso.with(this.c).load(this.url).fit().centerCrop().into(im);
            }catch (Exception e2) {

            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            try {
//                Picasso.with(this.c).load(this.url).fit().centerCrop().into(im);
            }catch (Exception e2) {

            }
//            TextView txt = (TextView) activity.findViewById(R.id.output);
//            txt.setText("Executed"); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }*/


}