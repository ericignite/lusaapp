package library;

import android.content.Context;

import com.example.android.sunshine.app.routing.AsyncArrayListReceiver;
import com.example.android.sunshine.app.routing.models.CrewMember;
import com.example.android.sunshine.app.routing.models.PropertyInRoute;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFunctions {
    private final String LOG_TAG = UserFunctions.class.getSimpleName();
    private static JSONParser jsonParser;

    private static String loginURL = "http://app.landscapesusa.com/main/logincheck/";
    private static String crewListURL = "http://app.landscapesusa.com/main/getCrewList/";
    private static String getAllMembersURL = "http://app.landscapesusa.com/main/getAllMembers/";
    private static String getAllPropertiesURL = "http://app.landscapesusa.com/main/getAllProperties/";
    private static String getCrewRouteURL = "http://app.landscapesusa.com/main/getCrewRoute/";
    private static String registerURL = "http://app.landscapesusa.com/main/register/";

    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String question_tag = "question";
    private static ArrayList<CrewMember> memberList;
    private static ArrayList<PropertyInRoute> propertyList;
    private static String crewID = "1";

    // constructor
    public UserFunctions() {

        if (jsonParser == null)
            jsonParser = new JSONParser();
    }

    //login with user provided email/pass
    public JSONObject loginUser(String email, String password) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("user", email));
        params.add(new BasicNameValuePair("pwd", password));
        //Log.v("userfunctions", "loginuser");
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        return json;
    }

    public static void getAllMembers(final AsyncArrayListReceiver receiver) {
        if (memberList != null) {
            receiver.onReceivedArrayList(memberList);
        }

        // Create a client to perform networking
        AsyncHttpClient client = new AsyncHttpClient();

        // Have the client get a JSONArray of data
        // and define how to respond
        client.get(getAllMembersURL,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject jsonObject)
                    {
                        memberList = new ArrayList<CrewMember>();

                        try {

                       //     JSONObject membersObj = jsonObject.optJSONObject("members");
                            JSONArray members = jsonObject.optJSONArray("members");

                            for (int i = 0; i < members.length(); i++) {
                                JSONObject memberObj = members.getJSONObject(i);

                                CrewMember member = new CrewMember(
                                        memberObj.getString("id"),
                                        memberObj.getString("name"),
                                        null);
                                memberList.add(member);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        receiver.onReceivedArrayList(memberList);

                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {}
                });



    }
    public static void getCrewRoute(final AsyncArrayListReceiver receiver) {
        if (propertyList != null) {
            receiver.onReceivedArrayList(propertyList);
        }

        // Create a client to perform networking
        AsyncHttpClient client = new AsyncHttpClient();

        // Have the client get a JSONArray of data
        // and define how to respond
        client.get(getCrewRouteURL+"/?id="+crewID,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject jsonObject)
                    {

                        propertyList = new ArrayList<PropertyInRoute>();

                        try {

                       //     JSONObject membersObj = jsonObject.optJSONObject("members");
                            JSONArray jsonResult = jsonObject.optJSONArray("properties");

                            for (int i = 0; i < jsonResult.length(); i++) {
                                JSONObject jsonObj = jsonResult.getJSONObject(i);

                                PropertyInRoute propertyItem = new PropertyInRoute(
                                        jsonObj.getString("name"),
                                        jsonObj.getString("budget_hrs"),
                                        jsonObj.getString("shortname")
                                );
                                propertyList.add(propertyItem);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        receiver.onReceivedArrayList(propertyList);

                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {}
                });



    }
    public static void getAllProperties(final AsyncArrayListReceiver receiver) {
        if (propertyList != null) {
            receiver.onReceivedArrayList(propertyList);
        }

        // Create a client to perform networking
        AsyncHttpClient client = new AsyncHttpClient();

        // Have the client get a JSONArray of data
        // and define how to respond
        client.get(getAllPropertiesURL,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject jsonObject)
                    {

                        propertyList = new ArrayList<PropertyInRoute>();

                        try {

                       //     JSONObject membersObj = jsonObject.optJSONObject("members");
                            JSONArray jsonResult = jsonObject.optJSONArray("properties");

                            for (int i = 0; i < jsonResult.length(); i++) {
                                JSONObject jsonObj = jsonResult.getJSONObject(i);

                                PropertyInRoute propertyItem = new PropertyInRoute(
                                        jsonObj.getString("name"),
                                        jsonObj.getString("budget_hrs"),
                                        jsonObj.getString("shortname")
                                );
                                propertyList.add(propertyItem);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        receiver.onReceivedArrayList(propertyList);

                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {}
                });



    }

    public JSONObject getCrewList(String crewid) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("id", crewid));

        JSONObject allMembers = jsonParser.getJSONFromUrl(getAllMembersURL, params);


        JSONObject json = jsonParser.getJSONFromUrl(crewListURL, params);
        // return json
        return json;
    }

    //register a new user with name/email/pass
    public JSONObject registerUser(String name, String age, String email, String password) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("age", age));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;
    }

    //determine if the user is logged in
    public boolean isUserLoggedIn(Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if (count > 0) {
            // user logged in
            return true;
        }
        return false;
    }

    //logout the user
    public boolean logoutUser(Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
}