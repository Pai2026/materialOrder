package model;

import java.io.Serializable;

public class Inventory implements Serializable{
	private Integer materialId;
	private String materialName;
	private Integer stockQty;
	private Integer unitPrice;
	
	public Inventory() {}
	
	public Inventory(Integer materialId, String materialName, Integer stockQty, Integer unitPrice) {
		super();
		this.materialId = materialId;
		this.materialName = materialName;
		this.stockQty = stockQty;
		this.unitPrice = unitPrice;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Integer getStockQty() {
		return stockQty;
	}

	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}
}
