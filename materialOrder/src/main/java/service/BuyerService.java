package service;

import model.Buyer;

public interface BuyerService {
	boolean AddBuyer(Buyer buyer);
	Buyer find_buyer_by_username_and_password(String username,String password);
	boolean find_username(String username);

}
