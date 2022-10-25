package com.market.entity;

public class Customer {
	private String idCus;
	private String Name;
	private String Phone;
	private String Address;
	private int Sex;
	private String Day;
	private String Month;
	private String Year;

	public Customer() {
		super();
	}

	public Customer(String name, String phone, String address, int sex, String day, String month, String year) {
		this.Name = name;
		this.Phone = phone;
		this.Address = address;
		this.Sex = sex;
		this.Day = day;
		this.Month = month;
		this.Year = year;
	}

	public Customer(String address) {
		super();
		Address = address;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public int getSex() {
		return Sex;
	}

	public void setSex(int sex) {
		Sex = sex;
	}

	public String getDay() {
		return Day;
	}

	public void setDay(String day) {
		Day = day;
	}

	public String getMonth() {
		return Month;
	}

	public void setMonth(String month) {
		Month = month;
	}

	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public String getIdCus() {
		return idCus;
	}

	public void setIdCus(String idCus) {
		this.idCus = idCus;
	}
}
