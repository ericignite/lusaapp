package com.example.android.sunshine.app.routing;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.routing.models.CrewMember;

import java.util.ArrayList;
import java.util.Locale;

import library.DatabaseHandler;
import library.UserFunctions;

public class AddCrewActivity extends Activity implements AsyncArrayListReceiver {

    ListView listView;
    AddCrewListAdapter adapter;
    EditText editsearch;
    ArrayList<CrewMember> allMembers = new ArrayList<CrewMember>();
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crew);

        listView = (ListView) findViewById(R.id.all_members_list);

        db = new DatabaseHandler(this);

        UserFunctions.getAllMembers(this);

        editsearch = (EditText) findViewById(R.id.member_search_filter);

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

    public void onReceivedArrayList(ArrayList list) {
        allMembers = list;

        // Pass results to ListViewAdapter Class
        adapter = new AddCrewListAdapter(this, getLayoutInflater(),allMembers);

        // Binds the Adapter to the ListView
        listView.setAdapter(adapter);

    }

    public void AddMember(CrewMember member)
    {
        db.addCrewMember(member);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_crew, menu);
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
}
