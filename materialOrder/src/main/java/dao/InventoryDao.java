package dao;

import java.util.List;

import model.Inventory;

public interface InventoryDao {

	void add(Inventory item);
	
	List<Inventory> selectAll();
	List<Inventory> selectById(int materialId);
	
	void update(Inventory item);
	
	void delete(int materialId);
	
}
