package checkingapi;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by ahmet on 30.10.2016.
 */

class GetAnswer  extends AsyncTask<Void, Void, Void> {

    public GetAnswer(ClientMethods clientMethods, long checkinid, int answerid, String username, String password)
    {
        _ClientMethods=clientMethods;
        _checkinid=checkinid;
        _answerid=answerid;
        _username=username;
        _password=password;
    }
    boolean Res;
    ClientMethods _ClientMethods;
    long _checkinid;
    int _answerid;
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
        String jsonStr = webreq.makeWebServiceCall(Constant.service_URL + "checkin/answer/"+_checkinid+"/"+_answerid+"/"+_username+"/"+_password, WebRequest.GETRequest);

        Log.d("Response: ", "> " + jsonStr);

        Res=(jsonStr.equals(new String("true")));

        return null;
    }

    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);

        if (_ClientMethods!=null) _ClientMethods.Answer(Res);
    }

}