package android.example.civilopediamobile;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;

import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WebView mWebView;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    List<Leader> leaderList;
    private List<Civilization> civList;
    private CivilizationCollection civInstance;
    private LeaderCollection leaderInstance;
    private String currFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            currFrag = savedInstanceState.getString("currFrag");
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CivInfoFragment fragment = new CivInfoFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.fragment_container, fragment, "civViewer");
                currFrag = "civViewer";
                ft.commit();
            }
        });

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        View fragmentContainer = findViewById(R.id.fragment_container);

        if(currFrag == null) {

            WikiInfoFragment fragment = new WikiInfoFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fragment.setUrl("https://civilization.fandom.com/wiki/Civilization_VI");
            ft.replace(R.id.fragment_container, fragment, "wikiViewer");
            ft.addToBackStack(null);
            currFrag = "wikiViewer";
            ft.commit();
        }

        /**mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://civilization.fandom.com/wiki/Government_(Civ6)");**/
        createLeaders();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currFrag", currFrag);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareMenuData() {
        MenuModel menuModel = new MenuModel("Civilization Viewer", true, false,
                "civViewer");
        headerList.add(menuModel);
        menuModel = new MenuModel("Game Generator", true, false, "gameGenerator");
        headerList.add(menuModel);
        civList = CivilizationCollection.get(getApplicationContext()).getCivilizations();
        civInstance = CivilizationCollection.get(getApplicationContext());
        leaderInstance = LeaderCollection.get(getApplicationContext());
        createLeaders();
        setUpCivilizationsMenu();
        createUniqueInfrastructure();
        setUpColors();
        setUpUniqueUnits();
        setUpLeadersMenu();
        setUpDistrictsMenu();
        setUpBuildingsMenu();
        setUpTileImprovements();
        setUpUnitsMenu();
        setUpWondersMenu();
        setUpNaturalWondersMenu();
    }

    private void setUpCivilizationsMenu(){
        MenuModel menuModel = new MenuModel("Civilizations", true, true,
                "https://www.journaldev.com/9333/android-webview-example-tutorial");


        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        String[] civNames = {
                getString(R.string.civ_american_name), getString(R.string.civ_arabian_name), getString(R.string.civ_australian_name),
                getString(R.string.civ_aztec_name), getString(R.string.civ_brazilian_name), getString(R.string.civ_canadian_name),
                getString(R.string.civ_chinese_name), getString(R.string.civ_cree_name), getString(R.string.civ_dutch_name),
                getString(R.string.civ_egyptian_name), getString(R.string.civ_english_name), getString(R.string.civ_french_name),
                getString(R.string.civ_georgian_name), getString(R.string.civ_german_name), getString(R.string.civ_greek_name),
                getString(R.string.civ_hungarian_name), getString(R.string.civ_incan_name), getString(R.string.civ_indian_name),
                getString(R.string.civ_indonesian_name), getString(R.string.civ_japanese_name), getString(R.string.civ_khmer_name),
                getString(R.string.civ_kongolese_name), getString(R.string.civ_korean_name), getString(R.string.civ_macedonian_name),
                getString(R.string.civ_mali_name), getString(R.string.civ_maori_name), getString(R.string.civ_mapuche_name),
                getString(R.string.civ_mongolian_name), getString(R.string.civ_norwegian_name), getString(R.string.civ_nubian_name),
                getString(R.string.civ_ottoman_name), getString(R.string.civ_persian_name), getString(R.string.civ_phoenician_name),
                getString(R.string.civ_polish_name), getString(R.string.civ_roman_name), getString(R.string.civ_russian_name),
                getString(R.string.civ_scottish_name), getString(R.string.civ_scythian_name), getString(R.string.civ_spanish_name),
                getString(R.string.civ_sumerian_name), getString(R.string.civ_swedish_name), getString(R.string.civ_zulu_name)
        };
        civInstance = CivilizationCollection.get(getApplicationContext());
        for(String name: civNames){
            List<Leader> validLeader = new ArrayList<>();
            for(Leader leader: leaderInstance.getLeaders()) {
                if (leader.getCivilization().equals(name)){
                    validLeader.add(leader);
                }
            }
            civInstance.addCivilization(new Civilization(name, validLeader));
        }
        String[] url = new String[civNames.length];
        for(int i = 0; i < civNames.length; i++){
            url[i] = "https://civilization.fandom.com/wiki/" + civNames[i] + "_(Civ6)";
        }
        MenuModel childModel;
        for(int i = 0; i < civNames.length; i++) {
            childModel = new MenuModel(civNames[i], false, false, url[i]);
            childModelsList.add(childModel);
        }
        /**MenuModel childModel = new MenuModel("Core Java Tutorial", false, false, "https://www.journaldev.com/7153/core-java-tutorial");
         childModelsList.add(childModel);

         childModel = new MenuModel("Java FileInputStream", false, false, "https://www.journaldev.com/19187/java-fileinputstream");
         childModelsList.add(childModel);

         childModel = new MenuModel("Java FileReader", false, false, "https://www.journaldev.com/19115/java-filereader");
         childModelsList.add(childModel);**/


        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }

    }

    private void setUpLeadersMenu(){
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel menuModel = new MenuModel("Leaders", true, true, ""); //Menu of Python Tutorials
        headerList.add(menuModel);

        String[] leaderNames = {
                "Alexander", "Amanitore", "Catherine de Medici", "Chandragupta", "Cleopatra",
                "Cyrus", "Dido", "Eleanor of Aquitaine", "Eleanor of Aquitaine",
                "Frederick Barbarossa", "Gandhi", "Genghis Khan", "Gilgamesh", "Gitarja", "Gorgo",
                "Harald Hardrada", "Hojo Tokimune", "Jadwiga", "Jayavarman VII", "John Curtin",
                "Kristina", "Kupe", "Lautaro", "Mansa Musa", "Matthias Corvinus", "Montezuma",
                "Mvemba a Nzinga", "Pachacuti", "Pedro II", "Pericles", "Peter", "Philip II",
                "Poundmaker", "Qin Shi Huang", "Robert the Bruce", "Saladin", "Seondeok", "Shaka",
                "Suleiman", "Tamar", "Teddy Roosevelt", "Tomyris", "Trajan", "Victoria",
                getString(R.string.lead_wilfrid_name), "Wilhelmina"
        };
        String[] urls = new String[leaderNames.length];
        for(int i = 0; i < leaderNames.length; i++){
            urls[i] = "https://civilization.fandom.com/wiki/" + leaderNames[i].replaceAll(" ", "_") + "_(Civ6)";
            MenuModel childModel = new MenuModel(leaderNames[i], false, false, urls[i]);
            childModelsList.add(childModel);
        }

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
    }

    private void setUpDistrictsMenu(){
        MenuModel menuModel = new MenuModel("Districts", true, true, "");
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        String[] districtNames = {
                getString(R.string.dis_city_center_name), getString(R.string.dis_campus_name), getString(R.string.dis_seowon_name),
                getString(R.string.dis_canal_name), getString(R.string.dis_dam_name), getString(R.string.dis_holy_site_name),
                getString(R.string.dis_lavra_name), getString(R.string.dis_theater_square_name), getString(R.string.dis_acropolis_name),
                getString(R.string.dis_encampment_name), getString(R.string.dis_ikanda_name), getString(R.string.dis_harbor_name),
                getString(R.string.dis_royal_navy_dockyard_name), getString(R.string.dis_commercial_hub_name), getString(R.string.dis_suguba_name),
                getString(R.string.dis_industrial_zone_name), getString(R.string.dis_hansa_name), getString(R.string.dis_entertainment_complex_name),
                getString(R.string.dis_street_carnival_name), getString(R.string.dis_aqueduct_name), getString(R.string.dis_bath_name),
                getString(R.string.dis_neighborhood_name), getString(R.string.dis_mbanza_name), getString(R.string.dis_aerodrome_name),
                getString(R.string.dis_spaceport_name), getString(R.string.dis_government_plaza_name), getString(R.string.dis_water_park_name),
                getString(R.string.dis_copacabana_name)
        };
        String[] urls = new String[districtNames.length];
        for(int i = 0; i < districtNames.length; i++){
            urls[i] = "https://civilization.fandom.com/wiki/" + districtNames[i].replaceAll(" ", "_") + "_(Civ6)";
            MenuModel childModel = new MenuModel(districtNames[i], false, false, urls[i]);
            childModelsList.add(childModel);
        }

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
    }

    private void setUpBuildingsMenu(){
        MenuModel menuModel = new MenuModel("Buildings", true, true, "");
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        String[] buildingNames = {
                getString(R.string.build_airport_name), getString(R.string.build_amphitheater_name), getString(R.string.build_ancestral_hall_name),
                getString(R.string.build_ancient_walls_name), getString(R.string.build_aquarium_name), getString(R.string.build_aquatics_center_name),
                getString(R.string.build_archaeological_museum_name), getString(R.string.build_arena_name), getString(R.string.build_armory_name),
                getString(R.string.build_art_museum_name), getString(R.string.build_audience_chamber_name), getString(R.string.build_bank_name),
                getString(R.string.build_barracks_name), getString(R.string.build_basilikoli_paides_name), getString(R.string.build_broadcast_center),
                getString(R.string.build_cathedral_name), getString(R.string.build_coal_power_plant), getString(R.string.build_dare_mehr_name),
                getString(R.string.build_electronics_factory_name), getString(R.string.build_factory_name), getString(R.string.build_ferris_wheel_name),
                getString(R.string.build_film_studio_name), getString(R.string.build_flood_barrier_name), getString(R.string.build_food_market_name),
                getString(R.string.build_foreign_ministry_name), getString(R.string.build_granary_name), getString(R.string.build_grand_bazaar_name),
                getString(R.string.build_grand_masters_chapel_name), getString(R.string.build_gurdwara_name), getString(R.string.build_hangar_name),
                getString(R.string.build_hydroelectric_dam_name), getString(R.string.build_intelligence_agency_name), getString(R.string.build_library_name),
                getString(R.string.build_lighthouse_name), getString(R.string.build_madrasa_name), getString(R.string.build_marae_name),
                getString(R.string.build_market_name), getString(R.string.build_medieval_walls_name), getString(R.string.build_meeting_house_name),
                getString(R.string.build_military_academy_name), getString(R.string.build_monument_name), getString(R.string.build_mosque_name),
                getString(R.string.build_national_history_museum_name), getString(R.string.build_nuclear_power_plant_name), getString(R.string.build_oil_power_plant),
                getString(R.string.build_ancient_walls_name), getString(R.string.build_aquarium_name), getString(R.string.build_aquatics_center_name),
                getString(R.string.build_ordu_name), getString(R.string.build_pagoda_name), getString(R.string.build_palace_name),
                getString(R.string.build_power_plant_name), getString(R.string.build_prasat_name), getString(R.string.build_queens_bibliotheque_name),
                getString(R.string.build_renaissance_walls_name), getString(R.string.build_research_lab_name), getString(R.string.build_research_lab_name),
                getString(R.string.build_seaport_name), getString(R.string.build_sewer_name), getString(R.string.build_shipyard_name),
                getString(R.string.build_shopping_mall_name), getString(R.string.build_shrine_name), getString(R.string.build_stable_name),
                getString(R.string.build_stadium_name), getString(R.string.build_stave_church_name), getString(R.string.build_stock_exchange_name),
                getString(R.string.build_stupa_name), getString(R.string.build_sukiennice_name), getString(R.string.build_synagogue_name),
                getString(R.string.build_temple_name), getString(R.string.build_thermal_bath_name), getString(R.string.build_tlachtli_name),
                getString(R.string.build_tsikhe_name), getString(R.string.build_university_name), getString(R.string.build_war_department),
                getString(R.string.build_warlords_throne_name), getString(R.string.build_wat_name), getString(R.string.build_water_mill_name),
                getString(R.string.build_workshop_name), getString(R.string.build_zoo_name)
        };

        String[] urls = new String[buildingNames.length];
        for(int i = 0; i < buildingNames.length; i++){
            urls[i] = "https://civilization.fandom.com/wiki/" + buildingNames[i].replaceAll(" ", "_") + "_(Civ6)";
            MenuModel childModel = new MenuModel(buildingNames[i], false, false, urls[i]);
            childModelsList.add(childModel);
        }

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
    }

    private void setUpTileImprovements(){
        MenuModel menuModel = new MenuModel("Tile Improvements", true, true, "");
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        String[] tileImprovements = {
                getString(R.string.tile_farm_name), getString(R.string.tile_mine_name), getString(R.string.tile_quarry_name),
                getString(R.string.tile_plantation_name), getString(R.string.tile_camp_name), getString(R.string.tile_pasture_name),
                getString(R.string.tile_fishing_boats_name), getString(R.string.tile_lumber_mill_name), getString(R.string.tile_fort_name),
                getString(R.string.tile_airstrip_name), getString(R.string.tile_seaside_resort_name), getString(R.string.tile_geothermal_plant_name),
                getString(R.string.tile_wind_farm_name), getString(R.string.tile_solar_farm_name), getString(R.string.tile_offshore_wind_farm_name),
                getString(R.string.tile_ski_resort_name), getString(R.string.tile_oil_well_name), getString(R.string.tile_offshore_oil_rig_name),
                getString(R.string.tile_missile_silo_name), getString(R.string.tile_mountain_tunnel_name), getString(R.string.tile_railroad_name),
                getString(R.string.tile_seastead_name), getString(R.string.tile_alcazar_name), getString(R.string.tile_cahokia_mounds_name),
                getString(R.string.tile_colossal_heads_name), getString(R.string.tile_moai_name), getString(R.string.tile_monastery_name),
                getString(R.string.tile_nazca_line_name), getString(R.string.tile_chateau_name), getString(R.string.tile_chemamull_name),
                getString(R.string.tile_roman_fort_name), getString(R.string.tile_golf_course_name), getString(R.string.tile_great_wall_name),
                getString(R.string.tile_ice_hockey_rink_name), getString(R.string.tile_kampung_name), getString(R.string.tile_kurgan_name),
                getString(R.string.tile_mekewap_name), getString(R.string.tile_mission_name), getString(R.string.tile_nubian_pyramid_name),
                getString(R.string.tile_open_air_museum_name), getString(R.string.tile_outback_station_name), getString(R.string.tile_pa_name),
                getString(R.string.tile_pairidaerza_name), getString(R.string.tile_polder_name), getString(R.string.tile_qhapaq_nan_name),
                getString(R.string.tile_sphinx_name), getString(R.string.tile_stepwell_name), getString(R.string.tile_terrace_farm_name),
                getString(R.string.tile_ziggurat_name), getString(R.string.tile_city_park_name), getString(R.string.tile_fishery_name),
                getString(R.string.tile_national_park_name)

        };
        String[] urls = new String[tileImprovements.length];
        for(int i = 0; i < tileImprovements.length; i++){
            urls[i] = "https://civilization.fandom.com/wiki/" + tileImprovements[i].replaceAll(" ", "_") + "_(Civ6)";
            MenuModel childModel = new MenuModel(tileImprovements[i], false, false, urls[i]);
            childModelsList.add(childModel);
        }

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
    }

    private void setUpUnitsMenu(){
        MenuModel menuModel = new MenuModel("Units", true, true, "");
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        String[] units = getResources().getStringArray(R.array.BasicUnits);
        String[] urls = new String[units.length];
        for(int i = 0; i < units.length; i++){
            urls[i] = "https://civilization.fandom.com/wiki/" + units[i].replaceAll(" ", "_") + "_(Civ6)";
            MenuModel childModel = new MenuModel(units[i], false, false, urls[i]);
            childModelsList.add(childModel);
        }

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
    }

    private void setUpWondersMenu(){
        MenuModel menuModel = new MenuModel("Wonders", true, true, "");
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        String[] units = getResources().getStringArray(R.array.wonders);
        String[] urls = new String[units.length];
        for(int i = 0; i < units.length; i++){
            urls[i] = "https://civilization.fandom.com/wiki/" + units[i].replaceAll(" ", "_") + "_(Civ6)";
            MenuModel childModel = new MenuModel(units[i], false, false, urls[i]);
            childModelsList.add(childModel);
        }

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
    }

    private void setUpNaturalWondersMenu(){
        MenuModel menuModel = new MenuModel("Natural Wonders", true, true, "");
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        String[] units = getResources().getStringArray(R.array.naturalWonders);
        String[] urls = new String[units.length];
        for(int i = 0; i < units.length; i++){
            urls[i] = "https://civilization.fandom.com/wiki/" + units[i].replaceAll(" ", "_") + "_(Civ6)";
            MenuModel childModel = new MenuModel(units[i], false, false, urls[i]);
            childModelsList.add(childModel);
        }

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
    }

    private void setUpUniqueUnits(){
        civInstance.getCivilization(getString(R.string.civ_phoenician_name))
                .addUniqueUnit(getString(R.string.unit_bireme_name));
        civInstance.getCivilization(getString(R.string.civ_aztec_name))
                .addUniqueUnit(getString(R.string.unit_eagle_warrior_name));
        civInstance.getCivilization(getString(R.string.civ_greek_name))
                .addUniqueUnit(getString(R.string.unit_hoplite_name));
        civInstance.getCivilization(getString(R.string.civ_egyptian_name))
                .addUniqueUnit(getString(R.string.unit_maryannu_name));
        civInstance.getCivilization(getString(R.string.civ_cree_name))
                .addUniqueUnit(getString(R.string.unit_okihtcitaw_name));
        civInstance.getCivilization(getString(R.string.civ_nubian_name))
                .addUniqueUnit(getString(R.string.unit_pitati_name));
        leaderInstance.getLeader(getString(R.string.lead_harald_name), getString(R.string.civ_norwegian_name))
                .addUniqueUnit(getString(R.string.unit_viking_longship_name));
        civInstance.getCivilization(getString(R.string.civ_sumerian_name))
                .addUniqueUnit(getString(R.string.unit_war_cart_name));
        leaderInstance.getLeader(getString(R.string.lead_alexander_name), getString(R.string.civ_macedonian_name))
                .addUniqueUnit(getString(R.string.unit_hetairoi_name));
        civInstance.getCivilization(getString(R.string.civ_macedonian_name))
                .addUniqueUnit(getString(R.string.unit_hypaspist_name));
        civInstance.getCivilization(getString(R.string.civ_persian_name))
                .addUniqueUnit(getString(R.string.unit_immortal_name));
        civInstance.getCivilization(getString(R.string.civ_roman_name))
                .addUniqueUnit(getString(R.string.unit_legion_name));
        civInstance.getCivilization(getString(R.string.civ_kongolese_name))
                .addUniqueUnit(getString(R.string.unit_ngao_name));
        civInstance.getCivilization(getString(R.string.civ_scythian_name))
                .addUniqueUnit(getString(R.string.unit_saka_name));
        civInstance.getCivilization(getString(R.string.civ_maori_name))
                .addUniqueUnit(getString(R.string.unit_toa_name));
        civInstance.getCivilization(getString(R.string.civ_indian_name))
                .addUniqueUnit(getString(R.string.unit_varu_name));
        civInstance.getCivilization(getString(R.string.civ_ottoman_name))
                .addUniqueUnit(getString(R.string.unit_barbary_name));
        civInstance.getCivilization(getString(R.string.civ_norwegian_name))
                .addUniqueUnit(getString(R.string.unit_berserker_name));
        leaderInstance.getLeader(getString(R.string.lead_matthias_name), getString(R.string.civ_hungarian_name))
                .addUniqueUnit(getString(R.string.unit_black_army_name));
        civInstance.getCivilization(getString(R.string.civ_chinese_name))
                .addUniqueUnit(getString(R.string.unit_crouching_name));
        civInstance.getCivilization(getString(R.string.civ_khmer_name))
                .addUniqueUnit(getString(R.string.unit_domrey_name));
        civInstance.getCivilization(getString(R.string.civ_zulu_name))
                .addUniqueUnit(getString(R.string.unit_impi_name));
        civInstance.getCivilization(getString(R.string.civ_indonesian_name))
                .addUniqueUnit(getString(R.string.unit_jong_name));
        civInstance.getCivilization(getString(R.string.civ_mongolian_name))
                .addUniqueUnit(getString(R.string.unit_keshig_name));
        civInstance.getCivilization(getString(R.string.civ_georgian_name))
                .addUniqueUnit(getString(R.string.unit_khevsur_name));
        civInstance.getCivilization(getString(R.string.civ_arabian_name))
                .addUniqueUnit(getString(R.string.unit_mamluk_name));
        civInstance.getCivilization(getString(R.string.civ_mali_name))
                .addUniqueUnit(getString(R.string.unit_mandekalu_name));
        civInstance.getCivilization(getString(R.string.civ_japanese_name))
                .addUniqueUnit(getString(R.string.unit_samurai_name));
        civInstance.getCivilization(getString(R.string.civ_incan_name))
                .addUniqueUnit(getString(R.string.unit_warakaq_name));
        civInstance.getCivilization(getString(R.string.civ_polish_name))
                .addUniqueUnit(getString(R.string.unit_winged_name));
        civInstance.getCivilization(getString(R.string.civ_swedish_name))
                .addUniqueUnit(getString(R.string.unit_carolean_name));
        civInstance.getCivilization(getString(R.string.civ_spanish_name))
                .addUniqueUnit(getString(R.string.unit_conquistador_name));
        civInstance.getCivilization(getString(R.string.civ_dutch_name))
                .addUniqueUnit(getString(R.string.unit_zeven_name));
        civInstance.getCivilization(getString(R.string.civ_korean_name))
                .addUniqueUnit(getString(R.string.unit_hwacha_name));
        leaderInstance.getLeader(getString(R.string.lead_suleiman_name), getString(R.string.civ_ottoman_name))
                .addUniqueUnit(getString(R.string.unit_janissary_name));
        civInstance.getCivilization(getString(R.string.civ_mapuche_name))
                .addUniqueUnit(getString(R.string.unit_malon_name));
        civInstance.getCivilization(getString(R.string.civ_english_name))
                .addUniqueUnit(getString(R.string.unit_sea_dog_name));
        civInstance.getCivilization(getString(R.string.civ_russian_name))
                .addUniqueUnit(getString(R.string.unit_cossack_name));
        civInstance.getCivilization(getString(R.string.civ_french_name))
                .addUniqueUnit(getString(R.string.unit_garde_name));
        civInstance.getCivilization(getString(R.string.civ_scottish_name))
                .addUniqueUnit(getString(R.string.unit_highlander_name));
        civInstance.getCivilization(getString(R.string.civ_hungarian_name))
                .addUniqueUnit(getString(R.string.unit_huszar_name));
        civInstance.getCivilization(getString(R.string.civ_brazilian_name))
                .addUniqueUnit(getString(R.string.unit_minas_name));
        leaderInstance.getLeader(getString(R.string.lead_victoria_name), getString(R.string.civ_english_name))
                .addUniqueUnit(getString(R.string.unit_redcoat_name));
        leaderInstance.getLeader(getString(R.string.lead_teddy_name), getString(R.string.civ_american_name))
                .addUniqueUnit(getString(R.string.unit_rough_rider_name));
        civInstance.getCivilization(getString(R.string.civ_australian_name))
                .addUniqueUnit(getString(R.string.unit_digger_name));
        civInstance.getCivilization(getString(R.string.civ_canadian_name))
                .addUniqueUnit(getString(R.string.unit_mountie_name));
        civInstance.getCivilization(getString(R.string.civ_german_name))
                .addUniqueUnit(getString(R.string.unit_uboat_name));
        civInstance.getCivilization(getString(R.string.civ_american_name))
                .addUniqueUnit(getString(R.string.unit_mustang_name));
    }

    private void setUpColors(){
        civInstance = CivilizationCollection.get(getApplicationContext());
        leaderInstance = LeaderCollection.get(getApplicationContext());
        leaderInstance.getLeader(getString(R.string.lead_teddy_name), getString(R.string.civ_american_name))
                .setColors(R.color.USBlue, R.color.USWhite);
        leaderInstance.getLeader(getString(R.string.lead_saladin_name), getString(R.string.civ_arabian_name))
                .setColors(R.color.ARYellow, R.color.ARGreen);
        leaderInstance.getLeader(getString(R.string.lead_curtin_name), getString(R.string.civ_australian_name))
                .setColors(R.color.AUGreen, R.color.AUYellow);
        leaderInstance.getLeader(getString(R.string.lead_montezuma_name), getString(R.string.civ_aztec_name))
                .setColors(R.color.AZCyan, R.color.AZRed);
        leaderInstance.getLeader(getString(R.string.lead_pedro_name), getString(R.string.civ_brazilian_name))
                .setColors(R.color.BZGreen, R.color.BZYellow);
        leaderInstance.getLeader(getString(R.string.lead_wilfrid_name), getString(R.string.civ_canadian_name))
                .setColors(R.color.CAWhite, R.color.CARed);
        leaderInstance.getLeader(getString(R.string.lead_qin_name), getString(R.string.civ_chinese_name))
                .setColors(R.color.CHGreen, R.color.CHWhite);
        leaderInstance.getLeader(getString(R.string.lead_poundmaker_name), getString(R.string.civ_cree_name))
                .setColors(R.color.CRIndigo, R.color.CRGreen);
        leaderInstance.getLeader(getString(R.string.lead_wilhelmina_name),getString(R.string.civ_dutch_name))
                .setColors(R.color.DUOrange, R.color.DUBlue);
        leaderInstance.getLeader(getString(R.string.lead_cleopatra_name), getString(R.string.civ_egyptian_name))
                .setColors(R.color.EGTurquoise, R.color.EGYellow);
        leaderInstance.getLeader(getString(R.string.lead_victoria_name), getString(R.string.civ_english_name))
                .setColors(R.color.VictoriaRed, R.color.ENWhite);
        leaderInstance.getLeader(getString(R.string.lead_eleanor_name), getString(R.string.civ_english_name))
                .setColors(R.color.EleanorPink, R.color.ENWhite);
        leaderInstance.getLeader(getString(R.string.lead_catherine_name), getString(R.string.civ_french_name))
                .setColors(R.color.FRBlue, R.color.FRYellow);
        leaderInstance.getLeader(getString(R.string.lead_eleanor_name), getString(R.string.civ_french_name))
                .setColors(R.color.EleanorPink, R.color.FRYellow);
        leaderInstance.getLeader(getString(R.string.lead_tamar_name), getString(R.string.civ_georgian_name))
                .setColors(R.color.GEOWhite, R.color.GEOOrange);
        leaderInstance.getLeader(getString(R.string.lead_frederick_name), getString(R.string.civ_german_name))
                .setColors(R.color.GERGray, R.color.GERBlack);
        leaderInstance.getLeader(getString(R.string.lead_pericles_name), getString(R.string.civ_greek_name))
                .setColors(R.color.GRBlue, R.color.PericlesWhite);
        leaderInstance.getLeader(getString(R.string.lead_gorgo_name), getString(R.string.civ_greek_name))
                .setColors(R.color.GorgoBrown, R.color.GRBlue);
        leaderInstance.getLeader(getString(R.string.lead_matthias_name), getString(R.string.civ_hungarian_name))
                .setColors(R.color.HUGreen, R.color.HUOrange);
        leaderInstance.getLeader(getString(R.string.lead_pachacuti_name), getString(R.string.civ_incan_name))
                .setColors(R.color.INCABrown, R.color.INCAYellow);
        leaderInstance.getLeader(getString(R.string.lead_gandhi_name), getString(R.string.civ_indian_name))
                .setColors(R.color.INDIPurple, R.color.INDIBlue);
        leaderInstance.getLeader(getString(R.string.lead_chandragupta_name), getString(R.string.civ_indian_name))
                .setColors(R.color.INDIBlue, R.color.INDIPurple);
        leaderInstance.getLeader(getString(R.string.lead_gitarja_name), getString(R.string.civ_indonesian_name))
                .setColors(R.color.INDORed, R.color.INDOIndigo);
        leaderInstance.getLeader(getString(R.string.lead_hojo_name), getString(R.string.civ_japanese_name))
                .setColors(R.color.JAWhite, R.color.JARed);
        leaderInstance.getLeader(getString(R.string.lead_jayavarman_name), getString(R.string.civ_khmer_name))
                .setColors(R.color.KHPurple, R.color.KHOrange);
        leaderInstance.getLeader(getString(R.string.lead_mvemba_name), getString(R.string.civ_kongolese_name))
                .setColors(R.color.KONYellow, R.color.KONRed);
        leaderInstance.getLeader(getString(R.string.lead_seondeok_name), getString(R.string.civ_korean_name))
                .setColors(R.color.KORRed, R.color.KORLavender);
        leaderInstance.getLeader(getString(R.string.lead_alexander_name), getString(R.string.civ_macedonian_name))
                .setColors(R.color.MACGray, R.color.MACYellow);
        leaderInstance.getLeader(getString(R.string.lead_mansa_name), getString(R.string.civ_mali_name))
                .setColors(R.color.MALRed, R.color.MALGold);
        leaderInstance.getLeader(getString(R.string.lead_kupe_name), getString(R.string.civ_maori_name))
                .setColors(R.color.MAORed, R.color.MAOBlue);
        //TODO Less Bright
        leaderInstance.getLeader(getString(R.string.lead_lautaro_name), getString(R.string.civ_mapuche_name))
                .setColors(R.color.MAPBlue, R.color.MAPLightBlue);
        leaderInstance.getLeader(getString(R.string.lead_genghis_name), getString(R.string.civ_mongolian_name))
                .setColors(R.color.MONRed, R.color.MONOrange);
        leaderInstance.getLeader(getString(R.string.lead_harald_name), getString(R.string.civ_norwegian_name))
                .setColors(R.color.NORNavy, R.color.NORRed);
        leaderInstance.getLeader(getString(R.string.lead_amanitore_name), getString(R.string.civ_nubian_name))
                .setColors(R.color.NUBCream, R.color.NUBBrown);
        leaderInstance.getLeader(getString(R.string.lead_suleiman_name), getString(R.string.civ_ottoman_name))
                .setColors(R.color.OTWhite, R.color.OTGreen);
        leaderInstance.getLeader(getString(R.string.lead_cyrus_name), getString(R.string.civ_persian_name))
                .setColors(R.color.PEPeriwinkle, R.color.PERed);
        leaderInstance.getLeader(getString(R.string.lead_dido_name), getString(R.string.civ_phoenician_name))
                .setColors(R.color.PHPurple, R.color.PHTeal);
        leaderInstance.getLeader(getString(R.string.lead_jadwiga_name), getString(R.string.civ_polish_name))
                .setColors(R.color.PORed, R.color.POPink);
        leaderInstance.getLeader(getString(R.string.lead_trajan_name), getString(R.string.civ_roman_name))
                .setColors(R.color.ROViolet, R.color.ROGold);
        leaderInstance.getLeader(getString(R.string.lead_peter_name), getString(R.string.civ_russian_name))
                .setColors(R.color.RUYellow, R.color.RUBlack);
        leaderInstance.getLeader(getString(R.string.lead_robert_name), getString(R.string.civ_scottish_name))
                .setColors(R.color.SCOTWhite, R.color.SCOTBlue);
        leaderInstance.getLeader(getString(R.string.lead_tomyris_name), getString(R.string.civ_scythian_name))
                .setColors(R.color.SCYTan, R.color.SCYRed);
        leaderInstance.getLeader(getString(R.string.lead_philip_name), getString(R.string.civ_spanish_name))
                .setColors(R.color.SPRed, R.color.SPYellow);
        leaderInstance.getLeader(getString(R.string.lead_gilgamesh_name), getString(R.string.civ_sumerian_name))
                .setColors(R.color.SUIndigo, R.color.SUOrange);
        leaderInstance.getLeader(getString(R.string.lead_kristina_name), getString(R.string.civ_swedish_name))
                .setColors(R.color.SWBlue, R.color.SWYellow);
        leaderInstance.getLeader(getString(R.string.lead_shaka_name), getString(R.string.civ_zulu_name))
                .setColors(R.color.ZUBrown, R.color.ZUWhite);
    }

    private void createUniqueInfrastructure(){
        civInstance = CivilizationCollection.get(getApplicationContext());
        civInstance.getCivilization(getString(R.string.civ_american_name))
                .setUniqueInfrastructure(getString(R.string.build_film_studio_name));
        civInstance.getCivilization(getString(R.string.civ_arabian_name))
                .setUniqueInfrastructure(getString(R.string.build_madrasa_name));
        civInstance.getCivilization(getString(R.string.civ_australian_name))
                .setUniqueInfrastructure(getString(R.string.tile_outback_station_name));
        civInstance.getCivilization(getString(R.string.civ_aztec_name))
                .setUniqueInfrastructure(getString(R.string.build_tlachtli_name));
        civInstance.getCivilization(getString(R.string.civ_brazilian_name))
                .setUniqueInfrastructure(getString(R.string.dis_street_carnival_name));
        civInstance.getCivilization(getString(R.string.civ_brazilian_name))
                .setUniqueInfrastructure(getString(R.string.dis_copacabana_name));
        civInstance.getCivilization(getString(R.string.civ_canadian_name))
                .setUniqueInfrastructure(getString(R.string.tile_ice_hockey_rink_name));
        civInstance.getCivilization(getString(R.string.civ_chinese_name))
                .setUniqueInfrastructure(getString(R.string.tile_great_wall_name));
        civInstance.getCivilization(getString(R.string.civ_cree_name))
                .setUniqueInfrastructure(getString(R.string.tile_mekewap_name));
        civInstance.getCivilization(getString(R.string.civ_dutch_name))
                .setUniqueInfrastructure(getString(R.string.tile_polder_name));
        civInstance.getCivilization(getString(R.string.civ_egyptian_name))
                .setUniqueInfrastructure(getString(R.string.tile_sphinx_name));
        civInstance.getCivilization(getString(R.string.civ_english_name))
                .setUniqueInfrastructure(getString(R.string.dis_royal_navy_dockyard_name));
        civInstance.getCivilization(getString(R.string.civ_french_name))
                .setUniqueInfrastructure(getString(R.string.tile_chateau_name));
        civInstance.getCivilization(getString(R.string.civ_georgian_name))
                .setUniqueInfrastructure(getString(R.string.build_tsikhe_name));
        civInstance.getCivilization(getString(R.string.civ_german_name))
                .setUniqueInfrastructure(getString(R.string.dis_hansa_name));
        civInstance.getCivilization(getString(R.string.civ_greek_name))
                .setUniqueInfrastructure(getString(R.string.dis_acropolis_name));
        civInstance.getCivilization(getString(R.string.civ_hungarian_name))
                .setUniqueInfrastructure(getString(R.string.build_thermal_bath_name));
        civInstance.getCivilization(getString(R.string.civ_incan_name))
                .setUniqueInfrastructure(getString(R.string.tile_terrace_farm_name));
        civInstance.getCivilization(getString(R.string.civ_indian_name))
                .setUniqueInfrastructure(getString(R.string.tile_stepwell_name));
        civInstance.getCivilization(getString(R.string.civ_indonesian_name))
                .setUniqueInfrastructure(getString(R.string.tile_kampung_name));
        civInstance.getCivilization(getString(R.string.civ_japanese_name))
                .setUniqueInfrastructure(getString(R.string.build_electronics_factory_name));
        civInstance.getCivilization(getString(R.string.civ_khmer_name))
                .setUniqueInfrastructure(getString(R.string.build_prasat_name));
        civInstance.getCivilization(getString(R.string.civ_kongolese_name))
                .setUniqueInfrastructure(getString(R.string.dis_mbanza_name));
        civInstance.getCivilization(getString(R.string.civ_korean_name))
                .setUniqueInfrastructure(getString(R.string.dis_seowon_name));
        civInstance.getCivilization(getString(R.string.civ_macedonian_name))
                .setUniqueInfrastructure(getString(R.string.build_basilikoli_paides_name));
        civInstance.getCivilization(getString(R.string.civ_mali_name))
                .setUniqueInfrastructure(getString(R.string.dis_suguba_name));
        civInstance.getCivilization(getString(R.string.civ_maori_name))
                .setUniqueInfrastructure(getString(R.string.build_marae_name));
        civInstance.getCivilization(getString(R.string.civ_mapuche_name))
                .setUniqueInfrastructure(getString(R.string.tile_chemamull_name));
        civInstance.getCivilization(getString(R.string.civ_mongolian_name))
                .setUniqueInfrastructure(getString(R.string.build_ordu_name));
        civInstance.getCivilization(getString(R.string.civ_norwegian_name))
                .setUniqueInfrastructure(getString(R.string.build_stave_church_name));
        civInstance.getCivilization(getString(R.string.civ_nubian_name))
                .setUniqueInfrastructure(getString(R.string.tile_nubian_pyramid_name));
        civInstance.getCivilization(getString(R.string.civ_ottoman_name))
                .setUniqueInfrastructure(getString(R.string.build_grand_bazaar_name));
        civInstance.getCivilization(getString(R.string.civ_persian_name))
                .setUniqueInfrastructure(getString(R.string.tile_pairidaerza_name));
        civInstance.getCivilization(getString(R.string.civ_phoenician_name))
                .setUniqueInfrastructure(getString(R.string.dis_cothon_name));
        civInstance.getCivilization(getString(R.string.civ_polish_name))
                .setUniqueInfrastructure(getString(R.string.build_sukiennice_name));
        civInstance.getCivilization(getString(R.string.civ_roman_name))
                .setUniqueInfrastructure(getString(R.string.dis_bath_name));
        civInstance.getCivilization(getString(R.string.civ_roman_name))
                .setUniqueInfrastructure(getString(R.string.tile_roman_fort_name));
        civInstance.getCivilization(getString(R.string.civ_russian_name))
                .setUniqueInfrastructure(getString(R.string.dis_lavra_name));
        civInstance.getCivilization(getString(R.string.civ_scottish_name))
                .setUniqueInfrastructure(getString(R.string.tile_golf_course_name));
        civInstance.getCivilization(getString(R.string.civ_scythian_name))
                .setUniqueInfrastructure(getString(R.string.tile_kurgan_name));
        civInstance.getCivilization(getString(R.string.civ_spanish_name))
                .setUniqueInfrastructure(getString(R.string.tile_mission_name));
        civInstance.getCivilization(getString(R.string.civ_sumerian_name))
                .setUniqueInfrastructure(getString(R.string.tile_ziggurat_name));
        civInstance.getCivilization(getString(R.string.civ_swedish_name))
                .setUniqueInfrastructure(getString(R.string.tile_open_air_museum_name));
        civInstance.getCivilization(getString(R.string.civ_zulu_name))
                .setUniqueInfrastructure(getString(R.string.dis_ikanda_name));
    }

    private void createLeaders(){

        leaderInstance = LeaderCollection.get(getApplicationContext());
        leaderInstance.addLeader(new Leader(getString(R.string.lead_teddy_name), getString(R.string.civ_american_name)));
        leaderInstance.addLeader(new Leader(getString(R.string.lead_saladin_name), getString(R.string.civ_arabian_name)));
        leaderInstance.addLeader(new Leader(getString(R.string.lead_curtin_name), getString(R.string.civ_australian_name)));
        leaderInstance.addLeader(new Leader(getString(R.string.lead_montezuma_name), getString(R.string.civ_aztec_name)));
        leaderInstance.addLeader(new Leader(getString(R.string.lead_pedro_name), getString(R.string.civ_brazilian_name)));
        leaderInstance.addLeader(new Leader(getString(R.string.lead_wilfrid_name), getString(R.string.civ_canadian_name)));
        leaderInstance.addLeader(new Leader(getString(R.string.lead_qin_name), getString(R.string.civ_chinese_name)));
        leaderInstance.addLeader(new Leader(getString(R.string.lead_poundmaker_name), getString(R.string.civ_cree_name)));
        leaderInstance.addLeader(new Leader(getString(R.string.lead_wilhelmina_name), getString(R.string.civ_dutch_name)));
        leaderInstance.addLeader(new Leader("Cleopatra", getString(R.string.civ_egyptian_name)));
        leaderInstance.addLeader(new Leader("Victoria", getString(R.string.civ_english_name)));
        leaderInstance.addLeader(new Leader("Eleanor of Aquitaine", getString(R.string.civ_english_name)));
        leaderInstance.addLeader(new Leader("Eleanor of Aquitaine", getString(R.string.civ_french_name)));
        leaderInstance.addLeader(new Leader("Catherine de Medici", getString(R.string.civ_french_name)));
        leaderInstance.addLeader(new Leader("Tamar", getString(R.string.civ_georgian_name)));
        leaderInstance.addLeader(new Leader("Frederick Barbarossa", getString(R.string.civ_german_name)));
        leaderInstance.addLeader(new Leader("Pericles", getString(R.string.civ_greek_name)));
        leaderInstance.addLeader(new Leader("Gorgo", getString(R.string.civ_greek_name)));
        leaderInstance.addLeader(new Leader("Matthias Corvinus", getString(R.string.civ_hungarian_name)));
        leaderInstance.addLeader(new Leader("Pachacuti", getString(R.string.civ_incan_name)));
        leaderInstance.addLeader(new Leader("Gandhi", getString(R.string.civ_indian_name)));
        leaderInstance.addLeader(new Leader("Chandragupta", getString(R.string.civ_indian_name)));
        leaderInstance.addLeader(new Leader("Gitarja", getString(R.string.civ_indonesian_name)));
        leaderInstance.addLeader(new Leader("Hojo Tokimune", getString(R.string.civ_japanese_name)));
        leaderInstance.addLeader(new Leader("Jayavarman VII", getString(R.string.civ_khmer_name)));
        leaderInstance.addLeader(new Leader("Mvemba a Nzinga", getString(R.string.civ_kongolese_name)));
        leaderInstance.addLeader(new Leader("Seondeok", getString(R.string.civ_korean_name)));
        leaderInstance.addLeader(new Leader("Alexander", getString(R.string.civ_macedonian_name)));
        leaderInstance.addLeader(new Leader("Mansa Musa", getString(R.string.civ_mali_name)));
        leaderInstance.addLeader(new Leader("Kupe", getString(R.string.civ_maori_name)));
        leaderInstance.addLeader(new Leader("Lautaro", getString(R.string.civ_mapuche_name)));
        leaderInstance.addLeader(new Leader("Genghis Khan", getString(R.string.civ_mongolian_name)));
        leaderInstance.addLeader(new Leader("Harald Hardrada", getString(R.string.civ_norwegian_name)));
        leaderInstance.addLeader(new Leader("Amanitore", getString(R.string.civ_nubian_name)));
        leaderInstance.addLeader(new Leader("Suleiman", getString(R.string.civ_ottoman_name)));
        leaderInstance.addLeader(new Leader("Cyrus", getString(R.string.civ_persian_name)));
        leaderInstance.addLeader(new Leader("Dido", getString(R.string.civ_phoenician_name)));
        leaderInstance.addLeader(new Leader("Jadwiga", getString(R.string.civ_polish_name)));
        leaderInstance.addLeader(new Leader("Trajan", getString(R.string.civ_roman_name)));
        leaderInstance.addLeader(new Leader("Peter", getString(R.string.civ_russian_name)));
        leaderInstance.addLeader(new Leader("Robert the Bruce", getString(R.string.civ_scottish_name)));
        leaderInstance.addLeader(new Leader("Tomyris", getString(R.string.civ_scythian_name)));
        leaderInstance.addLeader(new Leader("Philip II", getString(R.string.civ_spanish_name)));
        leaderInstance.addLeader(new Leader("Gilgamesh", getString(R.string.civ_sumerian_name)));
        leaderInstance.addLeader(new Leader("Kristina", getString(R.string.civ_swedish_name)));
        leaderInstance.addLeader(new Leader("Shaka", getString(R.string.civ_zulu_name)));


        for(Leader lead: leaderInstance.getLeaders()){
            lead.setUrl("https://civilization.fandom.com/wiki/" + lead.getName().replaceAll(" ", "_") + "_(Civ6)");
        }
        //leaderList = Arrays.asList(leader);
        //LeaderCollection.get(getApplicationContext()).addLeaders(leaderList);
        //LeaderCollection.get(getApplicationContext()).setLeaders(leaderList);
    }

    private void populateExpandableList() {
        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                /**if (headerList.get(groupPosition).isGroup) {
                    if(!headerList.get(groupPosition).hasChildren) {
                        mWebView.loadUrl(headerList.get(groupPosition).url);
                        onBackPressed();
                    }
                }**/
                if (headerList.get(groupPosition).url.equals("civViewer")) {
                    CivInfoFragment fragment = new CivInfoFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.fragment_container, fragment, "civViewer");
                    currFrag = "civViewer";
                    ft.commit();
                    onBackPressed();
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    if (model.url.equals("civViewer")) {
                        CivInfoFragment fragment = new CivInfoFragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.addToBackStack(null);
                        ft.replace(R.id.fragment_container, fragment, "civViewer");
                        currFrag = "civViewer";
                        ft.commit();
                    }else if (model.url.length() > 0) {
                        WikiInfoFragment fragment = new WikiInfoFragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        fragment.setUrl(model.url);
                        ft.replace(R.id.fragment_container, fragment, "wikiViewer");
                        currFrag = "wikiViewer";
                        ft.commit();
                        //mWebView.loadUrl(model.url);
                        onBackPressed();
                    }
                }
                return false;
            }
        });
    }
}
