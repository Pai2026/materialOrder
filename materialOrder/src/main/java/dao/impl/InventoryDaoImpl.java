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
		String sql="INSERT INTO inventory (material_name,stock_qty) VALUES (?,?)";
		try (Connection conn = Tool.getDb();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, item.getMaterialName());
			ps.setInt(2, item.getStockQty());
			ps.executeUpdate();
		} catch (SQLException e) {
           
			throw new RuntimeException("新增庫存失敗: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Inventory> selectAll() {
		List<Inventory> list= new ArrayList<>();
		String sql="SELECT * FROM inventory";
		
		try (Connection conn = Tool.getDb();
			 PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			while(rs.next()) {
				Inventory i=new Inventory();
				i.setMaterialId(rs.getInt("material_id"));
				i.setMaterialName(rs.getString("material_name"));
				i.setStockQty(rs.getInt("stock_qty"));
				i.setUnitPrice(rs.getInt("unit_price"));
				list.add(i);
			}
		} catch (SQLException e) {
			throw new RuntimeException("查詢所有庫存失敗: " + e.getMessage(), e);
		}
		return list;
	}

	@Override
	public List<Inventory> selectById(int materialId) {
		List<Inventory> list= new ArrayList<>();
		String sql="SELECT * FROM inventory WHERE material_id = ?";
		try (Connection conn = Tool.getDb();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, materialId);
			try (ResultSet rs = ps.executeQuery()) {
				while(rs.next()){
					Inventory i=new Inventory();
					i.setMaterialId(rs.getInt("material_id"));
					i.setMaterialName(rs.getString("material_name"));
					i.setStockQty(rs.getInt("stock_qty"));
					i.setUnitPrice(rs.getInt("unit_price"));
					list.add(i);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("查詢單筆庫存失敗: " + e.getMessage(), e);
		}
		return list;
	}

	@Override
	public void update(Inventory item) {
		String sql="UPDATE inventory SET material_name =?, stock_qty=? WHERE material_id=?";
		try (Connection conn = Tool.getDb();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, item.getMaterialName());
			ps.setInt(2, item.getStockQty());
			ps.setInt(3, item.getMaterialId());
			
            
			int rows = ps.executeUpdate();
            
            
			if (rows == 0) {
				System.err.println("【警告】找不到對應的庫存項目！Material ID: " + item.getMaterialId());
			} else {
				System.out.println("【成功】庫存已更新！Material ID: " + item.getMaterialId() + "，最新數量: " + item.getStockQty());
			}
			
		} catch (SQLException e) {
            
			throw new RuntimeException("更新庫存資料庫失敗: " + e.getMessage(), e);
		}
	}

	@Override
	public void delete(int materialId) {
		String sql="DELETE FROM inventory WHERE material_id=?";
		try (Connection conn = Tool.getDb();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, materialId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("刪除庫存失敗: " + e.getMessage(), e);
		}
	}
}