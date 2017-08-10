package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import entity.Student;

/**
 * Created by MaZhihua on 2017/8/10.
 */

public class StudentService {

    Context context;

    public StudentService(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    /**
     * 保存
     * @param student
     */
    public void saveObject(Student student) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(student);
            objectOutputStream.flush();
            byte data[] = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();
            MyDbHelper dbhelper = MyDbHelper.getInstens(context);
            SQLiteDatabase database = dbhelper.getWritableDatabase();
            database.execSQL("insert into classtable (classtabledata) values(?)", new Object[] { data });
            database.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Student> getObject() {
        MyDbHelper dbhelper = MyDbHelper.getInstens(context);
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from classtable", null);
        ArrayList<Student> list = new ArrayList<>(10000);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                byte data[] = cursor.getBlob(cursor.getColumnIndex("classtabledata"));
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
                    Student student = (Student) inputStream.readObject();
                    list.add(student);
                    inputStream.close();
                    arrayInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return list;

    }
}
