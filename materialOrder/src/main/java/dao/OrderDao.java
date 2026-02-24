package dao;

import java.util.List;

import model.Order;

public interface OrderDao {
	//create
	void add(Order order);
	
	//read
	List<Order> selectAll();
	List<Order> selectbyid(int id);
	List<Order> selectByDateRange(String start, String end);
	
	//update
	void update(Order order);
	
	//delete
	void deleteById(int id);

}
