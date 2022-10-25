package com.market.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.market.jdbc.Connect;

public class CustomerDao {
	static Connection conn = Connect.getConnect();

	public String getnameCus(String string) {
		System.out.println("dsfasdfadfs");
		try {
			// Connection cnn = Connect.getConnect();
			String sql = "select * from virtual.v_showAllCus where idCustomer ='" + string + "'";
			PreparedStatement pt;
			pt = conn.prepareStatement(sql);
			ResultSet rs = pt.executeQuery();
			if (rs.next())
				return rs.getString("nameCustomer");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("da vo");
		return null;
	}

}
