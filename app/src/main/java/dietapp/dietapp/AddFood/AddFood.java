package dietapp.dietapp.AddFood;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dietapp.dietapp.R;

public class AddFood extends AppCompatActivity {

    ListView searchList, alreadyAddedFood;
    ArrayList<String> itemsOnSearch,itemsAdded;
    HashMap<Integer,String> test;
    Map<Integer,String> multiMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        searchList = (ListView) findViewById(R.id.searchFoodList);
        alreadyAddedFood = (ListView) findViewById(R.id.alreadyAddedList);

        itemsOnSearch = new ArrayList<>();
        itemsAdded=new ArrayList<>();




        //CALL SEARCH FUNCTION
        foodSearch();
    }


    private void foodSearch() {

        itemsOnSearch.add("one");
        itemsOnSearch.add("two");
        itemsOnSearch.add("three");
        itemsOnSearch.add("four");
        itemsOnSearch.add("one");
        itemsOnSearch.add("two");
        itemsOnSearch.add("three");
        itemsOnSearch.add("four");
        itemsOnSearch.add("one");
        itemsOnSearch.add("two");
        itemsOnSearch.add("three");
        itemsOnSearch.add("four");
        itemsOnSearch.add("one");
        itemsOnSearch.add("two");
        itemsOnSearch.add("three");
        itemsOnSearch.add("four");

        //ArrayAdapter converts an ArrayList of objects into View items loaded into the ListView container.
        ArrayAdapter<String> searchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemsOnSearch);
        searchList.setAdapter(searchAdapter);
        searchList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchMessage = searchList.getItemAtPosition(position).toString(); //searchMessage gets the value of the pressed item in list
                Toast.makeText(AddFood.this, "" + searchMessage, Toast.LENGTH_SHORT).show();
                //GO TO THE OTHER LISTVIEW
                alreadyAdded(searchMessage);
            }
        });

    }

    /*
    A MenuInflater is an object that is able to create Menu from xml resources (of course only menus resources),
     that is : construct a new instance of Menu given a menu resource identifier.
    The onCreateOptionMenu(Menu) is called when the menu button of the device is pressed,
     or either Activity.openOptionsMenu() is called.
    The actual rendering of the menu is handled by the activity.
     Just before it is shown, the Activity passes to you the menu so that you can fill it with your own items, then shows it.
    So Android undertakes that since it's not your business to render the menu,
    you shall not control what menu is actually passed to you inside onCreateOptionsMenu.
     */


    //METHOD FOR SEARCHING THE FOODS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //tutorial:https://www.youtube.com/watch?v=jJYSm_yrT7I
        //Goes to res->search_menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.food_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> tempList = new ArrayList<>();

                for (String temp : itemsOnSearch) {
                    if (temp.toLowerCase().contains(newText.toLowerCase())) {
                        tempList.add(temp);
                        Log.d("alekos", "templist->" + tempList);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddFood.this, android.R.layout.simple_list_item_1, tempList);
                searchList.setAdapter(adapter);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    public void alreadyAdded(String searchedMessage) {

        itemsAdded.add(searchedMessage);

        ArrayAdapter<String>addedAdapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemsAdded);
        alreadyAddedFood.setAdapter(addedAdapter);

    }
}

