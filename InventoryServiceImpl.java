package service.impl;

import java.util.List;

import dao.InventoryDao;
import dao.impl.InventoryDaoImpl;
import model.Inventory;
import service.InventoryService;

public class InventoryServiceImpl implements InventoryService{
	
	private final InventoryDao inventoryDao = new InventoryDaoImpl();

	@Override
	public void addInventory(Inventory item) {
		if (item.getMaterialName() == null || item.getMaterialName().isBlank()) {
            throw new RuntimeException("Validation Failed: Material name is required.");}
		if (item.getStockQty() != null && item.getStockQty() < 0) {
            throw new RuntimeException("Validation Failed: Initial stock cannot be negative.");
        }
		inventoryDao.add(item);
	}

	@Override
	public List<Inventory> getAllInventory() {
		return inventoryDao.selectAll();
	}

	@Override
	public List<Inventory> getInventoryById(int materialId) {
		return inventoryDao.selectById(materialId);
	}

	@Override
	public void updateInventory(Inventory item) {
		if (item.getMaterialId() == null || item.getMaterialId() <= 0) {
            throw new RuntimeException("Update Failed: Invalid Material ID.");
        }
        inventoryDao.update(item);
	}

	@Override
	public void deleteInventory(int materialId) {
		if (materialId <= 0) {
            throw new RuntimeException("Delete Failed: Invalid Material ID.");
        }
        inventoryDao.delete(materialId);
	}

	@Override
	public void reduceStock(int materialId, int amount) {
		List<Inventory> items = inventoryDao.selectById(materialId);
		if (items.isEmpty()) {
            throw new RuntimeException("Stock Reduction Failed: Material not found.");
        }
        
        Inventory currentItem = items.get(0);
        int newQty = currentItem.getStockQty() - amount;
        
        if (newQty < 0) {
            throw new RuntimeException("Stock Reduction Failed: Insufficient stock for " + currentItem.getMaterialName());
        }
        
        currentItem.setStockQty(newQty);
        inventoryDao.update(currentItem);
    }
}
