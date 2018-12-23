package dietapp.dietapp.Model;

import java.util.ArrayList;

public class SharedListFood {

    private ArrayList<String> mlist;


    public SharedListFood(ArrayList<String> list){
        this.mlist=list;
    }

    public ArrayList<String> getMlist() {
        return mlist;
    }
}

