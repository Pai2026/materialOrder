package model;

import java.io.Serializable;

public class Customer implements Serializable{
	private Integer customerId;
    private String customerName;
    private String customerCountry;
    
    public Customer() {}
    
	public Customer(Integer customerId, String customerName, String customerCountry) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerCountry = customerCountry;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCountry() {
		return customerCountry;
	}

	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}
    
    

}
