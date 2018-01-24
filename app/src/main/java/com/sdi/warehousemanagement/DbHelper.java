package com.sdi.warehousemanagement; /**
 * Created by PatriciaDamian on 1/24/2018.
 */
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sdi.warehousemanagement.entities.Category;
import com.sdi.warehousemanagement.entities.Product;
import com.sdi.warehousemanagement.entities.Stock;
import com.sdi.warehousemanagement.entities.User;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "warehouse_management.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    private Dao<User, Integer> dao = null;
    private RuntimeExceptionDao<User, Integer> runtimeDao = null;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DbHelper.class.getName(), "onCreate");

//            TableUtils.dropTable(connectionSource, User.class, true);
//            TableUtils.dropTable(connectionSource, Stock.class, true);
//            TableUtils.dropTable(connectionSource, Product.class, true);
//            TableUtils.dropTable(connectionSource, Category.class, true);

            TableUtils.createTableIfNotExists(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, Category.class);
            TableUtils.createTableIfNotExists(connectionSource, Product.class);

        } catch (SQLException e) {
            Log.e(DbHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        // here we try inserting data in the on-create as a test
        RuntimeExceptionDao<User, Integer> dao = getUserDao();
        // create some entries in the onCreate
        User user = new User("user", "pass", "hshs", "hjsjs");
        dao.create(user);
        Category categ = new Category("Snowboard");
        getCategoryDao().create(categ);
        getCategoryDao().create(new Category("Ski"));
        getCategoryDao().create(new Category("Streetwear"));
        getProductDao().create(new Product("1234", "Burton", 500, "da best", categ, 10));
        getUserDao().create(new User("patri", "admin", "Patrikia", "Damian"));
        getUserDao().create(new User("admin", "admin", "Patrikia", "Damian"));
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DbHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Product.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DbHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<User, Integer> getDao() throws SQLException {
        if (dao == null) {
            dao = getDao(User.class);
        }
        return dao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<User, Integer> getUserDao() {
        if (runtimeDao == null) {
            runtimeDao = getRuntimeExceptionDao(User.class);
        }
        return runtimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        dao = null;
        runtimeDao = null;
    }

    public RuntimeExceptionDao<Category, Integer> getCategoryDao() {

        try {
            return getRuntimeExceptionDao(Category.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RuntimeExceptionDao<Product, Integer> getProductDao() {
        try {
            return getRuntimeExceptionDao(Product.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RuntimeExceptionDao<Stock, Integer> getStockDao() {
        try {
            return getRuntimeExceptionDao(Stock.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
