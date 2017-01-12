package com.imerir.annuaireimerir;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fab;
    DisplayMode mode;


    enum DisplayMode {
        STUDENT,
        ENTERPRISE
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
        //fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == fab){
            if (mode == DisplayMode.STUDENT){

            }else if (mode == DisplayMode.ENTERPRISE){

            }
        }
    }

    public void setMode(DisplayMode newMode){
        if(newMode==DisplayMode.STUDENT){
            /*fab.setImageDrawable(new IconicsDrawable(this)
                    .icon(GoogleMaterial.Icon.//àdefinir)
                    .color(Color.WHITE).sizeDp(24));*/
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, StudentFragment.newInstance(), "student").commit();
        }else if (newMode==DisplayMode.ENTERPRISE){
            /*fab.setImageDrawable(new IconicsDrawable(this)
                    .icon(GoogleMaterial.Icon.//àdefinir)
                    .color(Color.WHITE).sizeDp(24));*/
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, EnterpriseFragment.newInstance(), "enterprise").commit();
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
        switch (id){
            case R.id.action_sortby:
                //pop up du dialogue pour changer le mode de tri
                return true;
            case R.id.action_account:
                // go account page
                return true;
            case R.id.action_enterprise:
                setMode(DisplayMode.ENTERPRISE);
                return true;
            case R.id.action_student:
                setMode(DisplayMode.STUDENT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
