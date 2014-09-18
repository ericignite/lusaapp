package com.example.android.sunshine.app.routing;

    import android.app.Activity;
    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.EditText;
    import android.widget.ListView;

    import com.example.android.sunshine.app.R;
    import com.example.android.sunshine.app.routing.models.PropertyInRoute;

    import java.util.ArrayList;
    import java.util.Locale;

    import library.DatabaseHandler;
    import library.UserFunctions;

    public class ChooseOutOfRouteActivity extends Activity implements AsyncArrayListReceiver {
        ListView listView;
        ChoosePropertyListAdapter adapter;
        EditText editsearch;
        ArrayList<PropertyInRoute> allProperties = new ArrayList<PropertyInRoute>();
        DatabaseHandler db;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select_out_of_route_property);

            listView = (ListView) findViewById(R.id.property_list);
            db = new DatabaseHandler(this);

            UserFunctions.getAllProperties(this);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //TODO start drive time
                }
            });
            editsearch = (EditText) findViewById(R.id.property_search_filter);

            // Capture Text in EditText
            editsearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                    String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                    // TODO Auto-generated method stub
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

    public void onReceivedArrayList(ArrayList list) {
        allProperties = list;

        // Pass results to ListViewAdapter Class
        adapter = new ChoosePropertyListAdapter(this, getLayoutInflater(), allProperties);

        // Binds the Adapter to the ListView
        listView.setAdapter(adapter);

    }
}
