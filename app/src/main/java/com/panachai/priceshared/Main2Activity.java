package com.panachai.priceshared;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MyPagerAdapter adapter;
    ViewPager pager;
    private final String MY_PREFS = "CustomerLogin";

    private NavigationView navigationView;
    private SharedPreferences shared;

    // Save SharedPreferences
    //private SharedPreferences.Editor editor = shared.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        //Log.d("eeeeeeeeeeeeee", getResources().getString(R.string.urllink));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        shared = getApplicationContext().getSharedPreferences(MY_PREFS,
                Context.MODE_PRIVATE);
        //check login if ถ้าล็อคอินแล้วไปอีกทาง ++++++++++++++++++++++++++++++++++++++++

        Boolean statusLog = shared.getBoolean("statusLog", false);

        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        if (statusLog) {
            MenuItem nav_camara = menu.findItem(R.id.nav_login);
            // set new title to the MenuItem
            nav_camara.setTitle("Logout");
        }
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//adapter
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager);//ทำ view pager
        pager.setAdapter(adapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
//check login if ถ้าล็อคอินแล้วไปอีกทาง ++++++++++++++++++++++++++++++++++++++++

            Boolean statusLog = shared.getBoolean("statusLog", false);

            // get menu from navigationView
            Menu menu = navigationView.getMenu();

            if (statusLog) {
                createAndShowAlertDialog();

            } else {

                Intent intent = new Intent(this, Login_Activity.class);
                startActivity(intent);
            }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


        } /*else if (id == R.id.nav_register) {
            Intent intent = new Intent(this, Register_Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //AlertDialog yes no to logout
    private void createAndShowAlertDialog() {
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);

        adb.setTitle("Confirm?");
        adb.setMessage("Plese Confirm");
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {

            public void onClick(DialogInterface dialog, int arg1) {
                logout();
                Toast.makeText(Main2Activity.this, "Log out complete",
                        Toast.LENGTH_LONG).show();
            }
        });
        adb.show();
    }

    private void logout() {
        //ให้เด้ง dialog จะล็อคเอาท์จริงไหม แล้ว ทำการ ลบค่าออก
        //Log.d("booleans",createAndShowAlertDialog()+"");
            SharedPreferences.Editor editor = shared.edit();
            // editor.putBoolean("statusLog", false);
            editor.clear();
            editor.apply();

            //refresh
            finish();
            startActivity(getIntent());
    }
}
