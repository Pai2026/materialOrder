package dao;

import java.util.List;

import model.Buyer;

public interface BuyerDao {
	//c
	void add(Buyer b);
	
	//r
	List<Buyer> selectAll();
	List<Buyer> selectByUsername(String username);
	List<Buyer> selectByUsernameAndPassword(String username, String password);
	
	
	//u
	void update(Buyer b);
	//d
	void delete(int id);
}
