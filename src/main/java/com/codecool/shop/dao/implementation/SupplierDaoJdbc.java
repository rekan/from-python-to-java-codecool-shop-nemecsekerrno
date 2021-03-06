package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DBController;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hamargyuri on 2016. 11. 29..
 */
public class SupplierDaoJdbc implements SupplierDao {
    private String query;
    private ResultSet res;
    private Supplier supplier;

    public SupplierDaoJdbc() {
    }

    @Override
    public void add(Supplier supplier) {
        query = "INSERT INTO supplier (s_name, s_description) VALUES ('" + supplier.getName() + "', '"
                + supplier.getDescription() + "');";
        try (Connection conn = DBController.getConnection(); Statement statement = conn.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) throws SQLException {
        query = "SELECT * FROM supplier WHERE s_id='" + id + "';";
        Supplier supplier = null;
        try (Connection connection = DBController.getConnection(); Statement statement = connection.createStatement()) {
            res = statement.executeQuery(query);
            if (res.next()) {
                supplier = new Supplier(res.getString("s_name"), res.getString("s_description"));
                supplier.setId(id);
                supplier.setProducts(new ProductDaoJdbc().getBy(supplier));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }

    @Override
    public void remove(int id) {
        query = "DELETE FROM supplier WHERE s_id='" + id + "';";
        try (Connection connection = DBController.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getAll() throws SQLException {
        List<Supplier> suppliers = new ArrayList();
        query = "SELECT * FROM supplier;";
        try (Connection connection = DBController.getConnection(); Statement statement = connection.createStatement()) {
            res = statement.executeQuery(query);
            while (res.next()) {
                supplier = new Supplier(res.getString("s_name"), res.getString("s_description"));
                supplier.setId(res.getInt("s_id"));
                supplier.setProducts(new ProductDaoJdbc().getBy(supplier));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    @Override
    public void clearAll() {
        String drop = "DROP TABLE supplier;";
        String create = "CREATE TABLE supplier (s_id serial PRIMARY KEY, s_name varchar(40), " +
                "s_description varchar(100));";
        try (Connection connection = DBController.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(drop);
            statement.executeUpdate(create);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
