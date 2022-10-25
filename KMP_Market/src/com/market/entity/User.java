package com.market.entity;

public class User {
	private String userName;
	private String passWord;
	private String confirmPass;

	public User(String userName, String passWord, String confirmPass) {
		this.userName = userName;
		this.passWord = passWord;
		this.confirmPass = confirmPass;
	}

	public User(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;

	}

	public User() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

}
