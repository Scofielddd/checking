package checkingapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import checkingapi.model.Checkin;

/**
 * Created by ahmet on 29.10.2016.
 */
class GetCheckins extends AsyncTask<Void, Void, Void> {

    public GetCheckins(ClientMethods clientMethods, int page, String username, String password)
    {
        _ClientMethods=clientMethods;
        _page=page;
        _username=username;
        _password=password;
    }
    ArrayList<Checkin> checkinList;
    ClientMethods _ClientMethods;
    int _page;
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
        String jsonStr = webreq.makeWebServiceCall(Constant.service_URL + "user/checkins/"+_page+"/"+_username+"/"+_password, WebRequest.GETRequest);

        Log.d("Response: ", "> " + jsonStr);

        checkinList = JSONParser.ParseCheckins(jsonStr);

        return null;
    }

    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);

        if (_ClientMethods!=null) _ClientMethods.Checkins(checkinList);
    }

}
