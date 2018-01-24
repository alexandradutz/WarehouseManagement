package com.sdi.warehousemanagement.service;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.sdi.warehousemanagement.DbHelper;
import com.sdi.warehousemanagement.entities.Category;
import com.sdi.warehousemanagement.entities.Product;
import com.sdi.warehousemanagement.entities.Stock;
import com.sdi.warehousemanagement.entities.User;

import java.sql.SQLException;

/**
 * Created by PatriciaDamian on 1/24/2018.
 */

public class Service {
    private DbHelper dbHelper;
    private RuntimeExceptionDao<User, Integer> userDao;
    private RuntimeExceptionDao<Category, Integer> categoryDao;
    private RuntimeExceptionDao<Product, Integer> productDao;
    private RuntimeExceptionDao<Stock, Integer> stockDao;

    public Service(Context context) {
        dbHelper = new DbHelper(context);
        userDao = dbHelper.getUserDao();
        productDao = dbHelper.getProductDao();
        stockDao = dbHelper.getStockDao();
        categoryDao = dbHelper.getCategoryDao();
    }

    public Product getProduct(String code){
        QueryBuilder<Product, Integer> productQuery = productDao.queryBuilder();
        try {
            return productQuery.where().eq("product_code", code).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addProduct(String product_code, String name, int price, String description, int category_id) {
        Category categ = getCategory(category_id);
        if (categ != null) {
            try {
                productDao.create(new Product(product_code, name, price, description, categ));
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    public Category getCategory(int id) {
        try {
            return categoryDao.queryForId(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeProduct(String code) {
        QueryBuilder<Product, Integer> productQuery = productDao.queryBuilder();
        try {
            Product prod = productQuery.where().eq("product_code", code).queryForFirst();
            productDao.delete(prod);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(String product_code, String name, int price, String description, int category_id) {
        QueryBuilder<Product, Integer> productQuery = productDao.queryBuilder();
        try {
            Product prod = productQuery.where().eq("product_code", product_code).queryForFirst();
            prod.setCategory(getCategory(category_id));
            prod.setDescription(description);
            prod.setName(name);
            prod.setPrice(price);
            productDao.update(prod);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
