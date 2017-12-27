package com.example.pepi.givemesomefood;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TransaksiMain extends AppCompatActivity {
    DBCkonn dbCkonn;
    ListView list;

    final Context p=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_main);
        dbCkonn = new DBCkonn(this);
        load();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c=dbCkonn.selectedMenu(id);
                final String sendId=c.getString(0);
                final String sendName=c.getString(1);
                final String sendHarga=c.getString(2);
                LayoutInflater layoutInflater=LayoutInflater.from(p);
                View mView =layoutInflater.inflate(R.layout.trabsaksi,null);
                AlertDialog.Builder alertDialogBuilderUserInput=new AlertDialog.Builder(p);
                alertDialogBuilderUserInput.setView(mView);
                final EditText jumlah=(EditText)mView.findViewById(R.id.inputjumlah);
                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        dbCkonn.insertDataPesanan(sendName,Integer.parseInt(sendHarga),Integer.parseInt(jumlah.getText().toString()),Integer.parseInt(sendHarga)*Integer.parseInt(jumlah.getText().toString()));
                    }
                })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog=alertDialogBuilderUserInput.create();
                alertDialog.setTitle("Add to Chart");
                alertDialog.show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bayar) {
            Intent intent = new Intent(getApplicationContext(),Detailbelanja.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(TransaksiMain.this, R.layout.listmakanan, cursor, from, to);
        adapter.notifyDataSetChanged();
        list = (ListView) findViewById(R.id.listmenu2);
        list.setAdapter(adapter);
    }
}
