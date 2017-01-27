package jp.ac.it_college.std.s15007.jinro;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by samuel on 17/01/18.
 */

public class GameWindowDay extends Activity {

    private Handler mHandler = new Handler();
    private Runnable updateText;
    private JinroDBHelper myDb;
    private PostComment postData;
    private TabHost tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_window_day);

        tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();

        TabSpec tab1 = tabs.newTabSpec("tab1");
        tab1.setIndicator("チャット");
        tab1.setContent(R.id.tab1);
        tabs.addTab(tab1);

        TabSpec tab2 = tabs.newTabSpec("tab2");
        tab2.setIndicator("メンバー");
        tab2.setContent(R.id.tab2);
        tabs.addTab(tab2);

        setTimer();
        tabs.setCurrentTab(0);

        show_comment();

        getFlick();
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

    private void getFlick() {
        View flickView = getWindow().getDecorView(); // Activity画面
        float adjustX = 150.0f;
        float adjustY = 150.0f;

        new FlickCheck(flickView, adjustX, adjustY) {
            @Override
            public void getFlick(int swipe) {
                switch (swipe) {
                    case FLICK_RIGHT:
                        tabs.setCurrentTab(0);
                        break;
                    case FLICK_LEFT:
                        tabs.setCurrentTab(1);
                        break;
                }
            }
        };
    }

    private void post_comment() {
        SQLiteDatabase db = myDb.getWritableDatabase();

        EditText comment = (EditText) findViewById(R.id.edit_comment);

    }

    private void show_comment() {

        JinroDBHelper myDb = new JinroDBHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();

        Cursor cursor = db.query(JinroDBHelper.TABLE_NAME_VILLAGE, new String[] {
                        JinroDBHelper.ColumnsVillage.VILLAGE_NAME,
                        JinroDBHelper.ColumnsVillage.PLAYER_NAME },
                null, null, null, null, null);

        boolean isEof = cursor.moveToFirst();

        final ArrayList<String> village_name_list = new ArrayList<>();
        ArrayList<String> player_name_list = new ArrayList<>();
        ArrayList<Village> villages = new ArrayList<>();

        ListView myListView = (ListView) findViewById(R.id.chat_list);


        while (isEof) {
            String v_name_data = cursor.getString(0);
            String p_name_data = cursor.getString(1);

            village_name_list.add(v_name_data);
            player_name_list.add(p_name_data);

            Village village = new Village();


            village.setVillage(v_name_data);
            village.setPlayer(p_name_data);
            villages.add(village);
            isEof = cursor.moveToNext();
        }

        VillageAdapter adapter = new VillageAdapter(this, 0, villages);


        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                AlertDialog.Builder alertDlg = new AlertDialog.Builder(GameWindowDay.this);
                alertDlg.setMessage(village_name_list.get(pos) + " に入りますか？");
                alertDlg.setNegativeButton("キャンセル", null);
                alertDlg.setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                // 表示
                alertDlg.create().show();
            }
        });

        db.close();

    }

    public class ShowPlayerComment {
        private String player_name_data;
        private String comment_data;

        public String getPlayer_name_data() {
            return player_name_data;
        }

        public void setPlayer_name_data(String player_name_data) {
            this.player_name_data = player_name_data;
        }

        public String getComment() {
            return comment_data;
        }

        public void setComment(String comment) {
            this.comment_data = comment;
        }
    }

    public class PostComment {
        String player_name;
        String comment;
    }



    public class Village {
        private String village;
        private String player;

        public String getVillage() {
            return village;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public String getPlayer() {
            return player;
        }

        public void setPlayer(String player) {
            this.player = player;
        }
    }


    public class VillageAdapter extends ArrayAdapter<Village> {

        private LayoutInflater layoutInflater;

        public VillageAdapter(Context c, int id, ArrayList<Village> villages) {
            super(c, id, villages);
            this.layoutInflater = (LayoutInflater) c.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE
            );
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(
                        R.layout.chat_tab_list_item,
                        parent,
                        false
                );
            }

            Village village = (Village) getItem(position);

            ((TextView) convertView.findViewById(R.id.post_column_name))
                    .setText(village.getVillage()+ " > ");
            ((TextView) convertView.findViewById(R.id.post_column_body))
                    .setText(village.getPlayer());

            return convertView;
        }
    }
}
