package model;

import java.io.Serializable;

public class Supplier implements Serializable {
	private Integer supplierId;
    private String supplierName;
    private String supplierCountry;
    private Integer materialId;
    
    public Supplier() {}

	public Supplier(Integer supplierId, String supplierName, String supplierCountry, Integer materialId) {
		super();
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.supplierCountry = supplierCountry;
		this.materialId = materialId;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierCountry() {
		return supplierCountry;
	}

	public void setSupplierCountry(String supplierCountry) {
		this.supplierCountry = supplierCountry;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	
    
    

}
