package jp.ac.it_college.std.s15007.jinro;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by samuel on 17/01/18.
 */

public class FindVillage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_village);

        show_village();

        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void show_village() {
        JinroDBHelper myDb = new JinroDBHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();

        Cursor cursor = db.query("village", new String[] { "village_name", "player_name" },
                null, null, null, null, null);
        boolean isEof = cursor.moveToFirst();

        ArrayList<String> items = new ArrayList<>();
        final ArrayList<String> village_name_list = new ArrayList<>();
        ArrayList<String> player_name_list = new ArrayList<>();

        while (isEof) {
            String village_data = String.format("%s       %s", cursor.getString(0), cursor.getString(1));
            String v_name_data = cursor.getString(0);
            String p_name_data = cursor.getString(1);
            village_name_list.add(v_name_data);
            player_name_list.add(p_name_data);
            items.add(village_data);
            isEof = cursor.moveToNext();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.village_list_item,
                items
        );

        ListView myListView = (ListView) findViewById(R.id.village_list);
        myListView.setEmptyView(findViewById(R.id.empty_village_list));
        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                ListView listView = (ListView)parent;
                String item = (String)listView.getItemAtPosition(pos);

                AlertDialog.Builder alertDlg = new AlertDialog.Builder(FindVillage.this);
                alertDlg.setMessage(village_name_list.get(pos) + " に入りますか？");
                alertDlg.setNegativeButton("キャンセル", null);
                alertDlg.setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        StartActivity();
                    }
                });

                // 表示
                alertDlg.create().show();
            }
        });

        db.close();
    }

    private void StartActivity() {
        Intent intent = new Intent(this, GameWindow.class);
        startActivity(intent);
    }

    public class Village {
        private Bitmap icon;
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

        public Bitmap getIcon() {
            return icon;
        }

        public void setIcon(Bitmap icon) {
            this.icon = icon;
        }
    }
}
