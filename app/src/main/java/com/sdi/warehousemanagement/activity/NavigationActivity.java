package com.sdi.warehousemanagement.activity;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.SparseArray;
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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.query.In;
import com.sdi.warehousemanagement.DbHelper;
import com.sdi.warehousemanagement.R;
import com.sdi.warehousemanagement.entities.Category;
import com.sdi.warehousemanagement.entities.Product;
import com.sdi.warehousemanagement.entities.Stock;
import com.sdi.warehousemanagement.entities.User;
import com.sdi.warehousemanagement.fragment.HomeFragment;
import com.sdi.warehousemanagement.fragment.ItemFragment;
import com.sdi.warehousemanagement.fragment.QRFragment;
import com.sdi.warehousemanagement.service.Service;

import java.sql.SQLException;
import java.util.List;

import info.androidhive.barcode.BarcodeReader;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BarcodeReader.BarcodeReaderListener {

    private Service dbService = new Service(NavigationActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.nav_header_main);
//        EditText txtUsername = (EditText)findViewById(R.id.txtUserName);
//        EditText txtFullname = (EditText)findViewById(R.id.txtFullName);
//
//        String username = getIntent().getStringExtra("username");
//        User currentUser = dbService.getUserByName(username);
//        if (username != null) {
//            txtUsername.setText(getIntent().getStringExtra("username"));
//            txtFullname.setText(getIntent().getStringExtra("fullName"));
//        }

        setContentView(R.layout.activity_navigation);
        mayRequestCamera();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationActivity.this,Edit.class);
                startActivity(intent);
//                System.out.println("FAB CLICK");
//                User u = new User("user", "pass", "fn", "ln");
//                DbHelper dbHelper = new DbHelper(NavigationActivity.this);
//                RuntimeExceptionDao<User, Integer> userDao = dbHelper.getUserDao();
//                RuntimeExceptionDao<Category, Integer> categoryDao = dbHelper.getCategoryDao();
//                RuntimeExceptionDao<Product, Integer> productDao = dbHelper.getProductDao();
//                RuntimeExceptionDao<Stock, Integer> stockDao = dbHelper.getStockDao();
//                List<User> list = userDao.queryForAll();
//                System.out.println("===============================");
//                for (User user : list) {
//                    System.out.println(user.toString());
//                }
//                System.out.println("=====================================");
//                userDao.create(u);
//                for (User user : list) {
//                    System.out.println(user.toString());
//                }
//                System.out.println("=====================================");
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Category c1 = new Category("categ");
//                categoryDao.create(c1);
//                productDao.create(new Product( "code1", "p1", 12, "" , c1));
//
//                System.out.println(categoryDao.queryForAll().toString());
//                System.out.println(productDao.queryForAll().toString());
//
//                QueryBuilder<Category, Integer> categoryQuery = categoryDao.queryBuilder();
//                QueryBuilder<Product, Integer> productQuery = productDao.queryBuilder();
//                try {
//                    productQuery.where().eq("category_id", 1);
//                    categoryQuery.join(productQuery);
//                    System.out.println(categoryQuery.query());
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(productDao.queryForId(1));
//                stockDao.create(new Stock(productDao.queryForId(1), "M", 100));
//                System.out.println(stockDao.queryForAll());

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                ItemFragment frg = (ItemFragment) fragmentManager.findFragmentByTag("ITEM");
                if(frg!=null)
                    frg.filterSearch(s);
                return false;
            }
        });

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Fragment fragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment,"HOME").commit();
        } else if (id == R.id.nav_list) {
            Fragment fragment = new ItemFragment();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment,"ITEM").commit();

        } else if (id == R.id.QR) {
            Fragment fragment = new QRFragment();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment,"QR").commit();
        }


        return true;
    }

    private boolean mayRequestCamera() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(findViewById(R.id.fab), R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{CAMERA}, 123);
                        }
                    });
        } else {
            requestPermissions(new String[]{CAMERA}, 123);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission refused", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onScanned(Barcode barcode) {

        Intent intent = new Intent(NavigationActivity.this, Edit.class);
        Product product = dbService.getProduct(barcode.displayValue);
        if (product != null) {
            intent.putExtra("prodid", product.getProduct_code());
            intent.putExtra("name", product.getName());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("quantity", product.getQuantity());
            intent.putExtra("category", product.getCategory().getName());
            intent.putExtra("description", product.getDescription());
            startActivity(intent);
        } else {
            intent.putExtra("new_qrcode", barcode.displayValue);
            startActivity(intent);
        }
    }


    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {

    }
}
