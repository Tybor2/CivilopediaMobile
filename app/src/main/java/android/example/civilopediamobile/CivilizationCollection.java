package android.example.civilopediamobile;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class CivilizationCollection {

    private static CivilizationCollection civilizationCollection;

    private Context context;
    private List<Civilization> civilizationList;

    public static CivilizationCollection get(Context context) {
        if(civilizationCollection == null)
            civilizationCollection = new CivilizationCollection(context);
        return civilizationCollection;
    }

    private CivilizationCollection(Context context){
        this.context = context.getApplicationContext();
        civilizationList = new ArrayList<>();
    }

    public void addCivilizations(List<Civilization> civilizationsToAdd){
        civilizationList = civilizationsToAdd;
    }

    public void addCivilization(Civilization civilizationToAdd){
        civilizationList.add(civilizationToAdd);
    }

    public List<Civilization> getCivilizations(){
        return civilizationList;
    }

    public Civilization getCivilization(String name){
        for(Civilization civilization: civilizationList){
            if(civilization.getName().equals(name))
                return civilization;
        }
        return null;
    }
}
