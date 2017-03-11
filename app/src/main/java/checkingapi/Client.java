package checkingapi;

import android.content.Context;
import android.util.Log;

import checkingapi.model.User;

/**
 * Created by ahmet on 29.10.2016.
 */
public final class Client {
    private Client(){}
    private static String Username = new String("");
    private static String Password = new String("");
    public static User user=null;
    //public static ClientMethods clientMethods;

    public static void Login(String username, String password, ClientMethods clientMethods)
    {
        if (!(username.isEmpty() || password.isEmpty()))
            new GetLogin(clientMethods,username,password).execute();
    }

    static void LoginRes(User Res, String username, String password, ClientMethods clientMethods)
    {
        if (Res!=null)
        {
            Username=username;
            Password=password;
        }
        else
        {
            Username = new String("");
            Password = new String("");
        }
        user=Res;
        if (clientMethods!=null) clientMethods.Login(user);
    }

    public static User LoginSync(String username, String password, ClientMethods clientMethods)
    {
        if (!(username.isEmpty() || password.isEmpty()))
        {
            WebRequest webreq = new WebRequest();
            // Making a request to url and getting response
            String jsonStr = webreq.makeWebServiceCall(Constant.service_URL + "user/login/"+username+"/"+password, WebRequest.GETRequest);
            Log.d("Response: ", "> " + jsonStr);
            user = JSONParser.ParseLogin(jsonStr);
            LoginRes(user,username,password,clientMethods);
            return user;
        }
        return null;
    }

    public static void Register(String username, String password, String email, String name, String surname, ClientMethods clientMethods)
    {
        if (!(username.isEmpty() || password.isEmpty() || email.isEmpty() || name.isEmpty() || surname.isEmpty()))
        {
            new GetRegister(clientMethods,username,password,email,name,surname).execute();
        }
    }

    public static boolean RegisterSync(String username, String password, String email, String name, String surname, ClientMethods clientMethods)
    {
        boolean Res=false;
        if (!(username.isEmpty() || password.isEmpty() || email.isEmpty() || name.isEmpty() || surname.isEmpty()))
        {
            WebRequest webreq = new WebRequest();
            // Making a request to url and getting response
            String jsonStr = webreq.makeWebServiceCall(Constant.service_URL + "user/register/"+username+"/"+password+"/"+email+"/"+name+"/"+surname, WebRequest.GETRequest);
            Log.d("Response: ", "> " + jsonStr);
            Res = (jsonStr.equals(new String("true")));
        }
        if (clientMethods!=null) clientMethods.Register(Res);
        return Res;
    }

    public static void Username(String username, ClientMethods clientMethods)
    {
        if (!username.isEmpty())
        {
            new GetUsername(clientMethods,username).execute();
        }
    }

    public static void Email(String email, ClientMethods clientMethods)
    {
        if (!email.isEmpty())
        {
            new GetEmail(clientMethods,email).execute();
        }
    }

    public static void Checkins(int page, ClientMethods clientMethods)
    {
        if (!(Username.isEmpty() || Password.isEmpty()))
            new GetCheckins(clientMethods, page, Username, Password).execute();
    }

    public static void Location(double locationX, double locationY, ClientMethods clientMethods)
    {
        if (!(Username.isEmpty() || Password.isEmpty()))
            new GetLocation(clientMethods, locationX, locationY, Username, Password).execute();
    }

    public static void Do(long placeid, ClientMethods clientMethods)
    {
        if (!(Username.isEmpty() || Password.isEmpty()))
            new GetDo(clientMethods,placeid, Username, Password).execute();
    }

    public static void Answer(long checkinid, int answerid, ClientMethods clientMethods)
    {
        if (!(Username.isEmpty() || Password.isEmpty()))
            new GetAnswer(clientMethods,checkinid, answerid, Username, Password).execute();
    }
}