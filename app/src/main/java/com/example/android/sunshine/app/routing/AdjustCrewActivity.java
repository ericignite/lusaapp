package com.example.android.sunshine.app.routing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.sunshine.app.LoginActivity;
import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.SettingsActivity;
import com.example.android.sunshine.app.routing.models.CrewMember;

import java.util.ArrayList;

import library.DatabaseHandler;


public class AdjustCrewActivity extends ActionBarActivity {

    private final String LOG_TAG = AdjustCrewActivity.class.getSimpleName();
    ArrayList<CrewMember> currentCrew;
    CurrentCrewListAdapter crewArrayAdapter;
    ListView crewListView;
    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "in onCreate");
        super.onCreate(savedInstanceState);


        setContentView(R.layout.adjust_crew);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new AdjustCrewFragment())
//                    .commit();
//        }

        db = new DatabaseHandler(this);
        currentCrew = db.getCurrentCrew();

        crewListView = (ListView) findViewById(R.id.crew_list);
        crewArrayAdapter = new CurrentCrewListAdapter(this,getLayoutInflater(),currentCrew);
        crewListView.setAdapter(crewArrayAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
        }

        if (id == R.id.action_login){
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }
        if (id == R.id.action_beginday){
            startActivity(new Intent(this, BeginDayActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onStart() {
        Log.v(LOG_TAG, "in onStart");
        super.onStart();
        // The activity is about to become visible.
    }

    @Override
    protected void onResume() {
        Log.v(LOG_TAG, "in onResume");
        super.onResume();
        // The activity has become visible (it is now "resumed").

        currentCrew = db.getCurrentCrew();
        crewArrayAdapter.UpdateCrew(currentCrew);
    }

    @Override
    protected void onPause() {
        Log.v(LOG_TAG, "in onPause");
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }

    @Override
    protected void onStop() {
        Log.v(LOG_TAG, "in onStop");
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }

    @Override
    protected void onDestroy() {
        Log.v(LOG_TAG, "in onDestroy");
        super.onDestroy();
        // The activity is about to be destroyed.
    }

    public void onAddCrewButtonClick(View view) {

        startActivity(new Intent(this, AddCrewActivity.class));
    }


    public void removeMember(CrewMember member)
    {
        db.removeCrewMember(member);

    }


    public static class AdjustCrewFragment extends Fragment {
        Button btnLogin;
        Button btnLinkToRegister;
        TextView loginErrorMsg;
        private final String LOG_TAG = AdjustCrewFragment.class.getSimpleName();
        public AdjustCrewFragment() {
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setHasOptionsMenu(true);
        }
        @Override
        public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.forecastfragment, menu);

        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if (id == R.id.action_refresh) {

                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onStart() {
            super.onStart();

        }
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.adjust_crew, container, false);

            Button current_crew_btn = (Button) rootView.findViewById(R.id.current_crew);
            Button add_crew_btn = (Button) rootView.findViewById(R.id.add_crew);
            Button drive_next_property_btn = (Button) rootView.findViewById(R.id.drive_next_property);
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.


            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.



            current_crew_btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Log.d("Test", "onClickListener ist login_btn");
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                }
            });
            add_crew_btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Log.d("Test", "onClickListener ist login_btn");
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                }
            });

            drive_next_property_btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Log.d("Test", "onClickListener ist login_btn");
                    startActivity(new Intent(getActivity(), PickPropertyActivity.class));

                }
            });

            return rootView;
        }

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("TEXT","something");

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        String savedText = savedInstanceState.getString("TEXT");
        Log.d(LOG_TAG,"RESTORED "+savedText);
    }
}
