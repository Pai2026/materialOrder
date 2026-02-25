package service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import dao.OrderDao;
import dao.impl.OrderDaoImpl;
import model.Order;
import service.InventoryService;         
import service.OrderService;
import util.Tool;

public class OrderServiceImpl implements OrderService {
    
    private final OrderDao orderDao = new OrderDaoImpl();
    
    
    private final InventoryService inventoryService = new InventoryServiceImpl();

    // --- Create ---
    @Override
    public boolean addOrder(Order morder) {
        if (morder.getNeedfulQty() != null && morder.getNeedfulQty() >= 0) {
            orderDao.add(morder);
            return true;
        }
        return false;
    }

    // --- Read
    @Override
    public List<Order> allOrder() {
        return orderDao.selectAll();
    }

    @Override
    public List<Order> findById(int id) {
        return orderDao.selectbyid(id);
    }

    @Override
    public List<Order> findByKeyword(String keyword) {
                               
        return orderDao.selectAll().stream()
            .filter(o -> o.getMaterialName().contains(keyword) || 
                         String.valueOf(o.getMaterialId()).contains(keyword))
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByDate(String startDate, String endDate) {
       
        return orderDao.selectByDateRange(startDate, endDate);
    }

    // --- Update ---
    @Override
    public boolean updateOrder(Order morder) {
        if (morder.getNeedfulQty() >= 0) {
            orderDao.update(morder);
            return true;
        }
        return false;
    }

    // --- Delete ---
    @Override
    public boolean deleteOrderById(int id) {
        if (id > 0) {
            orderDao.deleteById(id);
            return true;
        }
        return false;
    }

    // --- Checkout (結帳與庫存連動) ---
    @Override
    public String checkout(List<Order> list) {
        if (list == null || list.isEmpty()) return "empty";
        
        // 生成 14 位數訂單編號
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderNo = sdf.format(new Date());
        
        try {
            for (Order o : list) {
                // 1. 寫入資料庫 
                Tool.addOrderToDb(o, orderNo);
                
                // 2. 同步扣除庫存
                // 先確認有填寫扣庫數 (>0) 才去執行，避免浪費資源操作資料庫
                if (o.getDeductQty() != null && o.getDeductQty() > 0) {
                    inventoryService.reduceStock(o.getMaterialId(), o.getDeductQty());
                }
            }
            return orderNo;
        } catch (Exception e) {
           
            e.printStackTrace();
            return "error";
        }
    }
}