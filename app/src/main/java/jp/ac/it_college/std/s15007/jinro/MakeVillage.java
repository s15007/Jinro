package jp.ac.it_college.std.s15007.jinro;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by samuel on 17/01/18.
 */

public class MakeVillage extends Activity {

    private JinroDBHelper myDb;
    private DataStr data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_village);

        myDb = new JinroDBHelper(this);
        data = new DataStr();

        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btn_make = (Button) findViewById(R.id.btn_make_village);
        btn_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = myDb.getWritableDatabase();

                EditText pname_id = (EditText) findViewById(R.id.player_name);
                EditText vname_id = (EditText) findViewById(R.id.village_name);
                data.playerName = pname_id.getText().toString();
                data.villageName = vname_id.getText().toString();

                if (data.playerName.equals("") && data.villageName.equals("")) {
                    Toast.makeText(MakeVillage.this, "名前と村名が未入力です", Toast.LENGTH_LONG).show();
                } else if (data.playerName.equals("")) {
                    Toast.makeText(MakeVillage.this, "名前が入力されていません", Toast.LENGTH_LONG).show();
                } else if (data.villageName.equals("")) {
                    Toast.makeText(MakeVillage.this, "村名が入力されていません", Toast.LENGTH_LONG).show();
                } else {
                    insertData(data);
                }
            }
        });
    }

    private class DataStr {
        String villageName;
        String playerName;
    }

    private void insertData (DataStr villageData){
        SQLiteDatabase db = myDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(JinroDBHelper.ColumnsVillage.VILLAGE_NAME, villageData.villageName);
        values.put(JinroDBHelper.ColumnsVillage.PLAYER_NAME, villageData.playerName);

        long id = db.insert(myDb.TABLE_NAME_VILLAGE, null, values);

        if (id < 0) {
            Toast.makeText(MakeVillage.this, "登録に失敗しました", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MakeVillage.this, "登録しました", Toast.LENGTH_LONG).show();
            StartActivity();
        }
        db.close();
    }

    private void StartActivity() {
        Intent intent = new Intent(this, FindVillage.class);
        startActivity(intent);
    }
}
