package com.sdi.warehousemanagement.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sdi.warehousemanagement.R;
import com.sdi.warehousemanagement.entities.Category;
import com.sdi.warehousemanagement.entities.Product;
import com.sdi.warehousemanagement.service.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Edit extends AppCompatActivity {

    TextView txtQR;
    EditText txtQuantity;
    EditText txtName;
    EditText txtPrice;
    EditText txtDescription;
    Spinner spinner;
    private Service dbService = new Service(Edit.this);
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
        txtPrice = (EditText)findViewById(R.id.txtPrice);
        txtDescription = (EditText) findViewById(R.id.txtDescription);


        Button btnSave = (Button)findViewById(R.id.txtSave);
        Button btnRemove = (Button)findViewById(R.id.txtRemove);

        if(getIntent().getExtras()!=null) {
            if (getIntent().getExtras().containsKey("new_qrcode")) {
                String qrcode = getIntent().getStringExtra("new_qrcode");
                txtQR.setText(qrcode);
                txtQuantity.setText("1");
                txtPrice.setText("0");
            } else if (getIntent().getExtras().containsKey("prodid")) {
                txtQR.setText(getIntent().getStringExtra("prodid"));
                txtName.setText(getIntent().getStringExtra("name"));
                txtPrice.setText(String.valueOf(getIntent().getIntExtra("price",0)));
                txtQuantity.setText(String.valueOf(getIntent().getIntExtra("quantity",0)));
                txtDescription.setText(getIntent().getStringExtra("description"));
            }
        }


        HashMap<String, Category> categories = new HashMap<>();

        List<Category> categList = dbService.getAllCategories();
        ArrayList<String> categNames = new ArrayList<>();
        for(Category p : categList) {
            categNames.add(p.getName());
        }
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(Edit.this,
                android.R.layout.simple_list_item_1, categNames);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);
        if(getIntent().getExtras() != null){
            int category = getIntent().getIntExtra("category",0);
            if(category < categories.size())
                spinner.setSelection(category);
        }



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtQR.getText().toString();
                String categName = String.valueOf(spinner.getItemAtPosition(spinner.getSelectedItemPosition()));
                Category categ = dbService.getCategoryByName(categName);

                if (categ != null) {
                    dbService.updateProduct(id, txtName.getText().toString(),
                            Integer.parseInt(txtPrice.getText().toString()),
                            txtDescription.getText().toString(),
                            categ.getCategory_id(),
                            Integer.parseInt(txtQuantity.getText().toString()));
                }
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtQR.getText().toString();
                dbService.removeProduct(id);
                Edit.this.finish();
            }
        });
    }

}
