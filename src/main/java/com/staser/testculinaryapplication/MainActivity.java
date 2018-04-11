package com.staser.testculinaryapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.staser.testculinaryapplication.adapters.RecipeArrayAdapter;
import com.staser.testculinaryapplication.models.Recipe;
import com.staser.testculinaryapplication.parsing.ParserJsonToRecipesMap;
import com.staser.testculinaryapplication.parsing.ParserStringToJson;
import com.staser.testculinaryapplication.sqlite.DataBase;
import com.staser.testculinaryapplication.sqlite.DbHandler;
import com.staser.testculinaryapplication.tasks.TaskGetFromDb;
import com.staser.testculinaryapplication.tasks.TaskGetHttp;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.staser.testculinaryapplication.common.Common.LOG;
import static com.staser.testculinaryapplication.common.Common.SEARCH_URL;
import static com.staser.testculinaryapplication.common.Common.DEFAULT_URL;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnLongClickListener {

    private DataBase dataBase;

    private ListView lvRecipesList;
    private SearchView svSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initViewElements();
        this.initDb();
        this.initListeners();

        this.getRecipesFromHttp("");
        this.showListViewSearchedRecipes("");

        Log.d(LOG, getClass().getName() + " ... ... onCreate");
    }

    private void initListeners() {
        this.svSearch.setOnQueryTextListener(this);
        this.svSearch.setOnLongClickListener(this);

        Log.d(LOG, getClass().getName() + " ... ... initListeners");
    }

    private void initDb() {
        this.dataBase = new DataBase(this);

        Log.d(LOG, getClass().getName() + " ... ... initDb");
    }

    private void initViewElements() {
        this.lvRecipesList = (ListView) findViewById(R.id.lvRecipes);
        this.svSearch = (SearchView) findViewById(R.id.svSearch);

        Log.d(LOG, getClass().getName() + " ... ... initViewElements");
    }

    private void getRecipesFromHttp(String searchText) {

        // getting String response  from Http
        TaskGetHttp _taskGetHttp;
        if (searchText.equals("")) {
            _taskGetHttp = new TaskGetHttp(DEFAULT_URL);
        } else {
            _taskGetHttp = new TaskGetHttp(SEARCH_URL + searchText.trim());
        }
        _taskGetHttp.execute();

        String _responseStr = "";
        try {
            _responseStr = _taskGetHttp.get();
            Log.d(LOG, getClass().getName() + " ... onCreate ... _responseStr: " + _responseStr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        this.setRecipesToDb(_responseStr);
    }

    private void setRecipesToDb(String responseStr) {

        //creating JsonObject from String
        JSONObject _jsonResponceObject = null;
        if (responseStr != null && !responseStr.equals("")) {
            _jsonResponceObject = ParserStringToJson.prepareJsonObject(responseStr);
            Log.d(LOG, getClass().getName() + " ... onCreate ... _jsonObject: " + _jsonResponceObject.toString());
        } else {
            Toast.makeText(this, R.string.toast_msg_none_http_response, Toast.LENGTH_LONG).show();
        }

        //creating Map of recipes and insert recipes from Map to DataBase
        if (_jsonResponceObject != null) {
            Map<String, Recipe> _recipesMap = new HashMap<>();
            _recipesMap = ParserJsonToRecipesMap.prepareRecipesMap(_jsonResponceObject);
            for (Recipe _recipe : _recipesMap.values()) {
                DbHandler.dbInsertRecipe(this.dataBase, _recipe);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.dataBase.close();

        Log.d(LOG, getClass().getName() + " ... ... onDestroy");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        this.getRecipesFromHttp(query);
        this.showListViewSearchedRecipes(query);

        this.svSearch.clearFocus();

        Log.d(LOG, getClass().getName() + " ... onQueryTextSubmit ... R.id.svSearch " + query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText.equals("")) {
            this.showListViewSearchedRecipes("");
        }

        Log.d(LOG, getClass().getName() + " ... ... onQueryTextChange");
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
//        this.dataBase.getWritableDatabase().delete(T_RECIPES, null, null);
        return false;
    }


    private void showListViewSearchedRecipes(String searchInTitle) {

        TaskGetFromDb _taskGetFromDb = new TaskGetFromDb(searchInTitle, this.dataBase);
        _taskGetFromDb.execute();
        List<Recipe> _list = null;
        try {
            _list = _taskGetFromDb.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (_list != null && _list.size() > 0) {
            RecipeArrayAdapter _recipeArrayAdapter = new RecipeArrayAdapter(this, R.layout.recipe_item_layout, _list);
            this.lvRecipesList.setAdapter(_recipeArrayAdapter);
        } else {
            Toast.makeText(this, R.string.no_matches_was_found, Toast.LENGTH_SHORT).show();
        }
    }
}
