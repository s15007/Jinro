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
    public static final String TABLE_NAME_USERS = "users";
    public static final String TABLE_NAME_POST = "post";

    public interface ColumnsVillage extends BaseColumns {
        public static final String _ID = "id";
        public static final String VILLAGE_NAME = "village_name";
        public static final String PLAYER_NAME = "player_name";
        public static final String CHAT = "chat";
        public static final String MEMBER_ID = "member_id";
    }

    public interface ColumnsUsers extends BaseColumns {
        public static final String _ID = "id";
        public static final String NAME = "name";
        public static final String VILLAGE_ID = "village_id";
        public static final String JOB = "job";
        public static final String DATA_MODIFIED = "date_modified";
    }

    public interface ColumnsPost extends BaseColumns {
        public static final String _ID = "id";
        public static final String USER_ID = "user_id";
        public static final String BODY = "body";
        public static final String VILLAGE_ID = "village_id";
        public static final String DATA_MODIFIED = "data_modified";
    }

    private static final String CREATE_TABLE_VILLAGE = "CREATE TABLE " +
            TABLE_NAME_VILLAGE + " ( " +
            ColumnsVillage._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ColumnsVillage.VILLAGE_NAME + " TEXT, " +
            ColumnsVillage.CHAT + " TEXT, " +
//            ColumnsVillage.MEMBER_ID + " INTEGER, " +
            ColumnsVillage.PLAYER_NAME + " TEXT);";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " +
            TABLE_NAME_USERS + " ( " +
            ColumnsUsers._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ColumnsUsers.VILLAGE_ID + " INTEGER, " +
            ColumnsUsers.NAME + " TEXT, " +
            ColumnsUsers.JOB + " TEXT, " +
            ColumnsUsers.DATA_MODIFIED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL);";

    private static final String CREATE_TABLE_POST = "CREATE TABLE " +
            TABLE_NAME_POST + " ( " +
            ColumnsPost._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ColumnsPost.USER_ID + " INTEGER, " +
            ColumnsPost.VILLAGE_ID + " INTEGER, " +
            ColumnsPost.BODY + " TEXT, " +
            ColumnsPost.DATA_MODIFIED + " INTEGER NOT NULL);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_VILLAGE);
        db.execSQL(CREATE_TABLE_USERS);
//        db.execSQL(CREATE_TABLE_POST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
