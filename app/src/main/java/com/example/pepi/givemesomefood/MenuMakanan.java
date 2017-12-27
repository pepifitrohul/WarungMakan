package com.example.pepi.givemesomefood;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MenuMakanan extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DBCkonn dbCkonn;
    ListView list;

    final Context p=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_makanan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbCkonn = new DBCkonn(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),InputMenu.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        load();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c=dbCkonn.selectedMenu(id);
                final String sendId=c.getString(0);
                String sendName=c.getString(1);
                String sendHarga=c.getString(2);
                LayoutInflater layoutInflater=LayoutInflater.from(p);
                View mView =layoutInflater.inflate(R.layout.tampildetailmenu,null);
                AlertDialog.Builder alertDialogBuilderUserInput=new AlertDialog.Builder(p);
                alertDialogBuilderUserInput.setView(mView);
                final EditText nama = (EditText)mView.findViewById(R.id.textnama);
                final EditText harga = (EditText)mView.findViewById(R.id.textharga);
                final TextView idmenu = (TextView) mView.findViewById(R.id.textid);
                final Button update=(Button)mView.findViewById(R.id.btnupdate);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbCkonn.update(Long.parseLong(idmenu.getText().toString()),nama.getText().toString(),Integer.parseInt(harga.getText().toString()));
                        Intent intent=new Intent(p,MenuMakanan.class);
                        startActivity(intent);
                    }
                });
                nama.setText(sendName);
                harga.setText(sendHarga);
                idmenu.setText(sendId);
                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbCkonn.deleteMenu(Long.parseLong(sendId));
                        load();
                    }
                })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog=alertDialogBuilderUserInput.create();
                alertDialog.setTitle("Detail Menu");
                alertDialog.show();

            }
        });
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void load(){
        Cursor cursor = null;
        try {
            cursor = dbCkonn.readAllMenu();
        } catch (Exception e) {
            Toast.makeText(this,"salah",Toast.LENGTH_LONG).show();
        }
        String[] from = new String[]{"nama", "harga"};
        int[] to = new int[]{R.id.menu, R.id.harga };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(MenuMakanan.this, R.layout.listmakanan, cursor, from, to);
        adapter.notifyDataSetChanged();
        list = (ListView) findViewById(R.id.listmakanan);
        list.setAdapter(adapter);
    }

}
