package jp.ac.it_college.std.s15007.jinro;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by samuel on 17/01/18.
 */

public class FindVillage extends Activity {

    private String v_name_data;
    private String v_author_data;
    private int village_id;

    private JinroDBHelper myDb;
    MediaPlayer mp = null;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_village);

        show_village();

        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp = MediaPlayer.create(FindVillage.this, R.raw.b_069);
                mp.setVolume(0.8f, 0.8f);
                mp.start();
                finish();
            }
        });

        Button btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp = MediaPlayer.create(FindVillage.this, R.raw.b_069);
                mp.setVolume(0.8f, 0.8f);
                mp.start();
                show_village();
            }
        });
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

    private void show_village() {
        JinroDBHelper myDb = new JinroDBHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();

        Cursor cursor = db.query(JinroDBHelper.TABLE_NAME_VILLAGE, new String[] {
                JinroDBHelper.ColumnsVillage._ID,
                JinroDBHelper.ColumnsVillage.VILLAGE_NAME,
                JinroDBHelper.ColumnsVillage.PLAYER_NAME },
                null, null, null, null, null);

        boolean isEof = cursor.moveToFirst();

        final ArrayList<String> village_name_list = new ArrayList<>();
        final ArrayList<String> player_name_list = new ArrayList<>();
        final ArrayList<Integer> village_id_list = new ArrayList<>();
        ArrayList<Village> villages = new ArrayList<>();

        ListView myListView = (ListView) findViewById(R.id.village_list);

        int icon = R.drawable.english_animal_d_21_03;

        while (isEof) {
            village_id = cursor.getInt(0);
            v_name_data = cursor.getString(1);
            v_author_data = cursor.getString(2);

            village_name_list.add(v_name_data);
            player_name_list.add(v_author_data);
            village_id_list.add(village_id);

            Village village = new Village();

            village.setIcon(BitmapFactory.decodeResource(
                    getResources(),
                    icon
            ));
            village.setVillage(v_name_data);
            village.setPlayer(v_author_data);
            villages.add(village);
            isEof = cursor.moveToNext();
        }

        VillageAdapter adapter = new VillageAdapter(this, 0, villages);

        myListView.setEmptyView(findViewById(R.id.empty_village_list));
        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                mp = MediaPlayer.create(FindVillage.this, R.raw.b_069);
                mp.start();

                v_name_data = village_name_list.get(pos);
                v_author_data = player_name_list.get(pos);
                village_id = village_id_list.get(pos);

                AlertDialog.Builder alertDlg = new AlertDialog.Builder(FindVillage.this);
                alertDlg.setMessage(village_name_list.get(pos) + " に入りますか？");
                alertDlg.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mp = MediaPlayer.create(FindVillage.this, R.raw.b_069);
                        mp.setVolume(0.8f, 0.8f);
                        mp.start();
                    }
                });
                alertDlg.setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mp = MediaPlayer.create(FindVillage.this, R.raw.b_069);
                        mp.setVolume(0.8f, 0.8f);
                        mp.start();
                        StartActivity();
                    }
                });

                alertDlg.create().show();
            }
        });
        db.close();
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
                        R.layout.village_list_item,
                        parent,
                        false
                );
            }

            Village village = (Village) getItem(position);

            ((ImageView) convertView.findViewById(R.id.icon))
                    .setImageBitmap(village.getIcon());
            ((TextView) convertView.findViewById(R.id.vNameColumn))
                    .setText(village.getVillage());
            ((TextView) convertView.findViewById(R.id.pNameColumn))
                    .setText(village.getPlayer());

            return convertView;
        }
    }

    private void StartActivity() {
        JinroDBHelper myDb = new JinroDBHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();

        User user = new User();

        user.village_id = village_id;
        user.name = setName();

        if (user.name == null) {
            Toast.makeText(FindVillage.this, "メンバーがいっぱいです", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues values = new ContentValues();

            values.put(JinroDBHelper.ColumnsUsers.VILLAGE_ID, user.village_id);
            values.put(JinroDBHelper.ColumnsUsers.NAME, user.name);

            long id = db.insert(JinroDBHelper.TABLE_NAME_USERS, null, values);

            Intent intent = new Intent(this, InVillage.class);
            intent.putExtra("village_id", village_id);
            intent.putExtra("village_name", v_name_data);
            intent.putExtra("author_name", v_author_data);
            intent.putExtra("player_name", user.name);

            if (id < 0) {
                Toast.makeText(FindVillage.this, "登録に失敗しました", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(FindVillage.this, "登録しました", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
            db.close();
        }

    }

    public class User {
        private String name;
        private String job;
        private int village_id;
    }

    private String setName() {

        int i = 0;

        ArrayList<String> RandomName = new ArrayList<>();
        RandomName.add("太郎");RandomName.add("ヤマハ");RandomName.add("バカ");
        RandomName.add("山田");RandomName.add("川崎");RandomName.add("あさはら");
        RandomName.add("ヤス");RandomName.add("れんほう");RandomName.add("ダ-ス");
        RandomName.add("ホンダ");RandomName.add("のぶ");RandomName.add("たくぞう");
        Collections.shuffle(RandomName);
        while (!checkName(RandomName.get(i))) {
            if (i >= RandomName.size() - 1){
                return null;
            } else {
                i++;
            }
        }
        return RandomName.get(i);
    }

    private boolean checkName(String randName) {
        JinroDBHelper myDb = new JinroDBHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();

        Cursor cursor = db.query("users", new String[]{"name"},
                "village_id = ?", new String[]{"" + village_id},
                null, null, null);

        int Index = cursor.getColumnIndex("name");
        ArrayList<String> name_list = new ArrayList<>();

        while (cursor.moveToNext()) {
            name_list.add(cursor.getString(Index));
        }
        return name_list.indexOf(randName) == -1;
    }

}
