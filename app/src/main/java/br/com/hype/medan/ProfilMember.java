package br.com.hype.medan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by IQBAL on 9/1/2016.
 */


public class ProfilMember extends AppCompatActivity {
    Button btnSubmit;
    EditText edNamaDepan, edNamaBelakang, edPonsel;
    String nama, nama_depan, nama_belakang, id_sosmed, fb_gplus, language, gender, tahun_lahir, koordinat, kodeVerifikasi;

    //shared prefernces untuk data order dan harga
    public static final String profilPREFERENCES = "profilPreference";
    SharedPreferences profilSharedpreferences;

    //This is our root url
    public static final String ROOT_URL = "http://api.hypemedan.id";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_member);

        //ambil koordinat gps
        profilSharedpreferences = getSharedPreferences(profilPREFERENCES, Context.MODE_PRIVATE);

        String getKoordinat = profilSharedpreferences.getString("koordinat", null);
        koordinat = getKoordinat;

        Log.e("Koordinatnya = ", "I shouldn't be " + getKoordinat);


        //fb_gplus social network name
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String socialNetworkName = preferences.getString(LoginActivity.USER_SOCIAL, null);

        fb_gplus = socialNetworkName;
        nama = preferences.getString(LoginActivity.PROFILE_NAME, "");
        id_sosmed = preferences.getString(LoginActivity.PROFILE_EMAIL, "");

        Pattern p = Pattern.compile("[ ]");
        String[] result = p.split(nama);


        for (int i = 0; i < result.length; i++) {
            if (i == 0) {
                nama_depan = result[i];
            }
            if (i != 0) {
                if (result[i] != null) {
                    nama_belakang += result[i] + " ";
                }
            }
        }

        nama_belakang = nama_belakang.replace("null", "");

        int count = 2016-1979;
        int total = 0;
        String [] tahunlahir = new String[count];
        for (int i=0; count>i; i++) {
            tahunlahir[i] = String.valueOf(i+1979);
            total += i+1;
        }




        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        edNamaDepan = (EditText) findViewById(R.id.edNamaDepan);
        edNamaBelakang = (EditText) findViewById(R.id.edNamaBelakang);
        edPonsel = (EditText) findViewById(R.id.edPhone);

        edNamaDepan.setText(nama_depan);
        edNamaBelakang.setText(nama_belakang);

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("Bahasa Indonesia", "English");

        MaterialSpinner spinnerJenisKelamin = (MaterialSpinner) findViewById(R.id.spinnerJenisKelamin);
        spinnerJenisKelamin.setItems("Pria", "Wanita");

        MaterialSpinner spinnerTahunLahir = (MaterialSpinner) findViewById(R.id.spinnerTahunLahir);
        spinnerTahunLahir.setItems(tahunlahir);

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, item + " Selected", Snackbar.LENGTH_LONG).show();
                language = item;
            }
        });

        spinnerJenisKelamin.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, item + " Selected", Snackbar.LENGTH_LONG).show();
                gender = item;
            }
        });

        spinnerTahunLahir.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, item + " Selected", Snackbar.LENGTH_LONG).show();
                tahun_lahir = item;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(ProfilMember.this, DataInterest.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);

                //insertUser();
                createVerivikasi();



            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void createVerivikasi() {

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100);
        String kode   = String.valueOf(randomInt);
        kodeVerifikasi = kode;
        //Here we will handle the http request to insert user to mysql db
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        CreateVerifikasiKodeAPI api = adapter.create(CreateVerifikasiKodeAPI.class);

        //Defining the method insertuser of our interface
        api.createVerifikasi(

                edPonsel.getText().toString(),
                kodeVerifikasi,

                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                        /*
                        Untuk proses development berikutnya
                        programmer harus membuat apakah ada data yang di edit atau tidak
                        kemudian programmer harus membedakan apakah user baru pertamakali login
                        atau user ingin mengubah data dirinya
                            #Perlu struktur looping yang lebih baik
                            #HailHydra
                        - Jika user cuman edit profil maka dapat langsung di bypass ke
                          Activity DataInterest
                         */
                        Toast.makeText(ProfilMember.this, output, Toast.LENGTH_LONG).show();
                        String Success = "sukses";
                        if (output.toLowerCase().contains(Success.toLowerCase())) {
                            String nmrhp = edPonsel.getText().toString();

                            Intent i = new Intent(ProfilMember.this, VerifikasiHP.class);
                            i.putExtra("nomor_hp", nmrhp);
                            i.putExtra("kode", kodeVerifikasi);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(ProfilMember.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void insertUser() {

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100);
        String id_user = String.valueOf(randomInt);
        //Here we will handle the http request to insert user to mysql db
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        RegisterAPI api = adapter.create(RegisterAPI.class);

        //Defining the method insertuser of our interface
        api.insertUser(


                //Passing the values by getting it from editTexts
                id_user,
                id_sosmed,
                fb_gplus,
                koordinat,
                edNamaDepan.getText().toString(),
                edNamaBelakang.getText().toString(),
                language,
                edPonsel.getText().toString(),
                gender,
                tahun_lahir,
                "",
                "",
                "",
                "hold",


                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                        Toast.makeText(ProfilMember.this, output, Toast.LENGTH_LONG).show();
                        String Success = "berhasil";
                        if (output.toLowerCase().contains(Success.toLowerCase())) {
                            Intent i = new Intent(ProfilMember.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(ProfilMember.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ProfilMember Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.hype.medan/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ProfilMember Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.hype.medan/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
