package dietapp.dietapp.Shared;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;



public class SharedListFoodHelper {

    // The injected SharedPreferences implementation to use for persistence.
    private SharedPreferences prefs;


    // Constructor with dependency injection.
    public SharedListFoodHelper(SharedPreferences sharedPreferences) {
        prefs = sharedPreferences;
    }

    //SHARED PREFERENCES Save ArrayList
    public boolean saveArrayList(SharedListFood list) {
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list.getMlist()); //put in json the list from my model(SharedFoodList) which is the list i provide(itemsAdded)
        editor.putString("testShared", json);
        return editor.commit();     // This line is IMPORTANT !!!
    }

    //SHARED PREFERENCES Load ArrayList
    public ArrayList<String> getArrayList() {

        ArrayList<String> loadArrayList;

        Gson gson = new Gson();
        String json = prefs.getString("testShared", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        loadArrayList= gson.fromJson(json, type);
        return loadArrayList;

    }

}