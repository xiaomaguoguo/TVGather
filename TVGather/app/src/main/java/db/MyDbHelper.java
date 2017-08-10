package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MaZhihua on 2017/8/10.
 */

public class MyDbHelper extends SQLiteOpenHelper {

    private static MyDbHelper dbhelper = null;
    public static MyDbHelper getInstens(Context context) {
        if (dbhelper == null) {
            dbhelper = new MyDbHelper(context);
        }
        return dbhelper;
    }

    private MyDbHelper(Context context) {
        super(context, "datebase.db", null, 1);
        // TODO Auto-generated constructor stub
    }

    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql_class_table="create table if not exists classtable(_id integer primary key autoincrement,classtabledata text)";
        sqLiteDatabase.execSQL(sql_class_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
