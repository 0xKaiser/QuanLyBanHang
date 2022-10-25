package com.market.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.market.entity.User;
import com.market.jdbc.Connect;
import com.market.services.Md5;

public class UserDAO {
	static Connection conn = Connect.getConnect();

	public String checkSignInUser(User user, Md5 mh) {
		try {
			String sql = "select * from person.Users";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				if (user.getUserName().compareTo(rs.getString("username")) == 0
						&& mh.convertHashToString(user.getPassWord()).compareTo(rs.getString("password")) == 0) {
					return rs.getString("iduser");
				}
			} // user.getPassWord()
		} catch (Exception e) {
			System.out.println("UserDAO.checkSignInUser()" + e);
		}
		return null;
	}

	public String checkPhone(String s) {
		try {
//			System.out.println("id s là " + s);
			String sql = "select * from person.Customer";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				// if(rs.getString("idCustomer").compareTo(s)==0 &&
				// rs.getString("address")!=null) {
				if (rs.getString("idCustomer").compareTo(s) == 0) {
					System.out.println("id cus " + rs.getString("idCustomer"));
					return rs.getString("idCustomer");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
