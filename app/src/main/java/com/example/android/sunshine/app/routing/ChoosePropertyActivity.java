package com.example.android.sunshine.app.routing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.routing.models.PropertyInRoute;

import java.util.ArrayList;

import library.DatabaseHandler;
import library.UserFunctions;

public class ChoosePropertyActivity extends Activity implements AsyncArrayListReceiver {
    ListView listView;
    ChoosePropertyListAdapter adapter;

    ArrayList<PropertyInRoute> allProperties = new ArrayList<PropertyInRoute>();
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_property);

        listView = (ListView) findViewById(R.id.property_list);
        db = new DatabaseHandler(this);

        UserFunctions.getCrewRoute(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO start drive time
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_property, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startDriveTime(PropertyInRoute property) {
        // TODO start drive activity with timer
    }
    public void onOutOfRouteClick(View view) {

        startActivity(new Intent(this, ChooseOutOfRouteActivity.class));
    }
    public void onReceivedArrayList(ArrayList list) {
        allProperties = list;

        // Pass results to ListViewAdapter Class
        adapter = new ChoosePropertyListAdapter(this, getLayoutInflater(), allProperties);

        // Binds the Adapter to the ListView
        listView.setAdapter(adapter);

    }
}
