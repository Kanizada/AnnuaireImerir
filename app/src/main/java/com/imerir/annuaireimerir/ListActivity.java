package com.imerir.annuaireimerir;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fab;
    DisplayMode mode;


    enum DisplayMode {
        STUDENT,
        ENTERPRISE,
        LOCATION
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Liste des élèves");
        setMode(DisplayMode.STUDENT);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == fab){
            if (mode == DisplayMode.STUDENT){

            }else if (mode == DisplayMode.ENTERPRISE){

            }else if (mode == DisplayMode.LOCATION){

            }
        }
    }

    public void setMode(DisplayMode newMode){
        if(newMode==DisplayMode.STUDENT){

        }else if (newMode==DisplayMode.ENTERPRISE){

        }else if (newMode==DisplayMode.LOCATION){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
