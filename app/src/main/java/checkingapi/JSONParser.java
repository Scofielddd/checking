package checkingapi;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import checkingapi.model.*;
/**
 * Created by ahmet on 29.10.2016.
 */
class JSONParser {
    // JSON Node names for Checkin
    private static final String TAG_CHECKIN_PLACENAME = "PlaceName";
    private static final String TAG_CHECKIN_DATETIME = "Dt";
    private static final String TAG_CHECKIN_POINT = "Point";

    //JSON Node names for User
    private static final String TAG_USER_USERNAME = "Username";
    private static final String TAG_USER_EMAIL = "Email";
    private static final String TAG_USER_NAME = "Name";
    private static final String TAG_USER_SURNAME = "Surname";
    private static final String TAG_USER_POINT = "Point";

    //JSON Node names for Place
    private static final String TAG_PLACE_ID = "Id";
    private static final String TAG_PLACE_NAME = "Name";
    private static final String TAG_PLACE_LOCATIONX = "LocationX";
    private static final String TAG_PLACE_LOCATIONY = "LocationY";

    //JSON Node names for Question
    private static final String TAG_QUESTION_CHECKINID = "CheckinId";
    private static final String TAG_QUESTION_QUEST = "Quest";
    private static final String TAG_QUESTION_A = "A";
    private static final String TAG_QUESTION_B = "B";
    private static final String TAG_QUESTION_C = "C";
    private static final String TAG_QUESTION_D = "D";

    public static ArrayList<Checkin> ParseCheckins(String json) {
        if (json != null) {
            try {
                ArrayList<Checkin> checkinList = new ArrayList<Checkin>();

                // Getting JSON Array node
                JSONArray checkins = new JSONArray(json);

                // looping through All Checkins
                for (int i = 0; i < checkins.length(); i++) {
                    JSONObject c = checkins.getJSONObject(i);

                    String placename = c.getString(TAG_CHECKIN_PLACENAME);
                    String datetime = c.getString(TAG_CHECKIN_DATETIME);
                    long point = c.getLong(TAG_CHECKIN_POINT);

                    Checkin checkin = new Checkin();
                    checkin.Placename=placename;
                    try {
                        checkin.Dt = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS").parse(datetime);
                    } catch (ParseException e){
                        checkin.Dt = new Date();
                    }
                    checkin.Point=point;
                    // adding checkin to checkins list
                    checkinList.add(checkin);
                }
                return checkinList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }
    }


    public static User ParseLogin(String json) {
        if (json != null) {
            try {
                User _user = new User();

                // Getting JSON Array node
                JSONObject user = new JSONObject(json);

                String username = user.getString(TAG_USER_USERNAME);
                String email = user.getString(TAG_USER_EMAIL);
                String name = user.getString(TAG_USER_NAME);
                String surname = user.getString(TAG_USER_SURNAME);
                long point = user.getLong(TAG_USER_POINT);

                _user.Username=username;
                _user.Email=email;
                _user.Name=name;
                _user.Surname=surname;
                _user.Point=point;

                return _user;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }
    }

    public static ArrayList<Place> ParseLocation(String json) {
        if (json != null) {
            try {
                ArrayList<Place> placeList = new ArrayList<Place>();

                // Getting JSON Array node
                JSONArray places = new JSONArray(json);

                // looping through All places
                for (int i = 0; i < places.length(); i++) {
                    JSONObject c = places.getJSONObject(i);

                    long id = c.getLong(TAG_PLACE_ID);
                    String name = c.getString(TAG_PLACE_NAME);
                    double locationX = c.getDouble(TAG_PLACE_LOCATIONX);
                    double locationY = c.getDouble(TAG_PLACE_LOCATIONY);


                    Place place = new Place();
                    place.Id=id;
                    place.Name=name;
                    place.LocationX=locationX;
                    place.LocationY=locationY;

                    // adding place to places list
                    placeList.add(place);
                }
                return placeList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }
    }

    public static Question ParseDo(String json) {
        if (json != null) {
            try {
                Question _question = new Question();

                // Getting JSON Array node
                JSONObject question = new JSONObject(json);

                long checkinid = question.getLong(TAG_QUESTION_CHECKINID);
                String quest = question.getString(TAG_QUESTION_QUEST);
                String a = question.getString(TAG_QUESTION_A);
                String b = question.getString(TAG_QUESTION_B);
                String c = question.getString(TAG_QUESTION_C);
                String d = question.getString(TAG_QUESTION_D);

                _question.CheckinId=checkinid;
                _question.Quest=quest;
                _question.A=a;
                _question.B=b;
                _question.C=c;
                _question.D=d;

                return _question;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }
    }
}
