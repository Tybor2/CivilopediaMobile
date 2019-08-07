package android.example.civilopediamobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class CivInfoUrlViewerActivity extends AppCompatActivity{
    private static final String URL_EXTRA = "urlToView";

    private Spinner civilizationSpinner;
    private Spinner leaderSpinner;
    private TextView infrastructureTV;
    private TextView spinnerLabel;
    private TextView unitTV;
    private ConstraintLayout parentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.civ_info_url_viewer);
        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String url = intent.getStringExtra("urlToView");
        url = "https://civilization.fandom.com/wiki/" + url + "_(Civ6)";
        WikiInfoFragment fragment = new WikiInfoFragment();
        fragment.setUrl(url);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.civ_url_viewer_frag_container, fragment);
        ft.commit();

    }






}
