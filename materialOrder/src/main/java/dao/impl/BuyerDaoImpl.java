package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BuyerDao;
import model.Buyer;
import util.Tool;

public class BuyerDaoImpl implements BuyerDao{

	@Override
	public void add(Buyer b) {
		Connection conn = Tool.getDb();
		String sql="INSERT INTO buyer(username, password, name, country) VALUES(?,?,?,?)";
		
		try {
		PreparedStatement ps= conn.prepareCall(sql);
		ps.setString(1, b.getUsername());
        ps.setString(2, b.getPassword());
        ps.setString(3, b.getName());
        ps.setString(4, b.getCountry());
        
        ps.executeUpdate();
        ps.close();
        conn.close();
		} catch (SQLException e) {
            e.printStackTrace();
        } 	
	}

	@Override
	public List<Buyer> selectAll() {
		List<Buyer> list = new ArrayList<>();
        Connection conn = Tool.getDb();
        String sql = "SELECT * FROM buyer";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            	Buyer b = new Buyer(
                        rs.getInt("buyer_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("country")
                    );
                    list.add(b);
                }
            	rs.close();
                ps.close();
                conn.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
     
	}

	@Override
	public List<Buyer> selectByUsername(String username) {
		List<Buyer> list = new ArrayList<>();
        Connection conn = Tool.getDb();
        String sql = "SELECT * FROM buyer WHERE username = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Buyer b = new Buyer(
                    rs.getInt("buyer_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("country")
                );
                list.add(b);
            }
            
            rs.close();
            ps.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

	}

	@Override
	public List<Buyer> selectByUsernameAndPassword(String username, String password) {
		List<Buyer> list = new ArrayList<>();
        Connection conn = Tool.getDb();
String sql = "SELECT * FROM buyer WHERE username = ? AND password = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Buyer b = new Buyer(
                    rs.getInt("buyer_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("country")
                );
                list.add(b);
            }
            
            rs.close();
            ps.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
        
	}

	@Override
	public void update(Buyer b) {
		Connection conn = Tool.getDb();
String sql = "UPDATE buyer SET username=?, password=?, name=?, country=? WHERE buyer_id=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, b.getUsername());
            ps.setString(2, b.getPassword());
            ps.setString(3, b.getName());
            ps.setString(4, b.getCountry());
            ps.setInt(5, b.getBuyerId()); 
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void delete(int id) {
		Connection conn = Tool.getDb();
        String sql = "DELETE FROM buyer WHERE buyer_id=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

		
	}

}
