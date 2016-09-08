package br.com.hype.medan;

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

public class HargaAdapter extends ArrayAdapter<Harga> {
    ArrayList<Harga> hargaList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public HargaAdapter(Context context, int resource, ArrayList<Harga> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        hargaList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.id = (TextView) v.findViewById(R.id.pid);
            holder.imageview = (ImageView) v.findViewById(R.id.img_merek);
            holder.tvItem = (TextView) v.findViewById(R.id.tvItem);
            holder.tvHarga = (TextView) v.findViewById(R.id.tvHarga);


            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.imageview.setImageResource(R.drawable.placeholder);
        new DownloadImageTask(holder.imageview).execute(hargaList.get(position).getGambar());
        holder.id.setText(hargaList.get(position).getId());
        holder.tvItem.setText(hargaList.get(position).getItem());
        holder.tvHarga.setText(hargaList.get(position).getHarga());
        return v;

    }

    static class ViewHolder {
        public TextView id;
        public ImageView imageview;
        public TextView tvItem;
        public TextView tvHarga;


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