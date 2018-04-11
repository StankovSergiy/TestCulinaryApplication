package com.staser.testculinaryapplication.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.staser.testculinaryapplication.models.Recipe;
import com.staser.testculinaryapplication.sqlite.DataBase;
import com.staser.testculinaryapplication.sqlite.DbHandler;

import java.util.ArrayList;
import java.util.List;

import static com.staser.testculinaryapplication.common.Common.LOG;

public class TaskGetFromDb extends AsyncTask<String, Integer, List<Recipe>> {

    private String search;
    private List<Recipe> recipesList;
    private DataBase dataBase;

    public TaskGetFromDb(String search, DataBase dataBase) {
        super();
        this.search = search;
        this.dataBase = dataBase;

        Log.d(LOG, "... ... new TaskGetFromDb was created");
        Log.i(LOG, "... search ... is: " + search);
    }


    @Override
    protected List<Recipe> doInBackground(String... objects) {
        this.recipesList = DbHandler.dbGetSelectedRecipes(this.dataBase, this.search);

        Log.d(LOG, getClass().getName() + " ... doInBackground ...search list is: " + this.recipesList.toString() );
        return recipesList;
    }
}
