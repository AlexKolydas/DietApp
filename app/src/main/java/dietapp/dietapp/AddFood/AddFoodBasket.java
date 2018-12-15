package dietapp.dietapp.AddFood;

import android.content.Intent;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import dietapp.dietapp.R;

import static dietapp.dietapp.AddFood.AddFood.count;
import static dietapp.dietapp.AddFood.AddFood.itemsAdded;

public class AddFoodBasket extends AppCompatActivity {

    ListView alreadyAddedFoodtest;
//    ArrayList<String>itemsAdded;
    ArrayAdapter<String> addedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_basket);

        alreadyAddedFoodtest = (ListView) findViewById(R.id.alreadyAddedList);
        registerForContextMenu(alreadyAddedFoodtest);


        alreadyAdded();
    }

    public void alreadyAdded() {

        //Display the static itemsAdded list
        addedAdapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemsAdded);
        alreadyAddedFoodtest.setAdapter(addedAdapter);
        addedAdapter.notifyDataSetChanged();
        Log.d("alekos","lista:"+itemsAdded);


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
