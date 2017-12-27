package com.example.pepi.givemesomefood;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Detailbelanja extends AppCompatActivity {
    DBCkonn dbCkonn;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailbelanja);
        dbCkonn=new DBCkonn(this);
        load();
    }
    public void load(){
        Cursor cursor = null;
        try {
            cursor = dbCkonn.readAllPesanan();
        } catch (Exception e) {
            Toast.makeText(this,"salah",Toast.LENGTH_LONG).show();
        }
        String[] from = new String[]{"nama_pesanan", "jumlah", "total"};
        int[] to = new int[]{R.id.namabelanja, R.id.jumlahbelanja,R.id.totalbelanja  };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(Detailbelanja.this, R.layout.belanja, cursor, from, to);
        adapter.notifyDataSetChanged();
        list = (ListView) findViewById(R.id.detailbelanja);
        list.setAdapter(adapter);
    }
}
