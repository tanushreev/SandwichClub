package com.tanushree.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tanushree.sandwichclub.model.Sandwich;
import com.tanushree.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ImageView mIngredientsIv;
    TextView mAlsoKnownAsTv;
    TextView mPlaceOfOriginTv;
    TextView mDescriptionTv;
    TextView mIngredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mIngredientsIv = findViewById(R.id.iv_image);
        mAlsoKnownAsTv = findViewById(R.id.tv_also_known);
        mPlaceOfOriginTv = findViewById(R.id.tv_origin);
        mDescriptionTv = findViewById(R.id.tv_description);
        mIngredientsTv = findViewById(R.id.tv_ingredients);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        //Log.v("Sandwich_details", sandwiches[position]);
        Sandwich sandwich;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);

            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .error(R.drawable.no_image)
                    .into(mIngredientsIv);

            setTitle(sandwich.getMainName());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich)
    {
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if(alsoKnownAsList.isEmpty()) {
            mAlsoKnownAsTv.setText("Unknown");
        }
        else
        {
            int i;
            for(i = 0; i < (alsoKnownAsList.size() - 1); i++)
            {
                mAlsoKnownAsTv.append(alsoKnownAsList.get(i) + "\n");
            }
            mAlsoKnownAsTv.append(alsoKnownAsList.get(i));
        }

        if(sandwich.getPlaceOfOrigin().equals("")) {
            mPlaceOfOriginTv.setText("Unknown");
        }
        else {
            mPlaceOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        }

        mDescriptionTv.setText(sandwich.getDescription());

        List<String> ingredientsList = sandwich.getIngredients();

        int i;
        for(i = 0; i < (ingredientsList.size() - 1); i++)
        {
            mIngredientsTv.append(ingredientsList.get(i) + "\n");
        }
        mIngredientsTv.append(ingredientsList.get(i));
    }
}