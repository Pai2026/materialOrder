package service;

import java.util.List;

import model.Inventory;

public interface InventoryService {

	void addInventory(Inventory item);
	List<Inventory> getAllInventory();
	List<Inventory> getInventoryById(int materialId);
	void updateInventory(Inventory item);
	void deleteInventory(int materialId);
	void reduceStock(int materialId, int amount);
}
