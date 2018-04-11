package com.staser.testculinaryapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.staser.testculinaryapplication.R;
import com.staser.testculinaryapplication.models.Recipe;


import java.util.List;

import static com.staser.testculinaryapplication.common.Common.LOG;

public class RecipeArrayAdapter extends ArrayAdapter {


    private List<Recipe> searchedRecipesList;
    private Context context;


    public RecipeArrayAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.searchedRecipesList = objects;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater _layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View _view = _layoutInflater.inflate(R.layout.recipe_item_layout, parent, false);

        final Recipe _recipe = this.searchedRecipesList.get(position);

        ImageView _ivThumbnail = (ImageView) _view.findViewById(R.id.ivThumbnail);
        String _url = _recipe.getrThrumbnail().trim();
        if (!_url.equals(""))
            Picasso
                    .get()
                    .load(_url)
                    .into(_ivThumbnail);

        TextView _tvTitle = (TextView) _view.findViewById(R.id.tvTitle);
        _tvTitle.setText(_recipe.getrTitle());
        TextView _tvIngredients = (TextView) _view.findViewById(R.id.tvIngredients);
        _tvIngredients.setText(_recipe.getrIngridients());

        _view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(_recipe.getrHref().trim()));
                        context.startActivity(intent);
                    }
                }
        );

        Log.d(LOG, getClass().getName() + " ... ... getView");
        return _view;
    }


}
