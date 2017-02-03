package jp.ac.it_college.std.s15007.jinro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created by samuel on 17/01/25.
 */

public class GameWindowExecution extends Activity {
    private Handler mHandler = new Handler();
    private Runnable updateText;
    private String player_name;
    private int village_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_window_execution);

        Intent intent = getIntent();
        player_name = intent.getStringExtra("player_name");
        village_id = intent.getIntExtra("village_id", 0);

        TextView time = (TextView) findViewById(R.id.time);
        time.setText("60");

        TextView player = (TextView) findViewById(R.id.pname);
        player.setText(player_name);

        TabHost tabhost = (TabHost) findViewById(R.id.tabhost);
        tabhost.setup();

        TabHost.TabSpec tab1 = tabhost.newTabSpec("tab1");
        tab1.setIndicator("チャット");
        tab1.setContent(R.id.tab1);
        tabhost.addTab(tab1);

        TabHost.TabSpec tab2 = tabhost.newTabSpec("tab2");
        tab2.setIndicator("メンバー");
        tab2.setContent(R.id.tab2);
        tabhost.addTab(tab2);

        tabhost.setCurrentTab(0);

        updateText = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GameWindowExecution.this, GameWindow.class);
                intent.putExtra("time", "night");
                intent.putExtra("village_id", village_id);
                intent.putExtra("player_name", player_name);
                TextView setTime = (TextView) findViewById(R.id.time);
                Integer count = Integer.valueOf(setTime.getText().toString());
                count -= 1;
                setTime.setText(count.toString());
                mHandler.removeCallbacks(updateText);
                if (count > 0) {
                    mHandler.postDelayed(updateText, 1000);
                } else {
                    startActivity(intent);
                }
            }
        };
        mHandler.postDelayed(updateText, 1000);
    }
}
