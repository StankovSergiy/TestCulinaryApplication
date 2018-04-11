package com.staser.testculinaryapplication.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.staser.testculinaryapplication.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import static com.staser.testculinaryapplication.common.Common.J_HREF;
import static com.staser.testculinaryapplication.common.Common.J_INGREDIENTS;
import static com.staser.testculinaryapplication.common.Common.J_THUMBNAIL;
import static com.staser.testculinaryapplication.common.Common.J_TITLE;
import static com.staser.testculinaryapplication.common.Common.LOG;
import static com.staser.testculinaryapplication.common.Common.T_RECIPES;


public class DbHandler {

    public static boolean dbInsertRecipe(DataBase db, Recipe recipe) {
        // checking if recipe is present in DataBase
        boolean _flag = false;
        if (isRowsAlreadyPresent(db, recipe.getrHref())) {
            _flag = false;
        } else {
            // creating row
            ContentValues _cv = new ContentValues();
            _cv.put(J_TITLE, recipe.getrTitle());
            _cv.put(J_HREF, recipe.getrHref());
            _cv.put(J_INGREDIENTS, recipe.getrIngridients());
            _cv.put(J_THUMBNAIL, recipe.getrThrumbnail());

            // getting DataBase and insert the row
            SQLiteDatabase _sqLite = db.getWritableDatabase();

            long _rowId = _sqLite.insert(T_RECIPES, null, _cv);
            Log.d(LOG, "... dbInsertRecipe ... row inserted, ID = " + _rowId);
            if (_rowId != -1) {
                _flag = true;
            }
            Log.d(LOG, "... dbInsertRecipe ..." + _flag);
        }
        return _flag;
    }

    private static boolean isRowsAlreadyPresent(DataBase db, String href) {
        boolean _isPresent = false;
        SQLiteDatabase _sqLite = db.getWritableDatabase();
        Cursor _cursor;
        String[] _columns = new String[]{J_HREF};
        _cursor = _sqLite.query(T_RECIPES,
                _columns,
                null,
                null,
                null,
                null,
                null);
        if (_cursor.moveToFirst()) {
            int _hrefColIndex = _cursor.getColumnIndex(J_HREF);
            do {
                if (_cursor.getString(_hrefColIndex).trim().equals(href)) {
                    _isPresent = true;
                    Log.d(LOG, "... isRowsAlreadyPresent ... is: \n" + _cursor.getString(_hrefColIndex));
                }
            } while (_cursor.moveToNext());
        } else {
            Log.d(LOG, "... isRowsAlreadyPresent ... 0 rows");
        }
        _cursor.close();

        return _isPresent;
    }


    public static List<Recipe> dbGetSelectedRecipes(DataBase db, String forSearch) {
        Recipe _recipe;
        List<Recipe> _recipesArray = new ArrayList<>();
        SQLiteDatabase _sqLite = db.getWritableDatabase();
        Cursor _cursor;
        String sql = "SELECT " +
                J_TITLE + ", " +
                J_HREF + ", " +
                J_INGREDIENTS + ", " +
                J_THUMBNAIL + " " +
                "FROM recipes " +
                "WHERE title LIKE \"%" +
                forSearch.trim() +"%\"";

        if (forSearch.equals("")) {
            _cursor = _sqLite.query(T_RECIPES,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
        } else {
            _cursor = _sqLite.rawQuery(sql, null);
        }

        if (_cursor.moveToFirst()) {

            int _titleColIndex = _cursor.getColumnIndex(J_TITLE);
            int _hrefColIndex = _cursor.getColumnIndex(J_HREF);
            int _ingredientsColIndex = _cursor.getColumnIndex(J_INGREDIENTS);
            int _thumbnailColIndex = _cursor.getColumnIndex(J_THUMBNAIL);

            do {
                _recipe = new Recipe(
                        _cursor.getString(_titleColIndex).trim(),
                        _cursor.getString(_hrefColIndex).trim(),
                        _cursor.getString(_ingredientsColIndex).trim(),
                        _cursor.getString(_thumbnailColIndex).trim());
                Log.d(LOG, "... dbGetSelectedRecipes ... new one number is: \n" + _recipe.toString());

                if (_recipe.getrTitle() != null
                        && !_recipe.getrTitle().equals("")
                        && _recipe.getrIngridients() != null
                        && !_recipe.getrIngridients().equals("")) {
                    _recipesArray.add(_recipe);
                }
            } while (_cursor.moveToNext());
        } else {
            Log.d(LOG, "... dbGetSelectedRecipes ... _cursor.getCount() == 0 in Database for this search: " + forSearch);
        }
        _cursor.close();

        Log.d(LOG, "... dbGetSelectedRecipes ... \n" + _recipesArray.toString() + "\n");
        return _recipesArray;
    }

}
