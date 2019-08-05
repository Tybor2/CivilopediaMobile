package android.example.civilopediamobile;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class GameCreatorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Spinner civilizationSpinner;
    private Spinner leaderSpinner;
    private TextView infrastructureTV;
    private TextView spinnerLabel;
    private TextView unitTV;
    private ConstraintLayout parentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_creator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(getApplicationContext(), GameCreatorActivity.class);
                startActivity(i);
            }
        });


        final CivilizationCollection civInstance = CivilizationCollection.get(getApplicationContext());
        List<Civilization> civs = civInstance.getCivilizations();
        civilizationSpinner = (Spinner) findViewById(R.id.civ_spinner);
        ArrayAdapter<Civilization> Civadapter = new ArrayAdapter<>(this, R.layout.spinner_layout, civs);
        civilizationSpinner.setAdapter(Civadapter);

        LeaderCollection leaderInstance = LeaderCollection.get(getApplicationContext());
        List<Leader> leaders = civInstance.getCivilization(civilizationSpinner.getSelectedItem().toString()).getLeaders();
        leaderSpinner = (Spinner) findViewById(R.id.leader_spinner);
        ArrayAdapter<Leader> leaderAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, leaders);
        leaderSpinner.setAdapter(leaderAdapter);

        parentLayout = (ConstraintLayout) findViewById(R.id.game_creator_layout);

        unitTV = (TextView) findViewById(R.id.unique_units_tv);


        civilizationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Civilization chosen = civInstance.getCivilization(civilizationSpinner.getSelectedItem().toString());
                spinnerLabel = (TextView)findViewById(R.id.spinner_label);
                //spinnerLabel.setTextColor(getResources().getColor(chosen.getColors().get(1)));
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(chosen.getColors().get(1)));
                updateLeaderSpinners(chosen);
                updateLabels(chosen);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Civilization chosen = civInstance.getCivilization("American");
                TextView spinnerLabel = (TextView)findViewById(R.id.spinner_label);
                spinnerLabel.setTextColor(getResources().getColor(chosen.getColors().get(1)));
                updateLeaderSpinners(chosen);
            }
        });



        infrastructureTV = (TextView) findViewById(R.id.unique_infra_tv);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void changeTextColor(ViewGroup layout, Leader leader){
        for(int i = 0; i < layout.getChildCount();i++){
            View view = layout.getChildAt(i);
            if(view instanceof TextView){
                ((TextView)view).setTextColor(getResources().getColor(leader.getColors().get(1)));
            } else if(view instanceof Spinner){
                changeTextColor((ViewGroup)view, leader);
            }
        }
    }

    private void updateLeaderSpinners(final Civilization civ){
        final LeaderCollection leaderInstance = LeaderCollection.get(getApplicationContext());
        List<Leader> leaders = civ.getLeaders();
        leaderSpinner = (Spinner) findViewById(R.id.leader_spinner);
        final ArrayAdapter<Leader> leaderAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, leaders);
        leaderSpinner.setAdapter(leaderAdapter);





        leaderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerLabel = (TextView)findViewById(R.id.spinner_label);
                Leader leader = leaderInstance.getLeader(leaderSpinner.getSelectedItem().toString(), civ.getName());
                changeTextColor(parentLayout, leader);
                parentLayout.setBackgroundColor(getResources().getColor(leader.getColors().get(0)));
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(leader.getColors().get(1)));
                updateUnitLabel(civ, leader);
                //spinnerLabel.setTextColor(civ.getColors().get(1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinnerLabel = (TextView)findViewById(R.id.spinner_label);
                //spinnerLabel.setTextColor(civ.getColors().get(1));
                Leader leader = leaderInstance.getLeader(leaderSpinner.getSelectedItem().toString(), civ.getName());
                changeTextColor(parentLayout, leader);
                parentLayout.setBackgroundColor(getResources().getColor(leader.getColors().get(0)));
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(leader.getColors().get(1)));
                updateUnitLabel(civ, leader);
            }
        });
    }

    private void updateLabels(Civilization civ){
        List<String> infra = civ.getUniqueInfrastructure();
        String label = "";
        for(String s: infra){
            if(label.equals(""))
                label += s;
            else
                label += (", " + s);
        }
        infrastructureTV.setText(label);
    }

    private void updateUnitLabel(Civilization civ, Leader leader){
        unitTV.setText("No Unique Units");
        for(String unit: civ.getUniqueUnits()){
            if(unitTV.getText().equals("No Unique Units"))
                unitTV.setText(unit);
            else
                unitTV.setText(unitTV.getText() + ", " + unit);
        }

        for(String unit: leader.getUniqueUnits()){
            if(unitTV.getText().equals("No Unique Units"))
                unitTV.setText(unit);
            else
                unitTV.setText(unitTV.getText() + ", " + unit);
        }
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


}
