package dietapp.dietapp.AddFood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dietapp.dietapp.R;
import dietapp.dietapp.Model.SharedListFood;
import dietapp.dietapp.Shared.SharedListFoodHelper;


public class AddFood extends AppCompatActivity {

    ListView searchList;
    ArrayList<String> itemsOnSearch;
    static ArrayList<String> itemsAdded;


    FloatingActionButton fabToBasket;
    static TextView countText;
    static String searchMessage;
    static int count;

    // The helper that manages writing to SharedPreferences.
    private SharedListFoodHelper sharedArrayPreferencesHelper;

    SharedListFood sharedArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        searchList = (ListView) findViewById(R.id.searchFoodList);
        registerForContextMenu(searchList); //GIVING ME THE CHOICE TO LONG CLICK AND SELECT ACTION

        itemsOnSearch = new ArrayList<>();

        itemsAdded = new ArrayList<>();

        countText = (TextView) findViewById(R.id.basketCount);




        //FAB BUTTON
        fabToBasket = (FloatingActionButton) findViewById(R.id.fabToBasket);
        fabToBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToBasket = new Intent(AddFood.this, AddFoodBasket.class);
                startActivity(goToBasket);
            }
        });

        // Instantiate a SharedPreferencesHelper.
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        sharedArrayPreferencesHelper=new SharedListFoodHelper(sharedPreferences);



        //CALL SEARCH FUNCTION
        foodSearch();
        //CALL COUNT TEXT ON FAB BUTTON FUNCTION
        countTextbasket(count);
    }

    //Count text method on fab button
    public static void countTextbasket(int countT) {
        if (countT == 0) {
            countText.setText(null);
        } else {
            countText.setText(String.valueOf(count));
        }
    }

    public void foodSearch() {


        itemsOnSearch.add("one1");
        itemsOnSearch.add("two1");
        itemsOnSearch.add("three1");
        itemsOnSearch.add("four1");
        itemsOnSearch.add("one1");
        itemsOnSearch.add("two1");
        itemsOnSearch.add("three1");
        itemsOnSearch.add("four1");
        itemsOnSearch.add("one1");
        itemsOnSearch.add("two 2");
        itemsOnSearch.add("three2");
        itemsOnSearch.add("four2");
        itemsOnSearch.add("one2");
        itemsOnSearch.add("two2");
        itemsOnSearch.add("three2");
        itemsOnSearch.add("four2");


        //ArrayAdapter converts an ArrayList of objects into View items loaded into the ListView container.
        ArrayAdapter<String> searchAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemsOnSearch);

        searchList.setAdapter(searchAdapter1);

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                count++;
                countTextbasket(count);
                searchMessage = searchList.getItemAtPosition(position).toString(); //searchMessage gets the value of the pressed item in list
                //VLEPO AN IPARXEI I LEKSI TWO KAI NA VALO KAPOIO COUNTER GIA TOUS PONTOUS
                if(searchMessage.contains("two")){
                    Log.d("alekos","tak"+searchMessage);
                }
                Toast.makeText(AddFood.this, "" + searchMessage, Toast.LENGTH_SHORT).show();

                itemsAdded.add(searchMessage);// made it static so it is created here but displayed in the AddFoodBasket.java

                //Shared Preferences
                sharedArray=new SharedListFood(itemsAdded);

                boolean isSuccess= sharedArrayPreferencesHelper.saveArrayList(sharedArray); //sends itemsAdded to saveArrayList in shared preferences
                if (isSuccess) {
                    Toast.makeText(getApplicationContext(),"Personal information saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Personal information NOT", Toast.LENGTH_LONG).show();
                }
            }
        });

        //import the loadedList to the itemsAdded because if i don't it will override my old shared preferences list
        ArrayList<String> loadedList = sharedArrayPreferencesHelper.getArrayList();//gets the loaded list from shared preferences
        //if loadedlist from shared preferences is not null add it to my itemsAdded
        if (loadedList != null) {
            itemsAdded = loadedList;
        }
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
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddFood.this, android.R.layout.simple_list_item_1, tempList);
                searchList.setAdapter(adapter);

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    //WHEN PRESSING BACK BUTTON
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        count = 0;
    }

}

