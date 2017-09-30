package me.fahadalrabbani.golfscorecard;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

public class MainActivity extends ListActivity {
    private static final String PREFS_FILE = "me.fahadalrabbani.golfscorecard.preferences";
    private static final String KEY_STROKECOUNT = "KEY_STROKECOUNT";

    private SharedPreferences.Editor mEditor;
    private Hole[] mHoles = new Hole[18];
    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences mSharedPreferences;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();
        int strokes = 0;

        for (int i = 0; i < mHoles.length; i++) {
            strokes = mSharedPreferences.getInt(KEY_STROKECOUNT + i, 0);
            mHoles[i] = new Hole(String.format(Locale.getDefault(), "Hole %d : ", i + 1), strokes);
        }

        mListAdapter = new ListAdapter(getApplicationContext(), mHoles);
        setListAdapter(mListAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();

        for (int i = 0; i < mHoles.length; i++) {
            mEditor.putInt(KEY_STROKECOUNT + i, mHoles[i].getStrokeCount());
        }

        mEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_clearstroke) {
            mEditor.clear();
            mEditor.apply();
            for (Hole hole : mHoles) {
                hole.setStrokeCount(0);
            }
            mListAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
