package com.sdi.warehousemanagement.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdi.warehousemanagement.Entity;
import com.sdi.warehousemanagement.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Edit extends AppCompatActivity {

    TextView txtQR;
    EditText txtQuantity;
    EditText txtName;
    Spinner spinner;
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



        txtQR = (TextView)findViewById(R.id.txtProd);
        txtQuantity = (EditText)findViewById(R.id.txtQuantity);
        txtName = (EditText)findViewById(R.id.editName);
        spinner = (Spinner)findViewById(R.id.spinCategory);

        Button btnSave = (Button)findViewById(R.id.txtSave);
        Button btnRemove = (Button)findViewById(R.id.txtRemove);

        if(getIntent().getExtras()!=null) {
            if (getIntent().getExtras().containsKey("new_qrcode")) {
                String qrcode = getIntent().getStringExtra("new_qrcode");
                txtQR.setText(qrcode);
                txtQuantity.setText("1");
            }else
            if (getIntent().getExtras().containsKey("prodid")) {
                txtQR.setText(getIntent().getStringExtra("prodid"));
                txtName.setText(getIntent().getStringExtra("name"));

                txtQuantity.setText(String.valueOf(getIntent().getIntExtra("quantity",0)));
            }
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("categories");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,String> entities = new HashMap<>();
                for(DataSnapshot child : dataSnapshot.getChildren() ){
                    String key = child.getKey();
                    String e = child.getValue(String.class);
                    entities.put(key,e);
                }
                ArrayList<String> categories = new ArrayList<>();
                for(String k : entities.keySet())
                    categories.add(entities.get(k));
                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(Edit.this,
                        android.R.layout.simple_list_item_1, categories);
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adp1);
                if(getIntent().getExtras()!=null){
                    int category = getIntent().getIntExtra("category",0);
                    if(category<categories.size())
                        spinner.setSelection(category);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtQR.getText().toString();
                Entity e = new Entity(txtName.getText().toString(),String.valueOf(spinner.getSelectedItemPosition()),Integer.parseInt(txtQuantity.getText().toString()));
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("items");
                myRef.child(id).setValue(e);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtQR.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("items");
                myRef.child(id).removeValue();
                Edit.this.finish();
            }
        });
    }

}
