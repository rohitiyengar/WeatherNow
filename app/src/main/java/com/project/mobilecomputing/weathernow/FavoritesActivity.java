package com.project.mobilecomputing.weathernow;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.mobilecomputing.weathernow.helpers.WeatherDBHelper;
import com.project.mobilecomputing.weathernow.models.Favorites;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends Activity {
    List<Favorites> favorites;
    List<String> favoritesCityList;
    ListView favListView;
    ArrayAdapter arrayAdapter;
    WeatherDBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#06272E")));
        db = new WeatherDBHelper(this);
        favorites=db.getAllFavorites();
        favoritesCityList=new ArrayList<String>();
        for (Favorites f:favorites)
        {
            favoritesCityList.add(f.getLocation());
        }
        favListView = (ListView)findViewById(R.id.favoritesListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, favoritesCityList);
        favListView.setAdapter(arrayAdapter);

        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent weatherIntent = new Intent(FavoritesActivity.this, WeatherDetails.class);
                weatherIntent.putExtra("mode",0);//City Mode.
                weatherIntent.putExtra("city", favoritesCityList.get(position).replaceAll("\\s", "%20"));
                startActivity(weatherIntent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fav_his, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            case R.id.action_del:
                db.deleteAllFavorites();
                favoritesCityList.clear();
                favorites.clear();
                favorites = db.getAllFavorites();
                for(Favorites h:favorites)
                {
                    favoritesCityList.add(h.getLocation());
                }
                arrayAdapter.addAll(favoritesCityList);
                arrayAdapter.notifyDataSetChanged();
                favListView.invalidateViews();
                favListView.refreshDrawableState();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
