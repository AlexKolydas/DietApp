package dietapp.dietapp.AddFood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dietapp.dietapp.R;

import static dietapp.dietapp.AddFood.AddFood.count;
import static dietapp.dietapp.AddFood.AddFood.countTextbasket;
import static dietapp.dietapp.AddFood.AddFood.itemsAdded;

public class AddFoodBasket extends AppCompatActivity {

    ListView alreadyAddedFoodtest;
    ArrayAdapter<String> addedAdapter;
    static ArrayList<String> ar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_basket);

        alreadyAddedFoodtest = (ListView) findViewById(R.id.alreadyAddedList);
        registerForContextMenu(alreadyAddedFoodtest);

        getArrayList();//Shared preferences get List
        alreadyAdded();

    }

    public ArrayList<String> getArrayList(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AddFoodBasket.this);
        Gson gson = new Gson();
        String json = prefs.getString("testShared", null);
        if(json==null){
            Toast.makeText(getApplicationContext(),"dobryy",Toast.LENGTH_SHORT).show();
        }else {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            itemsAdded = gson.fromJson(json, type);
        }
        return itemsAdded;

    }

    public void alreadyAdded() {


        //Display the static itemsAdded list
        addedAdapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemsAdded);
        alreadyAddedFoodtest.setAdapter(addedAdapter);
        addedAdapter.notifyDataSetChanged();
        Log.d("alekos","lista:"+itemsAdded);


    }
    // OPTIONS ONLONGCLICK
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_general, menu);
        menu.setHeaderTitle("Επιλογές");
    }

    //REMOVES FOOD ONLONGCLICK
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId()==R.id.delete){
            count--;
            countTextbasket(count);
            Toast.makeText(getApplicationContext(),"Διαγράφηκε",Toast.LENGTH_LONG).show();
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo(); // init the info the position from
            itemsAdded.remove(info.position); // remove the item from the list
            addedAdapter.notifyDataSetChanged();//updating the adapter

            if(itemsAdded.size()==0) {
                //Remove ArrayList from sharedPreferences because if i open FoodBasket again the list is still there.
                PreferenceManager.getDefaultSharedPreferences(AddFoodBasket.this).edit().remove("testShared").apply();
            }else {
                saveArrayList(itemsAdded); //Save the new ArrayList
            }

        }else{
            return false;
        }
        return true;
    }

    //SHARED PREFERENCES Save ArrayList
    public void saveArrayList(ArrayList<String> list){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AddFoodBasket.this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("testShared", json);
        editor.apply();     // This line is IMPORTANT !!!
    }
}
