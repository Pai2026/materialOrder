package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.CustomerDao;
import model.Customer;
import util.Tool;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public void add(Customer customer) {
        String sql = "INSERT INTO customer (customer_name, customer_country) VALUES (?, ?)";
        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerCountry());
            
            ps.executeUpdate();
            System.out.println("DAO: Customer added successfully.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> selectAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("customer_id"));
                c.setCustomerName(rs.getString("customer_name"));
                c.setCustomerCountry(rs.getString("customer_country"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Customer> selectById(int customerId) {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setCustomerId(rs.getInt("customer_id"));
                    c.setCustomerName(rs.getString("customer_name"));
                    c.setCustomerCountry(rs.getString("customer_country"));
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customer SET customer_name = ?, customer_country = ? WHERE customer_id = ?";
        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerCountry());
            ps.setInt(3, customer.getCustomerId());
            
            ps.executeUpdate();
            System.out.println("DAO: Customer updated successfully.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int customerId) {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, customerId);
            ps.executeUpdate();
            System.out.println("DAO: Customer deleted successfully.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}