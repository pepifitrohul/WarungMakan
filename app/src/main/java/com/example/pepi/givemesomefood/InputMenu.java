 package com.example.pepi.givemesomefood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

 public class InputMenu extends AppCompatActivity {
    EditText nama,harga;
    Button save;
    DBCkonn dbCkonn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_menu);
        nama=(EditText)findViewById(R.id.inputmenu);
        harga=(EditText)findViewById(R.id.inputharga);
        save=(Button)findViewById(R.id.save);
        dbCkonn=new DBCkonn(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbCkonn.insertDataMenu(nama.getText().toString(),Integer.parseInt(harga.getText().toString()));
                Intent intent = new Intent(getApplicationContext(),MenuMakanan.class);
                startActivity(intent);
            }
        });

    }
}
