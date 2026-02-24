package service;

import java.util.List;

import model.Supplier;

public interface SupplierService {

	void addSupplier(Supplier s);
	List<Supplier> getAllSuppliers();
	void updateSupplier(Supplier s);
	void deleteSupplier(int supplierId);
	Supplier getSupplierById(int supplierId);
	List<Supplier> getSuppliersByMaterial(int materialId);
}
