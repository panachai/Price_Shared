package com.panachai.priceshared;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.otto.Subscribe;

public class ReviewItemActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String url = "10.0.2.2/Webservice"; //"10.0.2.2/Webservice" //consolesaleth.esy.es

    private int proID;
    private String proName;
    private String proDes;
    private String image;

    private ImageView imageView;
    private TextView tvTitle;
    private TextView tvDes;


    private final String MY_PREFS = "CustomerLogin";

    private NavigationView navigationView;
    private SharedPreferences shared;


    private DB_ProductdetailResponse[] productdetailReview;

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_item);

//---------------------------------------------------
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

//---------------------------------------------------
        //review part
        proID = getIntent().getIntExtra("proID", 0);
        proName = getIntent().getStringExtra("proName");
        proDes = getIntent().getStringExtra("proDes");
        image = getIntent().getStringExtra("proImage");

        String type = "review";
        //เอา proID ส่งไป where ต่อใน db
        DBHelper dbhelper = new DBHelper(this.getApplicationContext());
        dbhelper.execute(type,String.valueOf(proID));

        Log.d("ReviewItemActivity","DBHelper pass");
/*
        Log.d("proID",""+proID);
        Log.d("proName",proName);
        Log.d("proDes",proDes);
        Log.d("image",image);
*/
        imageView = (ImageView) findViewById(R.id.imageView1);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDes = (TextView) findViewById(R.id.tvDes);

        tvTitle.setText(proName);
        tvDes.setText(proDes);

        Glide.with(this.getApplicationContext())
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)   //บังคับให้ Glide เซฟรูปทุกขนาด ลง Cache
                .into(imageView);

        //review button
        Button button = (Button) findViewById(R.id.btreview);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddReviewActivity.class);

                intent.putExtra("proID", proID);
                intent.putExtra("proName", proName);
                intent.putExtra("proDes", proDes);
                intent.putExtra("image", image);
                startActivity(intent);
            }
        });

    }

    @Subscribe
    public void onProductResponse(DB_ProductdetailResponse[] dataDetail) {
        productdetailReview = dataDetail;
        Log.d("ReviewItemActivity","reciver from dbhelper pass");

//productReview

        double[] proDePrice = new double[productdetailReview.length];
        String[] proDeDes = new String[productdetailReview.length];

        String[] supDeDes = new String[productdetailReview.length];
        String[] cusName = new String[productdetailReview.length];

        String[] proDeDate = new String[productdetailReview.length];
        Log.d("ReviewItemActivity","set variable pass");

        for (int i = 0; i < productdetailReview.length; i++) {
            Log.d("ReviewItemActivity","in for");
            proDePrice[i] = productdetailReview[i].getProDePrice();
            proDeDes[i] = productdetailReview[i].getProDeDes();
            supDeDes[i] = productdetailReview[i].getSupDeDes();
            cusName[i] = productdetailReview[i].getCusName();
            proDeDate[i] = productdetailReview[i].getProDeDate();

        }
        Log.d("ReviewItemActivity","before listview");
        ListView listView = (ListView)findViewById(R.id.lvReview);
        Log.d("ReviewItemActivity","after listview");
//--------
        CustomAdapterListviewReview adapter = new CustomAdapterListviewReview
                (this.getApplicationContext(), proDePrice, proDeDes, supDeDes,cusName,proDeDate);//แก้ตรงนี้

        listView.setAdapter(adapter);
        Log.d("ReviewItemActivity","setAdapter");
//--------


    }

//hamberger menu
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


        }
        /*else if (id == R.id.nav_register) {
            Intent intent = new Intent(this, Register_Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        }
*/
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
                Toast.makeText(ReviewItemActivity.this, "Log out complete",
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
