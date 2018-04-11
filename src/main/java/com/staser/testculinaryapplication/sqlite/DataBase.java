package com.staser.testculinaryapplication.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.staser.testculinaryapplication.common.Common.J_HREF;
import static com.staser.testculinaryapplication.common.Common.J_INGREDIENTS;
import static com.staser.testculinaryapplication.common.Common.J_THUMBNAIL;
import static com.staser.testculinaryapplication.common.Common.J_TITLE;
import static com.staser.testculinaryapplication.common.Common.LOG;
import static com.staser.testculinaryapplication.common.Common.T_RECIPES;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {
        super(context, "RecipesDB", null, 1);

        Log.d(LOG, getClass().getName() + " ... ... DataBase class object was created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + T_RECIPES + " (_id integer primary key autoincrement, "
                + J_TITLE + "  text, "
                + J_HREF + " text, "
                + J_INGREDIENTS + " text, "
                + J_THUMBNAIL + " text);");

        Log.d(LOG, getClass().getName() + " ... onCreate ... database \"RecipesDB\"  was created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(LOG, getClass().getName() + " ... onUpgrade ... database \"RecipesDB\"  was upgraded");
    }
}
