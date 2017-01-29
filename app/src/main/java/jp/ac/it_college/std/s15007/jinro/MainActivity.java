package jp.ac.it_college.std.s15007.jinro;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(this, R.raw.desperate);
        mp.setVolume(0.5f, 0.5f);
        mp.setLooping(true);
        mp.start();

        Button findVillage = (Button) findViewById(R.id.btn_find_village);
        Button makeVillage = (Button) findViewById(R.id.btn_make_village);

        findVillage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp = MediaPlayer.create(MainActivity.this, R.raw.b_069);
                mp.setVolume(0.8f, 0.8f);
                mp.start();
                Intent intent = new Intent(getApplication(), FindVillage.class);
                startActivity(intent);
            }
        });


        makeVillage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp = MediaPlayer.create(MainActivity.this, R.raw.b_069);
                mp.setVolume(0.8f, 0.8f);
                mp.start();
                Intent intent = new Intent(getApplication(), MakeVillage.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }
}
