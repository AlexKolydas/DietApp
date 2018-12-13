package dietapp.dietapp.AddFood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import java.util.ArrayList;

import dietapp.dietapp.R;

public class AddFood extends AppCompatActivity {

    ListView searchList, alreadyAddedFood;
    ArrayList<String> itemsOnSearch1,itemsOnSearch2,itemsAdded;
    ArrayAdapter<String> addedAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        searchList = (ListView) findViewById(R.id.searchFoodList);
        registerForContextMenu(searchList); //GIVING ME THE CHOICE TO LONG CLICK AND SELECT ACTION

        alreadyAddedFood = (ListView) findViewById(R.id.alreadyAddedList);
        registerForContextMenu(alreadyAddedFood); //GIVING ME THE CHOICE TO LONG CLICK AND SELECT ACTION

        itemsOnSearch1 = new ArrayList<>();
        itemsOnSearch2 = new ArrayList<>();

        itemsAdded=new ArrayList<>();


        //CALL SEARCH FUNCTION
        foodSearch();
    }


    public void foodSearch() {

        itemsOnSearch1.add("one");
        itemsOnSearch1.add("two");
        itemsOnSearch1.add("three");
        itemsOnSearch1.add("four");
        itemsOnSearch1.add("one");
        itemsOnSearch1.add("two");
        itemsOnSearch1.add("three");
        itemsOnSearch1.add("four");
        itemsOnSearch1.add("one");

        itemsOnSearch2.add("two");
        itemsOnSearch2.add("three");
        itemsOnSearch2.add("four");
        itemsOnSearch2.add("one");
        itemsOnSearch2.add("two");
        itemsOnSearch2.add("three");
        itemsOnSearch2.add("four");




        //ArrayAdapter converts an ArrayList of objects into View items loaded into the ListView container.
        ArrayAdapter<String> searchAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,itemsOnSearch1);
        ArrayAdapter<String> searchAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemsOnSearch2);

        searchList.setAdapter(searchAdapter1);
        searchList.setAdapter(searchAdapter2);

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

                int count=0;
                int count1=0;
                for (String temp : itemsOnSearch1) {
                    if (temp.toLowerCase().contains(newText.toLowerCase())) {
                        tempList.add(temp);
                    }
                    count++;
                }
                Log.d("alekos", "templist->" +count);

                for (String temp : itemsOnSearch2) {
                    if (temp.toLowerCase().contains(newText.toLowerCase())) {
                        tempList.add(temp);
                    }
                    count1++;

                }
                Log.d("alekos", "templist->" +count1);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddFood.this, android.R.layout.simple_list_item_1, tempList);
                searchList.setAdapter(adapter);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    public void alreadyAdded(String searchedMessage) {

        itemsAdded.add(searchedMessage);

        addedAdapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemsAdded);
        alreadyAddedFood.setAdapter(addedAdapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_general, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId()==R.id.delete){
            Toast.makeText(getApplicationContext(),"calling code"+item,Toast.LENGTH_LONG).show();
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo(); // init the info the position from
            itemsAdded.remove(info.position); // remove the item from the list
            addedAdapter.notifyDataSetChanged();//updating the adapter
        }else{
            return false;
        }
        return true;
    }


}

