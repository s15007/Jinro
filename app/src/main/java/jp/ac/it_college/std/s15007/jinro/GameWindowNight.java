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

public class GameWindowNight extends Activity {

    private Handler mHandler = new Handler();
    private Runnable updateText;

    private int village_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_window_night);

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
                TextView setTime = (TextView) findViewById(R.id.time);
                Integer count = Integer.valueOf(setTime.getText().toString());
                count -= 1;
                setTime.setText(count.toString());
                mHandler.removeCallbacks(updateText);
                if (count > 0) {
                    mHandler.postDelayed(updateText, 1000);
                } else {
                    finish();
                }
            }
        };
        mHandler.postDelayed(updateText, 1000);

        Intent intent = getIntent();
        village_id = intent.getIntExtra("village_id", 0);

    }
}
