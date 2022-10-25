package com.market.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.market.entity.User;
import com.market.jdbc.Connect;
import com.market.services.Md5;

public class AdminDAO {
	Connection conn = Connect.getConnect();

	public String checkSignInAdmin(User user, Md5 mh) {
		try {
			String sql = "select * from person.Administrator";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				if (user.getUserName().compareTo(rs.getString("username")) == 0
						&& mh.convertHashToString(user.getPassWord()).compareTo(rs.getString("password")) == 0) {
//					System.out.println("");
					return rs.getString("idAdmin");
				}
			}
		} catch (Exception e) {
			System.out.println("UserDAO.checkSignInAdmin()" + e);
		}
		return null;

	}

}
