package checkingapi;

import android.os.AsyncTask;
import android.util.Log;

import checkingapi.model.User;

/**
 * Created by ahmet on 30.10.2016.
 */

class GetRegister extends AsyncTask<Void, Void, Void> {

    public GetRegister(ClientMethods clientMethods, String username, String password, String email, String name, String surname)
    {
        _ClientMethods=clientMethods;
        _username=username;
        _password=password;
        _email=email;
        _name=name;
        _surname=surname;
    }
    boolean Res;
    ClientMethods _ClientMethods;
    String _username;
    String _password;
    String _email;
    String _name;
    String _surname;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
// Creating service handler class instance
        WebRequest webreq = new WebRequest();

// Making a request to url and getting response
        String jsonStr = webreq.makeWebServiceCall(Constant.service_URL + "user/register/"+_username+"/"+_password+"/"+_email+"/"+_name+"/"+_surname, WebRequest.GETRequest);

        Log.d("Response: ", "> " + jsonStr);

        Res=(jsonStr.equals(new String("true")));

        return null;
    }

    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);

        if (_ClientMethods!=null) _ClientMethods.Register(Res);
    }

}