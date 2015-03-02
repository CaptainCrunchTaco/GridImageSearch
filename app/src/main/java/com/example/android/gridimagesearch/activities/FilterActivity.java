package com.example.android.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.gridimagesearch.R;

public class FilterActivity extends ActionBarActivity {

    public Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        addItemsOnImageSize();
        addItemsOnColorFilter();
        addItemsOnImageType();
        btnSave = (Button) findViewById(R.id.btnSave);
    }

    public void addItemsOnImageSize() {
        Spinner spinner = (Spinner) findViewById(R.id.size_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.size_arrays, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
    public void addItemsOnColorFilter() {
        Spinner spinner = (Spinner) findViewById(R.id.color_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.color_arrays, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
    public void addItemsOnImageType() {
        Spinner spinner = (Spinner) findViewById(R.id.type_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_arrays, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void buttonSave(View view) {
        EditText etSite = (EditText) findViewById(R.id.etSite);
        Spinner sizeSpinner = (Spinner)findViewById(R.id.size_spinner);
        Spinner colorSpinner = (Spinner)findViewById(R.id.color_spinner);
        Spinner typeSpinner = (Spinner)findViewById(R.id.type_spinner);
        String sizeString = sizeSpinner.getSelectedItem().toString();
        String colorString = colorSpinner.getSelectedItem().toString();
        String typeString = typeSpinner.getSelectedItem().toString();
        String site = etSite.getText().toString();
        Intent data = new Intent();
        data.putExtra("size",sizeString);
        data.putExtra("color",colorString);
        data.putExtra("type", typeString);
        data.putExtra("site", site);
        setResult(RESULT_OK, data);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
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
