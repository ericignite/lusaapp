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
import android.widget.TextView;

import com.example.android.sunshine.app.LoginActivity;
import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.SettingsActivity;


public class PickPropertyActivity extends ActionBarActivity {

    private final String LOG_TAG = PickPropertyActivity.class.getSimpleName();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "in onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PickPropertyFragment())
                    .commit();
        }
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
    public static class PickPropertyFragment extends Fragment {
        Button btnLogin;
        Button btnLinkToRegister;
        TextView loginErrorMsg;
        private final String LOG_TAG = PickPropertyFragment.class.getSimpleName();
        public PickPropertyFragment() {
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
            View rootView = inflater.inflate(R.layout.pick_property, container, false);

            Button adjust_crew_btn = (Button) rootView.findViewById(R.id.adjust_crew);


            adjust_crew_btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Log.d("Test", "onClickListener ist login_btn");
                    startActivity(new Intent(getActivity(), AdjustCrewActivity.class));

                }
            });
            return rootView;
        }
    }
}
