package com.example.android.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Eric Johnson on 9/10/2014.
 */
public  class HomeFragment extends Fragment {
    Button btnLogin;
    Button btnLinkToRegister;
    TextView loginErrorMsg;
    private final String LOG_TAG = HomeFragment.class.getSimpleName();
    public HomeFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnLogin = (Button) getActivity().findViewById(R.id.btnStartLogin);
        btnLinkToRegister = (Button) getActivity().findViewById(R.id.btnLinkToRegisterScreen);

        // Login button Click Event
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//                Log.v(LOG_TAG,"CLICKED LOGIN ON HOME");
//
//            }
//        });
//
//        // Link to Register Screen
//        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//                Log.v(LOG_TAG,"CLICKED LOGIN ON HOME");
//
//            }
//        });
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
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Button login_btn = (Button) rootView.findViewById(R.id.home_btnStartLogin);
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.


        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        login_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("Test", "onClickListener ist login_btn");
                startActivity(new Intent(getActivity(), LoginActivity.class));

            }
        });
        return rootView;
    }
}
