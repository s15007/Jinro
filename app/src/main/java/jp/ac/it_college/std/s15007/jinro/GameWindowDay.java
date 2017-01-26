package jp.ac.it_college.std.s15007.jinro;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 * Created by samuel on 17/01/18.
 */

public class GameWindowDay extends Activity {

    private Handler mHandler = new Handler();
    private Runnable updateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_window_day);

        final TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();

        final TabSpec tab1 = tabs.newTabSpec("tab1");
        tab1.setIndicator("チャット");
        tab1.setContent(R.id.tab1);
        tabs.addTab(tab1);

        TabSpec tab2 = tabs.newTabSpec("tab2");
        tab2.setIndicator("メンバー");
        tab2.setContent(R.id.tab2);
        tabs.addTab(tab2);

        View flickView = getWindow().getDecorView(); // Activity画面
        float adjustX = 150.0f;
        float adjustY = 150.0f;

        new FlickCheck(flickView, adjustX, adjustY) {
            @Override
            public void getFlick(int swipe) {
                switch (swipe) {
                    case FLICK_LEFT:
                        tabs.setCurrentTab(0);
                        break;
                    case FLICK_RIGHT:
                        tabs.setCurrentTab(1);
                        break;
                    default:
                        tabs.setCurrentTab(0);
                        break;
                }
            }
        };

        setTimer();
        tabs.setCurrentTab(0);


    }

    private void setTimer() {
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
    }
}
