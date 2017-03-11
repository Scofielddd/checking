package checkingapi;

import java.util.ArrayList;

import checkingapi.model.Checkin;
import checkingapi.model.Place;
import checkingapi.model.Question;
import checkingapi.model.User;

/**
 * Created by ahmet on 29.10.2016.
 */
public interface ClientMethods {
    void Checkins(ArrayList<Checkin> checkins);
    void Login(User user);
    void Register(boolean result);
    void Username(boolean result);
    void Email(boolean result);
    void Location(ArrayList<Place> places);
    void Do(Question question);
    void Answer(boolean result);
}