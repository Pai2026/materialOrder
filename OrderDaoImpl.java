package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.OrderDao;
import model.Order;
import util.Tool;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void add(Order order) {
		// [修正] SQL 語句：補上 supplier_id 欄位，確保欄位數量與問號一致 (共 12 個)
		String sql = "INSERT INTO orders (order_no, customer_id, buyer_id, material_id, supplier_id, " +
				     "material_name, unit_price, need_qty, stock_qty, deduct_qty, order_qty, total_price) " +
				     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection conn = Tool.getDb();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			
			String orderNo = (order.getOrderNo() == null || order.getOrderNo().isEmpty()) ? 
							 "ORD" + System.currentTimeMillis() : order.getOrderNo();
			
			ps.setString(1, orderNo);
			ps.setInt(2, order.getCustomerId());
			ps.setInt(3, order.getBuyerId());
			ps.setInt(4, order.getMaterialId());
			ps.setInt(5, order.getSupplierId());
			ps.setString(6, order.getMaterialName());
			ps.setInt(7, order.getUnitPrice());
			ps.setInt(8, order.getNeedfulQty());
			ps.setInt(9, order.getStockQty());
			ps.setInt(10, order.getDeductQty());
			ps.setInt(11, order.getOrderQty());
			ps.setInt(12, order.getTotal());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Order> selectAll() {
		List<Order> list = new ArrayList<>();
		String sql = "SELECT * FROM orders ORDER BY create_date DESC"; // 依時間排序
		
		try (Connection conn = Tool.getDb();
			 PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				list.add(mapRowToOrder(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return list;
	}

	@Override
	public List<Order> selectbyid(int id) {
		List<Order> list = new ArrayList<>();
		String sql = "SELECT * FROM orders WHERE order_id = ?";
		
		try (Connection conn = Tool.getDb();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					list.add(mapRowToOrder(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Order> selectByDateRange(String start, String end) {
	    List<Order> list = new ArrayList<>();
	    // 注意：欄位名稱需與資料庫一致 (create_date)
	    String sql = "SELECT * FROM orders WHERE DATE(create_date) BETWEEN ? AND ?";
	    
	    try (Connection conn = Tool.getDb();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, start);
	        ps.setString(2, end);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                list.add(mapRowToOrder(rs));
	            }
	        }
	    } catch (SQLException e) { 
	        e.printStackTrace(); 
	    }
	    return list;
	}
	
	@Override
	public void update(Order order) {
		String sql = "UPDATE orders SET customer_id=?, buyer_id=?, material_id=?, supplier_id=?, " +
				     "material_name=?, unit_price=?, need_qty=?, stock_qty=?, deduct_qty=?, " +
				     "order_qty=?, total_price=? WHERE order_id=?";
		
		try (Connection conn = Tool.getDb();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, order.getCustomerId());
			ps.setInt(2, order.getBuyerId());
			ps.setInt(3, order.getMaterialId());
			ps.setInt(4, order.getSupplierId());
			ps.setString(5, order.getMaterialName());
			ps.setInt(6, order.getUnitPrice());
			ps.setInt(7, order.getNeedfulQty());
			ps.setInt(8, order.getStockQty());
			ps.setInt(9, order.getDeductQty());
			ps.setInt(10, order.getOrderQty());
			ps.setInt(11, order.getTotal());
			ps.setInt(12, order.getOrderId());
	
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(int id) {
		String sql = "DELETE FROM orders WHERE order_id = ?";
		try (Connection conn = Tool.getDb();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Order mapRowToOrder(ResultSet rs) throws SQLException {
	    Order order = new Order();
	    order.setOrderId(rs.getInt("order_id"));
	    order.setOrderNo(rs.getString("order_no"));
	    order.setCustomerId(rs.getInt("customer_id"));
	    order.setBuyerId(rs.getInt("buyer_id"));
	    order.setMaterialId(rs.getInt("material_id"));
	    order.setSupplierId(rs.getInt("supplier_id")); 
	    order.setMaterialName(rs.getString("material_name"));
	    order.setUnitPrice(rs.getInt("unit_price"));
	    order.setNeedfulQty(rs.getInt("need_qty"));      // 資料庫是 need_qty
	    order.setStockQty(rs.getInt("stock_qty"));
	    order.setDeductQty(rs.getInt("deduct_qty"));
	    order.setOrderQty(rs.getInt("order_qty"));
	    order.setTotal(rs.getInt("total_price"));       // 資料庫是 total_price
	    order.setCreateDate(rs.getTimestamp("create_date")); // 資料庫是 create_date
	    return order;
	}
}