package dao;

import java.util.List;
import model.Customer;

public interface CustomerDao {
    
  
    void add(Customer customer);
    
  
    List<Customer> selectAll();
    

    List<Customer> selectById(int customerId);
    

    void update(Customer customer);
    
 
    void delete(int customerId);
}