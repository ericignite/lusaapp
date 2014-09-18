package library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.sunshine.app.routing.models.CrewMember;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
    private final String LOG_TAG = DatabaseHandler.class.getSimpleName();
	// Database Name
	private static final String DATABASE_NAME = "lusa";

	// Login table name
	private static final String TABLE_LOGIN = "login";
	private static final String CREW_LIST = "crewlist";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_LEADER = "leader";
	public static final String KEY_EMAIL = "email";
	private static final String KEY_UID = "uid";
	private static final String KEY_CREATED_AT = "created_at";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
			    + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID
				+ " TEXT," + KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(String name, String age, String email, String uid, String created_at) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name); // Name
		values.put(KEY_EMAIL, email); // Email
		values.put(KEY_UID, uid); // Email
		values.put(KEY_CREATED_AT, created_at); // Created At

		// Inserting Row
		db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
	}

	public void addUser(String email) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EMAIL, email); // Email

		// Inserting Row
		db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
	}

	public NameValuePair getUser() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("email"))
		return null;
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("name", cursor.getString(1));
			user.put("email", cursor.getString(2));
			user.put("uid", cursor.getString(3));
			user.put("created_at", cursor.getString(4));
			user.put("age", cursor.getString(5));
		}
		cursor.close();
		db.close();
		// return user
		return user;
	}

	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	public String returnRows() {
		String response = "";
		String countQuery = "SELECT * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		for (int j = 0; j < getRowCount(); j++) {
			Cursor cursor = db.rawQuery(countQuery, null);
			response += cursor.getColumnName(j);
		}
		Log.v("LT", response);
		return response;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_LOGIN, null, null);
		db.close();
	}
    public void setupCrewList(JSONArray data) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            try {
                Log.e(LOG_TAG, "DELETING table " + CREW_LIST + "because it may exist!");
                db.delete(CREW_LIST, null, null);
            } catch (SQLiteException e) {
                Log.d(LOG_TAG,"could not delete crew list");
            }

            try {
                Log.e(LOG_TAG, "Creating table " + CREW_LIST + "because it doesn't exist!" );
                String CREATE_CREW_LIST_TABLE = "CREATE TABLE " + CREW_LIST + "("
                        + KEY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_NAME + " TEXT,"
                        + KEY_LEADER + " INTEGER"
                        + ")";
                db.execSQL(CREATE_CREW_LIST_TABLE);
            } catch (SQLiteException e){
               Log.e(LOG_TAG,"error creating crew list");
            }


            // Inserting Rows

            for (int zz = 0; zz <= data.length();zz++){
                ContentValues values = new ContentValues();


                    JSONObject obj = data.getJSONObject(zz);
                    values.put(KEY_ID, obj.getString("id"));
                    values.put(KEY_NAME, obj.getString("name"));
                    values.put(KEY_LEADER, obj.getInt("leader"));

                    db.insert(CREW_LIST, null, values);
                    Log.v(LOG_TAG,"inserted id:"+ obj.getString("id")+ ", name: "+  obj.getString("name")+ ", leader: "+  obj.getInt("leader"));

            }


        } catch (JSONException e){

            if (e.getMessage().toString().contains("no such table")){
                Log.e(LOG_TAG, "Creating table " + CREW_LIST + "because it doesn't exist!" );
                // create table
                // re-run query, etc.
            }
        }

    }

    public void addCrewMember(CrewMember member) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID, member.id);
        values.put(KEY_NAME, member.name);
        values.put(KEY_LEADER, member.leader);

        db.insert(CREW_LIST, null, values);
        db.close();

    }

    public void removeCrewMember(CrewMember member)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(CREW_LIST,KEY_ID + "=" + member.id,null);

        db.close();
    }

    public ArrayList<CrewMember> getCurrentCrew(){

        SQLiteDatabase db = this.getWritableDatabase();
        String crewQuery = "SELECT id,name,leader FROM " + CREW_LIST;
        Cursor cur = db.rawQuery(crewQuery, null);
        ArrayList<CrewMember> arr = new ArrayList<CrewMember>();

        while (cur.moveToNext()) {

            int idIndex = cur.getColumnIndex(KEY_ID);
            int nameIndex = cur.getColumnIndex(KEY_NAME);
            int leaderIndex = cur.getColumnIndex(KEY_LEADER);

            CrewMember member = new CrewMember(cur.getString(idIndex),cur.getString(nameIndex),cur.getString(leaderIndex));

            arr.add(member);

        }

        db.close();

        return arr;
    }
}

