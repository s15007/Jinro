package jp.ac.it_college.std.s15007.jinro;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by samuel on 17/01/18.
 */

public class GameWindowDay extends Activity {

    private Handler mHandler = new Handler();
    private PostComment data;
    private Runnable updateText;
    private String player_name;
    private int village_id;
    TabHost tabHost;
    TabWidget tabWidget;
    MediaPlayer mp = null;

    private ListView mainlayout;
    private InputMethodManager inputMethodManager;
    private View window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_window_day);

        Intent intent = getIntent();

        player_name = intent.getStringExtra("player_name");
        String player_job = intent.getStringExtra("player_job");
        String village_name = intent.getStringExtra("village_name");
        village_id = intent.getIntExtra("village_id", 0);

        TextView player = (TextView) findViewById(R.id.pname);
        TextView job = (TextView) findViewById(R.id.pjob);
        player.setText(player_name);
        job.setText(" [" + player_job + "]");

        tabWidget = (TabWidget) findViewById(android.R.id.tabs);
        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setIndicator("チャット");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2");
        tab2.setIndicator("メンバー");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        tabHost.setCurrentTab(0);


        data = new PostComment();

        Button btn_comment = (Button) findViewById(R.id.btn_comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText comment = (EditText) findViewById(R.id.edit_comment);
                data.player_name = player_name;
                data.comment = comment.getText().toString();
                data.village_id = village_id;
                if (!data.comment.equals("")) {
                    comment.getEditableText().clear();
                    insertComment(data);
                    show_comment(village_id);
                }
            }
        });

        mainlayout = (ListView) findViewById(R.id.chat_list);
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        window = findViewById(R.id.chat_list);
        window.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                inputMethodManager.hideSoftInputFromWindow(mainlayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                mainlayout.requestFocus();
                return false;
            }
        });

        show_comment(village_id);
        show_users(village_id);
        setTimer();

    }

    public class PostComment {
        String player_name;
        String comment;
        int village_id;
    }

    public class Comments {
        private String playerName;
        private String playerComment;

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public String getPlayerComment() {
            return playerComment;
        }

        public void setPlayerComment(String playerComment) {
            this.playerComment = playerComment;
        }
    }

    public class CommentAdapter extends ArrayAdapter<Comments> {

        private LayoutInflater layoutInflater;

        public CommentAdapter(Context c, int id, ArrayList<Comments> villages) {
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

            Comments comments = (Comments) getItem(position);

            ((TextView) convertView.findViewById(R.id.post_column_name))
                    .setText(comments.getPlayerName()+ " >> ");
            ((TextView) convertView.findViewById(R.id.post_column_body))
                    .setText(comments.getPlayerComment());

            return convertView;
        }
    }

    private void show_users(int v_id) {

        JinroDBHelper myDb = new JinroDBHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();

        ListView myListView = (ListView) findViewById(R.id.list_players);

        Cursor cursor = db.query("users", new String[]{"name"},
                "village_id = ?", new String[]{"" + v_id},
                null, null, null);

        int Index = cursor.getColumnIndex("name");
        ArrayList<String> name_list = new ArrayList<>();

        while (cursor.moveToNext()) {
            name_list.add(cursor.getString(Index));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.game_members_row, name_list);
        myListView.setAdapter(adapter);

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

    private void insertComment(PostComment data) {
        JinroDBHelper myDb = new JinroDBHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(JinroDBHelper.ColumnsPost.VILLAGE_ID, data.village_id);
        values.put(JinroDBHelper.ColumnsPost.USER_NAME, data.player_name);
        values.put(JinroDBHelper.ColumnsPost.BODY, data.comment);

        long id = db.insert(JinroDBHelper.TABLE_NAME_POST, null, values);

        if (id == -1) {
            Toast.makeText(GameWindowDay.this, "送信できませんでした", Toast.LENGTH_LONG).show();
        }

    }

    private void show_comment(int v_id) {

        JinroDBHelper myDb = new JinroDBHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();

        Cursor cursor = db.query(JinroDBHelper.TABLE_NAME_POST, new String[]{
                JinroDBHelper.ColumnsPost.USER_NAME,
                JinroDBHelper.ColumnsPost.BODY},
                "village_id = ?", new String[]{"" + v_id},
                null, null, null);

        boolean isEof = cursor.moveToFirst();

        final ArrayList<String> village_name_list = new ArrayList<>();
        ArrayList<String> player_name_list = new ArrayList<>();
        ArrayList<Comments> villages = new ArrayList<>();

        ListView myListView = (ListView) findViewById(R.id.chat_list);


        while (isEof) {
            String v_name_data = cursor.getString(0);
            String p_name_data = cursor.getString(1);

            village_name_list.add(v_name_data);
            player_name_list.add(p_name_data);

            Comments comments = new Comments();


            comments.setPlayerName(v_name_data);
            comments.setPlayerComment(p_name_data);
            villages.add(comments);
            isEof = cursor.moveToNext();
        }

        CommentAdapter adapter = new CommentAdapter(this, 0, villages);


        myListView.setAdapter(adapter);
        myListView.setSelection(myListView.getCount() - 1);

        db.close();
    }

}
