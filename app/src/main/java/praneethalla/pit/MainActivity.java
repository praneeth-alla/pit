package praneethalla.pit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Main Activity which renders the PitView
 * Uses PitView for creating the layout {@link praneethalla.pit.PitView}
 **/

public class MainActivity extends AppCompatActivity {

    PitView mPitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPitView = new PitView(this);
        mPitView.setBackgroundColor(Color.WHITE);

        setContentView(mPitView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Toast.makeText(this, "Point added at origin", Toast.LENGTH_LONG).show();
                mPitView.addPoint();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
