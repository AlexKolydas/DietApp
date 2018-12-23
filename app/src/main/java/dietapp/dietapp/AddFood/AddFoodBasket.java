package dietapp.dietapp.AddFood;

import android.content.SharedPreferences;
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

import java.util.ArrayList;

import dietapp.dietapp.R;
import dietapp.dietapp.Model.SharedListFood;
import dietapp.dietapp.Shared.SharedListFoodHelper;

import static dietapp.dietapp.AddFood.AddFood.count;
import static dietapp.dietapp.AddFood.AddFood.countTextbasket;
import static dietapp.dietapp.AddFood.AddFood.itemsAdded;

public class AddFoodBasket extends AppCompatActivity {

    ListView alreadyAddedFoodtest;
    ArrayAdapter<String> addedAdapter;

    // The helper that manages writing to SharedPreferences.
    private SharedListFoodHelper sharedArrayPreferencesHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_basket);

        alreadyAddedFoodtest = (ListView) findViewById(R.id.alreadyAddedList);
        registerForContextMenu(alreadyAddedFoodtest);

        // Instantiate a SharedPreferencesHelper.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedArrayPreferencesHelper = new SharedListFoodHelper(sharedPreferences);


        alreadyAdded(); //display the list depends the adapter

    }


    public void alreadyAdded() {

        ArrayList<String> loadedList = sharedArrayPreferencesHelper.getArrayList();//gets the loaded list from shared preferences
        //if loadedlist from shared preferences is not null add it to my itemsAdded
        if (loadedList != null) {
            itemsAdded = loadedList;
        }
        addedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemsAdded);
        alreadyAddedFoodtest.setAdapter(addedAdapter);
        //Notify adapter
        addedAdapter.notifyDataSetChanged();


    }

    // OPTIONS ONLONGCLICK
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_general, menu);
        menu.setHeaderTitle("Επιλογές");
    }

    //REMOVES FOOD ONLONGCLICK
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            count--;
            countTextbasket(count);
            Toast.makeText(getApplicationContext(), "Διαγράφηκε", Toast.LENGTH_LONG).show();
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo(); // init the info the position from
            itemsAdded.remove(info.position); // remove the item from the list
            addedAdapter.notifyDataSetChanged();//updating the adapter

            if (itemsAdded.size() == 0) {
                //Remove ArrayList from sharedPreferences because if i open FoodBasket again the list is still there.
                PreferenceManager.getDefaultSharedPreferences(AddFoodBasket.this).edit().remove("testShared").apply();
            } else {

                //Shared Preferences
                SharedListFood sharedListFood = new SharedListFood(itemsAdded);
                sharedArrayPreferencesHelper.saveArrayList(sharedListFood);
            }

        } else {
            return false;
        }
        return true;
    }


}