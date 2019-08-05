package android.example.civilopediamobile;

public class Building {

    private String name;
    private String district;
    private String civilization;

    public Building(String name, String district, String civilization){
        this.name = name;
        this.district = district;
        this.civilization = civilization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCivilization() {
        return civilization;
    }

    public void setCivilization(String civilization) {
        this.civilization = civilization;
    }
}
