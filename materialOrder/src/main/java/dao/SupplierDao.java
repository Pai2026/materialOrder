package dao;

import java.util.List;

import model.Supplier;

public interface SupplierDao {
	
	void add(Supplier s);
	
	List<Supplier> selectAll();
	
	void update(Supplier s);
	void delete(int supplierId);

}
