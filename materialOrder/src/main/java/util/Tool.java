package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Order;

public class Tool {

    // 資料庫設定，建議集中管理
    private static final String URL = "jdbc:mysql://localhost:3306/materialOrder";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // 取得資料庫連線
    public static Connection getDb() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("找不到 MySQL 驅動程式");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("連線資料庫失敗");
            e.printStackTrace();
        }
        return conn;
    }

    // --- 序列化存取方法 (暫存訂單) ---

    public static void saveOrder(List<Order> morder) {
        try (FileOutputStream fos = new FileOutputStream("Morder.txt");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(morder);
        } catch (IOException e) {
            System.err.println("儲存 Morder.txt 失敗");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Order> readOrder() {
        List<Order> morder = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("Morder.txt");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            morder = (List<Order>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("找不到暫存訂單檔，回傳空清單");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return morder;
    }

    // --- 物件通用存取方法 ---

    public static void saveObject(Object object, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName + ".txt");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readObject(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName + ".txt");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return ois.readObject();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // --- 資料庫業務邏輯 ---

    // 取得單一材料庫存
    public static int getStock(String materialName) {
        String sql = "SELECT qty FROM inventory WHERE material_name = ?";
 
        try (Connection conn = getDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, materialName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("qty");
                }
            }
        } catch (SQLException e) {
            System.err.println("讀取庫存失敗：" + materialName);
            e.printStackTrace();
        }
        return 0;
    }

    // 更新庫存
    public static void updateStock(String name, int deductQty) {
        if (deductQty <= 0) return;
        String sql = "UPDATE inventory SET qty = qty - ? WHERE material_name = ?";
        
        try (Connection conn = getDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, deductQty);
            ps.setString(2, name);
            ps.executeUpdate();
            System.out.println("庫存更新成功: " + name + " 扣除 " + deductQty);
            
        } catch (SQLException e) {
            System.err.println("更新庫存失敗: " + name);
            e.printStackTrace();
        }
    }

    public static void addOrderToDb(Order o, String orderNo) {
       
        String sql = "INSERT INTO orders (order_no, customer_id, buyer_id, material_id, supplier_id, " +
                     "material_name, unit_price, need_qty, stock_qty, deduct_qty, order_qty, total_price) " +
                     "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try (Connection conn = getDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, orderNo);
            ps.setInt(2, o.getCustomerId());
            ps.setInt(3, o.getBuyerId());
            ps.setInt(4, o.getMaterialId());
            ps.setInt(5, o.getSupplierId()); // [新增設定值]
            ps.setString(6, o.getMaterialName());
            ps.setInt(7, o.getUnitPrice());
            ps.setInt(8, o.getNeedfulQty());
            ps.setInt(9, o.getStockQty());
            ps.setInt(10, o.getDeductQty());
            ps.setInt(11, o.getOrderQty());
            ps.setInt(12, o.getTotal());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 透過材料名稱取得 ID
    public static int getMaterialId(String materialName) {
        String sql = "SELECT material_id FROM inventory WHERE material_name = ?";
        try (Connection conn = getDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, materialName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("material_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}