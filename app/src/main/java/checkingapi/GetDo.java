package checkingapi;

import android.os.AsyncTask;
import android.util.Log;

import checkingapi.model.Question;
import checkingapi.model.User;

/**
 * Created by ahmet on 30.10.2016.
 */

class GetDo  extends AsyncTask<Void, Void, Void> {

    public GetDo(ClientMethods clientMethods, long placeid, String username, String password)
    {
        _ClientMethods=clientMethods;
        _placeid=placeid;
        _username=username;
        _password=password;
    }
    Question question;
    ClientMethods _ClientMethods;
    long _placeid;
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
        String jsonStr = webreq.makeWebServiceCall(Constant.service_URL + "checkin/do/"+_placeid+"/"+_username+"/"+_password, WebRequest.GETRequest);

        Log.d("Response: ", "> " + jsonStr);

        question = JSONParser.ParseDo(jsonStr);

        return null;
    }

    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);

        if (_ClientMethods!=null) _ClientMethods.Do(question);
    }

}
