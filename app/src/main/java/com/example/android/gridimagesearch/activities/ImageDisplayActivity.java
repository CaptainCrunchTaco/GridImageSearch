package com.example.android.gridimagesearch.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.gridimagesearch.R;
import com.example.android.gridimagesearch.TouchImageView;
import com.example.android.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        //Remove the action bar on image display activity
        getSupportActionBar().hide();
        //Check internet connection
        if(isNetworkAvailable()) {
            //Pull out the url from the intent
            ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
            //Find the image view
            TouchImageView ivImageResult = (TouchImageView) findViewById(R.id.ivImageResult);
            //Load the image url into the imageview using picasso
            Picasso.with(this).load(result.fullUrl).into(ivImageResult);
        } else {
            Toast.makeText(this, "Internet Connection Unavailable", Toast.LENGTH_LONG).show();
        }

    }

    //Check internet connection
    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
