package com.example.android.sunshine.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sunshine.app.routing.BeginDayActivity;

import org.json.JSONException;
import org.json.JSONObject;

import library.DatabaseHandler;
import library.JSONParser;
import library.UserFunctions;


public class LoginActivity extends Activity {

    private static boolean autoLogin = true;

    Button btnLogin;
    Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
    private final String LOG_TAG = LoginActivity.class.getSimpleName();
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_NAME2 = "age";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    private String re;

 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);


        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {

                ExecuteLogin();
                
            }
        });
 
        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        if (autoLogin)
            ExecuteLogin();
    }

    void ExecuteLogin()
    {
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Logging in...");
        LoginTask loginTask = new LoginTask(LoginActivity.this, progressDialog);
        loginTask.execute();


    }
    
    public void showLoginError(int responseCode) {
    	int duration = Toast.LENGTH_LONG;
    	Context context = getApplicationContext();
		Toast toast = Toast.makeText(context, "Login Error", duration);
		toast.show();
    }


    public class LoginTask extends AsyncTask<String, Void, JSONObject> {

        private ProgressDialog progressDialog;
        private LoginActivity activity;
        private int id = -1;
        private JSONParser jsonParser;
        private  String loginURL = "http://davegrounds.com/android_login_apitwo/";
        private  String registerURL = "http://davegrounds.com/android_login_apitwo/";
        private  String KEY_SUCCESS = "success";
        private  String KEY_ERROR = "error";
        private  String KEY_ERROR_MSG = "error_msg";
        private  String KEY_UID = "uid";
        private  String KEY_NAME = "name";
        private  String KEY_NAME2 = "age";
        private  String KEY_EMAIL = "email";
        private  String KEY_CREATED_AT = "created_at";
        private int responseCode = 0;
        private JSONObject crew = null;
        public LoginTask(LoginActivity activity, ProgressDialog progressDialog)
        {
            this.activity = activity;
            this.progressDialog = progressDialog;
        }

        @Override
        protected void onPreExecute()
        {
            progressDialog.show();
        }

        protected JSONObject doInBackground(String... arg0) {
            EditText userName = (EditText) activity.findViewById(R.id.loginEmail);
            EditText passwordEdit = (EditText) activity.findViewById(R.id.loginPassword);
            String email = userName.getText().toString();
            String password = passwordEdit.getText().toString();

            if (autoLogin) {
                email = "riley";
                password = "1";
            }

            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.loginUser(email, password);


            // check for login response
            try {
                if (json.getString(KEY_SUCCESS) != null) {
                    String res = json.getString(KEY_SUCCESS);

                    if (Integer.parseInt(res) == 1) {
                        String crewid = json.getJSONObject("user").getString("crewid").toString();
                        crew = userFunction.getCrewList(crewid );
                        //user successfully logged in
                        // Store user details in SQLite Database
                        DatabaseHandler db = new DatabaseHandler(activity.getApplicationContext());
                        JSONObject json_user = json.getJSONObject("user");
                        //Log.v("name", json_user.getString(KEY_NAME));
                        // Clear all previous data in database
                        //userFunction.logoutUser(activity.getApplicationContext());
                        //db.addUser(json_user.getString(KEY_EMAIL));

                        responseCode = 1;
                        // Close Login Screen
                        //finish();

                    } else {
                        responseCode = 0;
                        // Error in login
                    }
                }

            } catch (NullPointerException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject answer = new JSONObject();
                answer.put("responseCode", responseCode);
                answer.put("crew",crew);
                return answer;
            } catch (JSONException e) {
                return null;
            }

        }
        @Override
        protected void onPostExecute(JSONObject responseCode)
        {
            EditText userName = (EditText)activity.findViewById(R.id.loginEmail);
            EditText passwordEdit = (EditText)activity.findViewById(R.id.loginPassword);

            try {
                if (responseCode.getInt("responseCode") == 1) {
                    progressDialog.dismiss();
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    db.setupCrewList(responseCode.getJSONObject("crew").getJSONArray("crew"));
                    startActivity(new Intent(getApplicationContext(), BeginDayActivity.class));
                    //activity.loginReport(responseCode);
                } else {
                    progressDialog.dismiss();
                    activity.showLoginError(responseCode.getInt("responseCode"));

                }
            } catch (JSONException e) {
                Log.v(LOG_TAG,e.getMessage());
            }
        }
    }
}