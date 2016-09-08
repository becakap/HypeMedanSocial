package br.com.hype.medan;

/**
 * Created by Mohammad Iqbal on 9/7/2016.
 * Email : iqbalhood@gmail.com
 * Ini adalah fungsi setting adapter untuk menyiapkan data yang akan ditampilkan di
 * fragment
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;



public class EventAdapter extends ArrayAdapter<Event> {
    ArrayList<Event> eventList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public EventAdapter(Context context, int resource, ArrayList<Event> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        eventList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.id = (TextView) v.findViewById(R.id.pid);
            holder.imageview = (ImageView) v.findViewById(R.id.img_event);
            holder.tvNama = (TextView) v.findViewById(R.id.tvNama);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.imageview.setImageResource(R.drawable.placeholder);
        new DownloadImageTask(holder.imageview).execute(eventList.get(position).getGambar());
        holder.id.setText(eventList.get(position).getId());
        holder.tvNama.setText(eventList.get(position).getNama());
        return v;

    }


    static class ViewHolder {
        public TextView  id;
        public ImageView imageview;
        public TextView  tvNama;

    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }









}
