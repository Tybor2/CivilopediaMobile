package android.example.civilopediamobile;

import java.util.ArrayList;
import java.util.List;

public class Civilization {
    private String name;
    private String url;
    private List<Leader> leaders;
    private List<String> uniqueUnits;
    private List<String> uniqueInfrastructure;
    private List<Integer> colors;

    public Civilization(String Name, List<Leader> leaders){
        this.name = Name;
        this.leaders = leaders;
        uniqueInfrastructure = new ArrayList<>();
        uniqueUnits = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public List<Leader> getLeaders() {
        return leaders;
    }

    public List<String> getUniqueInfrastructure() {
        return uniqueInfrastructure;
    }

    public void setUniqueInfrastructure(String uniqueInfrastructure) {
        this.uniqueInfrastructure.add(uniqueInfrastructure);
    }

    public void setColors(int color1, int color2){
        colors = new ArrayList<>();
        colors.add(color1);
        colors.add(color2);
    }

    public List<Integer> getColors(){
        return colors;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getUniqueUnits() {
        return uniqueUnits;
    }

    public void setUniqueUnits(List<String> uniqueUnits) {
        this.uniqueUnits = uniqueUnits;
    }

    public void addUniqueUnit(String uniqueUnit){
        this.uniqueUnits.add(uniqueUnit);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
