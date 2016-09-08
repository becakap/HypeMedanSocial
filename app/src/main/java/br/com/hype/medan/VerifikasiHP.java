package br.com.hype.medan;
/*
* Create By Mohammad Iqbal 8/9/2016
* Email : iqbalhood@gmail.com
* Activity untuk send nomor hp dan kode verifikasi kemudian akan di cek oleh server
* apakah nomor hp dan kode verifikasi tepat kemudian akan mengubah status user dari
* hold ke active
* */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class VerifikasiHP extends AppCompatActivity {

    public static final String ROOT_URL     = "http://api.hypemedan.id";
    public static final String ROOT_URL_CI  = "http://hypemedan.id";
    Button btnSubmit;
    EditText edVerificationCode;
    String nomorhp, kode;
    Boolean sukses = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_hp);

        Intent intent = getIntent();
        nomorhp = intent.getStringExtra("nomor_hp");
        kode    = intent.getStringExtra("kode");



        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        edVerificationCode = (EditText)findViewById(R.id.edVerificationCode);





        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                String kode_input = edVerificationCode.getText().toString();
                /*
                * Jika kode intent sama dengan kode yang
                * di smskan maka jalankan if
                 */
                if(kode_input.equals(kode)) {
                    createVerivikasi();
                }else{
                    Toast.makeText(VerifikasiHP.this, "Kode Verifikasi Anda Salah" , Toast.LENGTH_LONG).show();
                }


            }
        });

        KirimSms();
    }

    private void createVerivikasi() {



        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        VerifikasiHpAPI api = adapter.create(VerifikasiHpAPI.class);

        //Defining the method insertuser of our interface
        api.verifikasiHPSET(
                nomorhp,
                edVerificationCode.getText().toString(),

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
                        Toast.makeText(VerifikasiHP.this, output, Toast.LENGTH_LONG).show();
                        String Success = "sukses";
                        if (output.toLowerCase().contains(Success.toLowerCase())) {
                            Intent i = new Intent(VerifikasiHP.this, DataInterest.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(VerifikasiHP.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void KirimSms() {



        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL_CI) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        KirimSmsVerifikasiAPI api = adapter.create(KirimSmsVerifikasiAPI.class);

        //Defining the method insertuser of our interface
        api.sendSmsVerifikasiAPI(
                nomorhp,
                kode,

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
                        Toast.makeText(VerifikasiHP.this, "Kirim sms "+output, Toast.LENGTH_LONG).show();
                        String Success = "sukses";
                        if (output.toLowerCase().contains(Success.toLowerCase())) {
                            sukses = true;
                        }



                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(VerifikasiHP.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }



}
