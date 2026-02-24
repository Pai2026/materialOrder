package service.impl;

import java.util.List;
import java.util.stream.Collectors;

import dao.SupplierDao;
import dao.impl.SupplierDaoImpl;
import model.Supplier;
import service.SupplierService;

public class SupplierServiceImpl implements SupplierService{
	
private final SupplierDao supplierDao = new SupplierDaoImpl();

	@Override
	public void addSupplier(Supplier s) {
		if (s.getSupplierName() == null || s.getSupplierName().isBlank()) {
            throw new RuntimeException("Add Failed: Supplier name is required.");
        }
        
        if (s.getSupplierCountry() == null || s.getSupplierCountry().isBlank()) {
            throw new RuntimeException("Add Failed: Supplier country is required.");
        }
        supplierDao.add(s);
        System.out.println("Service: Supplier " + s.getSupplierName() + " added successfully.");
    }

	@Override
	public List<Supplier> getAllSuppliers() {
		return supplierDao.selectAll();
	}


	@Override
	public void deleteSupplier(int supplierId) {
		if (supplierId <= 0) {
            throw new RuntimeException("Delete Failed: Invalid Supplier ID.");
        }

        supplierDao.delete(supplierId);
        System.out.println("Service: Supplier ID " + supplierId + " deleted successfully.");
		
	}

	@Override
	public Supplier getSupplierById(int supplierId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSupplier(Supplier s) {
		if (s.getSupplierId() == null || s.getSupplierId() <= 0) {
            throw new RuntimeException("Update Failed: Invalid Supplier ID.");
        }

        supplierDao.update(s);
        System.out.println("Service: Supplier ID " + s.getSupplierId() + " revised successfully.");
	}

	@Override
	public List<Supplier> getSuppliersByMaterial(int materialId) {
		return supplierDao.selectAll().stream().filter(s->s.getMaterialId() != null && s.getMaterialId() == materialId)
		.collect(Collectors.toList());		
	}


	

}
