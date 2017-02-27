package com.tobincorporated.mynewdrawingapp;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.design.widget.NavigationView;
import static android.R.attr.id;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    private CanvasView customCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);// in app_bar_main
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();// this creates the hamburger menu


        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        int i;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.redBox) {

            Context context = getApplicationContext();
            CharSequence text = "Color: Red";
            int duration = Toast.LENGTH_SHORT;
            Toast myMessage= Toast.makeText(context, text, duration);
            customCanvas.setDrawColor(new int[]{255,0,0} );
            myMessage.show();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_clear) {
            customCanvas.clearCanvas();

        } else if (id == R.id.blackBox) {
            Context context = getApplicationContext();
            CharSequence text = "Color: Black";
            int duration = Toast.LENGTH_SHORT;
            Toast myMessage= Toast.makeText(context, text, duration);
            customCanvas.setDrawColor(new int[]{0,0,0} );
            myMessage.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
