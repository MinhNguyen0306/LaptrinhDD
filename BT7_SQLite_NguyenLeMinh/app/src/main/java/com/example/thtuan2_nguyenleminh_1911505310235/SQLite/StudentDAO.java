package com.example.thtuan2_nguyenleminh_1911505310235.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ipsec.ike.TunnelModeChildSessionParams;

import com.example.thtuan2_nguyenleminh_1911505310235.Model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private SQLiteDatabase db;

    public StudentDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<Student> get(String sql, String ...selectArgs) {
        List<Student> students = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectArgs);

        while(cursor.moveToNext()){
            Student student = new Student();
            student.setId(cursor.getString(cursor.getColumnIndex("id")));
            student.setName(cursor.getString(cursor.getColumnIndex("name")));
            student.setDiem(cursor.getInt(cursor.getColumnIndex("diem")));
            students.add(student);
        }

        return students;
    }

    public List<Student> getAllStudent(){
        String sql = "select * from sinhvien";
        return get(sql);
    }

    public Student getStudentById(String id){
        String sql = "select * from nhanvien where id = ?";
        List<Student> students = get(sql, id);
        return students.get(0);
    }

    public long insert(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", student.getId());
        contentValues.put("name", student.getName());
        contentValues.put("diem", student.getDiem());
        return db.insert("sinhvien", null, contentValues);
    }

    public long update(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", student.getName());
        contentValues.put("diem", student.getDiem());
        return db.update("sinhvien", contentValues, "id=?", new String[]{student.getId()});
    }

    public int delete(String id){
        return db.delete("sinhvien", "id=?", new String[]{id});
    }
}
