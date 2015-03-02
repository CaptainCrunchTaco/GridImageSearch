package com.example.android.gridimagesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.example.android.gridimagesearch.EndlessScrollListener;
import com.example.android.gridimagesearch.R;
import com.example.android.gridimagesearch.adapters.ImageResultsAdapter;
import com.example.android.gridimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {
    private EditText etQuery;
    private StaggeredGridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private final int REQUEST_CODE = 20;
    private String size = "any";
    private String color = "any";
    private String type = "any";
    private String site = "";
    private String query = "";
    AsyncHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        //Creates the data source
        imageResults = new ArrayList<ImageResult>();
        //Attaches the data source to an adapter
        aImageResults = new ImageResultsAdapter(this, imageResults);
        //Link the adapter to the adapterview (gridview)
        gvResults.setAdapter(aImageResults);
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (StaggeredGridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Launch the image display activity
                //Creating an intent
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                //Get the image result to display
                ImageResult result = imageResults.get(position);
                //Pass image result into intent
                i.putExtra("result", result); //need to be serializable or parcelable
                //Launch new activity
                startActivity(i);

            }
        });
    }

    public void setupScrollListener() {
        gvResults.setOnScrollListener( new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
            // Append more data into the adapter
            public void customLoadMoreDataFromApi(int offset) {
                // This method probably sends out a network request and appends new data items to your adapter.
                // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
                // Deserialize API response and then construct new objects to append to the adapter
                if(isNetworkAvailable()) {
                    String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8" + "&start=" + Integer.toString(offset);
                    client.get(awesomeString(searchUrl), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            JSONArray imageResultsJson = null;
                            try {
                                imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                                //When you make changes to the adapter, it does modify the underlying data
                                aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Internet connection unavailable. Cannot receive more results.", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    // ActivityOne.java, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            size = data.getExtras().getString("size");
            color = data.getExtras().getString("color");
            type = data.getExtras().getString("type");
            site = data.getExtras().getString("site");
            // Toast the name to display temporarily on screen
            Toast.makeText(this, size + " " + color + " " + type + " " + site, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    //String with queries made to imgcolor, imgsz, imgtype, as_sitesearch
    public String awesomeString(String string) {
        if(!size.equals("any")) {
            string += "&imgsz=" + size;
        }
        if(!color.equals("any")) {
            string += "&imgcolor=" + color;
        }
        if(!type.equals("any")) {
            string += "&imgtype=" + type;
        }
        if(!site.equals("")) {
            string += "&as_sitesearch=" + site;
        }
        return string;
    }

    //Whenever button is pressed
    public void onImageSearch(View v) {
        if(isNetworkAvailable()) {
            query = etQuery.getText().toString();
            client = new AsyncHttpClient();
            //https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=8
            String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+ query + "&rsz=8";
            client.get(awesomeString(searchUrl),new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray imageResultsJson = null;
                    try {
                        imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                        imageResults.clear(); //clear the existing images from the array
                        //When you make changes to the adapter, it does modify the underlying data
                        aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setupScrollListener();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        } else {
            imageResults.clear();
            aImageResults.notifyDataSetChanged();
            Toast.makeText(this, "Internet connection unavailable", Toast.LENGTH_LONG).show();
        }

    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(SearchActivity.this, FilterActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
