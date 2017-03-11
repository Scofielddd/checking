package checkingapi;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by ahmet on 30.10.2016.
 */

class GetEmail extends AsyncTask<Void, Void, Void> {

    public GetEmail(ClientMethods clientMethods, String email)
    {
        _ClientMethods=clientMethods;
        _email=email;
    }
    boolean Res;
    ClientMethods _ClientMethods;
    String _email;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
// Creating service handler class instance
        WebRequest webreq = new WebRequest();

// Making a request to url and getting response
        String jsonStr = webreq.makeWebServiceCall(Constant.service_URL + "user/email/"+_email, WebRequest.GETRequest);

        Log.d("Response: ", "> " + jsonStr);

        Res=(jsonStr.equals(new String("true")));

        return null;
    }

    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);

        if (_ClientMethods!=null) _ClientMethods.Email(Res);
    }

}