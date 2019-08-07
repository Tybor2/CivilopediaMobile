package android.example.civilopediamobile;


import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CivInfoFragment extends Fragment {
    private Spinner civilizationSpinner;
    private Spinner leaderSpinner;
    private ConstraintLayout parentLayout;
    private TextView infrastructureTV;
    private TextView spinnerLabel;
    private TextView unitTV;

    public CivInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = getLayoutInflater().inflate(R.layout.content_game_creator, container, false);

        final CivilizationCollection civInstance = CivilizationCollection.get(view.getContext());
        List<Civilization> civs = civInstance.getCivilizations();
        civilizationSpinner = (Spinner) view.findViewById(R.id.civ_spinner);
        ArrayAdapter<Civilization> Civadapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_layout, civs);
        civilizationSpinner.setAdapter(Civadapter);

        LeaderCollection leaderInstance = LeaderCollection.get(view.getContext());
        List<Leader> leaders = civInstance.getCivilization(civilizationSpinner.getSelectedItem().toString()).getLeaders();
        leaderSpinner = (Spinner) view.findViewById(R.id.leader_spinner);
        ArrayAdapter<Leader> leaderAdapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_layout, leaders);
        leaderSpinner.setAdapter(leaderAdapter);
        spinnerLabel = (TextView)view.findViewById(R.id.spinner_label);
        parentLayout = (ConstraintLayout) view.findViewById(R.id.game_creator_layout);
        unitTV = (TextView) view.findViewById(R.id.unique_units_tv);


        civilizationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Civilization chosen = civInstance.getCivilization(civilizationSpinner.getSelectedItem().toString());
                spinnerLabel = (TextView)view.findViewById(R.id.spinner_label);
                //spinnerLabel.setTextColor(getResources().getColor(chosen.getColors().get(1)));
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(chosen.getColors().get(1)));
                updateLeaderSpinners(view, chosen);
                //updateLabels(chosen, );


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Civilization chosen = civInstance.getCivilization("American");
                TextView spinnerLabel = (TextView)view.findViewById(R.id.spinner_label);
                spinnerLabel.setTextColor(getResources().getColor(chosen.getColors().get(1)));
                updateLeaderSpinners(view, chosen);
            }
        });



        infrastructureTV = (TextView) view.findViewById(R.id.unique_infra_tv);

        return view;
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
        infrastructureTV.setTextColor(getResources().getColor(leader.getColors().get(1)));
        unitTV.setTextColor(getResources().getColor(leader.getColors().get(1)));
    }

    private void updateLeaderSpinners(View view, final Civilization civ){
        final LeaderCollection leaderInstance = LeaderCollection.get(view.getContext());
        List<Leader> leaders = civ.getLeaders();
        //leaderSpinner = (Spinner) view.findViewById(R.id.leader_spinner);
        final ArrayAdapter<Leader> leaderAdapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_layout, leaders);
        leaderSpinner.setAdapter(leaderAdapter);





        leaderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Leader leader = leaderInstance.getLeader(leaderSpinner.getSelectedItem().toString(), civ.getName());
                changeTextColor(parentLayout, leader);
                parentLayout.setBackgroundColor(getResources().getColor(leader.getColors().get(0)));
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(leader.getColors().get(1)));
                updateLabels(civ, leader);
                updateUnitLabel(civ, leader);
                //spinnerLabel.setTextColor(civ.getColors().get(1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinnerLabel = (TextView)adapterView.findViewById(R.id.spinner_label);
                //spinnerLabel.setTextColor(civ.getColors().get(1));
                Leader leader = leaderInstance.getLeader(leaderSpinner.getSelectedItem().toString(), civ.getName());
                changeTextColor(parentLayout, leader);
                parentLayout.setBackgroundColor(getResources().getColor(leader.getColors().get(0)));
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(leader.getColors().get(1)));
                updateLabels(civ, leader);
                updateUnitLabel(civ, leader);
            }
        });
    }

    private void updateLabels(Civilization civ, Leader leader){
        final List<String> infra = civ.getUniqueInfrastructure();
        final SpannableStringBuilder spanText = new SpannableStringBuilder();
        String label = "";
        final int i = 0;
        for(final String s: infra){
            spanText.append(s);
            spanText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CivInfoUrlViewerActivity.class);
                    intent.putExtra("urlToView", s);
                    startActivity(intent);
                }
            }, spanText.length() - s.length(), spanText.length(), 0);
            if(label.equals(""))
                label += s;
            else
                label += (", " + s);
        }
        //infrastructureTV.setText(label);
        spanText.setSpan(new ForegroundColorSpan(getResources().getColor(leader.getColors().get(1))), 0, spanText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        infrastructureTV.setMovementMethod(LinkMovementMethod.getInstance());
        infrastructureTV.setText(spanText, TextView.BufferType.SPANNABLE);
    }

    private void updateUnitLabel(Civilization civ, Leader leader){
        unitTV.setText("No Unique Units");
        String label = "";
        final SpannableStringBuilder spanText = new SpannableStringBuilder();

        for(final String unit: civ.getUniqueUnits()){
            if(spanText == null)
                spanText.append(", ");
            spanText.append(unit);
            spanText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CivInfoUrlViewerActivity.class);
                    intent.putExtra("urlToView", unit);
                    startActivity(intent);
                }
            }, spanText.length() - unit.length(), spanText.length(), 0);

        }

        for(final String unit: leader.getUniqueUnits()){
            if(!spanText.equals(""))
                spanText.append(", ");
            spanText.append(unit);
            spanText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CivInfoUrlViewerActivity.class);
                    intent.putExtra("urlToView", unit);
                    startActivity(intent);
                }
            }, spanText.length() - unit.length(), spanText.length(), 0);

        }
        spanText.setSpan(new ForegroundColorSpan(getResources().getColor(leader.getColors().get(1))), 0, spanText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        unitTV.setMovementMethod(LinkMovementMethod.getInstance());
        unitTV.setText(spanText, TextView.BufferType.SPANNABLE);
    }
}
