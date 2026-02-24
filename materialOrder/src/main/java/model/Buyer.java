package model;

import java.io.Serializable;

public class Buyer implements Serializable {
	private Integer buyerId;
	private String username;
	private String password;
	private String name;
	private String country;
	
	public Buyer(int buyerId, String username, String password, String name, String country)
	{
		super();
		this.buyerId= buyerId;
		this.username=username;
		this.password=password;
		this.name=name;
		this.country=country;
	}
	
	public Buyer(String username, String password, String name, String country) {
        super();
        this.username = username;
        this.password = password;
        this.name = name;
        this.country = country;
    }
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
