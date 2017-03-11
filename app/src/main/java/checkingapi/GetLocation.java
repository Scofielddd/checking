package checkingapi;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import checkingapi.model.Checkin;
import checkingapi.model.Place;

/**
 * Created by ahmet on 30.10.2016.
 */

class GetLocation extends AsyncTask<Void, Void, Void> {

    public GetLocation(ClientMethods clientMethods, double locationX, double locationY, String username, String password)
    {
        _ClientMethods=clientMethods;
        _locationX=locationX;
        _locationY=locationY;
        _username=username;
        _password=password;
    }
    ArrayList<Place> placeList;
    ClientMethods _ClientMethods;
    double _locationX;
    double _locationY;
    String _username;
    String _password;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
// Creating service handler class instance
        WebRequest webreq = new WebRequest();

// Making a request to url and getting response
        String jsonStr = webreq.makeWebServiceCall(Constant.service_URL + "checkin/location/"+_locationX+"/"+_locationY+"/"+_username+"/"+_password, WebRequest.GETRequest);

        Log.d("Response: ", "> " + jsonStr);

        placeList = JSONParser.ParseLocation(jsonStr);

        return null;
    }

    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);

        if (_ClientMethods!=null) _ClientMethods.Location(placeList);
    }

}