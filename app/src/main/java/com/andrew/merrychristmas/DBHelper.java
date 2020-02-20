package com.andrew.merrychristmas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TadieDiary.db";

    //public static final String INBOX_TABLE_NAME = "inboxdata";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 11);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table entries " +
                        "(id integer primary key, entry_time integer, entry_title text,the_entry text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS entries");
        onCreate(db);
    }
}
