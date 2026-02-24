package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Order implements Serializable{
	private Integer orderId;      
	private String orderNo;      
	private Integer customerId;
	private Integer buyerId;
	private Integer materialId;
	private Integer supplierId;
	private String materialName;
	private Integer unitPrice;
	private Integer needfulQty;  
	private Integer stockQty;
	private Integer deductQty;
	private Integer orderQty;
	private Integer total;        
	private Timestamp createDate; 
	
	
	public Order() {
		super();
	}

	public Order(Integer customerId, Integer buyerId, Integer materialId, Integer supplierId, 
            String materialName, Integer unitPrice, Integer needfulQty, 
            Integer stockQty, Integer deductQty, Integer orderQty, Integer total) {
    this.customerId = customerId;
    this.buyerId = buyerId;
    this.materialId = materialId;
    this.supplierId = supplierId; 
    this.materialName = materialName;
    this.unitPrice = unitPrice;
    this.needfulQty = needfulQty;
    this.stockQty = stockQty;
    this.deductQty = deductQty;
    this.orderQty = orderQty;
    this.total = total;
}
//Overloading
	public Order(Integer orderId, String orderNo,Integer customerId, Integer buyerId, Integer materialId, String materialName, Integer unitPrice, Integer needfulQty, Integer stockQty, Integer deductQty, Integer orderQty, Integer total) {
		super();
		this.orderId=orderId;
		this.orderNo=orderNo;
		this.customerId=customerId;
		this.buyerId = buyerId;
		this.materialId = materialId;
		this.materialName = materialName;
		this.unitPrice = unitPrice;
		this.needfulQty = needfulQty;
		this.stockQty = stockQty;
		this.deductQty = deductQty;
		this.orderQty = orderQty;
		this.total = total;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
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

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getNeedfulQty() {
		return needfulQty;
	}

	public void setNeedfulQty(Integer needfulQty) {
		this.needfulQty = needfulQty;
	}

	public Integer getStockQty() {
		return stockQty;
	}

	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	public Integer getDeductQty() {
		return deductQty;
	}

	public void setDeductQty(Integer deductQty) {
		this.deductQty = deductQty;
	}

	public Integer getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(Integer orderQty) {
		this.orderQty = orderQty;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Override
	public String toString() {
		return "Order [Id=" + orderId + ", orderNo=" + orderNo + ", 材料代碼=" + materialId+ ", 材料名=" + materialName + ", 單價=" + unitPrice + ", 需求數="
				+ needfulQty + ", 庫存數=" + stockQty + ", 扣庫數=" + deductQty + ", 下單數=" + orderQty
				+ ", 小計=" + total + "]";
	}
}