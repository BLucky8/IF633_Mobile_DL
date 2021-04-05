package umn.ac.id.week08_31943;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Tutorial2 extends AppCompatActivity {
    private int mCount = 0;
    private int mWarna;
    private TextView tvCounter;
    private Context context;
    private final String COUNTER_KEY = "counter";
    private final String WARNA_KEY = "warna";
    private SharedPreferences mPreferences;
    private String sharedPrefFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        tvCounter = (TextView) findViewById(R.id.tvCounter);
        sharedPrefFile = context.getPackageName();
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mCount = mPreferences.getInt(COUNTER_KEY, 0);
        tvCounter.setText(String.valueOf(mCount));
        mWarna = mPreferences.getInt(WARNA_KEY, mWarna);
        tvCounter.setBackgroundColor(mWarna);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNTER_KEY, mCount);
        outState.putInt(WARNA_KEY, mWarna);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(COUNTER_KEY, mCount);
        preferencesEditor.putInt(WARNA_KEY, mWarna);
        preferencesEditor.apply();
    }

    public void gantiBackground(View view){
        int warna = ((ColorDrawable) view.getBackground()).getColor();
        mWarna = warna;
        tvCounter.setBackgroundColor(warna);
    }

    public void tambahCounter(View view){
        mCount++;
        tvCounter.setText(String.valueOf(mCount));
    }

    public void reset(View view){
        mCount = 0;
        tvCounter.setText(String.valueOf(mCount));
        mWarna = ContextCompat.getColor(context, R.color.default_background);
        tvCounter.setBackgroundColor(mWarna);
    }
}
