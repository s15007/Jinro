package jp.ac.it_college.std.s15007.jinro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by samuel on 17/01/23.
 */

public class JinroDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "jinro.db";
    private static final int DB_VERSION = 1;

    public JinroDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static final String TABLE_NAME_VILLAGE = "village";

    public interface ColumnsVillage extends BaseColumns {
        public static final String _ID = "id";
        public static final String VILLAGE_NAME = "village_name";
        public static final String PLAYER_NAME = "player_name";
        public static final String CHAT = "chat";
        public static final String MEMBER_ID = "member_id";
    }

    private static final String CREATE_TABLE_VILLAGE = "CREATE TABLE " +
            TABLE_NAME_VILLAGE + " ( " +
            ColumnsVillage._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ColumnsVillage.VILLAGE_NAME + " TEXT, " +
            ColumnsVillage.CHAT + " TEXT, " +
            ColumnsVillage.MEMBER_ID + " INTEGER, " +
            ColumnsVillage.PLAYER_NAME + " TEXT);";



    public static final String TABLE_NAME_USERS = "users";

    public interface ColumnsUsers extends BaseColumns {
        public static final String _ID = "id";
        public static final String NAME = "name";
        public static final String JOB = "job";
        public static final String CHAT = "chat";
        public static final String DATA_MODIFIED = "date_modified";
    }

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " +
            TABLE_NAME_USERS + " ( " +
            ColumnsUsers._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ColumnsUsers.NAME + " TEXT, " +
            ColumnsUsers.DATA_MODIFIED + " INTEGER NOT NULL);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_VILLAGE);
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
