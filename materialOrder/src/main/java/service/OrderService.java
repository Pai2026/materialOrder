package service;

import java.util.List;

import model.Order;

public interface OrderService {
	//create
	boolean addOrder(Order morder);
	String checkout (List<Order> list);
	
	//read
	List<Order> allOrder();
	List<Order> findById(int id);
	// 根據日期範圍搜尋
	List<Order> findByDate(String startDate, String endDate);
	// 根據材料名稱或編號模糊搜尋
	List<Order> findByKeyword(String keyword);
	
	//update
	boolean updateOrder(Order morder);
	
	//delete
	boolean deleteOrderById(int id);

}

