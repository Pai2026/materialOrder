package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.SupplierDao;
import model.Supplier;
import util.Tool;

public class SupplierDaoImpl implements SupplierDao{
	
	@Override
	public void add(Supplier s) {
		Connection conn=Tool.getDb();
		String sql="INSERT INTO supplier(supplier_name, supplier_country, material_id) VALUES (?,?,?)";

		try {
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, s.getSupplierName());
	        ps.setString(2, s.getSupplierCountry());
	        ps.setInt(3, s.getMaterialId()); // 只存 ID
	        
	        ps.executeUpdate();
	        ps.close(); conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }	
	}

	@Override
	public List<Supplier> selectAll() {
		   List<Supplier> list =new ArrayList<>();
		   Connection conn=Tool.getDb();
		   String sqi="SELECT * FROM supplier";
		   
		   try {
	            PreparedStatement ps = conn.prepareStatement(sqi);
	            ResultSet rs = ps.executeQuery();
	            
	            while(rs.next()) {
	                Supplier s = new Supplier(
	                    rs.getInt("supplier_id"),
	                    rs.getString("supplier_name"),
	                    rs.getString("supplier_country"),
	                    rs.getInt("material_id"));
	                list.add(s);
	            }
	            
	            rs.close(); ps.close(); conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }

	@Override
	public void update(Supplier s) {
		Connection conn=Tool.getDb();
		String sql="UPDATE Supplier SET supplier_name=? , supplier_country=? , material_id=? WHERE supplier_id=?";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1,s.getSupplierName());
			ps.setString(2, s.getSupplierCountry());
			ps.setInt(3, s.getMaterialId());
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int supplierId) {
		Connection conn=Tool.getDb();
		String sql="DELETE FROM supplier WHERE supplier_id=?";
		try {
			PreparedStatement ps= conn.prepareStatement(sql);
			ps.setInt(1, supplierId);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	}