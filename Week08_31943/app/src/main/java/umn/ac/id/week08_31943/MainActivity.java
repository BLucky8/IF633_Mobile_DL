package umn.ac.id.week08_31943;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public Button tutorial1, tutorial2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        tutorial1 = (Button) findViewById(R.id.tutorial1);
        tutorial2 = (Button) findViewById(R.id.tutorial2);

        tutorial1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Tutorial1.class);
                startActivity(i);
            }
        });
        tutorial2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Tutorial2.class);
                startActivity(i);
            }
        });
    }
}
