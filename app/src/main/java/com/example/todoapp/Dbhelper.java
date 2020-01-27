package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Dbhelper extends SQLiteOpenHelper {


    private static final String Db_NAME="TESTEer";
    private static final int DB_VER = 1;
    private static final String DB_TABLE = "TASK";
    private static final String DB_COLUMN = "TaskName";

    public Dbhelper(Context context){
        super(context,Db_NAME,null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL);",DB_TABLE,DB_COLUMN);
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query  = String.format("DELETE TABLE IF EXIST %s",DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNewTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN, task);
        db.insertWithOnConflict(DB_TABLE,null,values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }
    public void deletetask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN+" = ?",new String[]{task});
        db.close();
    }

    public ArrayList<String> getTaskList(){
        ArrayList<String > tasklist = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{DB_COLUMN},null,null,null,null,null);
        while (cursor.moveToNext()){
           int index = cursor.getColumnIndex(DB_COLUMN);
           tasklist.add(cursor.getString(index));
        }
        cursor.close();
        db.close();

        return tasklist;
    }

}
