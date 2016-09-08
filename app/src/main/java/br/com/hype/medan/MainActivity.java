package br.com.hype.medan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.hype.medan.login.Auth;
import br.com.hype.medan.login.FacebookAuth;
import br.com.hype.medan.login.GoogleAuth;
import br.com.hype.medan.login.SimpleAuthListener;
import br.com.hype.medan.login.SocialProfile;
import br.com.hype.medan.login.TwitterAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static final String DEFAULT_COVER = "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQN9FVv2cnfoEW7pRKDh0OubDnHEG_BCg-jTEzYHzeaO7vIGbNo";
    static final String DEFAULT_IMAGE = "http://static.squarespace.com/static/523ce201e4b0cd883dbb8bbf/t/53bf2302e4b06e125c374401/1405035267633/profile-icon.png";
    final int REQUEST_CODE = 1000;

    Toolbar toolbar;
    NavigationView navigationView;
    TextView name;
    TextView email;
    TextView emailView;
    ImageView cover;
    ImageView photo;
    EditText shareContent;
    TextView pickFilename;
    Button pickFile;
    Button share;
    String socialNetwork;

    Uri imageOrVideo;
    Auth auth;

    private SimpleAuthListener authListener = new SimpleAuthListener() {
        @Override
        public void onRevoke() {
            logoutUser();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.social_toolbar);
        navigationView = (NavigationView) findViewById(R.id.social_menu);
        name = (TextView) findViewById(R.id.social_name);
        email = (TextView) findViewById(R.id.social_email);

        cover = (ImageView) findViewById(R.id.social_cover);
        photo = (ImageView) findViewById(R.id.social_photo);

        setSupportActionBar(toolbar);
        setupUserInfo();

        //create correct auth manager according user account
        if (socialNetwork.equals(SocialProfile.FACEBOOK)){
            auth = new FacebookAuth(this, authListener);
        } else if(socialNetwork.equals(SocialProfile.GOOGLE)){
            auth = new GoogleAuth(this, authListener);
            auth.login();
        }else{
            auth = new TwitterAuth(this, authListener);
        }

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (auth instanceof GoogleAuth){
            //disconnect google client api
            auth.logout();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_logout){
            if(socialNetwork != null){
                auth.revoke();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            imageOrVideo = data.getData();
            pickFilename.setText(imageOrVideo.getPath());
        }
    }

    @Override
    public void onClick(View view) {
       /*
        int viewId = view.getId();

        if(viewId == R.id.btnHappening){
            Log.v("Klik Respon", " Happening di Klik");
        }else if(viewId == R.id.btnToday){
            Log.v("Klik Respon", " Today di Klik");
        }

        */

    }

    public void happening(View view){

        Intent k = new Intent(MainActivity.this, HargaActivity.class);
        startActivity(k);

    }

    public void today(View view){

        System.out.println("Today Sudah di Klik");

    }



    private void setupUserInfo(){
        Picasso picasso = Picasso.with(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String socialNetworkName = preferences.getString(LoginActivity.USER_SOCIAL, null);
        socialNetwork = socialNetworkName;

        String profileName =  preferences.getString(LoginActivity.PROFILE_NAME, "");

        if (socialNetwork.equals(SocialProfile.TWITTER)){
            name.setText("@" + profileName);
        }else{
            name.setText(profileName);
        }

        email.setText(preferences.getString(LoginActivity.PROFILE_EMAIL, ""));

        picasso.load(preferences.getString(LoginActivity.PROFILE_COVER, DEFAULT_COVER))
                .into(cover);

        picasso.load(preferences.getString(LoginActivity.PROFILE_IMAGE, DEFAULT_IMAGE))
                .transform(new RoundedTransformation(getResources()))
                .into(photo);
    }

    private void logoutUser(){
        //clear share preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit()
                         .clear()
                         .apply();

        //clear back stack activity and back to login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finishAffinity();
    }
}
