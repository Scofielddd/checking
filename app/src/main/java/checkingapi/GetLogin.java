package checkingapi;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import checkingapi.model.Checkin;
import checkingapi.model.User;

/**
 * Created by ahmet on 29.10.2016.
 */

class GetLogin extends AsyncTask<Void, Void, Void> {

    public GetLogin(ClientMethods clientMethods, String username, String password)
    {
        _ClientMethods=clientMethods;
        _username=username;
        _password=password;
    }
    User user;
    ClientMethods _ClientMethods;
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
        String jsonStr = webreq.makeWebServiceCall(Constant.service_URL + "user/login/"+_username+"/"+_password, WebRequest.GETRequest);

        Log.d("Response: ", "> " + jsonStr);

        user = JSONParser.ParseLogin(jsonStr);

        return null;
    }

    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);

        Client.LoginRes(user,_username,_password, _ClientMethods);
    }

}
