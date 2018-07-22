package edu.csulb.theteam;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(R.id.fragment,
                new EventsFragment()).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_friends:
                                getFragmentManager().beginTransaction().replace(R.id.fragment,
                                        new EventsFragment()).commit();
                            case R.id.action_events:
                                getFragmentManager().beginTransaction().replace(R.id.fragment,
                                        new EventsFragment()).commit();

                            case R.id.action_music:
                                getFragmentManager().beginTransaction().replace(R.id.fragment,
                                        new EventsFragment()).commit();
                        }
                        return true;
                    }
                });


    }
}
