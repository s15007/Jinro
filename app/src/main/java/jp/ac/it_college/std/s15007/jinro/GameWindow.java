package jp.ac.it_college.std.s15007.jinro;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 * Created by samuel on 17/01/18.
 */

public class GameWindow extends Activity {
    public int time = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_window);

        TabHost tabhost = (TabHost) findViewById(R.id.tabhost);
        tabhost.setup();

        TabSpec tab1 = tabhost.newTabSpec("tab1");
        tab1.setIndicator("チャット");
        tab1.setContent(R.id.tab1);
        tabhost.addTab(tab1);

        TabSpec tab2 = tabhost.newTabSpec("tab2");
        tab2.setIndicator("メンバー");
        tab2.setContent(R.id.tab2);
        tabhost.addTab(tab2);

        tabhost.setCurrentTab(0);

        TextView setTime = (TextView) findViewById(R.id.time);

    }
}
