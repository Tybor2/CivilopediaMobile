package android.example.civilopediamobile;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameGeneratorFragment extends Fragment {
    private TextView civTV;
    private TextView leaderTV;
    private TextView difficultyTV;
    private TextView mapTV;
    private TextView sizeTV;
    private TextView victoryTV;
    private TextView ageTV;
    private TextView tempTV;
    private TextView rainTV;
    private TextView seaTV;
    private TextView resourcesTV;
    private TextView startTV;
    private boolean advanced;


    public GameGeneratorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_generator, container, false);
        civTV = (TextView) view.findViewById(R.id.generate_civ_TV);
        leaderTV = (TextView) view.findViewById(R.id.generate_leader_TV);
        difficultyTV = (TextView) view.findViewById(R.id.generate_difficulty_TV);
        mapTV = (TextView) view.findViewById(R.id.generate_map_TV);
        sizeTV = (TextView) view.findViewById(R.id.generate_size_TV);
        victoryTV = (TextView) view.findViewById(R.id.generate_victory_TV);
        ageTV = (TextView) view.findViewById(R.id.generate_age_TV);
        tempTV = (TextView) view.findViewById(R.id.generate_temp_TV);
        rainTV = (TextView) view.findViewById(R.id.generate_rain_TV);
        seaTV = (TextView) view.findViewById(R.id.generate_sea_TV);
        resourcesTV = (TextView) view.findViewById(R.id.generate_resource_TV);
        startTV = (TextView) view.findViewById(R.id.generate_start_TV);
        Button basicButton = view.findViewById(R.id.basic_game_button);
        basicButton.setText("Generate Basic Game");
        basicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advanced = false;
                updateBasicLabels();
            }
        });

        Button advancedButton = view.findViewById(R.id.advanced_game_button);
        advancedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advanced = true;
                updateBasicLabels();
                updateAdvancedLabels();
            }
        });
        return view;
    }

    private void updateBasicLabels(){
        CivilizationCollection civInstance = CivilizationCollection.get(getContext());
        LeaderCollection leaderInstance = LeaderCollection.get(getContext());
        Random rand = new Random();
        int randomIndex = rand.nextInt(civInstance.getCivilizations().size());
        Civilization civ = civInstance.getCivilizations().get(randomIndex);
        civTV.setText(civ.getName());
        randomIndex = rand.nextInt(leaderInstance.getLeaders(civ).size());
        leaderTV.setText(leaderInstance.getLeaders(civ).get(randomIndex).getName());
        String[] difficulty = getResources().getStringArray(R.array.difficulty);
        String[] map = getResources().getStringArray(R.array.map);
        String[] size = getResources().getStringArray(R.array.mapSize);
        String[] victory = getResources().getStringArray(R.array.victory);
        difficultyTV.setText(difficulty[rand.nextInt(difficulty.length)]);
        mapTV.setText(map[rand.nextInt(map.length)]);
        sizeTV.setText(size[rand.nextInt(size.length)]);
        victoryTV.setText(victory[rand.nextInt(victory.length)]);
        if(!advanced){
            ageTV.setVisibility(View.INVISIBLE);
            tempTV.setVisibility(View.INVISIBLE);
            rainTV.setVisibility(View.INVISIBLE);
            seaTV.setVisibility(View.INVISIBLE);
            resourcesTV.setVisibility(View.INVISIBLE);
            startTV.setVisibility(View.INVISIBLE);
        }
    }

    private void updateAdvancedLabels(){
        Random rand = new Random();
        String[] age = getResources().getStringArray(R.array.worldAge);
        String[] temperature = getResources().getStringArray(R.array.temperature);
        String[] rainfall = getResources().getStringArray(R.array.rainfall);
        String[] seaLevel = getResources().getStringArray(R.array.seaLevel);
        String[] resources = getResources().getStringArray(R.array.resources);
        String[] start = getResources().getStringArray(R.array.start);
        ageTV.setText(age[rand.nextInt(age.length)]);
        ageTV.setVisibility(View.VISIBLE);
        tempTV.setText(temperature[rand.nextInt(temperature.length)]);
        tempTV.setVisibility(View.VISIBLE);
        rainTV.setText(rainfall[rand.nextInt(rainfall.length)]);
        rainTV.setVisibility(View.VISIBLE);
        seaTV.setText(seaLevel[rand.nextInt(seaLevel.length)]);
        seaTV.setVisibility(View.VISIBLE);
        resourcesTV.setText(resources[rand.nextInt(resources.length)]);
        resourcesTV.setVisibility(View.VISIBLE);
        startTV.setText(start[rand.nextInt(start.length)]);
        startTV.setVisibility(View.VISIBLE);
    }

}
