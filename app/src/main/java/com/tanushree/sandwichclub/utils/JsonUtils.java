package com.tanushree.sandwichclub.utils;

import com.tanushree.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException
    {
        JSONObject sandwichData = new JSONObject(json);

        JSONObject name = sandwichData.getJSONObject("name");

        String mainName = name.getString("mainName");

        //Log.i("Main_Name", mainName);

        JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");

        List<String> alsoKnownAs_list = new ArrayList<String>();

        for(int i = 0; i < alsoKnownAs.length(); i++)
        {
            //Log.i("alsoKnownAs JSONArray", alsoKnownAs.getString(i));
            alsoKnownAs_list.add(alsoKnownAs.getString(i));
        }

        /*if(!alsoKnownAs_list.isEmpty()) {
            for (String y : alsoKnownAs_list)
                Log.i("alsoKnownAs_list", y);
        }*/


        String placeOfOrigin = sandwichData.getString("placeOfOrigin");
        //Log.i("placeOfOrigin", placeOfOrigin);

        String description = sandwichData.getString("description");
        //Log.i("description", description);

        String image = sandwichData.getString("image");
        //Log.i("image", image);

        String ingredients = sandwichData.getString("ingredients");
        String ingredients1 = ingredients.replace("[", "");
        String ingredients2 = ingredients1.replace("]", "");
        String ingredients3 = ingredients2.replace("\"", "");
        String[] ingredients_arr = ingredients3.split(",");
        //for(String x : ingredients_arr)
        //Log.i("ingredients_arr", x);
        //Log.i("ingredients", ingredients);
        //Log.i("ingredients3", ingredients3);

        List<String> ingredients_list = Arrays.asList(ingredients_arr);

        //for(String y : ingredients_list)
            //Log.i("ingredients_list", y);

        Sandwich sandwich = new Sandwich();
        sandwich.setMainName(mainName);
        sandwich.setAlsoKnownAs(alsoKnownAs_list);
        sandwich.setPlaceOfOrigin(placeOfOrigin);
        sandwich.setDescription(description);
        sandwich.setImage(image);
        sandwich.setIngredients(ingredients_list);

        return sandwich;
    }
}