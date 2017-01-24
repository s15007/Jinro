package jp.ac.it_college.std.s15007.jinro;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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

        while (isEof) {
            String village_data = String.format("%s       %s", cursor.getString(0), cursor.getString(1));
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

        db.close();
    }
}
