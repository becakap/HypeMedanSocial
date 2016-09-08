package br.com.hype.medan;

import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Fragment that displays "Monday".
 */
public class MondayFragment extends Fragment {

    ArrayList<Harga> hargaList;

    HargaAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //This layout contains your list view
        View view = inflater.inflate(R.layout.fragment_monday, container, false);

        hargaList = new ArrayList<Harga>();
        new JSONAsyncTask().execute("http://api.gerobaksampah.org/androidapi/get_all_harga.php");

        //now you must initialize your list view
        ListView listview =(ListView)view.findViewById(R.id.lv_item);

        adapter = new HargaAdapter(getActivity(), R.layout.lsv_item_harga, hargaList);

        listview.setAdapter(adapter);



        //To have custom list view use this : you must define CustomeAdapter class
        // listview.setadapter(new CustomeAdapter(getActivity()));
        //getActivty is used instead of Context
        return view;


    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("harga");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Harga harga = new Harga();
                        harga.setId(object.getString("pid"));
                        harga.setItem(object.getString("item"));
                        harga.setHarga(object.getString("harga"));
                        harga.setGambar(object.getString("gambar"));

                        hargaList.add(harga);
                    }
                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }





}
