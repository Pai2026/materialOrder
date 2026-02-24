package service.impl;

import java.util.List;
import dao.BuyerDao;
import dao.impl.BuyerDaoImpl;
import model.Buyer;
import service.BuyerService;

public class BuyerServiceImpl implements BuyerService {
	
	private final BuyerDao buyerDao = new BuyerDaoImpl();
	
	@Override
	public boolean AddBuyer(Buyer buyer) {
		if(find_username(buyer.getUsername())) {
			System.out.println("帳號已存在，無法新增");
			return false;
		} else {
			buyerDao.add(buyer);
			System.out.println("註冊成功");
	        return true;
		}
	}

	@Override
	public Buyer find_buyer_by_username_and_password(String username,String password)
	{
		Buyer buyer=null;
		List<Buyer> l = buyerDao.selectByUsernameAndPassword(username, password);
		if(l != null && !l.isEmpty()) {
			return l.get(0); // 回傳第一筆找到的客戶
		}
		
		return null;		
	}

	@Override
	public boolean find_username(String username) {
		List<Buyer> l = buyerDao.selectByUsername(username);
		if(l != null && !l.isEmpty()) {
			return true;
		}
		return false;
	}
	
	

}
