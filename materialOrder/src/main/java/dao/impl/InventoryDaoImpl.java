package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.InventoryDao;
import model.Inventory;
import util.Tool;

public class InventoryDaoImpl implements InventoryDao {
	
	@Override
	public void add(Inventory item) {
	Connection conn=Tool.getDb();
	String sql="INSERT INTO inventory (material_name,qty) VALUES (?,?)";
	try {
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1, item.getMaterialName());
		ps.setInt(2, item.getStockQty());
		ps.executeUpdate();
		ps.close();
		conn.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}

	@Override
	public List<Inventory> selectAll() {
		List<Inventory> list= new ArrayList<>();
		Connection conn=Tool.getDb();
		String sql="SELECT * FROM inventory";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Inventory i=new Inventory();
				i.setMaterialId(rs.getInt("material_id"));
				i.setMaterialName(rs.getString("material_name"));
				i.setStockQty(rs.getInt("stock_qty"));
				i.setUnitPrice(rs.getInt("unit_price"));
				list.add(i);
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
	public List<Inventory> selectById(int materialId) {
		List<Inventory> list= new ArrayList<>();
		Connection conn=Tool.getDb();
		String sql="SELECT * FROM inventory WHERE material_id = ?";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, materialId);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Inventory i=new Inventory();
				i.setMaterialId(rs.getInt("material_id"));
				i.setMaterialName(rs.getString("material_name"));
				i.setStockQty(rs.getInt("stock_qty"));
				i.setUnitPrice(rs.getInt("unit_price"));
				list.add(i);
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
	public void update(Inventory item) {
		Connection conn=Tool.getDb();
		String sql="UPDATE inventory SET material_name =?, qty=? WHERE material_id=?";
		try {
			PreparedStatement ps= conn.prepareStatement(sql);
			ps.setString(1, item.getMaterialName());
			ps.setInt(2, item.getStockQty());
			ps.setInt(3, item.getMaterialId());
			ps.executeUpdate();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int materialId) {
		Connection conn=Tool.getDb();
		String sql="DELETE FROM inventory WHERE material_id=?";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, materialId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
