package android.example.civilopediamobile;

import java.util.ArrayList;
import java.util.List;

public class Leader {

    private String name;
    private String civilization;
    private String url;
    private List<String> uniqueUnits;
    private List<String> uniqueDistricts;
    private List<String> uniqueBuildings;
    private List<String> uniqueImprovements;
    private List<Integer> colors;

    public Leader(String name, String civilization){
        this.name = name;
        this.civilization = civilization;
        colors = new ArrayList<>();
        uniqueUnits = new ArrayList<>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getCivilization() {
        return civilization;
    }

    public void setColors(int color1, int color2){

        this.colors.add(color1);
        this.colors.add(color2);

    }

    public List<Integer> getColors(){
        return colors;
    }

    public List<String> getUniqueUnits() {
        return uniqueUnits;
    }

    public void addUniqueUnit(String uniqueUnit) {
        this.uniqueUnits.add(uniqueUnit);
    }

    public List<String> getUniqueDistricts() {
        return uniqueDistricts;
    }

    public void setUniqueDistricts(List<String> uniqueDistricts) {
        this.uniqueDistricts = uniqueDistricts;
    }

    public List<String> getUniqueBuildings() {
        return uniqueBuildings;
    }

    public void setUniqueBuildings(List<String> uniqueBuildings) {
        this.uniqueBuildings = uniqueBuildings;
    }

    public List<String> getUniqueImprovements() {
        return uniqueImprovements;
    }

    public void setUniqueImprovements(List<String> uniqueImprovements) {
        this.uniqueImprovements = uniqueImprovements;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
