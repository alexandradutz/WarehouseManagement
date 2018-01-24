package com.sdi.warehousemanagement.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sdi.warehousemanagement.R;

public class Edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        TextView txtID = (TextView)findViewById(R.id.txtID);
        TextView txtQR = (TextView)findViewById(R.id.txtQR);
        EditText txtQuantity = (EditText)findViewById(R.id.txtQuantity);
        EditText txtName = (EditText)findViewById(R.id.editName);
        Button btnSave = (Button)findViewById(R.id.txtSave);
        Button btnRemove = (Button)findViewById(R.id.txtRemove);

        if(getIntent().getExtras().containsKey("new_qrcode")) {
            String qrcode = getIntent().getStringExtra("new_qrcode");
            txtQR.setText("QRCode: " + qrcode);
            txtID.setText("ID: new");
            txtQuantity.setText("1");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
