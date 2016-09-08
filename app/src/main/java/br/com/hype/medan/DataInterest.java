package br.com.hype.medan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DataInterest extends AppCompatActivity {




    //Root URL of our web service
    public static final String ROOT_URL = "http://api.hypemedan.id/";

    //Strings to bind with intent will be used to send data to other activity
    public static final String KEY_INTEREST_ID = "id";
    public static final String KEY_INTEREST_HASTAG = "hastag";

    String hashtag_kirim;

    //List of type books this list will store type Book which is our data model
    private List<Interest> interest;

    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_interest);
        getInterest();

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(DataInterest.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });


    }

    public void getInterest(){
        //While the app fetched data we are displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        InterestAPI api = adapter.create(InterestAPI.class);

        //Defining the method
        api.getInterest(new Callback<List<Interest>>() {
            @Override
            public void success(List<Interest> list, Response response) {
                //Dismissing the loading progressbar
                loading.dismiss();

                //Storing the data in our list
                interest = list;

                //Calling a method to show the list

                showInterest();
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
            }
        });
    }

    //Our method to show list
    private void showInterest(){
        //String array to store all the book names
        String[] items = new String[interest.size()];

        final LinearLayout lm = (LinearLayout) findViewById(R.id.layoutBtnInterest);

        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        //Traversing through the whole list to get all the names
        for(int i=0; i<interest.size(); i++){
            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);

            // Create Button
            final Button btn = new Button(this);

            // Give button an ID
            btn.setId(i+1);
            btn.setText(interest.get(i).getHastag());
            // set the layoutParams on the button
            btn.setLayoutParams(params);


            // Set click listener for button
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String k = String.valueOf(btn.getId());
                    Toast.makeText(getApplicationContext(),
                            "Clicked Button Id :" + k,
                            Toast.LENGTH_LONG).show();

                    hashtag_kirim += k+",";

                }
            });

            //Add button to LinearLayout
            ll.addView(btn);
            //Add button to LinearLayout defined in XML
            lm.addView(ll);

        }




    }


    private void kirimHastag() {


        //Here we will handle the http request to insert user to mysql db
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        HashtagAPI api = adapter.create(HashtagAPI.class);

        //Defining the method insertuser of our interface
        api.kirimHastagAPI(

                hashtag_kirim,

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
                        Toast.makeText(DataInterest.this, output, Toast.LENGTH_LONG).show();
                        String Success = "sukses";
                        if (output.toLowerCase().contains(Success.toLowerCase())) {

                            Intent i = new Intent(DataInterest.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(DataInterest.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }




}
