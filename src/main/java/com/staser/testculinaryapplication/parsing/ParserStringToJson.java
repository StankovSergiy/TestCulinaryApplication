package com.staser.testculinaryapplication.parsing;

import org.json.JSONException;
import org.json.JSONObject;


public class ParserStringToJson {


    public static JSONObject prepareJsonObject(String response) {
        JSONObject _jObject = null;
        try {
            _jObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return _jObject;
    }


}
