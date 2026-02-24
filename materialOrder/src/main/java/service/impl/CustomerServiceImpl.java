package service.impl;

import java.util.List;

import dao.CustomerDao;
import dao.impl.CustomerDaoImpl;
import model.Customer;
import service.CustomerService;

public class CustomerServiceImpl implements CustomerService{
	
	private final CustomerDao customerDao= new CustomerDaoImpl();

	@Override
	public void addCustomer(Customer customer) {
		if (customer.getCustomerName() == null || customer.getCustomerName().isBlank()) {
            throw new RuntimeException("Add Failed: Customer Name is required.");
        }
		if (customer.getCustomerCountry() == null || customer.getCustomerCountry().isBlank()) {
            throw new RuntimeException("Add Failed: Customer Country is required.");}
		customerDao.add(customer);
		System.out.println("Service: Customer '" + customer.getCustomerName() + "' registered.");
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return customerDao.selectAll();
	}

	@Override
	public Customer getCustomerById(int customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCustomer(Customer customer) {
		if (customer.getCustomerId() == null || customer.getCustomerId() <= 0) {
            throw new RuntimeException("Update Failed: Invalid Customer ID.");
        }
        
        customerDao.update(customer);
        System.out.println("Service: Customer ID " + customer.getCustomerId() + " updated.");
		
	}

	@Override
	public void deleteCustomer(int customerId) {
		if (customerId <= 0) {
            throw new RuntimeException("Delete Failed: Invalid Customer ID.");
        }
        
        customerDao.delete(customerId);
        System.out.println("Service: Customer ID " + customerId + " deleted.");
		
	}

}
