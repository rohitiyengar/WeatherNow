package com.project.mobilecomputing.weathernow;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.project.mobilecomputing.weathernow.helpers.WeatherDBHelper;
import com.project.mobilecomputing.weathernow.models.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends Activity {

    List<History> history;
    ListView historyListView;
    ArrayAdapter arrayAdapter;
    List<String> historyCityList;
    WeatherDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#06272E")));

        db = new WeatherDBHelper(this);
        historyCityList = new ArrayList<String>();
        history = db.getAllHistory();
        for (History h : history) {
            historyCityList.add(h.getLocation());
        }

        historyListView = (ListView) findViewById(R.id.historyListView);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, historyCityList);
        historyListView.setAdapter(arrayAdapter);

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent weatherIntent = new Intent(HistoryActivity.this, WeatherDetails.class);
                weatherIntent.putExtra("mode", 0);//City Mode.
                weatherIntent.putExtra("city", historyCityList.get(position).replaceAll("\\s", "%20"));
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
            case R.id.action_del: //Clear History by showing Alert Dialog
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure you want to clear history?");

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        db.deleteAllHistory();
                        historyCityList.clear();
                        history.clear();
                        history = db.getAllHistory();
                        for (History h : history) {
                            historyCityList.add(h.getLocation());
                        }
                        arrayAdapter.addAll(historyCityList);
                        arrayAdapter.notifyDataSetChanged();
                        historyListView.invalidateViews();
                        historyListView.refreshDrawableState();
                        Toast.makeText(HistoryActivity.this, "Cleared History", Toast.LENGTH_LONG).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
