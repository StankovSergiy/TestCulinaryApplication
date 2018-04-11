package com.staser.testculinaryapplication.parsing;

import android.util.Log;

import com.staser.testculinaryapplication.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.staser.testculinaryapplication.common.Common.J_HREF;
import static com.staser.testculinaryapplication.common.Common.J_INGREDIENTS;
import static com.staser.testculinaryapplication.common.Common.J_RESULT;
import static com.staser.testculinaryapplication.common.Common.J_THUMBNAIL;
import static com.staser.testculinaryapplication.common.Common.J_TITLE;
import static com.staser.testculinaryapplication.common.Common.LOG;

public class ParserJsonToRecipesMap {


    public static Map<String, Recipe> prepareRecipesMap(JSONObject jsonRecipes) {
        Map<String, Recipe> _recipesMap = new HashMap<>();
        JSONArray _recipesJsonArray;

        String _rTitle = "";
        String _rHref = "";
        String _rIngredients = "";
        String _rThumbnail = "";

        try {
            _recipesJsonArray = jsonRecipes.getJSONArray(J_RESULT);

            Log.d(LOG, "... prepareRecipesMap ... array of Json is:" + _recipesJsonArray.toString());
            if (_recipesJsonArray.length() > 0) {

                for (int i = 0; i < _recipesJsonArray.length(); i++) {
                    JSONObject _tempJsonRecipe = _recipesJsonArray.getJSONObject(i);
                    _rTitle = _tempJsonRecipe.getString(J_TITLE).trim();
                    _rHref = _tempJsonRecipe.getString(J_HREF).trim();
                    _rIngredients = _tempJsonRecipe.getString(J_INGREDIENTS).trim();
                    _rThumbnail = _tempJsonRecipe.getString(J_THUMBNAIL).trim();
                    Recipe _recipe = new Recipe(_rTitle, _rHref, _rIngredients, _rThumbnail);
                    _recipesMap.put(_rTitle, _recipe);
                }

            } else {
                Log.d(LOG, " ... prepareRecipesMap ... _recipesMap cant be created. reason is empty http response");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(LOG, " ... prepareRecipesMap ... _recipesMap: " + _recipesMap.keySet().toArray().toString());

        return _recipesMap;
    }

}
